package com.itmaxglobal.bcmmigrationsync.bcmv1.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.SessionEntityV1;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();

        account.setId(rs.getLong("id"));
        account.setImei(rs.getString("imei"));
        account.setImsi(rs.getString("imsi"));
        account.setMsisdn(rs.getString("msisdn"));
        account.setModelType(rs.getString("modelType"));
        account.setCounterfeit(rs.getBoolean("counterfeit"));
        account.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());
        account.setRegisteringDate(rs.getTimestamp("registeringDate").toLocalDateTime());
        account.setUpdatedDate(rs.getTimestamp("updatedDate").toLocalDateTime());
        account.setRoaming(rs.getBoolean("roaming"));
        account.setImeiQuantitySupport(rs.getInt("imeiQuantitySupport"));
        account.setBrand(rs.getString("brand"));
        account.setModel(rs.getString("model"));
        account.setSimSwapCounter(rs.getInt("simSwapCounter"));
        account.setOperator(rs.getInt("operator"));
        account.setAccountStatus(rs.getInt("accountStatus"));
        account.setStatusUpdateDate(rs.getTimestamp("statusUpdateDate").toLocalDateTime());
        account.setIsCloned(rs.getBoolean("isCloned"));
        account.setAccountOperator(rs.getInt("accountOperator"));
        account.setLastActivityDate(rs.getTimestamp("lastActivityDate").toLocalDateTime());


        return account;
    }
}
