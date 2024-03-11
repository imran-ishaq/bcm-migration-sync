package com.itmaxglobal.bcmmigrationsync.bcmv2.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatus;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZoneId;

public class SessionMapper{

    public static Session sessionMap(Account account){
        Session session = new Session();
        session.setImei(account.getImei());
        session.setImsi(account.getImsi());
        session.setMsisdn(account.getMsisdn());
        session.setCreatedAt(account.getCreatedDate());
        session.setModelType(account.getModelType());
        session.setCounterfeit(account.isCounterfeit()? 1 : 0);
        session.setUpdatedAt(account.getUpdatedDate());
        session.setRegisteringDate(account.getRegisteringDate());
        session.setRoaming(account.isRoaming() ? 1 : 0);
        session.setImeiQuantitySupport(account.getImeiQuantitySupport());
        session.setBrandName(account.getBrand());
        session.setModel(account.getModel());
        session.setSimSwapCounter(account.getSimSwapCounter());
        session.setOperator(account.getOperator());
        session.setImeiStatus(DeviceStatus.valueOf(account.getAccountStatus()));
        session.setStatusUpdateDate(account.getStatusUpdateDate()==null? null : Date.from(account.getStatusUpdateDate().atZone(ZoneId.systemDefault()).toInstant()));
        session.setIsCloned(account.getIsCloned());
        session.setAccountOperator(account.getAccountOperator());
        session.setLastActivityDate(account.getLastActivityDate()==null? null:Date.from(account.getLastActivityDate().atZone(ZoneId.systemDefault()).toInstant()));

        return session;
    }

    public static Session existingSessionMap(Session existingSession, Account account){
        Session session = sessionMap(account);
        session.setId(existingSession.getId());
        return session;
    }
}
