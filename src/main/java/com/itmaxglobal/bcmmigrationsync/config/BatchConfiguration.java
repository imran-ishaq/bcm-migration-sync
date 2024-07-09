package com.itmaxglobal.bcmmigrationsync.config;


import com.itmaxglobal.bcmmigrationsync.batchProcessing.AccountProcessor;
import com.itmaxglobal.bcmmigrationsync.batchProcessing.AccountWriter;
import com.itmaxglobal.bcmmigrationsync.batchProcessing.listeners.*;
import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {
    private final ItemReader<Account> accountReader;
    private final AccountProcessor accountProcessor;
    private final AccountWriter accountWriter;
    private final CustomChunkListener customChunkListener;
    private final CustomItemProcessListener<Account, Account> customItemProcessListener;
    private final CustomItemReadListener<Account> customItemReadListener;
    private final CustomItemWriteListener<Account> customItemWriteListener;
    private final CustomJobExecutionListener customJobExecutionListener;
    private final CustomStepExecutionListener customStepExecutionListener;

    @Value("${spring.batch.chunk-size}")
    int chunkSize;

//    @Value("${spring.batch.core-pool-size}")
//    int setCorePoolSize;
//
//    @Value("${spring.batch.max-pool-size}")
//    int setMaxPoolSize;

    @Autowired
    public BatchConfiguration(ItemReader<Account> accountReader, AccountProcessor accountProcessor,
                              AccountWriter accountWriter, CustomChunkListener customChunkListener,
                              CustomItemProcessListener<Account, Account> customItemProcessListener,
                              CustomItemReadListener<Account> customItemReadListener,
                              CustomItemWriteListener<Account> customItemWriteListener,
                              CustomJobExecutionListener customJobExecutionListener,
                              CustomStepExecutionListener customStepExecutionListener) {
        this.accountReader = accountReader;
        this.accountProcessor = accountProcessor;
        this.accountWriter = accountWriter;
        this.customChunkListener = customChunkListener;
        this.customItemProcessListener = customItemProcessListener;
        this.customItemReadListener = customItemReadListener;
        this.customItemWriteListener = customItemWriteListener;
        this.customJobExecutionListener = customJobExecutionListener;
        this.customStepExecutionListener = customStepExecutionListener;
    }


    @Bean(name = "batchDataSource")
    @BatchDataSource
    protected DataSource batchDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("batch-db")
                .addScript("/org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    @Bean(name = "stepForBatchProcess")
    public Step step(JobRepository jobRepository, @Qualifier("bcmV1TransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("StepForMigrationBatchProcess", jobRepository)
                .<Account, Account>chunk(chunkSize, transactionManager)
                .reader(accountReader)
                .processor(accountProcessor)
                .writer(accountWriter)
//                .taskExecutor(taskExecutor)
                .listener(customChunkListener)
                .listener(customItemProcessListener)
                .listener(customItemReadListener)
                .listener(customItemWriteListener)
                .listener(customJobExecutionListener)
                .listener(customStepExecutionListener)
                .allowStartIfComplete(true)
                .build();
    }
    @Bean
    public Job job(JobRepository jobRepository, @Qualifier("stepForBatchProcess") Step step) {
        return new JobBuilder("StepForMigrationBatchProcess", jobRepository)
                .start(step)
                .build();
    }
//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(setCorePoolSize);
//        taskExecutor.setMaxPoolSize(setMaxPoolSize);
//        taskExecutor.afterPropertiesSet();
//        return taskExecutor;
//    }
}
