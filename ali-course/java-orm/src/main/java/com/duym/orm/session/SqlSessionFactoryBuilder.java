package com.duym.orm.session;
  
import java.io.InputStream;

import com.duym.orm.config.Resources;
import com.duym.orm.config.XmlConfigurationBuilder;
import com.duym.orm.model.Configuration;
import org.dom4j.DocumentException;  
  
/**  
 * SQL会话对象工厂的建造者  
 */  
public class SqlSessionFactoryBuilder {  
  
    /**  
     * SQL会话对象工厂的建造方法  
     * @param inputStream 输入流  
     * @return SQL会话对象工厂  
     * @throws DocumentException  
     */
    public static SqlSessionFactory build(InputStream inputStream) throws DocumentException {  
        // 1.使用dom4j解析配置文件，将解析出来的内容封装到Configuration对象  
        Configuration configuration = XmlConfigurationBuilder.build(inputStream);
        // 2.创建SQL会话对象工厂：生产SqlSession  
        return new DefaultSqlSessionFactory(configuration);  
    }  
  
    /**  
     * SQL会话对象工厂的建造方法  
     * @param path 配置文件路径  
     * @return SQL会话对象工厂  
     * @throws DocumentException  
     */
    public static SqlSessionFactory build(String path) throws DocumentException {  
        return build(Resources.getResourceAsStream(path));
    }  
  
}