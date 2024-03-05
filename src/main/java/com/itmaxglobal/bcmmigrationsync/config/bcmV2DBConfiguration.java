package com.itmaxglobal.bcmmigrationsync.config;

import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(basePackages = "com.itmaxglobal.bcmmigrationsync.bcmv2.repository",
        entityManagerFactoryRef = "bcmV2ManagerFactory",
        transactionManagerRef= "bcmV2TransactionManager")
public class bcmV2DBConfiguration {

    @Bean
    @ConfigurationProperties("com.bcm.app-db-v2")
    public DataSourceProperties bcmV2DBProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource bcmV2DBDataSource() {
        return bcmV2DBProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "bcmV2ManagerFactory")
    public LocalContainerEntityManagerFactoryBean bcmV2ManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(bcmV2DBDataSource())
                .packages(Session.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager bcmV2TransactionManager(
            final @Qualifier("bcmV2ManagerFactory") LocalContainerEntityManagerFactoryBean bcmV2ManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(bcmV2ManagerFactory.getObject()));
    }
}
