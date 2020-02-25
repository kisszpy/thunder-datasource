import com.yukens.datasource.ResultWrapper;
import com.yukens.datasource.ThunderDataSource;
import com.yukens.datasource.config.DataSourceConfig;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThunderDataSourceTest {

    @Test
    public void test() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        properties.load(in);
        System.out.println(properties.get("java"));
    }

    @Test
    public void testConfig() throws IOException {
        System.out.println(DataSourceConfig.getInstance().getDriverClassName());
        System.out.println(DataSourceConfig.getInstance().getPassword());
        System.out.println(DataSourceConfig.getInstance().getUsername());
        System.out.println(DataSourceConfig.getInstance().getUrl());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(DataSourceConfig.getInstance());
            }).start();
        }
    }

    @Test
    public void testConnection() throws SQLException, InterruptedException {
        ThunderDataSource dataSource = new ThunderDataSource();
        ExecutorService es = Executors.newFixedThreadPool(50);
        int count = 100000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            es.execute(()-> {
                try {
                    scripts2(dataSource,es);
                    countDownLatch.countDown();
                    System.out.println("执行第：" + finalI);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();

    }

    private void scripts2(ThunderDataSource dataSource, ExecutorService es) throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select 1 from dual");
            if (resultSet.next()) {
                System.out.println(Thread.currentThread().getName() + "查询结果 ：" + resultSet.getInt(1));
                resultSet.close();
                dataSource.release(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Future<ResultWrapper> scripts(ThunderDataSource dataSource, ExecutorService es) throws SQLException {
        return es.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ": 开始执行");
                Connection connection = dataSource.getConnection();
                ResultSet resultSet = connection.createStatement().executeQuery("select 1 from dual");
                if (resultSet.next()) {
                    System.out.println("内部执行" + resultSet.getInt(1));
                }
                System.out.println("fffffff");
                return new ResultWrapper(resultSet, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
