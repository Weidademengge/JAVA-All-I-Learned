
为了区别源码Mybatis，手写的就叫yourbatis。

`此手写版yourbatis是阿里斑马培训期间写的代码，以及b站博学谷13小时撕开Mybatis源码视频。由于阿里代码不能对外公开，下面链接是b站视频，可以点击观看。`
b站博学谷视频： https://www.bilibili.com/video/BV1R14y1W7yS/?spm_id_from=333.337.search-card.all.click

# 一、关于简易版到底有多简易

## 为什么会有mybatis

手写先看一下JDBC创建过程
![[Pasted image 20230307090738.png]]

JDBC问题分析：

1. 数据库配置信息硬编码------->配置文件
2. 频繁创建释放数据库连接--------->连接池
3. sql语句、参数、返回结果集获取 均存在硬编码问题----------->XML配置文件
4. 需要手动封装结果集，较为繁琐-------------->反射、内省

根据JDBC存在的问题，我们来实现一个简易的Mybatis

## 主要实现的功能和方法

下图是整个简易版的流程图，写程序的过程中若是有不清楚的可以看这个图。
![[Pasted image 20230314140754.png]]

# 二、创建父工程write-openResource-mybatis

## 1、创建一个空的父工程write-openResource-mybatis

创建后留下pom文件，剩下全删了。由于我已经有父工程了，所以write-openResource-mybatis作为一个父模块创建
![[Pasted image 20230313140310.png]]

## 2、创建yourbatis和yourbatis_test模块

点击右键，点击New和Module,分别创建yourbatis_test和yourbatis
- yourbatis_test是用来测试的
- yourbatis是手写的类源码

![[Pasted image 20230313140450.png]]

在yourbatis模块中，更改pom文件。主要是添加\<dependecy>依赖。请注意mysql数据库的版本。这里我用的是mysql8的版本，这里和后面的sqlMapperConfig中数据库配置有关系。后面无论是mysql5还是mysql8都会进行配置。

yourbatis的pom文件
```xml
<?xml version="1.0" encoding="UTF-8"?>  
<project xmlns="http://maven.apache.org/POM/4.0.0"  
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">  
    <parent>        <artifactId>write-openResource-mybatis</artifactId>  
        <groupId>com.wddmg</groupId>  
        <version>1.0-SNAPSHOT</version>  
    </parent>    <modelVersion>4.0.0</modelVersion>  
  
    <artifactId>yourbaytis</artifactId>  
  
    <dependencies>        
	    <dependency>           
		    <groupId>mysql</groupId>  
            <artifactId>mysql-connector-java</artifactId>  
            <version>8.0.30</version>  
        </dependency>        
	    <dependency>            
	        <groupId>junit</groupId>  
            <artifactId>junit</artifactId>  
            <version>4.12</version>  
            <scope>test</scope>  
        </dependency>        
        <!-- dom4j-->  
        <dependency>  
            <groupId>dom4j</groupId>  
            <artifactId>dom4j</artifactId>  
            <version>1.6.1</version>  
        </dependency>        
        <!-- xpath依赖-->  
        <dependency>  
            <groupId>jaxen</groupId>  
            <artifactId>jaxen</artifactId>  
            <version>1.1.6</version>  
        </dependency>        
        <dependency>            
	        <groupId>com.alibaba</groupId>  
            <artifactId>druid</artifactId>  
            <version>1.2.14</version>  
        </dependency>        
        <dependency>            
	        <groupId>log4j</groupId>  
            <artifactId>log4j</artifactId>  
            <version>1.2.17</version>  
        </dependency>  
  
    </dependencies></project>
```

以下是各个依赖的说明：
| 依赖名 | 说明          |
|:------ |:------------- |
| junit  | 用于@Test注解 |
| dom4j  | 用于解析XML              |
| jaxen  | xpath表达式              |
| druid  | 数据库连接池              |
| log4j  | 日志              |

yourbatis_test的pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>  
<project xmlns="http://maven.apache.org/POM/4.0.0"  
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">  
    <parent>        <artifactId>write-openResource-mybatis</artifactId>  
        <groupId>com.wddmg</groupId>  
        <version>1.0-SNAPSHOT</version>  
    </parent>    <modelVersion>4.0.0</modelVersion>  
  
    <artifactId>yourbatis_test</artifactId>  
  
    <dependencies>        
	    <dependency>            
		    <groupId>com.wddmg</groupId>  
            <artifactId>yourbaytis</artifactId>  
            <version>1.0-SNAPSHOT</version>  
        </dependency>        
        <dependency>            
	        <groupId>junit</groupId>  
            <artifactId>junit</artifactId>  
            <version>4.13.2</version>  
            <scope>test</scope>  
        </dependency>    
    </dependencies>  
</project>
```

yourbatis_test只需要两个依赖，一个是yourbatis模块，一个是junit用于测试

在yourbayis_test中的main包下添加resources，添加数据库配置文件sqlMapConfig.xml，用于配置数据库的基本信息
![[Pasted image 20230313143146.png]]

在sqlMapConfig.xml中，我们需要配置两项
- 数据库信息----->用于连接数据库
- 映射文件----->用于一次性加载配置文件时，同时加载映射文件
```xml
<configuration>  
  
<!--    配置数据库信息-->  
    <dataSource>  
        <property name ="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>  
        <property name ="url" value="jdbc:mysql://localhost:3306/test?serverTimezone=UTC&amp;useUnicode=true&amp;characterEncoding=utf8"></property>  
        <property name ="username" value="root"></property>  
        <property name ="password" value="root"></property>  
    </dataSource><!--    引入映射配置文件-->  
    <mappers>  
        <mapper resource="mapper/UserMapper.xml"></mapper>  
    </mappers>  
