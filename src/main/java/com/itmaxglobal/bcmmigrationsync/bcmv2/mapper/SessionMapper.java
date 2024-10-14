package com.itmaxglobal.bcmmigrationsync.bcmv2.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatus;

import java.sql.Date;
import java.time.ZoneId;

public class SessionMapper{

    public static Session toEntity(Account account, int accountOperator, Integer brandModelId){
        Session session = new Session();
        session.setImei(account.getImei());
        session.setImsi(Long.parseLong(account.getImsi()));
        session.setMsisdn(account.getMsisdn());
        fillSession(session, account, accountOperator, brandModelId);
        return session;
    }

    public static Session toUpdate(Session existingSession, Account account, int accountOperator, Integer brandModelId, boolean imsiMsisdnFlag){
        if(imsiMsisdnFlag){
            existingSession.setImsi(Long.parseLong(account.getImsi()));
            existingSession.setMsisdn(account.getMsisdn());
        }
        fillSession(existingSession, account, accountOperator, brandModelId);
        return existingSession;
    }

    private static void fillSession(Session session, Account account, int accountOperator, Integer brandModelId){
        session.setModelType(account.getModelType());
        session.setCounterfeit(account.isCounterfeit()? 1 : 0);
        session.setCreatedAt(account.getCreatedDate());
        session.setRegisteringDate(account.getRegisteringDate());
        session.setUpdatedAt(account.getUpdatedDate());
        session.setRoaming(account.isRoaming() ? 1 : 0);
        session.setImeiQuantitySupport(account.getImeiQuantitySupport());
        session.setBrandModelId(brandModelId);
        session.setSimSwapCounter(account.getSimSwapCounter());
        session.setOperator(account.getOperator());
        session.setIsStolen(account.getBlocked());
        session.setImeiStatus(DeviceStatus.valueOf(account.getAccountStatus()));
        session.setStatusUpdateDate(account.getStatusUpdateDate() == null ? null : account.getStatusUpdateDate());
        session.setIsCloned(account.getIsCloned() == 1);
        session.setAccountOperator(accountOperator);
        session.setLastActivityDate(account.getLastActivityDate() == null ? null : account.getLastActivityDate());
    }
}
