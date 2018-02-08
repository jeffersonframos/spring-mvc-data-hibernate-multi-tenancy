/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.tenant;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Component;

/**
 *
 * @author jeffe
 */
@Component(value = "dataSourceLookup")
public class MultiTenantDataSourceLookup extends MapDataSourceLookup{

    private final static String TENANT_FILES_FOLDER = "C:/data-source/tenants";

    @Autowired
    public MultiTenantDataSourceLookup(DataSource dataSource) {
        super();
        
        try {
            initializeDataSources(dataSource);
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }

    /**
     * It initialize all the datasources. If multitenancy is activated it also
     * add dataSources for different tenants on tenantDbConfigs or
     * tenantDbConfigsOverride
     *
     * @param tenantResolver
     * @throws IOException
     */
    private void initializeDataSources(DataSource defaultDataSource) throws IOException {
        //Get the path where server is stored, we will save configurations there,
        //so if we redeploy it will not be deleted
        System.out.println("MultiTenancy configuration: ");
        System.out.println("---------------------------------------------------");

        // Add the default tenant and datasource
        addDataSource("default", defaultDataSource);
        System.out.println("Configuring default tenant: DefaultTenant - Properties: " + defaultDataSource.toString());

        // Add the other tenants
        System.out.println("-- GLOBAL TENANTS --");
        addTenantDataSources(TENANT_FILES_FOLDER);
        System.out.println("---------------------------------------------------");
    }

    /**
     * Add Tenant datasources based on the default properties on
     * defaultDataSource and the configurations in dbConfigs.
     *
     * @param defaultDataSource
     * @param dbConfigs
     */
    private void addTenantDataSources(String tenantFilesFolder) {
        // Add the custom tenants and datasources
        try {
            List<String> tenantFilesList = getTenantFiles(tenantFilesFolder);
            for (String tenantFile : tenantFilesList) {
                // load properties
                Properties props = new Properties();
                props.load(getProperties(tenantFile));

                // Get tenantId using the filename and pattern
                String tenantId = tenantFile.replace(".properties", "");
                
                // Add new datasource with own configuration per tenant
                HikariDataSource customDataSource = createTenantDataSource(props);
                addDataSource(tenantId, customDataSource);

                System.out.println("Configured tenant: " + tenantId + " - Properties: " + customDataSource.toString());
            }
        } catch (IOException ioe) {
            System.out.println("Error getting the tenants: " + ioe.getMessage());
        }
    }

    /**
     * Create a datasource with tenant properties, if a property is not found in
     * Properties it takes the property from the defaultDataSource
     *
     * @return a BoneCPDataSource based on tenant and default properties
     */
    private HikariDataSource createTenantDataSource(Properties tenantProps) {
        HikariConfig config = getConfig(tenantProps);
        HikariDataSource customDataSource = new HikariDataSource(config);
        return customDataSource;
    }

    /**
     * Get the tenantId from filename using the pattern
     *
     * @param tenantPattern
     * @param filename
     * @return tenantId
     * @throws IOException
     */
    private List<String> getTenantFiles(String tenantFilesFolder) throws IOException {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".properties");
            }
        };
        File file = new File(tenantFilesFolder);
        List<String> tenantIdList = Arrays.asList(file.list(filter));
        return tenantIdList;
    }
    
    private HikariConfig getConfig(Properties properties){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("hibernate.connection.url"));
        config.setUsername(properties.getProperty("hibernate.connection.username"));
        config.setPassword(properties.getProperty("hibernate.connection.password"));
        try{
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("hibernate.connection.pool_size")));
        }catch(IllegalStateException | NumberFormatException ex){
            config.setMaximumPoolSize(5);
        }
        config.setConnectionTestQuery("SELECT 1");
        return config;
    }
    
    private FileInputStream getProperties(String tenantId) throws IOException{
        File file = new File(TENANT_FILES_FOLDER+"/"+tenantId);
        return new FileInputStream(file);
    }
    
}