</configuration>
```

这里我们同样在resource下建一个mapper文件夹，用于放映射文件。这里放入两个映射文件：
- userMapper.xml------->映射user类
- productMapper.xml--------->做个对比，没啥乱用

在UserMapper.xml中，添加要查询的信息和sql
```xml
<mapper namespace="com.wddmg.dao.IUserDao">  
  
    <!-- 唯一标识：namesapce.id statementId-->  
    <!--查询所有-->  
    <!--        规范：接口的全路径要和namespace的值保持一致  
            接口中的方法名要和id的值保持一致  
    -->  
    <select id="selectList" resultType="com.wddmg.pojo.User">  
        select * from user  
    </select>  
  
    <!--按条件查询-->  
    <!--        User user = new User()        user.setId(1);        user.setUserName("tom");     -->    
    <select id="selectOne" resultType="com.wddmg.pojo.User" parameterType="com.wddmg.pojo.User">  
        select * from user where id = #{id} and username = #{username}  
    </select>  
  
  
</mapper>
```

各个标签和属性的含义：
- \<select>标签用于查询语句
	- id：用于标识哪种select标签，有的select是查询所有，有的是查询单个，还有不同条件查询
	- resultType：返回的结果要封装的类。为了后面的反射这里要写全路径
	- parameterType：参数类型，是指#{id}和#{username}属性值，是要传入的值

- \<mapper>就是跟标签
	- namespace：帮助定位查询是哪个表，单靠id不行啊。说的再通俗点，就是通过namespace+id就能知道是user表还是product表。
	- 后面的statementId就是指的这个namespace的值

# 三、Resources类

Resources类就是用来读取XML的类。
在yourbatis模块中，新建一个io包，包下新建一个Resources类。
![[Pasted image 20230313151137.png]]

```java
package com.wddmg.io;  
  
import java.io.InputStream;  
  
/**  
 * @author duym  
 * @version $ Id: Resources, v 0.1 2023/03/07 10:56 banma-0163 Exp $  
 */public class Resources {  
  
    /**  
     * 根据配置文件的路径，加载配置文件成字节输入流，存到内存中  
     * @param path  
     * @return  
     */  
    public static InputStream getResourceAsStream(String path){  
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);  
        return resourceAsStream;  
    }  
}
```

接下来我们在yourbatis_test的test包下，新建一个test类，就叫YourBatisTest测试类，用于测试是否读取到xml文件。
![[Pasted image 20230313151810.png]]

```java
package com.wddmg.test;  
  
import com.wddmg.dao.IUserDao;  
import com.wddmg.io.Resources;  
import com.wddmg.pojo.User;  
import com.wddmg.sqlSession.SqlSession;  
import com.wddmg.sqlSession.SqlSessionFactory;  
import com.wddmg.sqlSession.SqlSessionFactoryBuilder;  
import org.junit.Test;  
import java.io.InputStream;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: IPersistentTest, v 0.1 2023/03/07 10:59 banma-0163 Exp $  
 */public class YourBatisTest {  
    
    @Test  
    public void test1() throws Exception {  
  
        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析  
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");  
		System.out.println(resourceAsStream);
    }  
  
}
```

控制台输出：
```java
java.io.BufferedInputStream@7e774085

Process finished with exit code 0
```

可以看到已经输出了结果，虽然显示的是地址，但至少不是null。

# 四、创建两个JavaBean容器

接下来我们创建两个映射配置类
- Configuration----->全局配置类，用于存放核心配置文件解析内容，对应sqlMapConfig
- MappedStatement----->映射配置文件，对应的就是usermapper

![[Pasted image 20230313154013.png]]

Configuration类：
```java
package com.wddmg.pojo;  
  
import javax.sql.DataSource;  
import java.util.HashMap;  
import java.util.Map;  
  
