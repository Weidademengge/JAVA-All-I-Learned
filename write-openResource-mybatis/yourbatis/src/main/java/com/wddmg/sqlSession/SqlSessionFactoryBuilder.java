package com.wddmg.sqlSession;

import com.wddmg.config.XMLConfigBuilder;
import com.wddmg.pojo.Configuration;
import org.dom4j.DocumentException;
import java.io.InputStream;

/**
 * 解析配置文件xml的inputStream
 * @author duym
 * @version $ Id: SqlSessionFactoryBuilder, v 0.1 2023/03/08 9:09 banma-0163 Exp $
 */
public class SqlSessionFactoryBuilder {

    /**
     * 1、解析配置i文件，封装容器对象
     * 2、创建SqlSessionFactory工厂对象
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException {

        //1、解析配置i文件，封装容器对象 XMLConfigBuilder:专门解析核心配置文件的解析类
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(inputStream);

        //2、创建SqlSessionFactory工厂对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
