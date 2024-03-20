package com.itmaxglobal.bcmmigrationsync.bcmv2.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Imei;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatus;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ImeiMapper {

    public static Imei imeiMap(Account account){
        Imei imei = new Imei();
        imei.setImei(account.getImei());
        imei.setModelType(account.getModelType());
        imei.setCounterfeit(account.isCounterfeit() ? 1 : 0);
        imei.setUpdatedAt(account.getUpdatedDate());
        imei.setRegisteringDate(account.getRegisteringDate());
        imei.setCreatedAt(account.getCreatedDate());
        imei.setImeiQuantitySupport(account.getImeiQuantitySupport());
        imei.setIsStolen(account.getBlocked());
        imei.setImeiStatus(account.getAccountStatus());
        imei.setBrand(account.getBrand());
        imei.setModel(account.getModel());
        imei.setSimSwapCounter(account.getSimSwapCounter());
        imei.setStatusUpdateDate(account.getStatusUpdateDate()==null? null :Date.from(account.getStatusUpdateDate().atZone(ZoneId.systemDefault()).toInstant()));
        imei.setIsCloned(account.getIsCloned() == 1);
        return imei;
    }

    public static Imei existingImeiMap(Imei existingImei, Account account){
        Imei imei = imeiMap(account);
        imei.setId(existingImei.getId());
        return imei;
    }
}
