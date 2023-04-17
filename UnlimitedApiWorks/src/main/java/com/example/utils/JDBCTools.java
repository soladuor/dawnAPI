package com.example.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 获取和释放连接的工具类
 *
 * @version 2.1
 */
public class JDBCTools {
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal;

    // 初始化数据库连接池，只需要执行一次（使用静态代码块）
    static {
        try {
            // 读取druid.properties文件
            Properties properties = new Properties();
            properties.load(JDBCTools.class.getClassLoader().getResourceAsStream("druid.properties"));
            // 创建一个数据库连接池
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            // 创建线程池
            threadLocal = new ThreadLocal<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取连接的方法
     */
    public static Connection getConnection() {
        Connection connection = threadLocal.get(); // 拿到线程共享的Connection对象
        if (connection == null) {
            try {
                connection = dataSource.getConnection(); // 从连接池中获取链接
                threadLocal.set(connection); // 将连接与当前线程绑定
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    /**
     * 归还连接的方法
     */
    public static void freeConnection() {
        Connection connection = threadLocal.get(); // 得到当前线程中的连接
        if (connection != null) {
            try {
                // 由于部分用户会将数据库设置为手动提交，所以这里需要将其设置为自动提交
                // 为了避免影响其他用户，这里需要修改为自动提交
                connection.setAutoCommit(true);
                connection.close(); // 归还数据库连接
                threadLocal.remove(); // 从ThreadLocal中移除当前线程已关闭的Connection对象
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
