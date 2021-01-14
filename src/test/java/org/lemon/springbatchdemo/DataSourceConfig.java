package org.lemon.springbatchdemo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(){
        return mock(DataSource.class);
    }
}