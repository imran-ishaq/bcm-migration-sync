package com.itmaxglobal.bcmmigrationsync.bcmv2.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.ImsiMsisdn;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class ImsiMsisdnMapper {

    public static ImsiMsisdn map(Account account){
        ImsiMsisdn imsiMsisdn = new ImsiMsisdn();
        imsiMsisdn.setId(account.getId());
        imsiMsisdn.setMsisdn(account.getMsisdn());
        imsiMsisdn.setImsi(account.getImsi());
        imsiMsisdn.setOperator(account.getOperator());
        imsiMsisdn.setCreatedAt(account.getCreatedDate());
        imsiMsisdn.setAccountOperator(account.getAccountOperator());
        imsiMsisdn.setRoaming(account.isRoaming());
        imsiMsisdn.setRegisteringDate(account.getRegisteringDate());
        imsiMsisdn.setUpdatedAt(account.getUpdatedDate());
        return imsiMsisdn;
    }
}
