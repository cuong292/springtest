package com.demo.springtest.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan("com.demo.springtest")
@PropertySource({"classpath:sql.properties", "classpath:connection.properties"})
public class RestMVCConfiguration {

    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        dataSource.setUser(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));

        dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
        return dataSource;
    }

    private int getIntProperty(String s) {
        String property = environment.getProperty(s);
        try {
            return Integer.valueOf(property);
        } catch (Exception e) {
            System.out.println("Property is wrong type");
        }
        return 0;
    }
}
