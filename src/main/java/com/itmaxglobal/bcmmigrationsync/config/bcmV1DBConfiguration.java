package com.itmaxglobal.bcmmigrationsync.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static com.itmaxglobal.bcmmigrationsync.util.Constants.*;

@Configuration
@EnableJpaRepositories(basePackages = "com.itmaxglobal.bcmmigrationsync.bcmv1.repository",
        entityManagerFactoryRef = "bcmV1ManagerFactory",
        transactionManagerRef = "bcmV1TransactionManager")
public class bcmV1DBConfiguration {

    @Value("${com.bcm.app-db-v1.hibernate.dialect}")
    String sqlDialect;

    @Value("${com.bcm.app-db-v1.hibernate.show-sql}")
    String showSQL;

    @Value("${com.bcm.app-db-v1.hibernate.format-sql}")
    String formatSQL;

    @Value("${com.bcm.app-db-v1.hibernate.hbm2ddl.auto}")
    String hbm2ddlAuto;

    @Value("${com.bcm.app-db-v1.package-scan}")
    String packageScan;

    @Bean
    @Primary
    @ConfigurationProperties("com.bcm.app-db-v1")
    public DataSourceProperties bcmV1DBProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource bcmV1DBDataSource() {
        return bcmV1DBProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "bcmV1ManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean bcmV1ManagerFactory(
            EntityManagerFactoryBuilder builder) {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put(SQL_HIBERNATE_DIALECT_KEY, sqlDialect);
        jpaProperties.put(SQL_HIBERNATE_SHOW_SQL_KEY, showSQL);
        jpaProperties.put(SQL_HIBERNATE_FORMAT_SQL_KEY, formatSQL);
        jpaProperties.put(SQL_HIBERNATE_HBM2DDL_AUTO_KEY, hbm2ddlAuto);
        return  builder
                .dataSource(bcmV1DBDataSource())
                .packages(packageScan)
                .persistenceUnit("accountPersistenceUnit")
                .properties(jpaProperties)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager bcmV1TransactionManager(
            final @Qualifier("bcmV1ManagerFactory") LocalContainerEntityManagerFactoryBean bcmV1ManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(bcmV1ManagerFactory.getObject()));
    }
}
