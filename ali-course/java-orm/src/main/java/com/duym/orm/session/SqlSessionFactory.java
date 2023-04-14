package com.duym.orm.session;
  
/**  
 * SQL会话对象工厂  
 */  
public interface SqlSessionFactory {  
    /**  
     * 创建一个SQL会话  
     * @return SQL会话  
     */  
    SqlSession openSession();  
}