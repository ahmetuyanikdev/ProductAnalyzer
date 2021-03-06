package com.project.config;

import com.project.AppVariables;
import com.project.service.ProductPersistenceService;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        dataSource.setDriverClassName(AppVariables.Driver_Class);
        dataSource.setUrl(AppVariables.DB_ConnectionUrl);
        dataSource.setUsername(AppVariables.DB_UserName);
        dataSource.setPassword(AppVariables.DB_Password);
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

    @Bean
    ProductPersistenceService productPersistenceService(HibernateTemplate hibernateTemplate){
        ProductPersistenceService productPersistenceService = new ProductPersistenceService();
        hibernateTemplate.setCheckWriteOperations(false);
        hibernateTemplate.afterPropertiesSet();
        productPersistenceService.setHibernateTemplate(hibernateTemplate);
        return productPersistenceService;
    }


}
