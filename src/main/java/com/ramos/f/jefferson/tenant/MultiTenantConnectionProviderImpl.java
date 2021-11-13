package com.ramos.f.jefferson.tenant;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

import static java.util.Objects.isNull;

public class MultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    
    private final DataSource defaultDataSource;
    private final DataSourceLookup dataSourceLookup;
    
    public MultiTenantConnectionProviderImpl(DataSource defaultDataSource, DataSourceLookup dataSourceLookup) {
        this.defaultDataSource = defaultDataSource;
        this.dataSourceLookup = dataSourceLookup;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        try{
            DataSource dataSource = dataSourceLookup.getDataSource(tenantIdentifier);
            return isNull(dataSource)
                    ? defaultDataSource
                    : dataSource;
        } catch (DataSourceLookupFailureException ex){
            System.out.println(ex.getMessage());
            return defaultDataSource;
        }
    }
    
    public void shutdown() {
        ((HikariDataSource) defaultDataSource).close();
    }
    
}
