package com.itmaxglobal.bcmmigrationsync.batchProcessing.listeners;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CustomChunkListener implements ChunkListener {

    private long start;

    @Override
    public void beforeChunk(@NotNull ChunkContext context) {
        start = System.currentTimeMillis();
        long readCount = context.getStepContext().getStepExecution().getReadCount();
        if(readCount != 0){
            log.info("Number of items read-[{}]", readCount);
        }
    }

    @Override
    public void afterChunk(@NotNull ChunkContext context) {
        log.info("Number of items write-[{}]-millis-[{}] ", context.getStepContext().getStepExecution().getWriteCount(), System.currentTimeMillis() - start);
    }
}