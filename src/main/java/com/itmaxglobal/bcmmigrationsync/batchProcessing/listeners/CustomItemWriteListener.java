package com.itmaxglobal.bcmmigrationsync.batchProcessing.listeners;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CustomItemWriteListener<T> implements ItemWriteListener<T> {

    private long start;

    @Override
    public void beforeWrite(@NotNull Chunk<? extends T> items) {
        start = System.currentTimeMillis();
        ItemWriteListener.super.beforeWrite(items);
        log.info("Writing items-[{}]", items.getItems().size());
    }

    @Override
    public void afterWrite(@NotNull Chunk<? extends T> items) {
        ItemWriteListener.super.afterWrite(items);
        log.info("Items written successfully-[{}]-millis-[{}]", items.size(), System.currentTimeMillis() - start);
    }

    @Override
    public void onWriteError(@NotNull Exception ex, @NotNull Chunk<? extends T> items) {
        ItemWriteListener.super.onWriteError(ex, items);
        log.error("Error occurred while writing items-[{}]", ex.getMessage());
    }
}