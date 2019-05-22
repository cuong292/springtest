package com.demo.springtest.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan("com.demo.springtest")
@PropertySource({"classpath:mysql-props.properties", "classpath:connection.properties"})
public class RestMVCConfiguration {

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (Exception e) {
            System.out.println("driver not found");
        }
        dataSource.setUser(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setInitialPoolSize(getIntProp(env.getProperty("connection.pool.initialPoolSize")));
        dataSource.setMaxPoolSize(getIntProp(env.getProperty("connection.pool.maxPoolSize")));
        dataSource.setMinPoolSize(getIntProp(env.getProperty("connection.pool.minPoolSize")));
        dataSource.setMaxIdleTime(getIntProp(env.getProperty("connection.pool.maxIdleTime")));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(getHibernateProperties());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        return sessionFactory;
    }

    private int getIntProp(String prop) {
        try {
            return Integer.parseInt(prop);
        } catch (Exception e) {
            return 0;
        }
    }

    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        return props;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }
}
