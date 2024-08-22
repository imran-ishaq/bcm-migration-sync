package com.itmaxglobal.bcmmigrationsync.bcmv1.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatus;
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
        account.setModelType(rs.getString("model_type"));
        account.setCounterfeit(rs.getBoolean("counterfeit"));
        account.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        account.setRegisteringDate(rs.getTimestamp("registering_date").toLocalDateTime());
        account.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
        account.setRoaming(rs.getBoolean("roaming"));
        account.setImeiQuantitySupport(rs.getInt("imei_quantity_support"));
        account.setBrand(rs.getString("brand"));
        account.setModel(rs.getString("model"));
        account.setSimSwapCounter(rs.getInt("sim_swap_counter"));
        account.setOperator(rs.getInt("operator"));
        account.setBlocked(rs.getBoolean("blocked"));
        account.setAccountStatus(rs.getString("account_status") == null ? DeviceStatus.Green.getCode() : rs.getInt("account_status"));
        account.setStatusUpdateDate(rs.getTimestamp("status_update_date") == null? null: rs.getTimestamp("status_update_date").toLocalDateTime());
        account.setIsCloned(rs.getInt("is_cloned"));
        account.setAccountOperator(rs.getInt("account_operator"));
        account.setLastActivityDate(rs.getTimestamp("last_activity_date_from_account_activity") == null? null: rs.getTimestamp("last_activity_date_from_account_activity").toLocalDateTime());
        account.setIsMigrated(rs.getBoolean("is_migrated"));

        return account;
    }
}
