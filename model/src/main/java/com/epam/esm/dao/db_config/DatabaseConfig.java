package com.epam.esm.dao.db_config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties")
public class DatabaseConfig {


    @Bean
    public DataSource dataSource(@Value("${db.url}") String url,
                                 @Value("${db.user}") String username,
                                 @Value("${db.password}") String password,
                                 @Value("${db.driver}") String driverName,
                                 @Value("${db.connections}") Integer connectionNumber) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(driverName);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxTotal(connectionNumber);
        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
