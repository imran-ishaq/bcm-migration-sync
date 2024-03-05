package com.itmaxglobal.bcmmigrationsync.batchProcessing;


import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.service.BatchQueryService;
import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.SessionEntityV1;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountReader {

    private final BatchQueryService batchQueryService;

    @Autowired
    public AccountReader(BatchQueryService batchQueryService) {
        this.batchQueryService = batchQueryService;
    }

    @Bean
    @StepScope
    public ItemReader<Account> reader() {
        return this.batchQueryService.executeSessionQuery();
    }

}
