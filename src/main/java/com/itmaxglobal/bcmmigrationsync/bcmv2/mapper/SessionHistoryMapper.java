package com.itmaxglobal.bcmmigrationsync.bcmv2.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.SessionHistory;

public class SessionHistoryMapper {

    public static SessionHistory toEntity(Account account){
        SessionHistory sessionHistory = new SessionHistory();

        sessionHistory.setImei(account.getImei());
        sessionHistory.setImsi(Long.parseLong(account.getImsi()));
        sessionHistory.setMsisdn(account.getMsisdn());
        sessionHistory.setCreatedDate(account.getCreatedDate());
        sessionHistory.setUpdatedDate(account.getUpdatedDate());
        sessionHistory.setLastActivityDate(account.getLastActivityDate());
        return sessionHistory;
    }

    public static SessionHistory toEntity(Session session){
        SessionHistory sessionHistory = new SessionHistory();

        sessionHistory.setImei(session.getImei());
        sessionHistory.setImsi(session.getImsi());
        sessionHistory.setMsisdn(session.getMsisdn());
        sessionHistory.setCreatedDate(session.getCreatedAt());
        sessionHistory.setUpdatedDate(session.getUpdatedAt());
        sessionHistory.setLastActivityDate(session.getLastActivityDate());

        return sessionHistory;
    }

}