/**  
 * 全局配置类：存放核心配置文件解析出来的内容  
 * @author duym  
 * @version $ Id: Configuration, v 0.1 2023/03/08 9:01 banma-0163 Exp $  
 */public class Configuration {  
  
    //数据源对象
    private DataSource dataSource;  
  
    /**  
     * map集合存放mappedStatement，这是因为一个mapper中会有多个sql语句，我们可以把这些sql都存放在这个map中
     * key:statementId--namespace.id     * value:MapppedStatement     
     * 
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
```


MappedStatement类：
```java
package com.wddmg.pojo;  
  
/**  
 * 映射配置文件  
 * @author duym  
 * @version $ Id: MappedStatement, v 0.1 2023/03/08 8:57 banma-0163 Exp $  
 */public class MappedStatement {  
  
    //唯一标识 statementId:namespace.id    private String statementId;  
    //返回值类型  
    private String resultType;  
    //参数值类型  
    private String parameterType;  
    //sql语句  
    private String sql;  
  
    public String getStatementId() {  
        return statementId;  
    }  
  

    public void setStatementId(String statementId) {  
        this.statementId = statementId;  
    }  
  
    public String getResultType() {  
        return resultType;  
    }  
  
    public void setResultType(String resultType) {  
        this.resultType = resultType;  
    }  
  
    public String getParameterType() {  
        return parameterType;  
    }  
  
    public void setParameterType(String parameterType) {  
        this.parameterType = parameterType;  
    }  
  
    public String getSql() {  
        return sql;  
    }  
  
    public void setSql(String sql) {  
        this.sql = sql;  
    }  
}
```

# 五、创建各种类--->要执行sql了

在yourbatis包下，创建sqlSession包以下几个类用于生产sqlSession

![[Pasted image 20230313155553.png]]

SqlSessionFactory接口
```java
package com.wddmg.sqlSession;  
  
/**  
 * @author duym  
 * @version $ Id: SqlSessionFactory, v 0.1 2023/03/08 9:11 banma-0163 Exp $  
 */public interface SqlSessionFactory {  
  
    /**  
     * 1、生产sqlSession对象  
     * 2、创建执行器对象  
     * @return  
     */  
    SqlSession openSession();  
}
```

创建SqlSessionFactoryBuilder
```java
package com.wddmg.sqlSession;  
  
import com.wddmg.config.XMLConfigBuilder;  
import com.wddmg.pojo.Configuration;  
import org.dom4j.DocumentException;  
import java.io.InputStream;  
  
/**  
 * 解析配置文件xml的inputStream  
 * @author duym  
 * @version $ Id: SqlSessionFactoryBuilder, v 0.1 2023/03/08 9:09 banma-0163 Exp $  
 */public class SqlSessionFactoryBuilder {  
  
    /**  
     * 1、解析配置文件，封装容器对象  
     * 2、创建SqlSessionFactory工厂对象  
     * @param inputStream  
     * @return  
     */  
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException {  
  
        //1、XMLConfigBuilder:专门解析核心配置文件的解析类  
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();  
        Configuration configuration = xmlConfigBuilder.parse(inputStream);  
  
        //2、创建SqlSessionFactory工厂对象  
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);  
        return defaultSqlSessionFactory;  
    }  
}
```

在config包下，创建xml解析类

![[Pasted image 20230313160301.png]]

创建XMLConfigBuilder--->用于解析Configuration
```java
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
 */public class XMLConfigBuilder {  
  
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
        //获取到xml的根节点对象，得到的就是<configuration></configuration>
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
```

创建XMLMapperBuilder解析类
```java
package com.wddmg.config;  
  
import com.wddmg.pojo.Configuration;  
import com.wddmg.pojo.MappedStatement;  
import org.dom4j.Document;  
import org.dom4j.DocumentException;  
import org.dom4j.Element;  
import org.dom4j.io.SAXReader;  
import java.io.InputStream;  
import java.util.List;  
  
/**  
 * 专门解析映射配置文件的类  
 * parse方法：解析映射配置文件--->mappedStatement ----->configuration里面的map集合中  
 * @author duym  
 * @version $ Id: XMLMapperBuilder, v 0.1 2023/03/08 13:23 banma-0163 Exp $  
 */public class XMLMapperBuilder {  
  
    private Configuration configuration;  
  
    public XMLMapperBuilder(Configuration configuration) {  
        this.configuration = configuration;  
    }  
  
    public void parse(InputStream resourceAsStream) throws DocumentException {  
  
        Document document = new SAXReader().read(resourceAsStream);  
        Element rootElement = document.getRootElement();  
  
        //这里只查找select标签，其他先不写了  
        List<Element> selectList = rootElement.selectNodes("//select");  
        String namespace = rootElement.attributeValue("namespace");  
        for(Element element:selectList){  
  
            String id = element.attributeValue("id");  
            String resultType = element.attributeValue("resultType");  
            String parameterType = element.attributeValue("parameterType");  
            String sql = element.getTextTrim();  
  
            //封装mappedStatement对象  
            MappedStatement mappedStatement = new MappedStatement();  
  
            //StatementId: namespace.id  
            String statementId = namespace + "." + id;  
            mappedStatement.setStatementId(statementId);  
            mappedStatement.setResultType(resultType);  
            mappedStatement.setParameterType(parameterType);  
            mappedStatement.setSql(sql);  
            mappedStatement.setSqlCommandType("select");  
  
            //将封装好的mappedStatement封装到configuration中  
            configuration.getMappedStatementMap().put(statementId,mappedStatement);  
        }  
    }  
}
```

这里我们配置好后，在test中输出一下
```java
package com.wddmg.test;  
  
import com.wddmg.dao.IUserDao;  
import com.wddmg.io.Resources;  
import com.wddmg.pojo.User;  
import com.wddmg.sqlSession.SqlSession;  
import com.wddmg.sqlSession.SqlSessionFactory;  
import com.wddmg.sqlSession.SqlSessionFactoryBuilder;  
import org.junit.Test;  
import java.io.InputStream;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: IPersistentTest, v 0.1 2023/03/07 10:59 banma-0163 Exp $  
 */public class YourBatisTest {  
    
    @Test  
    public void test1() throws Exception {  
  
        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析  
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");  
		//System.out.println(resourceAsStream);
		//2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象  
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);  
	  
		System.out.println(sqlSessionFactory);
    }  
  
}
```

控制台输出
```txt
com.wddmg.sqlSession.DefaultSqlSessionFactory@548b7f67

Process finished with exit code 0
```

这里打印出地址了，证明中间没报啥错，如果想看详细信息，debug一下，看一下sqlSessionFactory中的属性

六、创建SqlSession

在我们已经有了SqlSessionFactory接口之后，我们需要利用工厂模式生产SqlSession。
在生产SqlSession之前，我们先创建一个DefaultSqlSessionFactory实现类。

![[Pasted image 20230314090221.png]]

DefaultSqlSessionFactory
```java
package com.wddmg.sqlSession;  
  
  
import com.wddmg.executor.Executor;  
import com.wddmg.executor.SimpleExecutor;  
import com.wddmg.pojo.Configuration;  
  
