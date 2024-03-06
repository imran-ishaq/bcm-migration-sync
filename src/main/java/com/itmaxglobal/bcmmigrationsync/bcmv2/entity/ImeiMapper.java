package com.itmaxglobal.bcmmigrationsync.bcmv2.entity;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatus;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

public class ImeiMapper {

    public static Imei map(Account account){
        Imei imei = new Imei();
        imei.setId(account.getId());
        imei.setImei(account.getImei());
        imei.setModelType(account.getModelType());
        imei.setCounterfeit(account.isCounterfeit() ? 1 : 0);
        imei.setUpdatedAt(account.getUpdatedDate());
        imei.setRegisteringDate(account.getRegisteringDate());
        imei.setCreatedAt(account.getCreatedDate());
        imei.setImeiQuantitySupport(account.getImeiQuantitySupport());
        imei.setImeiStatus(DeviceStatus.valueOf(account.getAccountStatus()));
        imei.setBrand(account.getBrand());
        imei.setModel(account.getModel());
        imei.setSimSwapCounter(account.getSimSwapCounter());
        imei.setStatusUpdateDate(account.getStatusUpdateDate()==null? new java.util.Date():Date.from(account.getStatusUpdateDate().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        imei.setIsCloned(account.getIsCloned());
        return imei;
    }
}
