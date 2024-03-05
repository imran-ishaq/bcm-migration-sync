package com.itmaxglobal.bcmmigrationsync.bcmv2.entity;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.SessionEntityV1;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatus;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class SessionMapper{

    public Session map(SessionEntityV1 sessionEntityV1){
        Session session = new Session();
        session.setId(sessionEntityV1.getId());
        session.setImei(sessionEntityV1.getImei() == null? "" : sessionEntityV1.getImei());
        session.setImsi(sessionEntityV1.getImsi() == null? "" : sessionEntityV1.getImsi());
        session.setMsisdn(sessionEntityV1.getMsisdn() == null? "" : sessionEntityV1.getMsisdn());
        session.setCreatedAt(sessionEntityV1.getCreatedAt()  == null? LocalDateTime.now() : sessionEntityV1.getCreatedAt());
        session.setModelType(session.getModelType() == null? "" : sessionEntityV1.getModelType());
        session.setCounterfeit(sessionEntityV1.getCounterfeit() == null? 0 : sessionEntityV1.getCounterfeit());
        session.setRegisteringDate(sessionEntityV1.getRegisteringDate() == null? LocalDateTime.now() : sessionEntityV1.getRegisteringDate());
        session.setRoaming(sessionEntityV1.getRoaming() == null? 0 : sessionEntityV1.getRoaming());
        session.setImeiQuantitySupport(sessionEntityV1.getImeiQuantitySupport()  == null? 0 : sessionEntityV1.getImeiQuantitySupport());
        session.setBrandName(sessionEntityV1.getBrandName()==null? "" : sessionEntityV1.getBrandName());
        session.setModel(sessionEntityV1.getModel() == null? "" : sessionEntityV1.getModel());
        session.setSimSwapCounter(sessionEntityV1.getSimSwapCounter() == null? 0 : sessionEntityV1.getSimSwapCounter());
        session.setOperator(sessionEntityV1.getOperator() == null? 0 : sessionEntityV1.getOperator());
        session.setIsStolen(sessionEntityV1.getIsStolen() == null? true : sessionEntityV1.getIsStolen());
        session.setImeiStatus(sessionEntityV1.getImeiStatus() == null? DeviceStatus.Green : sessionEntityV1.getImeiStatus());
        session.setStatusUpdateDate(sessionEntityV1.getStatusUpdateDate() == null? Date.from(Instant.now()) : sessionEntityV1.getStatusUpdateDate());
        session.setIsCloned(sessionEntityV1.getIsCloned()  == null ? true : sessionEntityV1.getIsCloned());
        session.setAccountOperator(sessionEntityV1.getAccountOperator()==null ? 0 : sessionEntityV1.getAccountOperator());
        session.setLastActivityDate(sessionEntityV1.getLastActivityDate());

        return session;
    }
}
