package com.project.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Bean
    DataSource dataSource(){
        BasicDataSource dataSource  = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.99.100:32768/test_db");
        dataSource.setUsername("root");
        dataSource.setPassword("nesilrds123");
        return dataSource;
    }

    private Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql",false);
        return properties;
    }

    @Bean
    org.springframework.orm.hibernate4.LocalSessionFactoryBean sessionFactory(DataSource dataSource){

        org.springframework.orm.hibernate4.LocalSessionFactoryBean sessionFactory = new org.springframework.orm.hibernate4.LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPackagesToScan("com.project.model");
        return sessionFactory;
    }

    @Bean
    PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    HibernateTemplate hibernateTemplate(SessionFactory sessionFactory){
        return new HibernateTemplate(sessionFactory);
    }


}
