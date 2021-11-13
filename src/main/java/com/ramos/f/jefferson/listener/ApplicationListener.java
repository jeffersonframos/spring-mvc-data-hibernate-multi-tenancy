package com.ramos.f.jefferson.listener;

import com.ramos.f.jefferson.tenant.MultiTenantConnectionProviderImpl;
import com.ramos.f.jefferson.tenant.MultiTenantDataSourceLookup;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationListener implements ServletContextListener {
    private final MultiTenantDataSourceLookup tenantDataSourceLookup;
    private final MultiTenantConnectionProviderImpl tenantConnectionProviderImpl;
    
    public ApplicationListener(MultiTenantConnectionProviderImpl tenantConnectionProviderImpl,
            MultiTenantDataSourceLookup tenantDataSourceLookup) {
        this.tenantDataSourceLookup = tenantDataSourceLookup;
        this.tenantConnectionProviderImpl = tenantConnectionProviderImpl;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Closing datasources connections");
        tenantDataSourceLookup.shutdown();
        tenantConnectionProviderImpl.shutdown();
    }
    
}
