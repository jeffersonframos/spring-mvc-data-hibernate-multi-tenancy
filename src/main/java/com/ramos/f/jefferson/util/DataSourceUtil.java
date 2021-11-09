package com.ramos.f.jefferson.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.core.env.Environment;

public class DataSourceUtil {
    private DataSourceUtil() {}
    
    private static final String TEST_QUERY = "SELECT 1";
    
    public static HikariConfig getConfig(Properties properties){
        String jdbcUrl = properties.getProperty("hibernate.connection.url");
        String username = properties.getProperty("hibernate.connection.username");
        String password = properties.getProperty("hibernate.connection.password");
        String poolSize = properties.getProperty("hibernate.connection.pool_size");
        return getConfig(jdbcUrl, username, password, poolSize);
    }
    
    public static DataSource getDataSource(Environment env){
        String jdbcUrl = env.getRequiredProperty("hibernate.connection.url");
        String username = env.getRequiredProperty("hibernate.connection.username");
        String password = env.getRequiredProperty("hibernate.connection.password");
        String poolSize = env.getRequiredProperty("hibernate.connection.pool_size");
        String driverClass = env.getRequiredProperty("hibernate.connection.driver_class");
        HikariConfig config = getConfig(jdbcUrl, username, password, poolSize);
        config.setDriverClassName(driverClass);
        return new HikariDataSource(config);
    }
    
    private static HikariConfig getConfig(String jdbcUrl, String username, String password, String poolSize){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        try{
            config.setMaximumPoolSize(Integer.parseInt(poolSize));
        }catch(IllegalStateException | NumberFormatException ex){
            config.setMaximumPoolSize(5);
        }
        config.setConnectionTestQuery(TEST_QUERY);
        return config;
    }
}
