package com.itmaxglobal.bcmmigrationsync.service;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.ImeiMapper;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.ImsiMsisdnMapper;
import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.ImeiRepository;
import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.ImsiMsisdnRepository;
import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.SessionRepository;
import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.SessionEntityV1;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.SessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MigrationService {
    SessionRepository sessionRepository;
    ImeiRepository imeiRepository;
    ImsiMsisdnRepository imsiMsisdnRepository;
    @Lazy
    @Autowired
    public MigrationService(ImeiRepository imeiRepository, ImsiMsisdnRepository imsiMsisdnRepository, SessionRepository sessionRepository){
        this.sessionRepository = sessionRepository;
        this.imsiMsisdnRepository = imsiMsisdnRepository;
        this.imeiRepository = imeiRepository;
    }
    public Account startMigration(Account account){
        System.out.println("Started Migrating : "+account.toString());
        sessionRepository.save(SessionMapper.map(account));
        imeiRepository.save(ImeiMapper.map(account));
        imsiMsisdnRepository.save(ImsiMsisdnMapper.map(account));
        System.out.println("Successfully Migrated: "+ account);
        return account;
    }

}
