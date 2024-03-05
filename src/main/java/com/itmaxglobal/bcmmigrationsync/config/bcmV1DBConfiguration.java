package com.itmaxglobal.bcmmigrationsync.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.itmaxglobal.bcmmigrationsync.bcmv1.repository",
        entityManagerFactoryRef = "bcmV1ManagerFactory")
public class bcmV1DBConfiguration {

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
    public LocalContainerEntityManagerFactoryBean bcmV1ManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(bcmV1DBDataSource());
        entityManagerFactory.setPackagesToScan("com.itmaxglobal.bcmmigrationsync.bcmv1.entity");
        entityManagerFactory.setPersistenceUnitName("accountPersistenceUnit");
        entityManagerFactory.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
        jpaProperties.put("hibernate.show_sql", "false");
        jpaProperties.put("hibernate.format_sql", "false");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        entityManagerFactory.setJpaProperties(jpaProperties);

        return entityManagerFactory;
    }

    @Bean
    @Primary
    public PlatformTransactionManager bcmV1TransactionManager(
            final @Qualifier("bcmV1ManagerFactory") LocalContainerEntityManagerFactoryBean bcmV1ManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(bcmV1ManagerFactory.getObject()));
    }
}
