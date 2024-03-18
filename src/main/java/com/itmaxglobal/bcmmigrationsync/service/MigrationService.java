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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import static com.itmaxglobal.bcmmigrationsync.util.Constants.JOB_DATE_FORMATTER;

@Service
@Slf4j
public class MigrationService {
    SessionRepository sessionRepository;
    ImeiRepository imeiRepository;
    ImsiMsisdnRepository imsiMsisdnRepository;

    @Value("${com.bcm.goLiveDate}")
    private String goLiveDateInStr;

    @Lazy
    @Autowired
    public MigrationService(ImeiRepository imeiRepository, ImsiMsisdnRepository imsiMsisdnRepository, SessionRepository sessionRepository){
        this.sessionRepository = sessionRepository;
        this.imsiMsisdnRepository = imsiMsisdnRepository;
        this.imeiRepository = imeiRepository;
    }
    public Account startMigration(Account account){
        LocalDateTime goLiveDate = LocalDateTime.parse(goLiveDateInStr, DateTimeFormatter.ofPattern(JOB_DATE_FORMATTER));

        Optional<Imei> imei = imeiRepository.findFirstByImeiOrderByCreatedAtDesc(account.getImei());
        Optional<ImsiMsisdn> imsiMsisdn = imsiMsisdnRepository.findFirstByImsiAndMsisdnOrderByCreatedAtDesc(account.getImsi(), account.getMsisdn());
        Optional<Session> session = sessionRepository.findFirstByImeiAndImsiAndMsisdnAndCreatedAtGreaterThanOrderByCreatedAtDesc(account.getImei(), account.getImsi(), account.getMsisdn(), goLiveDate);

        if(imei.isPresent()){
            log.info("Already present imei-[{}]", imei.get());
            Imei updatedImei = imeiRepository.save(ImeiMapper.existingImeiMap(imei.get(), account));
            log.info("updated imei-[{}]", updatedImei);
        } else {
            log.info("Saving new imei");
            Imei newImei = imeiRepository.save(ImeiMapper.imeiMap(account));
            log.info("New imei saved-[{}]", newImei);
        }

        if(imsiMsisdn.isPresent()){
            log.info("Already present imsi-[{}]", imsiMsisdn.get());
            ImsiMsisdn updatedImsi = imsiMsisdnRepository.save(ImsiMsisdnMapper.existingImsiMap(imsiMsisdn.get(), account));
            log.info("updated imsi-[{}]", updatedImsi);
        } else {
            log.info("Saving new imsi");
            ImsiMsisdn newImsi = imsiMsisdnRepository.save(ImsiMsisdnMapper.imsiMap(account));
            log.info("New imsi saved-[{}]", newImsi);
        }

        if(session.isPresent()){
            if(Objects.nonNull(session.get().getLastActivityDate()) && Objects.nonNull(account.getLastActivityDate())){
                LocalDateTime lastActivityDateFromSession = session.get().getLastActivityDate()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if(!this.checkDateDifferenceInMinutes(account.getLastActivityDate(), lastActivityDateFromSession)){
                    log.info("Already present session-[{}]", session.get());
                    Session updatedSession = sessionRepository.save(SessionMapper.existingSessionMap(session.get(), account));
                    log.info("updated session-[{}]", updatedSession);
                }
            }
        } else {
            log.info("Saving new session");
            Session newSession = sessionRepository.save(SessionMapper.sessionMap(account));
            log.info("New session saved-[{}]", newSession);
        }
        return account;
    }

    private boolean checkDateDifferenceInMinutes(LocalDateTime dateFromAccount, LocalDateTime dateFromSession){
        Duration duration = Duration.between(dateFromAccount, dateFromSession);
        return duration.toMinutes() <= 15 && duration.toMinutes() >= -15;
    }
}
