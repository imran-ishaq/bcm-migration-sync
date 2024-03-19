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
        return account;
    }

    private boolean checkDateDifferenceInMinutes(LocalDateTime dateFromAccount, LocalDateTime dateFromSession){
        Duration duration = Duration.between(dateFromAccount, dateFromSession);
        return duration.toMinutes() <= 15 && duration.toMinutes() >= -15;
    }
}
