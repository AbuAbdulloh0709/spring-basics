package com.epam.esm.db_config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@Profile("test")
@PropertySource("classpath:h2.properties")
public class DatabaseConfigTest {
    @Bean
    public DataSource dataSource(
            @Value("${h2.url}") String url,
            @Value("${h2.user}") String username,
            @Value("${h2.password}") String password,
            @Value("${h2.driver}") String driverName,
            @Value("${h2.connectionNumber}") int connectionNumber
    ) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(driverName);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxTotal(connectionNumber);
        Resource initData = new ClassPathResource("creatingTestTables.sql");
        Resource fillData = new ClassPathResource("fillingTestTables.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData,fillData);
        DatabasePopulatorUtils.execute(databasePopulator, basicDataSource);
        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