/**  
 * @author duym  
 * @version $ Id: DefaultSqlSessionFactory, v 0.1 2023/03/08 13:48 banma-0163 Exp $  DefaultSqlSessionFactory
 */public class DefaultSqlSessionFactory implements SqlSessionFactory{  
  
  
    private Configuration configuration;  
  
    public DefaultSqlSessionFactory(Configuration configuration) {  
        this.configuration = configuration;  
    }  
  
    @Override  
    public SqlSession openSession(){  
        // 1、创建执行器对象  
        Executor simpleExecutor = new SimpleExecutor();  
  
  
        //  2、创建sqlSession对象  
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession(configuration,simpleExecutor);  
  
        return defaultSqlSession;  
  
  
    }  
}
```

可以看到在openSession方法中，需要创建两个对象，一个是执行器Executor，另一个是sqlSession对象。
这里我们创建SqlSession接口。
SqlSession
```java
package com.wddmg.sqlSession;  
  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: SqlSession, v 0.1 2023/03/08 13:57 banma-0163 Exp $  
 */public interface SqlSession {  
  
    /**  
     * 查询多个结果  
     * @return  
     */  
    <E> List<E> selectList(String statementId,Object param) throws Exception;  
  
    /**  
     * 查询单个结果  
     * @param statementId  
     * @param param  
     * @return  
     * @param <T>  
     */  
    <T> T selectOne(String statementId,Object param) throws Exception;  
  
    /**  
     * 清除资源  
     */  
    void close();  
  
    /**  
     * @return  
     * @param <T>  
     */  
    <T> T getMapper(Class<?> mapperClass);  
  
}
```

创建DefaultSqlSession实现SqlSession接口，,在这个类中需要创建增删改查的方法，这里我们只创建查询的方法，有兴趣的小伙伴可以补上增删改。
DefaultSqlSession
```java
package com.wddmg.sqlSession;  
  
import com.wddmg.executor.Executor;  
import com.wddmg.pojo.Configuration;  
import com.wddmg.pojo.MappedStatement;  
  
import java.lang.reflect.*;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: DefaultSqlSession, v 0.1 2023/03/08 13:57 banma-0163 Exp $  
 */public class DefaultSqlSession implements SqlSession{  
  
    private Configuration configuration;  
  
    private Executor executor;  
  
    public DefaultSqlSession(Configuration configuration, Executor executor) {  
        this.configuration = configuration;  
        this.executor = executor;  
    }  
	/**  
	 * 查询多个结果  
	 * @param statementId 定位要执行的sql语句，从而执行  
	 * @param param 查询的参数,下面的？  
	 *              select * from user where username lke '%?%'  
	 * @return  
	 * @param <E>  
	 * @throws Exception  
	 */
    @Override  
    public <E> List<E> selectList(String statementId, Object param) throws Exception {  
        // 查询操作委派给底层的执行器  
        // query():执行底层的JDBC 1、获取数据源对象，连接 2、sql是什么，参数是什么  
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);  
        List<E> list = executor.query(configuration,mappedStatement,param);  
        return list;  
    }  
    /**  
	 * 查询单个结果  
	 * @param statementId  
	 * @param param  
	 * @return  
	 * @param <T>  
	 * @throws Exception  
	 */
    @Override  
    public <T> T  selectOne(String statementId, Object param) throws Exception {  
        // 去调用selectList();  
        List<Object> list = this.selectList(statementId, param);  
        if(list.size() == 1){  
            return (T) list.get(0);  
        }else if(list.size() > 1){  
            throw new RuntimeException("返回结果过多");  
        }else{  
            return null;  
        }  
    }  
    /**  
	 * 关闭连接池  
	 */
    @Override  
    public void close() {  
        executor.close();  
    }  
}
```

# 六、创建执行器

执行器是真正执行sql的类，在DefaultSqlSessionFactory类中创建了Executor，并传入DefaultSqlSession。我们在executor包下，创建一个Executor接口，并创建一个SimpleExecutor实现类

![[Pasted image 20230314092543.png]]

Executor接口
```java
package com.wddmg.executor;  
  
import com.wddmg.pojo.Configuration;  
import com.wddmg.pojo.MappedStatement;  
import java.sql.SQLException;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: Executor, v 0.1 2023/03/08 14:08 banma-0163 Exp $  
 */public interface Executor {  
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, Exception;  
  
    void close();  
}
```

SimpleExecutor实现类
```java
package com.wddmg.executor;  
  
