package com.duym.orm.config;
  
import java.io.InputStream;  
import java.util.List;  
import java.util.Properties;  
  
import com.alibaba.druid.pool.DruidDataSource;

import com.duym.orm.model.Configuration;
import com.duym.orm.model.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
  
/**  
 * ORM框架的配置对象的建造者  
 */  
public class XmlConfigurationBuilder {  
  
    private static Configuration configuration = new Configuration();
  
    /**  
     * 使用dom4j对sqlMapConfig.xml配置文件进行解析  
     * @param inputStream  
     * @return  
     */  
    public static Configuration build(InputStream inputStream) throws DocumentException {  
        Document document = new SAXReader().read(inputStream);  
  
        // <configuration>  
        Element rootElement = document.getRootElement();  
        // <property>  
        List<Element> list = rootElement.selectNodes("//property");  
  
        Properties properties = new Properties();  
        list.forEach((Element element) -> {  
            String name = element.attributeValue("name");  
            String value = element.attributeValue("value");  
            properties.setProperty(name,value);  
        });  
  
        DruidDataSource druidDataSource = new DruidDataSource();  
        druidDataSource.setDriverClassName(properties.getProperty("driverClass"));  
        druidDataSource.setUrl(properties.getProperty("jdbcUrl"));  
        druidDataSource.setUsername(properties.getProperty("username"));  
        druidDataSource.setPassword(properties.getProperty("password"));  
        configuration.setDataSource(druidDataSource);  
  
        List<Element> mapperList = rootElement.selectNodes("//mapper");  
        parseMapperXML(mapperList, configuration);  
        return configuration;  
    }  
  
    private static void parseMapperXML(List<Element> mapperList, Configuration configuration) {  
        mapperList.forEach((Element mapper) -> {  
            String path = mapper.attributeValue("resource");  
            InputStream resourceAsStream = Resources.getResourceAsStream(path);  
            try {  
                Document document = new SAXReader().read(resourceAsStream);  
                // <mapper>  
                Element rootElement = document.getRootElement();  
                // <mapper namespace="user">  
                String namespace = rootElement.attributeValue("namespace");  
                // <select>  
                List<Element> selectList = rootElement.selectNodes("//select");  
  
                selectList.forEach((Element select) -> {  
                    String id = select.attributeValue("id");  
                    String resultType = select.attributeValue("resultType");  
                    String paramterType = select.attributeValue("paramterType");  
                    String sqlText = select.getTextTrim();  
  
                    MappedStatement mappedStatement = new MappedStatement();
                    mappedStatement.setId(id);  
                    mappedStatement.setResultType(resultType);  
                    mappedStatement.setParamterType(paramterType);  
                    mappedStatement.setSql(sqlText);  
  
                    configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);  
                });  
  
            } catch (DocumentException e) {  
                throw new RuntimeException(e);  
            }  
        });  
    }  
}