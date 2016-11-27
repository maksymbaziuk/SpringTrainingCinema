package com.baziuk.spring.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * Created by Maks on 10/10/16.
 */
@Configuration
@EnableTransactionManagement
public class H2DBConfig {

    @Value("classpath:config/db/clean.sql")
    private Resource H2_CLEANER_SCRIPT;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return createH2DataSource();
    }

    @Bean
    public DataSourceTransactionManager txManager(){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public TransactionManagementConfigurer transactionManagementConfigurer(){
        return () -> txManager();
    }

    private DataSource createH2DataSource() {
        EmbeddedDatabaseBuilder ds = new EmbeddedDatabaseBuilder();
        ds.addScript("classpath:config/db/schema.sql");
        ds.setType(EmbeddedDatabaseType.H2);
        return ds.build();
    }

    @Autowired
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabaseCleaner(databaseCleaner());
        return initializer;
    }

    private DatabasePopulator databaseCleaner() {
        ResourceDatabasePopulator cleaner = new ResourceDatabasePopulator();
        cleaner.addScript(H2_CLEANER_SCRIPT);
        return cleaner;
    }
}
