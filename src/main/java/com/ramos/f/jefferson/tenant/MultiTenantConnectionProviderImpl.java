/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.tenant;

import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

/**
 *
 * @author jeffe
 */
public class MultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl{
    
    @Autowired
    private DataSource defaultDataSource;
    
    @Autowired
    private DataSourceLookup dataSourceLookup;

    @Override
    protected DataSource selectAnyDataSource() {
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        try{
            DataSource dataSource = dataSourceLookup.getDataSource(tenantIdentifier);
            if (dataSource == null) {
                return defaultDataSource;
            }
            return dataSource;
        } catch (DataSourceLookupFailureException ex){
            System.out.println(ex.getMessage());
            return defaultDataSource;
        }
    }
    
}
