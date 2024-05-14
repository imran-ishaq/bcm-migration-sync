package com.itmaxglobal.bcmmigrationsync.bcmv2.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.ImsiMsisdn;

public class ImsiMsisdnMapper {

    public static ImsiMsisdn imsiMap(Account account){
        ImsiMsisdn imsiMsisdn = new ImsiMsisdn();
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

    public static ImsiMsisdn existingImsiMap(ImsiMsisdn existingImsi, Account account){
        ImsiMsisdn imsiMsisdn = imsiMap(account);
        imsiMsisdn.setId(existingImsi.getId());
        return imsiMsisdn;
    }
}
