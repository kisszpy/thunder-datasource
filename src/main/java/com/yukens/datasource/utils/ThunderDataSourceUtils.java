package com.yukens.datasource.utils;


import com.yukens.datasource.config.DataSourceConfig;
import com.yukens.datasource.exception.CreateConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据源工具类
 */
public class ThunderDataSourceUtils {

    private static final DataSourceConfig config = DataSourceConfig.getInstance();
    /**
     * 创建连接的工具类
     * @return
     */
    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(config.getUrl(),config.getUsername(),config.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new CreateConnectionException("create connection error");
    }
}
