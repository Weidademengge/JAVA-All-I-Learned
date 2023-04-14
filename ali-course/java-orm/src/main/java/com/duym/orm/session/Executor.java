package com.duym.orm.session;
  
import java.beans.IntrospectionException;  
import java.lang.reflect.InvocationTargetException;  
import java.sql.SQLException;  
import java.util.List;
import com.duym.orm.model.Configuration;
import com.duym.orm.model.MappedStatement;

  
/**  
 * 向数据库发出sql查询的执行器，用于封装JDBC的代码  
 */  
public interface Executor {  
  
    /**  
     * 向数据库发出sql查询  
     * @param configuration  
     * @param mappedStatement  
     * @param params  
     * @return 返回查询出的结果集  
     * @throws SQLException  
     * @throws ClassNotFoundException  
     * @throws NoSuchFieldException  
     * @throws IllegalAccessException  
     * @throws InstantiationException  
     * @throws IntrospectionException  
     * @throws InvocationTargetException  
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params)
        throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException,  
        InstantiationException, IntrospectionException, InvocationTargetException;  
}