package com.itmaxglobal.bcmmigrationsync.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/migration")
@Slf4j
public class MigrationController {
    private final JobLauncher jobLauncher;
    private final Job runBatchJob;

    @Autowired
    public MigrationController(JobLauncher jobLauncher, Job runBatchJob) {
        this.jobLauncher = jobLauncher;
        this.runBatchJob = runBatchJob;
    }

    @PostMapping(value = "/start")
    public void startMigration(){
        try {
            Map<String, JobParameter<?>> maps = new HashMap<>();
            maps.put("time", new JobParameter<>(System.currentTimeMillis(), Long.class));
            JobParameters parameters = new JobParameters(maps);
            Instant start = Instant.now();
            log.info("Batch processing is start...");
            JobExecution jobExecution = jobLauncher.run(runBatchJob, parameters);
            log.info("Batch processing startTime-[{}] endTime-[{}] status-[{}]", jobExecution.getStartTime(), jobExecution.getEndTime(), jobExecution.getStatus());

            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            log.info("Batch Job Time Execution-[" + duration.getSeconds() + "s]");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
