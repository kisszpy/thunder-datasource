package com.yukens.datasource;

import com.yukens.datasource.config.DataSourceConfig;
import com.yukens.datasource.thread.ThunderThreadPool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ThunderDataSource implements DataSource {

    private ConnectionPool pool;

    private DataSourceConfig config = DataSourceConfig.getInstance();

    public ThunderDataSource() {
        initialPool();
        ThunderThreadPool.create().scheduleAtFixedRate(new ConnectionPool.PoolMonitor(),
                10000, 1000, TimeUnit.MILLISECONDS);
    }

    public void release(Connection connection) {
        pool.add(connection);
    }

    private void initialPool() {
        this.pool = new ConnectionPool();
        try {
            for (int i = 0; i < config.getInitialSize(); i++) {
                Connection connection = DriverManager.getConnection(config.getUrl(),
                        config.getUsername(),
                        config.getPassword());
                pool.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("系统初始化完毕");
    }

    public Connection getConnection() throws SQLException {
        return pool.get();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    public String getDataSourceInfo() {
        // todo 后续做扩展暂时先这样子
        return "pool size is :" + pool.size();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    public void setLoginTimeout(int seconds) throws SQLException {

    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