import com.wddmg.config.BoundSql;  
import com.wddmg.pojo.Configuration;  
import com.wddmg.pojo.MappedStatement;  
import com.wddmg.utils.GenericTokenParser;  
import com.wddmg.utils.ParameterMapping;  
import com.wddmg.utils.ParameterMappingTokenHandler;  
import java.beans.PropertyDescriptor;  
import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
import java.sql.*;  
import java.util.ArrayList;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: SimpleExecutor, v 0.1 2023/03/08 14:09 banma-0163 Exp $  
 */public class SimpleExecutor implements Executor{  
  
    private Connection connection = null;  
  
    private PreparedStatement preparedStatement = null;  
  
    private ResultSet resultSet = null;  
    @Override  
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {  
        //1、加载驱动，获取数据库连接  
        connection = configuration.getDataSource().getConnection();  
  
        /**  
         * 获取preparedStatement预编译对象  
         * 获取要执行的sql语句  
         *                                     自定义的占位符  
         *      select * from user where id = #{id} and username = #{username}  
         * 替换：select * from user where id = ? and username = ?  
         * 解析替换的过程中，#{id}里面的值保存下来  
         */  
        String sql = mappedStatement.getSql();  
        BoundSql boundSql = getBoundSql(sql);  
        //获得最终需要的sql  
        String finalSql = boundSql.getFinalSql();  
        preparedStatement = connection.prepareStatement(finalSql);  
  
        //3、设置参数  
        //com.wddmg.pojo.User  
        String parameterType = mappedStatement.getParameterType();  
        Class<?> paramterTypeClass = Class.forName(parameterType);  
  
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();  
        for(int i = 0;i<parameterMappingList.size();i++){  
            ParameterMapping parameterMapping = parameterMappingList.get(i);  
            //id username  
            String paramName = parameterMapping.getContent();  
            //反射  
            Field declaredField = paramterTypeClass.getDeclaredField(paramName);  
            //属性有可能是私有的，暴力访问  
            declaredField.setAccessible(true);  
  
            Object value = declaredField.get(param);  
            //赋值占位符  
            preparedStatement.setObject(i+1,value);  
        }  
  
        // 4、执行sql，发起查询  
        resultSet = preparedStatement.executeQuery();  
  
        // 5、处理返回结果集  
        List<E> list= new ArrayList<>();  
        while(resultSet.next()){  
            //元数据信息 包含了字段名和值  
            ResultSetMetaData metaData = resultSet.getMetaData();  
  
            //com.bilibili.pojo.User  
            String resultType = mappedStatement.getResultType();  
            Class<?> resultTypeClass = Class.forName(resultType);  
            Object o = resultTypeClass.newInstance();  
  
  
            for(int i = 1;i<= metaData.getColumnCount();i++){  
  
                //字段名 id username                String columnName = metaData.getColumnName(i);  
                //字段的值  
                Object value = resultSet.getObject(columnName);  
  
                // 问题：现在要封装到哪一个实体中--------->封装到resultType对应的类  
                // 封装-->用到内省库，属性描述器  
                // 属性描述其：通过API方法获取某个属性的读写方法  
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultTypeClass);  
                Method writeMethod = propertyDescriptor.getWriteMethod();  
  
                //参数1：需要个实例对象  
                //参数2:要设置的值  
                writeMethod.invoke(o,value);  
  
            }  
            list.add((E) o);  
        }  
        return list;  
    }  
  
    /**  
     * 1、#{}占位符替换成？  
     * 2、解析替换的过程中，将#{}中的值保存下来  
     * @param sql  
     * @return  
     */  
    private BoundSql getBoundSql(String sql) {  
  
        //1、创建标记处理器：配合标记解析器完成标记的处理解析工作  
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();  
        //2、创建标记解析器  
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);  
  
        //#{}占位符替换成？ 2、解析替换的过程中 将#{}里面的值保存下来，存在ParameterMapping中  
        String finalSql = genericTokenParser.parse(sql);  
  
        //#{}里面值的集合 id,username        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();  
        BoundSql boundSql = new BoundSql(finalSql, parameterMappings);  
        return boundSql;  
    }  
  
    /**  
     * 释放资源  
     */  
    @Override  
    public void close() {  
        //释放资源  
        if(resultSet != null){  
            try {  
                resultSet.close();  
            }catch (SQLException e){  
                e.printStackTrace();  
            }  
        }  
        if(preparedStatement != null){  
            try{  
                preparedStatement.close();  
            }catch (SQLException e){  
                e.printStackTrace();  
            }  
        }  
        if(connection != null){  
            try{  
                connection.close();  
            }catch (SQLException e){  
                e.printStackTrace();  
            }  
        }  
    }  
}
```

由于我们在写mybatis中映射xml时，会使用#{}这种形势，因此我们在执行语句前需要对#{}进行解析，这里我们创建一个utils包，在包中，我们创建三个类和一个接口，用于解析sql中的#{}。这里是从源码中提取出来的
![[Pasted image 20230314093333.png]]

GenericTokenParser类
```java
package com.wddmg.utils;  
  
