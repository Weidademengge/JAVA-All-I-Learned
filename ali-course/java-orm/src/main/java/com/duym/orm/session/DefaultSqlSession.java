package com.duym.orm.session;
  
import java.beans.IntrospectionException;  
import java.lang.reflect.InvocationTargetException;  
import java.sql.SQLException;  
import java.util.List;

import com.duym.orm.model.Configuration;
import com.duym.orm.model.MappedStatement;

public class DefaultSqlSession implements SqlSession{  
  
    /**  
     * ORM框架的配置对象  
     */  
    private Configuration configuration;  
  
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;  
    }  
  
    @Override  
    public <T> List<T> selectList(String statementId, Object... params)  
        throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException,  
        InvocationTargetException, IllegalAccessException, InstantiationException {  
  
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        // 通过sql查询的执行器的query方法的调用，拿到查询出的结果集  
        Executor executor = new DefaultExecutor();  
        List<Object> resultList = executor.query(configuration, mappedStatement, params);  
        return (List<T>)resultList;  
    }  
  
    @Override  
    public <T> T selectOne(String statementId, Object... params)  
        throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException,  
        InvocationTargetException, IllegalAccessException, InstantiationException {  
  
        List<Object> objects = selectList(statementId, params);  
        if (objects.size() == 1) {  
            return (T)objects.get(0);  
        }  
        throw new SQLException("查询结果为空或者返回结果过多");  
    }  
  
}