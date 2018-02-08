/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.configuration;

import com.ramos.f.jefferson.tenant.MultiTenantConnectionProviderImpl;
import com.ramos.f.jefferson.tenant.TenantIdentifierResolver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author jeffe
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = "file:C:/data-source/default.properties")
@EnableJpaRepositories(basePackages = {"com.ramos.f.jefferson.repository"})
public class PersistenceContext {
    
    @Bean
    public DataSource dataSource(Environment env){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getRequiredProperty("hibernate.connection.driver_class"));
        config.setJdbcUrl(env.getRequiredProperty("hibernate.connection.url"));
        config.setUsername(env.getRequiredProperty("hibernate.connection.username"));
        config.setPassword(env.getRequiredProperty("hibernate.connection.password"));
        try{
            config.setMaximumPoolSize(Integer.parseInt(env.getRequiredProperty("hibernate.connection.pool_size")));
        }catch(IllegalStateException | NumberFormatException ex){
            config.setMaximumPoolSize(5);
        }
        config.setConnectionTestQuery("SELECT 1");
        return new HikariDataSource(config);
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.ramos.f.jefferson.entity");
        
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        Map<String, Object> multitenancyMap = new HashMap<String, Object>();
        multitenancyMap.put("hibernate.multi_tenant_connection_provider", multitenancyConnectionProvider());
        multitenancyMap.put("hibernate.tenant_identifier_resolver", tenantResolver());
        multitenancyMap.put("hibernate.multiTenancy", "DATABASE");
        
        entityManagerFactoryBean.setJpaPropertyMap(multitenancyMap);
        return entityManagerFactoryBean;
    }
    
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
    
    @Bean
    public MultiTenantConnectionProviderImpl multitenancyConnectionProvider() {
        return new MultiTenantConnectionProviderImpl();
    }

    @Bean
    public TenantIdentifierResolver tenantResolver() {
        return new TenantIdentifierResolver();
    }
    
}
