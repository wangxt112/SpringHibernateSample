package com.spring.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.spring.configuration" })
@PropertySource(value = { "classpath:application.properties" })

public class HibernateConfiguration {
    @Autowired
    private Environment environment;
    
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.spring.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        return dataSource;
    }
    
    public Properties hibernateProperties(){
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("dialect",environment.getRequiredProperty("hibernate.dialect"));
        hibernateProperties.put("show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        hibernateProperties.put("format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return hibernateProperties;
    }
    
    public HibernateTransactionManager transactionManager(SessionFactory s){
        HibernateTransactionManager tsManager = new HibernateTransactionManager();
        tsManager.setSessionFactory(s);
        return tsManager;
    }
}
