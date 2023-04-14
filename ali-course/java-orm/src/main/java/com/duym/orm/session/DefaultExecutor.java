package com.duym.orm.session;
  
import java.beans.IntrospectionException;  
import java.beans.PropertyDescriptor;  
import java.lang.reflect.Field;  
import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;  
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.List;

import com.duym.orm.model.BoundSql;
import com.duym.orm.model.Configuration;
import com.duym.orm.model.MappedStatement;
import com.duym.orm.util.GenericTokenParser;
import com.duym.orm.util.ParameterMapping;
import com.duym.orm.util.ParameterMappingTokenHandler;

  
/**  
 * 1.获取数据库链接  
 * 2.定义sql语句?表示占位符:获取sql语句  
 * 3.定义sql语句?表示占位符:转换sql语句  
 * 4.获取预处理statement  
 * 5.设置参数，第一个参数为sql语句中参数的序号(从1开始)，第二个参数为设置的参数值  
 * 6.向数据库发出sql执行查询，查询出结果集  
 * 7.遍历查询结果集  
 * 8.并封装返回对象  
 */  
public class DefaultExecutor implements Executor {  
    @Override  
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params)
        throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException,  
        InstantiationException, IntrospectionException, InvocationTargetException {  
        // 1.获取数据库链接  
        Connection connection = configuration.getDataSource().getConnection();  
  
        // 2.定义sql语句?表示占位符:获取sql语句  
        String sql = mappedStatement.getSql();  
  
        // 3.定义sql语句?表示占位符:转换sql语句  
        BoundSql boundSql = getBoundSql(sql);
  
        // 4.获取预处理statement  
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());  
  
        // 5.设置参数：获取入参的Class对象  
        String paramterType = mappedStatement.getParamterType();  
        Class<?> paramterTypeClass = paramterType != null ? Class.forName(paramterType) : null;  
  
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappings.size(); i++) {  
            ParameterMapping parameterMapping = parameterMappings.get(i);  
            String content = parameterMapping.getContent();  
            // java.lang.Class类的getDeclaredField()方法用于返回一个Field对象，该对象指示该类的给定声明字段。  
            Field declaredField = paramterTypeClass.getDeclaredField(content);  
            // 暴力访问  
            declaredField.setAccessible(true);  
            // java.lang.reflect.Field的get()方法用于获取字段对象的值  
            Object object = declaredField.get(params[0]);  
            // 5.设置参数：第一个参数为sql语句中参数的序号(从1开始)，第二个参数为设置的参数值  
            // preparedStatement.setString(1, "username5");  
            preparedStatement.setObject(i + 1, object);  
        }  
  
        // 6.向数据库发出sql执行查询，查询出结果集  
        ResultSet resultSet = preparedStatement.executeQuery();  
  
        // 7.遍历查询结果集：获取出参的Class对象  
        String resultType = mappedStatement.getResultType();  
        Class<?> resultTypeClass = resultType != null ? Class.forName(resultType) : null;  
  
        // 7.遍历查询结果集  
        List<Object> resultList = new ArrayList<>();  
        while (resultSet.next()) {  
            Object instance = resultTypeClass.newInstance();  
            // resultSet.getMetaData()方法用于获取表结构元数据：表名、字段数、字段名  
            ResultSetMetaData metaData = resultSet.getMetaData();  
            for (int i = 1; i <= metaData.getColumnCount(); i++) {  
                // 获取字段名  
                String columnName = metaData.getColumnName(i);  
                // 获取字段值  
                Object columnValue = resultSet.getObject(columnName);  
  
                // 获取resultTypeClass的某个属性的描述符  
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);  
                // 获得用于写入属性值的方法对象  
                Method writeMethod = propertyDescriptor.getWriteMethod();  
                // 写入属性值  
                writeMethod.invoke(instance, columnValue);  
            }  
  
            resultList.add(instance);  
        }  
  
        return (List<E>)resultList;  
    }  
  
    /**  
     * 完成对#{}的解析工作  
     *  
     * @param sql  
     * @return  
     */  
    private BoundSql getBoundSql(String sql) {  
        // 1.将#{}使用?代替  
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);  
  
        // 2.解析出#{}里面的值  
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();  
        return new BoundSql(parse, parameterMappings);  
    }  
}