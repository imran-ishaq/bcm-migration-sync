package com.itmaxglobal.bcmmigrationsync.service;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.*;
import com.itmaxglobal.bcmmigrationsync.bcmv2.mapper.*;
import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.*;
import com.itmaxglobal.bcmmigrationsync.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

import static com.itmaxglobal.bcmmigrationsync.util.Constants.*;

@Service
@Slf4j
public class MigrationService {

    @Value("${com.bcm.email-send-time}")
    private Double emailSendTime;

    private final SessionRepository sessionRepository;
    private final ImsiMsisdnRepository imsiMsisdnRepository;
    private final SessionHistoryRepository sessionHistoryRepository;
    private final BrandModelRepository brandModelRepository;
    private final ImsiOperatorConfigRepository imsiOperatorConfigRepository;
    private final EmailUtil emailUtil;
    private Instant lastEmailSentTime;
    private boolean emailSentFlag = true;
    private boolean firstEmailSentFlag = false;

    @Autowired
    ApplicationContext appContext;

    @Lazy
    @Autowired
    public MigrationService(ImsiMsisdnRepository imsiMsisdnRepository, SessionRepository sessionRepository, SessionHistoryRepository sessionHistoryRepository, BrandModelRepository brandModelRepository, ImsiOperatorConfigRepository imsiOperatorConfigRepository, EmailUtil emailUtil){
        this.sessionRepository = sessionRepository;
        this.imsiMsisdnRepository = imsiMsisdnRepository;
        this.sessionHistoryRepository = sessionHistoryRepository;
        this.brandModelRepository = brandModelRepository;
        this.imsiOperatorConfigRepository = imsiOperatorConfigRepository;
        this.emailUtil = emailUtil;
    }
    public Account startMigration(Account account) {
        try {
            lastEmailSentTime = Instant.now();
            Optional<ImsiOperatorConfig> imsiOperatorConfig;
            Optional<BrandModel> brandModel = brandModelRepository.findByTacNumber(account.getImei().substring(0, 8));
            Optional<ImsiMsisdn> imsiMsisdn = imsiMsisdnRepository.findFirstByImsiAndMsisdnOrderByCreatedAtDesc(Long.parseLong(account.getImsi()), account.getMsisdn());
            Optional<Session> session = sessionRepository.findFirstByImei(account.getImei());
            Integer brandModelId = 0;
            int accountOperator = 0;

            if(Boolean.TRUE.equals(account.isRoaming())){
                accountOperator = account.getOperator();
            } else {
                imsiOperatorConfig = imsiOperatorConfigRepository.findByImsiPrefix(account.getImsi().substring(0, 5));
                accountOperator = imsiOperatorConfig.map(ImsiOperatorConfig::getOperatorId).orElseGet(account::getOperator);
            }

            if (brandModel.isPresent()){
                if(!Objects.deepEquals(brandModel.get().getBrand(), account.getBrand())
                        || !Objects.deepEquals(brandModel.get().getModel(), account.getModel())) {
                    brandModel.get().setBrand(account.getBrand());
                    brandModel.get().setModel(account.getModel());
                    brandModelRepository.save(brandModel.get());
                }
                brandModelId = brandModel.get().getBrandModelId();
            } else {
                brandModelId = brandModelRepository.save(BrandModelMapper.toEntity(account)).getBrandModelId();
            }

            if(imsiMsisdn.isPresent()){
                imsiMsisdnRepository.save(ImsiMsisdnMapper.existingImsiMap(imsiMsisdn.get(), account, accountOperator));
            } else {
                imsiMsisdnRepository.save(ImsiMsisdnMapper.imsiMap(account, accountOperator));
            }

            if(session.isPresent()){
                if (account.getImsi().equals(session.get().getImsi().toString())){
                    sessionRepository.save(SessionMapper.toUpdate(session.get(), account, accountOperator, brandModelId, false));
                } else {
                    sessionHistoryRepository.save(SessionHistoryMapper.toEntity(session.get()));
                    sessionRepository.save(SessionMapper.toUpdate(session.get(), account, accountOperator, brandModelId, true));
                }
            } else {
                sessionRepository.save(SessionMapper.toEntity(account, accountOperator, brandModelId));
            }
            lastEmailSentTime = Instant.now();
            return account;
        } catch (Exception ex){
            log.info("Exception from MigrationService()");
            log.error(ex.getMessage());
            if(this.timeDifferenceInMinutes(lastEmailSentTime, Instant.now()) && !emailSentFlag){
                emailSentFlag = true;
            }
            if (ex instanceof RecoverableDataAccessException || ex instanceof CannotCreateTransactionException ||
                    ex instanceof DataAccessResourceFailureException || ex instanceof TransactionException) {
                if(!firstEmailSentFlag){
                    this.sendEmail(ex);
                } else if(this.timeDifferenceInMinutes(lastEmailSentTime, Instant.now())) {
                    this.sendEmail(ex);
                }
            }
        }
        return null;
    }

    private boolean checkDateDifferenceInMinutes(LocalDateTime dateFromAccount, LocalDateTime dateFromSession){
        Duration duration = Duration.between(dateFromAccount, dateFromSession);
        return duration.toMinutes() <= 15 && duration.toMinutes() >= -15;
    }

    private boolean timeDifferenceInMinutes(Instant previousTime, Instant currentTime){
        return Duration.between(previousTime, currentTime).toMinutes() > emailSendTime;
    }

    private void sendEmail(Exception ex){
        synchronized (this) {
            if (emailSentFlag) {
                emailUtil.sendEmail(EMAIL_SUBJECT, EMAIL_TEMPLATE_NAME, ex);
//                System.exit(SpringApplication.exit(appContext, () -> 0));
                lastEmailSentTime = Instant.now();
                emailSentFlag = false;
                firstEmailSentFlag = true;
            }
        }
    }
}