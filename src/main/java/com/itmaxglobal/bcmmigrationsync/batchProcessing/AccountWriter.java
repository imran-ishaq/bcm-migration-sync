package com.itmaxglobal.bcmmigrationsync.batchProcessing;


import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class AccountWriter implements ItemWriter<Account> {



    @Override
    public void write(Chunk<? extends Account> chunk) {

    }
}