/**  
 * @author Clinton Begin  
 */public class GenericTokenParser {  
  
  private final String openToken; //开始标记  
  private final String closeToken; //结束标记  
  private final TokenHandler handler; //标记处理器  
  
  public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {  
    this.openToken = openToken;  
    this.closeToken = closeToken;  
    this.handler = handler;  
  }  
  
  /**  
   * 解析${}和#{}  
   * @param text  
   * @return  
   * 该方法主要实现了配置文件、脚本等片段中占位符的解析、处理工作，并返回最终需要的数据。  
   * 其中，解析工作由该方法完成，处理工作是由处理器handler的handleToken()方法来实现  
   */  
  public String parse(String text) {  
    // 验证参数问题，如果是null，就返回空字符串。  
    if (text == null || text.isEmpty()) {  
      return "";  
    }  
  
    // 下面继续验证是否包含开始标签，如果不包含，默认不是占位符，直接原样返回即可，否则继续执行。  
    int start = text.indexOf(openToken, 0);  
    if (start == -1) {  
      return text;  
    }  
  
    // 把text转成字符数组src，并且定义默认偏移量offset=0、存储最终需要返回字符串的变量builder，  
    // text变量中占位符对应的变量名expression。判断start是否大于-1(即text中是否存在openToken)，如果存在就执行下面代码  
    char[] src = text.toCharArray();  
    int offset = 0;  
    final StringBuilder builder = new StringBuilder();  
    StringBuilder expression = null;  
    while (start > -1) {  
      // 判断如果开始标记前如果有转义字符，就不作为openToken进行处理，否则继续处理  
      if (start > 0 && src[start - 1] == '\\') {  
        builder.append(src, offset, start - offset - 1).append(openToken);  
        offset = start + openToken.length();  
      } else {  
        //重置expression变量，避免空指针或者老数据干扰。  
        if (expression == null) {  
          expression = new StringBuilder();  
        } else {  
          expression.setLength(0);  
        }  
        builder.append(src, offset, start - offset);  
        offset = start + openToken.length();  
        int end = text.indexOf(closeToken, offset);  
        while (end > -1) {////存在结束标记时  
          if (end > offset && src[end - 1] == '\\') {//如果结束标记前面有转义字符时  
            // this close token is escaped. remove the backslash and continue.  
            expression.append(src, offset, end - offset - 1).append(closeToken);  
            offset = end + closeToken.length();  
            end = text.indexOf(closeToken, offset);  
          } else {//不存在转义字符，即需要作为参数进行处理  
            expression.append(src, offset, end - offset);  
            offset = end + closeToken.length();  
            break;          }  
        }  
        if (end == -1) {  
          // close token was not found.  
          builder.append(src, start, src.length - start);  
          offset = src.length;  
        } else {  
          //首先根据参数的key（即expression）进行参数处理，返回?作为占位符  
          builder.append(handler.handleToken(expression.toString()));  
          offset = end + closeToken.length();  
        }  
      }  
      start = text.indexOf(openToken, offset);  
    }  
    if (offset < src.length) {  
      builder.append(src, offset, src.length - offset);  
    }  
    return builder.toString();  
  }  
}
```

ParameterMapping类
```java
package com.wddmg.utils;  
  
/**  
 * @author duym  
 * @version $ Id: ParameterMapping, v 0.1 2023/03/08 15:48 banma-0163 Exp $  
 */public class ParameterMapping {  
  
    private String content;  
  
    public ParameterMapping(String content) {  
        this.content = content;  
    }  
  
    public String getContent() {  
        return content;  
    }  
  
    public void setContent(String content) {  
        this.content = content;  
    }  
}
```

ParameterMappingTokenHandler
```java
package com.wddmg.utils;  
  
import java.util.ArrayList;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: ParameterMappingTokenHandler, v 0.1 2023/03/08 15:47 banma-0163 Exp $  
 */public class ParameterMappingTokenHandler implements TokenHandler {  
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();  
  
    // context是参数名称 #{id} #{username}  
    @Override  
    public String handleToken(String content) {  
        parameterMappings.add(buildParameterMapping(content));  
        return "?";  
    }  
  
    private ParameterMapping buildParameterMapping(String content) {  
        ParameterMapping parameterMapping = new ParameterMapping(content);  
        return parameterMapping;  
    }  
  
    public List<ParameterMapping> getParameterMappings() {  
        return parameterMappings;  
    }  
  
    public void setParameterMappings(List<ParameterMapping> parameterMappings) {  
        this.parameterMappings = parameterMappings;  
    }  
  
}
```

TokenHandler接口
```java
package com.wddmg.utils;  
  
/**  
 * @author Clinton Begin  
 */public interface TokenHandler {  
  String handleToken(String content);  
}
```

# 七、非代理测试

我们把所有的都配置好后，接下来在test中测试一下是否可行

```java
package com.wddmg.test;  
  
import com.wddmg.dao.IUserDao;  
import com.wddmg.io.Resources;  
import com.wddmg.pojo.User;  
import com.wddmg.sqlSession.SqlSession;  
import com.wddmg.sqlSession.SqlSessionFactory;  
import com.wddmg.sqlSession.SqlSessionFactoryBuilder;  
import org.junit.Test;  
import java.io.InputStream;  
import java.util.List;  
  
/**  
 * @author duym
 * @version $ Id: IPersistentTest, v 0.1 2023/03/07 10:59 banma-0163 Exp $  
 */public class YourBatisTest {  
  
    /**  
     * 传统方式（不使用mapper代理）测试  
     */  
    @Test  
    public void test1() throws Exception {  
  
        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析  
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");  
  
        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);  
  
        //3、生产sqlSession;创建执行器  
        SqlSession sqlSession = sqlSessionFactory.openSession();  
  
        //4、调用sqlSession方法  
		User user = new User();  
		user.setId(1);  
		user.setUsername("Tom");  
		User user2 = sqlSession.selectOne("user.selectOne", user);  
		  
		System.out.println("条件查询---->"+user2);  
		  
		System.out.println("查询全部----------");  
		List<User> list = sqlSession.selectList("user.selectList", null);  
		for (User user1 : list) {  
		    System.out.println(user1);  
		}
  
        //5、释放资源  
        sqlSession.close();  
    }  
  
}
```

输出结果：
```txt
log4j:WARN No appenders could be found for logger (com.alibaba.druid.pool.DruidDataSource).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
条件查询---->User{id=1, username='Tom'}
查询全部----------
User{id=1, username='Tom'}
User{id=2, username='Jerry'}
```

可以看到无论是全部查询还是条件查询，最终都返回了结果

# 八、代码优化

先说一下要优化啥，正常写mybatis是不会在自己代码中写YourBatisTest中的1、2、3、4的。我们都是把这些代码封装到dao层，所以我们现在yourbatis_test中的main创建两个包，dao和pojo
![[Pasted image 20230314103720.png]]

创建User类
```java
package com.wddmg.pojo;  
  
