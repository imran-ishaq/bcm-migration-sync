package com.itmaxglobal.bcmmigrationsync.batchProcessing.listeners;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CustomStepExecutionListener implements StepExecutionListener {

    private long start;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        start = System.currentTimeMillis();
        log.info("Starting step execution-[{}]",stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(@NotNull StepExecution stepExecution) {
        log.info("Step execution completed with status-[{}]-millis-[{}]", stepExecution.getStatus(), System.currentTimeMillis() - start);
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
