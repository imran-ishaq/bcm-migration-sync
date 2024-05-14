package com.itmaxglobal.bcmmigrationsync.service;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Imei;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.ImsiMsisdn;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import com.itmaxglobal.bcmmigrationsync.bcmv2.mapper.ImeiMapper;
import com.itmaxglobal.bcmmigrationsync.bcmv2.mapper.ImsiMsisdnMapper;
import com.itmaxglobal.bcmmigrationsync.bcmv2.mapper.SessionMapper;
import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.ImeiRepository;
import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.ImsiMsisdnRepository;
import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.SessionRepository;
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
    private final ImeiRepository imeiRepository;
    private final ImsiMsisdnRepository imsiMsisdnRepository;
    private final EmailUtil emailUtil;
    private Instant lastEmailSentTime;
    private boolean emailSentFlag = true;
    private boolean firstEmailSentFlag = false;

    @Autowired
    ApplicationContext appContext;

    @Lazy
    @Autowired
    public MigrationService(ImeiRepository imeiRepository, ImsiMsisdnRepository imsiMsisdnRepository, SessionRepository sessionRepository, EmailUtil emailUtil){
        this.sessionRepository = sessionRepository;
        this.imsiMsisdnRepository = imsiMsisdnRepository;
        this.imeiRepository = imeiRepository;
        this.emailUtil = emailUtil;
    }
    public Account startMigration(Account account) {
        try {
            lastEmailSentTime = Instant.now();
            Optional<Imei> imei = imeiRepository.findFirstByImeiOrderByCreatedAtDesc(account.getImei());
            Optional<ImsiMsisdn> imsiMsisdn = imsiMsisdnRepository.findFirstByImsiAndMsisdnOrderByCreatedAtDesc(account.getImsi(), account.getMsisdn());
            Optional<Session> session = sessionRepository.findFirstByImeiAndImsiAndMsisdnOrderByCreatedAtDesc(account.getImei(), account.getImsi(), account.getMsisdn());

            if(imei.isPresent()){
                imeiRepository.save(ImeiMapper.existingImeiMap(imei.get(), account));
            } else {
                imeiRepository.save(ImeiMapper.imeiMap(account));
            }

            if(imsiMsisdn.isPresent()){
                imsiMsisdnRepository.save(ImsiMsisdnMapper.existingImsiMap(imsiMsisdn.get(), account));
            } else {
                imsiMsisdnRepository.save(ImsiMsisdnMapper.imsiMap(account));
            }

            if(session.isPresent()){
                if(Objects.nonNull(session.get().getLastActivityDate()) && Objects.nonNull(account.getLastActivityDate())){
                    LocalDateTime lastActivityDateFromSession = session.get().getLastActivityDate()
                            .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if(!this.checkDateDifferenceInMinutes(account.getLastActivityDate(), lastActivityDateFromSession)){
                        sessionRepository.save(SessionMapper.existingSessionMap(session.get(), account));
                    }
                }
            } else {
                sessionRepository.save(SessionMapper.sessionMap(account));
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