/**  
 * @author duym  
 * @version $ Id: User, v 0.1 2023/03/08 14:36 banma-0163 Exp $  
 */public class User {  
  
    private Integer id;  
  
    private String username;  
  
    public Integer getId() {  
        return id;  
    }  
  
    public void setId(Integer id) {  
        this.id = id;  
    }  
  
    public String getUsername() {  
        return username;  
    }  
  
    public void setUsername(String username) {  
        this.username = username;  
    }  
  
    @Override  
    public String toString() {  
        return "User{" +  
                "id=" + id +  
                ", username='" + username + '\'' +  
                '}';  
    }  
}
```

dao包下创建IUserDao接口
```java
package com.wddmg.dao;  
  
import com.wddmg.pojo.User;  
import org.dom4j.DocumentException;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: IUserDao, v 0.1 2023/03/10 9:07 banma-0163 Exp $  
 */public interface IUserDao {  
  
    /**  
     * 查询所有  
     * @return  
     */  
    List<User> findAll() throws DocumentException;  
  
    /**  
     * 根据多条件查询  
     * @param user  
     * @return  
     */  
    User findByCondition(User user) throws Exception;  
}
```

dao包下创建UserDaoImpl实现类，并把加载、解析、生产、调用、关闭全部封装到实现类中。（这里可能会有人问，在使用mybatis时没有实现类，也没做这些步骤的处理，这个会在之后的动态代理解决）
```java
package com.wddmg.dao;  
  
import com.wddmg.io.Resources;  
import com.wddmg.pojo.User;  
import com.wddmg.sqlSession.SqlSession;  
import com.wddmg.sqlSession.SqlSessionFactory;  
import com.wddmg.sqlSession.SqlSessionFactoryBuilder;  
import org.dom4j.DocumentException;  
import java.io.InputStream;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: UserDaoImpl, v 0.1 2023/03/10 9:09 banma-0163 Exp $  
 */public class UserDaoImpl implements IUserDao{  
  
    @Override  
    public List<User> findAll() throws Exception {  
  
        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析  
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");  
  
        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);  
  
        //3、生产sqlSession;创建执行器  
        SqlSession sqlSession = sqlSessionFactory.openSession();  
  
        //4、调用sqlSession方法  
		List<User> list = sqlSession.selectList("user.selectList", null);  
		for (User user1 : list) {  
		    System.out.println(user1);  
		}
  
        //5、释放资源  
        sqlSession.close();  
        return null;    }  
  
    @Override  
    public User findByCondition(User user) throws Exception {  
        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析  
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");  
  
        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);  
  
        //3、生产sqlSession;创建执行器  
        SqlSession sqlSession = sqlSessionFactory.openSession();  
  
        //4、调用sqlSession方法  
        User user3 = new User();  
        user3.setId(1);  
        user3.setUsername("Tom");  
        User user2 = sqlSession.selectOne("user.selectOne", user);  
  
        System.out.println(user2);  
  
        //5、释放资源  
        sqlSession.close();  
        return user3;  
    }  
}
```

在这个实现类中还存在两个问题：
- dao的实现类中存在重复的代码，整个操作的过程模板重复（加载、解析、创建、执行、释放）
- dao的实现类中存在硬编码问题，比如\<User user2 = sqlSession.selectOne("user.selectOne", user)>这句中，还是用user.selectOne

解决的方法是，直接把实现类干掉，我特么不要了，然后让代理模式创建代理对象，因为代理模式只需要接口就能创建，所以也就不需要实现类了。

回到我们的SqlSession接口中，我们新增一个生成代理对象的方法，放在了最后getMapper
```java
package com.wddmg.sqlSession;  
  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: SqlSession, v 0.1 2023/03/08 13:57 banma-0163 Exp $  
 */public interface SqlSession {  
  
    /**  
     * 查询多个结果  
     * @return  
     */  
    <E> List<E> selectList(String statementId,Object param) throws Exception;  
  
    /**  
     * 查询单个结果  
     * @param statementId  
     * @param param  
     * @return  
     * @param <T>  
     */  
    <T> T selectOne(String statementId,Object param) throws Exception;  
  
    /**  
     * 清除资源  
     */  
    void close();  
  
    /**  
	 * 生成代理对象  
	 * @return  
	 * @param <T>  
	 */  
	<T> T getMapper(Class<?> mapperClass);
  
}
```

回到SqlSession实现类DefaultSqlSession中，我们重写getMapper方法。这样无论调用SqlSession中任意方法，无论是findAll还是findByCondition，都会执行getMapper这个代理方法。
```java
package com.wddmg.sqlSession;  
  
