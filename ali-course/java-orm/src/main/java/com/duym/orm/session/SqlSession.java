package com.duym.orm.session;
  
import java.beans.IntrospectionException;  
import java.lang.reflect.InvocationTargetException;  
import java.sql.SQLException;  
import java.util.List;  
  
/**  
 * SQL会话，用于执行SQl语句  
 */  
public interface SqlSession {  
  
    <T> List<T> selectList(String statementId, Object... params)  
        throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException,  
        InvocationTargetException, IllegalAccessException, InstantiationException;  
  
    <T> T selectOne (String statementId, Object... params)  
        throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException,  
        InvocationTargetException, IllegalAccessException, InstantiationException;  
  
}