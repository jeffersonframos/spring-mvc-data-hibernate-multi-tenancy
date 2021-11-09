package com.ramos.f.jefferson.configuration;

import com.ramos.f.jefferson.tenant.MultiTenantConnectionProviderImpl;
import com.ramos.f.jefferson.tenant.TenantIdentifierResolver;
import com.ramos.f.jefferson.util.DataSourceUtil;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:/default.properties")
@EnableJpaRepositories(basePackages = {"com.ramos.f.jefferson.repository"})
public class PersistenceContext {
    
    @Bean
    public DataSource dataSource(Environment env){
        return DataSourceUtil.getDataSource(env);
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource defaultDataSource, DataSourceLookup dataSourceLookup){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.ramos.f.jefferson.entity");
        
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        Map<String, Object> multitenancyMap = new HashMap<>();
        multitenancyMap.put("hibernate.multi_tenant_connection_provider", multitenancyConnectionProvider(defaultDataSource, dataSourceLookup));
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
    public MultiTenantConnectionProviderImpl multitenancyConnectionProvider(DataSource defaultDataSource, DataSourceLookup dataSourceLookup) {
        return new MultiTenantConnectionProviderImpl(defaultDataSource, dataSourceLookup);
    }

    @Bean
    public TenantIdentifierResolver tenantResolver() {
        return new TenantIdentifierResolver();
    }
    
}
