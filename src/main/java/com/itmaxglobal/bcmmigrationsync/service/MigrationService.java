package com.itmaxglobal.bcmmigrationsync.service;

import com.itmaxglobal.bcmmigrationsync.bcmv2.repository.SessionRepository;
import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.SessionEntityV1;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.SessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MigrationService {
    @Autowired
    SessionMapper sessionMapper;
    @Lazy
    @Autowired
    SessionRepository sessionRepository;
    public SessionEntityV1 startMigration(SessionEntityV1 session){
        System.out.println("Started Migrating : "+session.toString());
        sessionRepository.save(sessionMapper.map(session));
        System.out.println("Successfully Migrated: "+session.toString());
        return session;
    }

}
