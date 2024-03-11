package com.itmaxglobal.bcmmigrationsync.batchProcessing.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CustomItemReadListener<T> implements ItemReadListener<T> {
    @Override
    public void onReadError(Exception ex) {
        log.error("Error occurred while reading item-[{}]", ex.getMessage());
    }
}
