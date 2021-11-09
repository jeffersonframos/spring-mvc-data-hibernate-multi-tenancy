package com.ramos.f.jefferson.tenant;

import com.ramos.f.jefferson.util.DataSourceUtil;
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

    private void initializeDataSources(DataSource defaultDataSource) throws IOException {
        System.out.println("MultiTenancy configuration: ");
        System.out.println("---------------------------------------------------");

        addDataSource("default", defaultDataSource);
        System.out.println("Configuring default tenant: DefaultTenant - Properties: " + defaultDataSource.toString());

        System.out.println("-- GLOBAL TENANTS --");
        addTenantDataSources(TENANT_FILES_FOLDER);
        System.out.println("---------------------------------------------------");
    }

    private void addTenantDataSources(String tenantFilesFolder) {
        try {
            List<String> tenantFilesList = getTenantFiles(tenantFilesFolder);
            for (String tenantFile : tenantFilesList) {
                Properties props = new Properties();
                props.load(getProperties(tenantFile));

                String tenantId = tenantFile.replace(".properties", "");
                
                HikariDataSource customDataSource = createTenantDataSource(props);
                addDataSource(tenantId, customDataSource);

                System.out.println("Configured tenant: " + tenantId + " - Properties: " + customDataSource.toString());
            }
        } catch (IOException ioe) {
            System.out.println("Error getting the tenants: " + ioe.getMessage());
        }
    }

    private HikariDataSource createTenantDataSource(Properties tenantProps) {
        HikariConfig config = DataSourceUtil.getConfig(tenantProps);
        HikariDataSource customDataSource = new HikariDataSource(config);
        return customDataSource;
    }

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
    
    private FileInputStream getProperties(String tenantId) throws IOException{
        File file = new File(TENANT_FILES_FOLDER+"/"+tenantId);
        return new FileInputStream(file);
    }
    
}
