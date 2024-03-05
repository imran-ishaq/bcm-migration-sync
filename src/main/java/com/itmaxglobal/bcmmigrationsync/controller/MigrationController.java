package com.itmaxglobal.bcmmigrationsync.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/migration")
public class MigrationController {
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job runBatchJob;
    @GetMapping(value = "/start")
    public void startMigration(){
        markDuplicate();
    }

    public void markDuplicate() {
        try {
            Map<String, JobParameter<?>> maps = new HashMap<>();
            maps.put("status", new JobParameter<>(false, Boolean.class));
            JobParameters parameters = new JobParameters(maps);
            JobExecution jobExecution = jobLauncher.run(runBatchJob, parameters);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
