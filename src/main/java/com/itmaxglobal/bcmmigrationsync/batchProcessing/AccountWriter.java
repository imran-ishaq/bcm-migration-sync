package com.itmaxglobal.bcmmigrationsync.batchProcessing;


import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountWriter implements ItemWriter<Account> {
    @Lazy
    @Autowired
    JdbcTemplate jdbcTemplate;
    String updateQuery = "UPDATE billing.account SET is_migrated = ? WHERE id = ?";
    @Override
    public void write(Chunk<? extends Account> chunk) {
        List<? extends Account> accounts = chunk.getItems();
        for(Account account: accounts){
            jdbcTemplate.update(updateQuery, 1, account.getId());
        }
    }
}