import com.wddmg.executor.Executor;  
import com.wddmg.pojo.Configuration;  
import com.wddmg.pojo.MappedStatement;  
import java.lang.reflect.*;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: DefaultSqlSession, v 0.1 2023/03/08 13:57 banma-0163 Exp $  
 */public class DefaultSqlSession implements SqlSession{  
  
    private Configuration configuration;  
  
    private Executor executor;  
  
    public DefaultSqlSession(Configuration configuration, Executor executor) {  
        this.configuration = configuration;  
        this.executor = executor;  
    }  
  
    /**  
     * 查询多个结果  
     * @param statementId 定位要执行的sql语句，从而执行  
     * @param param 查询的参数,下面的？  
     *              select * from user where username lke '%?%'  
     * @return  
     * @param <E>  
     * @throws Exception  
     */    @Override  
    public <E> List<E> selectList(String statementId, Object param) throws Exception {  
        // 查询操作委派给底层的执行器  
        // query():执行底层的JDBC 1、获取数据源对象，连接 2、sql是什么，参数是什么  
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);  
        List<E> list = executor.query(configuration,mappedStatement,param);  
        return list;  
    }  
  
    /**  
     * 查询单个结果  
     * @param statementId  
     * @param param  
     * @return  
     * @param <T>  
     * @throws Exception  
     */    @Override  
    public <T> T  selectOne(String statementId, Object param) throws Exception {  
        // 去调用selectList();  
        List<Object> list = this.selectList(statementId, param);  
        if(list.size() == 1){  
            return (T) list.get(0);  
        }else if(list.size() > 1){  
            throw new RuntimeException("返回结果过多");  
        }else{  
            return null;  
        }  
    }  
  
    /**  
     * 关闭连接池  
     */  
    @Override  
    public void close() {  
        executor.close();  
    }  
  
    @Override  
    public <T> T getMapper(Class<?> mapperClass) {  
        //使用JDK动态代理生成基于接口的代理对象  
        Object proxy = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {  
  
            /**  
             * @param o 代理对象的引用，很少用  
             *  
             * @param method 被调用的方法的字节码对象  
             *  
             * @param objects 调用的方法的参数  
             *  
             * @return  
             * @throws Throwable  
             */            
            @Override  
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {  
                //具体的瑞吉：执行底层JDBC  
                //通过调用sqlSession里面的方法来完成方法调用  
                //参数的准备：1、statementId：com.wddmg.dao.IUserDao.findALl 2、param  
                //问题一：无法获取现有的statementId  
                //拿到findAll  
                String methodName = method.getName();  
                //com.wddmg.dao.IUserDao  
                String className = method.getDeclaringClass().getName();  
                String statementId = className + "." + methodName;  
  
                //方法调用，问题2：要调用sqlSession中的什么方法？  
                //改造当前工程：sqlCommandType  
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);  
                // select update delete insert  
                String sqlCommandType = mappedStatement.getSqlCommandType();  
                switch(sqlCommandType){  
                    case "select":  
                        //执行查询方法调用  
                        //问题3：该调用selectList还是selectOne  
                        Type genericReturnType = method.getGenericReturnType();  
                        //判断是否实现了泛型类型参数化-->其实就是返回值类型有没有泛型  
                        if(genericReturnType instanceof ParameterizedType){  
                            if(objects != null){  
                                return selectList(statementId,objects[0]);  
                            }  
                                return selectList(statementId,null);  
                        }  
                        return selectOne(statementId,objects[0]);  
                    case "update":  
                        //执行更新方法调用  
                        break;  
                    case "delete":  
                        //执行删除方法调用  
                        break;  
                    case "insert":  
                        //执行插入方法调用  
                        break;  
                }  
                return null;  
            }  
        });  
        return (T) proxy;  
    }  
}
```

写好代理后，我们在YourBatisTest类中，定义一个test2，用于测试：
```java
package com.wddmg.test;  
  
import com.wddmg.dao.IUserDao;  
import com.wddmg.io.Resources;  
import com.wddmg.pojo.User;  
import com.wddmg.sqlSession.SqlSession;  
import com.wddmg.sqlSession.SqlSessionFactory;  
import com.wddmg.sqlSession.SqlSessionFactoryBuilder;  
import org.junit.Test;  
import java.io.InputStream;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: IPersistentTest, v 0.1 2023/03/07 10:59 banma-0163 Exp $  
 */public class YourBatisTest {  
  
    /**  
     * 传统方式（不使用mapper代理）测试  
     */  
    @Test  
    public void test1() throws Exception {  
  
        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析  
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");  
  
        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);  
  
        //3、生产sqlSession;创建执行器  
        SqlSession sqlSession = sqlSessionFactory.openSession();  
  
        //4、调用sqlSession方法  
        User user = new User();  
        user.setId(1);  
        user.setUsername("Tom");  
        User user2 = sqlSession.selectOne("user.selectOne", user);  
  
        System.out.println("条件查询---->"+user2);  
  
        System.out.println("查询全部----------");  
        List<User> list = sqlSession.selectList("user.selectList", null);  
        for (User user1 : list) {  
            System.out.println(user1);  
        }  
  
        //5、释放资源  
        sqlSession.close();  
    }  
  
    /**  
     * mapper代理测试  
     */  
    @Test  
    public void test2() throws Exception {  
  
        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析  
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");  
  
        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);  
  
        //3、生产sqlSession;创建执行器  
        SqlSession sqlSession = sqlSessionFactory.openSession();  
  
        //4、调用sqlSession方法  
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);  
  
        User user1 = new User();  
        user1.setId(1);  
        user1.setUsername("Tom");  
        User user3 = userDao.findByCondition(user1);  
        System.out.println("条件查询---->" + user3);  
        System.out.println("查询全部----------");  
        List<User> all = userDao.findAll();  
        for(User user:all){  
            System.out.println(user);  
        }  
        //5、释放资源  
        sqlSession.close();  
    }  
}
```

我们在使用getMapper后通过代理实现了两个方法的查询，从而避免了问题2中的硬编码问题，但是问题一的流程还是写在test中，这个不用担心，因为mybatis是和spring框架一起使用，我们创建的这些都需要IOC帮我们创建，因此不会有我们来写这部分代码

查看结果输出，可以看到我们还是得到了想要的结果
```txt
log4j:WARN No appenders could be found for logger (com.alibaba.druid.pool.DruidDataSource).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
条件查询---->User{id=1, username='Tom'}
查询全部----------
User{id=1, username='Tom'}
User{id=2, username='Jerry'}

Process finished with exit code 0
```