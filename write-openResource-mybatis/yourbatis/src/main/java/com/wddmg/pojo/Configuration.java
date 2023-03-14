package com.wddmg.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类：存放核心配置文件解析出来的内容
 * @author duym
 * @version $ Id: Configuration, v 0.1 2023/03/08 9:01 banma-0163 Exp $
 */
public class Configuration {

    //数据源对象
    private DataSource dataSource;

    /**
     * map集合存放mappedStatement
     * key:statementId--namespace.id
     * value:MapppedStatement
     */
    private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
