package com.yukens.datasource.utils;


import com.yukens.datasource.config.DataSourceConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThunderDataSourceUtils {

    /**
     * 创建连接的工具类
     * @return
     */
    public static Connection createConnection() {
        DataSourceConfig config = DataSourceConfig.getInstance();
        try {
            return DriverManager.getConnection(config.getUrl(),config.getUsername(),config.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
