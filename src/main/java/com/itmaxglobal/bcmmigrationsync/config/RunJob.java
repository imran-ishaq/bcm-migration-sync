package com.itmaxglobal.bcmmigrationsync.config;

import com.itmaxglobal.bcmmigrationsync.util.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RunJob {

    private final JobLauncher jobLauncher;
    private final Job runBatchJob;

    @Autowired
    ApplicationContext applicationContext;


    @Value("${spring.batch.jobCheck}")
    private boolean jobCheck;

    @Autowired
    public RunJob(JobLauncher jobLauncher, Job runBatchJob) {
        this.jobLauncher = jobLauncher;
        this.runBatchJob = runBatchJob;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startMigrationJob() {
        while (jobCheck) {
            try {
                log.info("Batch processing is start...");

                Map<String, JobParameter<?>> maps = new HashMap<>();
                maps.put("time", new JobParameter<>(System.currentTimeMillis(), Long.class));

                JobParameters parameters = new JobParameters(maps);
                log.info("Migration job parameters-[{}]", parameters);
                Instant start = Instant.now();

                JobExecution jobExecution = jobLauncher.run(runBatchJob, parameters);
                log.info("Batch processing startTime-[{}] endTime-[{}] status-[{}]", jobExecution.getStartTime(), jobExecution.getEndTime(), jobExecution.getStatus());

                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                log.info("Batch Job Time Execution-[" + duration.getSeconds() + "s]");
            } catch (Exception ex) {
                log.error("Batch Job Exception-[{}]", ex.getMessage());
            }
        }
    }
}
