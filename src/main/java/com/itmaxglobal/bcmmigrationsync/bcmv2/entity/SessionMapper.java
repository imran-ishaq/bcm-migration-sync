package com.itmaxglobal.bcmmigrationsync.bcmv2.entity;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatus;
import org.springframework.stereotype.Component;

import java.sql.Date;

public class SessionMapper{

    public static Session map(Account account){
        Session session = new Session();
        session.setId(account.getId());
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
        session.setStatusUpdateDate(Date.from(account.getStatusUpdateDate().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        session.setIsCloned(account.getIsCloned());
        session.setAccountOperator(account.getAccountOperator());
        session.setLastActivityDate(Date.from(account.getLastActivityDate().atZone(java.time.ZoneId.systemDefault()).toInstant()));

        return session;
    }
}
