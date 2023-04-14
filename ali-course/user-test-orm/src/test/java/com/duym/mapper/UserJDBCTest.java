package com.duym.mapper;
  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;

import com.duym.domain.entity.UserDO.UserDO;
import org.junit.Test;
  
public class UserJDBCTest {  
  
    /**  
     * 1.加载数据库驱动  
     * 2.通过驱动管理类获取数据库链接  
     * 3.定义sql语句?表示占位符  
     * 4.获取预处理statement  
     * 5.设置参数，第一个参数为sql语句中参数的序号(从1开始)，第二个参数为设置的参数值  
     * 6.向数据库发出sql执行查询，查询出结果集  
     * 7.遍历查询结果集  
     * 8.并封装返回对象  
     */  
    @Test  
    public void selectOne() {  
        Connection connection = null;  
        PreparedStatement preparedStatement = null;  
        ResultSet resultSet = null;  
  
        try {  
            // 1.加载数据库驱动  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            // 2.通过驱动管理类获取数据库链接  
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?characterEncoding=utf-8",  
                "root", "root");  
            // 3.定义sql语句?表示占位符  
            String sql = "select * from tb_user where username = ?";  
            // 4.获取预处理statement  
            preparedStatement = connection.prepareStatement(sql);  
            // 5.设置参数，第一个参数为sql语句中参数的序号(从1开始)，第二个参数为设置的参数值  
            preparedStatement.setString(1, "username5");  
            // 6.向数据库发出sql执行查询，查询出结果集  
            resultSet = preparedStatement.executeQuery();  
            // 7.遍历查询结果集  
            while (resultSet.next()) {  
                long id = resultSet.getLong("id");  
                String username = resultSet.getString("username");  
  
                // 8.并封装返回对象  
                UserDO user = new UserDO();
                user.setId(id);  
                user.setUsername(username);  
                System.out.println(user);  
            }  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (resultSet != null) {  
                try {  
                    resultSet.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (preparedStatement != null) {  
                try {  
                    preparedStatement.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (connection != null) {  
                try {  
                    connection.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}