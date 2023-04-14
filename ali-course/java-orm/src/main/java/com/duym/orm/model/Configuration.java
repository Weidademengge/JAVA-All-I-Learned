package com.duym.orm.model;
  
import java.util.HashMap;  
import java.util.Map;  
  
import javax.sql.DataSource;  
  
/**  
 * ORM框架的配置对象  
 */  
public class Configuration {  
  
    private DataSource dataSource;  
  
    /**  
     * key:statementId     
	 * value:MappedStatement对象  
     */  
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();  
  
    public DataSource getDataSource() {  
        return dataSource;  
    }  
  
    public void setDataSource(DataSource dataSource) {  
        this.dataSource = dataSource;  
    }  
  
    public Map<String, MappedStatement> getMappedStatementMap() {  
        return mappedStatementMap;  
    }  
  
    public void setMappedStatementMap(  
        Map<String, MappedStatement> mappedStatementMap) {  
        this.mappedStatementMap = mappedStatementMap;  
    }  
}