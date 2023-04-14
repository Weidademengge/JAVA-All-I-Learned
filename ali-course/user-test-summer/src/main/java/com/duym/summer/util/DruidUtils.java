package com.duym.summer.util;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidUtils {

    private DruidUtils(){
    }

    private static DruidDataSource druidDataSource = new DruidDataSource();


    static {
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/db_test");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");

    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }
}