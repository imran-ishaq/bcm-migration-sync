package com.itmaxglobal.bcmmigrationsync.batchProcessing;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.service.MigrationService;
import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.SessionEntityV1;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountProcessor implements ItemProcessor<Account, Account> {
    private final MigrationService migrationService;

    @Autowired
    public AccountProcessor(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @Override
    public Account process(Account account) {
        return migrationService.startMigration(account);
    }
}
