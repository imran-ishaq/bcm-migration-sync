package com.itmaxglobal.bcmmigrationsync.batchProcessing.listeners;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CustomItemProcessListener<T, S> implements ItemProcessListener<T, S> {

    @Override
    public void onProcessError(@NotNull T item, Exception ex) {
        log.error("Error occurred while processing item-[{}]", ex.getMessage());
    }
}