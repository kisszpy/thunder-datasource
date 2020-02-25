package com.yukens.datasource;



import com.yukens.datasource.common.ObjectPool;
import com.yukens.datasource.config.DataSourceConfig;
import com.yukens.datasource.utils.ThunderDataSourceUtils;

import java.sql.Connection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool extends ObjectPool<Connection> {

    private static volatile CopyOnWriteArrayList<Connection> container = new CopyOnWriteArrayList<>();

    private static DataSourceConfig config = DataSourceConfig.getInstance();

    static ReentrantLock lock = new ReentrantLock();

    public ConnectionPool () {

    }

    @Override
    public void add(Connection connection) {
        container.add(connection);
    }

    @Override
    public Connection get() {
        lock.lock();
        try {
            if (container.size() > 0) {
                Connection connection =  container.get(0);
                container.remove(0);
                return connection;
            }
            while (!(container.size() > 0 )) {
                // 及时补充
                new PoolMonitor().run();
            }
        } finally {
            lock.unlock();
        }
        return get();
    }

    public static class PoolMonitor implements Runnable {

        @Override
        public void run() {
            System.out.println("开始检查");
            if (container.size() < config.getInitialSize() / 4 ) {
                // 开始扩容
                int limit = (config.getMaxActive() - container.size()) << 1;
                for (int i = container.size() ;i < limit; i++ ) {
                    System.out.println("扩容中，当前连接数：" + container.size());
                    container.add(ThunderDataSourceUtils.createConnection());
                }
            }
            if (container.size() > config.getMaxActive()) {
                // 开始回收
                for (int i = container.size() ;i < config.getInitialSize(); i-- ) {
                    container.add(ThunderDataSourceUtils.createConnection());
                }
            }
        }
    }
}
