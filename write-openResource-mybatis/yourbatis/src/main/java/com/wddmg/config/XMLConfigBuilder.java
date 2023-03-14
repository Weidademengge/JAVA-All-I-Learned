package com.wddmg.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.wddmg.io.Resources;
import com.wddmg.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 专门解析核心配置文件的解析类
 * @author duym
 * @version $ Id: XMLConfigBuilder, v 0.1 2023/03/08 9:14 banma-0163 Exp $
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 使用dom4j + xpath 解析配置文件，封装Configuration对象
     * @param inputStream
     * @return
     */
    public Configuration parse(InputStream inputStream) throws DocumentException {
        //dom4j解析inputStream
        Document document = new SAXReader().read(inputStream);
        //获取到xml的根节点对象
        Element rootElement = document.getRootElement();

        // //property是xpath表达式，不管property在第几层，直接找到它
        // 返回的list里的每条对应driverClassName、url、username、password
        List<Element> list = rootElement.selectNodes("//property");

        //Properties 这个存储list中的key value
        Properties properties = new Properties();
        for(Element element : list){
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        //使用druid创建数据连接池
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getProperty("driverClassName"));
        druidDataSource.setUrl(properties.getProperty("url"));
        druidDataSource.setUsername(properties.getProperty("username"));
        druidDataSource.setPassword(properties.getProperty("password"));

        //创建好的数据源对象封装到Configuration对象中
        configuration.setDataSource(druidDataSource);

        //--------------解析映射配置文件
        // 1.获取映射配置文件的路径
        // 2.根据路径进行映射配置文件的加载解析
        // 3.封装到MappedStatement--->configuration里面的map集合中

        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for(Element element:mapperList){
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);

            //专门解析映射配置文件的对象
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }
        return configuration;
    }
}
