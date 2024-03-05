package com.itmaxglobal.bcmmigrationsync.bcmv1.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.SessionEntityV1;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


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

        return account;
    }
}
