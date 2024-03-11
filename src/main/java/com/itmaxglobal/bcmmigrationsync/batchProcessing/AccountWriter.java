package com.itmaxglobal.bcmmigrationsync.batchProcessing;


import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv1.repository.AccountRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.itmaxglobal.bcmmigrationsync.util.Constants.UPDATE_ACCOUNT_ACTIVITY_QUERY;

@Component
public class AccountWriter implements ItemWriter<Account> {

    private final JdbcTemplate jdbcTemplate;

    private final AccountRepository accountRepository;

    @Lazy
    @Autowired
    public AccountWriter(AccountRepository accountRepository, JdbcTemplate jdbcTemplate) {
        this.accountRepository = accountRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends Account> chunk) {
        chunk.getItems().forEach(account -> accountRepository.updateIsMigrated(account.getId()));
        chunk.getItems().forEach(account -> jdbcTemplate.update(UPDATE_ACCOUNT_ACTIVITY_QUERY, account.getId()));
    }
}
