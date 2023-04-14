package com.duym.summer.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    /**
     * 存储当前线程的连接
     */
    private ThreadLocal<Connection> threadLocal = new ThreadLocal();

    public Connection getCurrentThreadConn() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            // 从连接池中获取连接
            connection = DruidUtils.getInstance().getConnection();
            // 将连接绑定到当前线程
            threadLocal.set(connection);
        }
        return connection;
    }
}
