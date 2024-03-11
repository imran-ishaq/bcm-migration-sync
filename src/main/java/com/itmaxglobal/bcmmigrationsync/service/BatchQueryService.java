package com.itmaxglobal.bcmmigrationsync.service;


import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv1.mapper.AccountRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Objects;


@Service
@Slf4j
public class BatchQueryService {
    @Qualifier("bcmV1DBDataSource")
    private final DataSource dataSource;

    @Value("${spring.batch.page-size}")
    int pageSize;

    @Value("${spring.batch.fetch-size}")
    int fetchSize;

    @Autowired
    public BatchQueryService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ItemReader<Account> executeSessionQuery() {
        try{
            final SqlPagingQueryProviderFactoryBean queryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();

            queryProviderFactoryBean.setDataSource(dataSource);
            queryProviderFactoryBean.setSelectClause("select *");
            queryProviderFactoryBean.setFromClause("from billing.getDataFromAccountAndAccountActivity");
            queryProviderFactoryBean.setSortKey("id");

            JdbcPagingItemReader<Account> itemReader = new JdbcPagingItemReader<>();
            itemReader.setDataSource(dataSource);
            itemReader.setPageSize(pageSize);
            itemReader.setFetchSize(fetchSize);
            itemReader.setRowMapper(new AccountRowMapper());
            itemReader.setQueryProvider(Objects.requireNonNull(queryProviderFactoryBean.getObject()));

            return itemReader;

        } catch (Exception ex){
            log.info("Exception from ExecuteSessionQuery()");
            log.error(ex.getMessage());
        }
        return null;
    }
}
