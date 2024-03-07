package com.itmaxglobal.bcmmigrationsync.batchProcessing.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CustomJobExecutionListener implements JobExecutionListener {

    private long start;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        start = System.currentTimeMillis();
        log.info("Starting job execution-[{}]", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job execution completed-[{}]-millis-[{}]",  jobExecution.getStatus(), System.currentTimeMillis() - start);
    }
}