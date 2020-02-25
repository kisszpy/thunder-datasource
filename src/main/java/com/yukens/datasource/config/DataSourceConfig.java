package com.yukens.datasource.config;

import com.yukens.datasource.constant.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class DataSourceConfig implements Config {

    private String username;

    private String password;

    private String url;

    private String driverClassName;

    private int initialSize;

    private int maxActive;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    private DataSourceConfig() {
        Properties properties = loadResource();
        username = properties.getProperty(Constant.CONFIG_KEY_USERNAME);
        password = properties.getProperty(Constant.CONFIG_KEY_PASSWORD);
        url = properties.getProperty(Constant.CONFIG_KEY_URL);
        driverClassName = properties.getProperty(Constant.CONFIG_KEY_DRIVER_CLASS_NAME);
        initialSize = properties.getProperty(Constant.CONFIG_KEY_POOL_INITIAL_SIZE) == null ? 8 : Integer.valueOf(properties.getProperty(Constant.CONFIG_KEY_POOL_INITIAL_SIZE));
        maxActive = properties.getProperty(Constant.CONFIG_KEY_POOL_MAX_ACTIVE) == null ? 8 : Integer.valueOf(properties.getProperty(Constant.CONFIG_KEY_POOL_MAX_ACTIVE));

        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DataSourceConfig getInstance() {
        return DataSourceConfigHolder.INSTANCE;
    }

    private Properties loadResource() {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream(Constant.CONFIG_FILE);
        Optional.ofNullable(in).orElseThrow(() -> {
            return new RuntimeException("config file not found , please check your config (jdbc.properties)");
        });
        try {
            properties.load(in);
        } catch (IOException e) {
            System.out.println("加载配置文件出错，请检查您的配置文件");
            e.printStackTrace();
        }
        return properties;
    }

    static class DataSourceConfigHolder {
        private static final DataSourceConfig INSTANCE = new DataSourceConfig();
    }
}
