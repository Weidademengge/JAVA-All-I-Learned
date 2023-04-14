package com.duym.orm.session;


import com.duym.orm.model.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{
  
    /**  
     * ORM框架的配置对象  
     */  
    private Configuration configuration;
  
    public DefaultSqlSessionFactory(Configuration configuration) {  
        this.configuration = configuration;  
    }  
    @Override  
    public SqlSession openSession() {  
        return new DefaultSqlSession(configuration);  
    }  
}