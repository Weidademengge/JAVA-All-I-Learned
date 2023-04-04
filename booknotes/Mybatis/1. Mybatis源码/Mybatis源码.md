
# 1 源码导入

## 1.1 官网源码下载

mybatis源码地址：
[https://github.com/mybatis/mybatis-3](https://github.com/mybatis/mybatis-3)

进入github页面后，点击44tags可以看到历史版本
![[Pasted image 20230317093016.png]]


## 1.2 中文注释的源码

这里我的github提供了带有中文注释的mybatis源码，并且对于mybatis的调试过程中的bug都已解决，若是下载github的高版本可能会有其他问题。希望直接git我的mybatis版本，后面的调试也是根据我这个版本解决的问题。
![[Pasted image 20230317102629.png]]

# 2 创建空的父项目 mybatis-3-open-resource

我在我的JAVA-All-I-Learned中创建了一个空的父项目mybatis-3-open-resource，只保留pom文件，其实就是把他当成一个文件夹使用。
![[Pasted image 20230317100933.png]]

接下来我们将mybatis-3-master的源码放入到文件夹中，把名改成mybatis-3,。如果mybatis-3项目没有显示蓝色小方块图标，可以在右侧maven中点击+添加pom。接下来就是等待源码包下载依赖和插件。
![[Pasted image 20230317101315.png]]

下载完成后，会发现mybatis-3中的pom文件会有报错，比如下图的maven-pdf-plugin。不用管，接着用。
![[Pasted image 20230317101537.png]]

这里注意一下，我们使用的是下载好的源码，可以看到版本号为3.5.0-SNAPSHOT。一会儿我们的测试项目需要引入这个源码包的依赖。
![[Pasted image 20230317101656.png]]

## 2.1 创建测试模块user-test

创建maven项目也行，spring项目也行，后面都会改这个测试模块的pom。
![[Pasted image 20230317101814.png]]

下面是user-test的pom文件，这里需要注意一下，我这里添加了一个mybatis-plus的包，是因为在测试中测试过mybatis-plus。大家可以不用管，也可以自行测试。
还有一点就是，在我们引入了下载的mybatis-3的包后，可以看到在依赖中我们已经添加了3.5.0-SNAPSHOT，但是在mybatis-spring-boot-starter依赖中有mybatis的包，所以我们需要把包排除。
这里建议大家使用maven-help插件，查看冲突的依赖。具体操作查看[[IDEA常用插件#12. Maven Helper（已安装）（强烈建议）]]
```xml
<?xml version="1.0" encoding="UTF-8"?>  
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">  
    <modelVersion>4.0.0</modelVersion>  
    <parent>        
	    <groupId>org.springframework.boot</groupId>  
        <artifactId>spring-boot-starter-parent</artifactId>  
        <version>2.4.2</version>  
        <relativePath/> <!-- lookup parent from repository -->  
    </parent>  
    <groupId>com.wddmg</groupId>  
    <artifactId>user-test</artifactId>  
    <version>0.0.1-SNAPSHOT</version>  
    <name>user-test</name>  
    <description>user-test</description>  
    <properties>        
	    <java.version>1.8</java.version>  
        <maven.compiler.target>1.8</maven.compiler.target>  
        <maven.compiler.source>1.8</maven.compiler.source>  
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>  
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>  
    </properties>    
	<dependencies>  
        <!-- mybatis plus支持 -->  
        <dependency>  
            <groupId>com.baomidou</groupId>  
            <artifactId>mybatis-plus-boot-starter</artifactId>  
            <version>3.3.1</version>  
        </dependency>  
        <!-- MySql驱动 -->  
        <dependency>  
            <groupId>mysql</groupId>  
            <artifactId>mysql-connector-java</artifactId>  
            <scope>runtime</scope>  
        </dependency>        
        <!-- mybatis支持 -->  
        <dependency>  
            <groupId>org.mybatis.spring.boot</groupId>  
            <artifactId>mybatis-spring-boot-starter</artifactId>  
            <version>2.1.1</version>  
            <exclusions>                
	            <exclusion>                    
		            <artifactId>mybatis</artifactId>  
                    <groupId>org.mybatis</groupId>  
                </exclusion>            
            </exclusions>        
        </dependency>        
        <!-- 指定mybatis版本 -->  
        <dependency>  
            <groupId>org.mybatis</groupId>  
            <artifactId>mybatis</artifactId>  
            <version>3.5.0-SNAPSHOT</version>  
        </dependency>        
        <!-- 常用工具类 -->  
        <dependency>  
            <groupId>org.apache.commons</groupId>  
            <artifactId>commons-lang3</artifactId>  
            <version>3.12.0</version>  
        </dependency>  
        <!-- 初始依赖 -->  
        <dependency>  
            <groupId>org.springframework.boot</groupId>  
            <artifactId>spring-boot-starter-web</artifactId>  
        </dependency>        
        <dependency>            
	        <groupId>org.springframework.boot</groupId>  
            <artifactId>spring-boot-devtools</artifactId>  
            <scope>runtime</scope>  
            <optional>true</optional>  
        </dependency>        
        <dependency>            
	        <groupId>org.springframework.boot</groupId>  
            <artifactId>spring-boot-configuration-processor</artifactId>  
            <optional>true</optional>  
        </dependency>        
	        <dependency>            
	        <groupId>org.springframework.boot</groupId>  
            <artifactId>spring-boot-starter-test</artifactId>  
            <scope>test</scope>  
        </dependency>    
    </dependencies>  
    <build>        
	    <plugins>            
		    <plugin>                
			    <groupId>org.springframework.boot</groupId>  
                <artifactId>spring-boot-maven-plugin</artifactId>  
            </plugin>            
            <plugin>                
	            <groupId>org.mybatis.generator</groupId>  
                <artifactId>mybatis-generator-maven-plugin</artifactId>  
                <version>1.4.0</version>  
            </plugin>        
        </plugins>    
    </build>  
</project>
```

在user-test模块中，新增测试MybatisTest，和手写的简易版一样。

![[Pasted image 20230317151511.png]]

```java
package com.wddmg.usertest;  
  
import com.wddmg.usertest.domain.entity.User;  
import org.apache.ibatis.io.Resources;  
import org.apache.ibatis.session.SqlSession;  
import org.apache.ibatis.session.SqlSessionFactory;  
import org.apache.ibatis.session.SqlSessionFactoryBuilder;  
import org.junit.jupiter.api.Test;  
import java.io.IOException;  
import java.io.InputStream;  
  
public class MybatisTest {  
  
    /**  
     * 传统方式  
     */  
    @Test  
    public void test1() throws IOException {  
        // 1. 读取配置文件，读成字节输入流，注意：现在还没解析  
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");  
  
        // 2. 解析配置文件，封装Configuration对象   创建DefaultSqlSessionFactory对象  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);  
  
        // 3. 生产了DefaultSqlsession实例对象   设置了事务不自动提交  完成了executor对象的创建  
        SqlSession sqlSession = sqlSessionFactory.openSession();  
  
        // 4.(1)根据statementid来从Configuration中map集合中获取到了指定的MappedStatement对象  
        //   (2)将查询任务委派了executor执行器  
        User user = sqlSession.selectOne("com.wddmg.usertest.mapper.gen.UserMapper.selectByPrimaryKey", 1);  
        System.out.println(user);  
        User user2 = sqlSession.selectOne("com.wddmg.usertest.mapper.gen.UserMapper.selectByPrimaryKey", 1);  
        System.out.println(user2);  
  
        // 5.释放资源  
        sqlSession.close();  
    }  
  
//    /**  
//     * mapper代理方式  
//     */  
//    @Test  
//    public void test2() throws IOException {  
//  
//        InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");  
//        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);  
//        SqlSession sqlSession = factory.openSession();  
//  
//        // 使用JDK动态代理对mapper接口产生代理对象  
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);  
//  
//        //代理对象调用接口中的任意方法，执行的都是动态代理中的invoke方法  
//        List<User> all = mapper.selectByExample(new UserExample());  
//        for (User user : all) {  
//            System.out.println(user);  
//        }  
//    }  
}
```

在main中新增MybatisConfig和对应的UserMapper

![[Pasted image 20230317151645.png]]

MybatisConfig.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE configuration PUBLIC "-//mybatis.org/DTD Config 3.0//EN"  
        "http://mybatis.org/dtd/mybatis-3-config.dtd" >  
<configuration>  
    <environments default="development">  
        <environment id="development">  
            <!-- 配置事务管理器的类型 -->  
            <transactionManager type="JDBC"/>  
            <!-- 配置数据源的类型，以及数据库连接的相关信息 -->  
            <dataSource type="POOLED">  
                <property name="driver" value="com.mysql.jdbc.Driver"/>  
                <property name="url" value="jdbc:mysql://localhost:3306/test?serverTimezone=UTC&amp;useUnicode=true&amp;characterEncoding=utf8"/>  
                <property name="username" value="root"/>  
                <property name="password" value="root"/>  
            </dataSource>        </environment>    </environments>    <!-- 配置映射配置文件的位置 -->  
    <mappers>  
        <mapper resource="mybatis/mapper/gen/UserMapper.xml"/>  
    </mappers></configuration>
```

UserMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">  
<mapper namespace="com.wddmg.usertest.mapper.gen.UserMapper">  
  <resultMap id="BaseResultMap" type="com.wddmg.usertest.domain.entity.User">  
    <id column="id" jdbcType="INTEGER" property="id" />  
    <result column="username" jdbcType="VARCHAR" property="username" />  
  </resultMap>  <sql id="Base_Column_List">  
    id, username  
  </sql>  
  <select id="selectByPrimaryKey" parameterType="java.lang.Integer" resultMap="BaseResultMap">  
    select   
<include refid="Base_Column_List" />  
    from test_user  
    where id = #{id,jdbcType=INTEGER}  </select>  
  <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer">  
    delete from test_user  
    where id = #{id,jdbcType=INTEGER}  </delete>  
  <insert id="insert" parameterType="com.wddmg.usertest.domain.entity.User">  
    insert into test_user (id, username)  
    values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR})  </insert>  
  <insert id="insertSelective" parameterType="com.wddmg.usertest.domain.entity.User">  
    insert into test_user  
    <trim prefix="(" suffix=")" suffixOverrides=",">  
      <if test="id != null">  
        id,  
      </if>  
      <if test="username != null">  
        username,  
      </if>  
    </trim>  
    <trim prefix="values (" suffix=")" suffixOverrides=",">  
      <if test="id != null">  
        #{id,jdbcType=INTEGER},  
      </if>  
      <if test="username != null">  
        #{username,jdbcType=VARCHAR},  
      </if>  
    </trim>  
  </insert>  
  <update id="updateByPrimaryKeySelective" parameterType="com.wddmg.usertest.domain.entity.User">  
    update test_user  
    <set>  
      <if test="username != null">  
        username = #{username,jdbcType=VARCHAR},  
      </if>  
    </set>  
    where id = #{id,jdbcType=INTEGER}  
  </update>  
  <update id="updateByPrimaryKey" parameterType="com.wddmg.usertest.domain.entity.User">  
    update test_user  
    set username = #{username,jdbcType=VARCHAR}    where id = #{id,jdbcType=INTEGER}  </update>  
</mapper>
```

# 3. 加载xml

![[Pasted image 20230317153953.png]]

## 3.1 ClassLoaderWrapper
ClassLoaderWrapper是一个类加载器包装类，详情可以看[[ClassLoader]]。这里用类加载器包装类的原因是，可以根据类加载是否为空，还可以做一些异常处理，简化代码。
- defaultClassLoader（默认类加载器）
- systemClassLoader（系统类加载器）

点进之后，这里通过不为null的类加载器加载xml

![[Pasted image 20230317154633.png]]

# 4. 创建sqlSessionFactory

SqlSessionFactoryBuilder类中，有三个重载方法，包括环境变量和properties变量
```java
public class SqlSessionFactoryBuilder {  
  
    public SqlSessionFactory build(Reader reader) {  
        return build(reader, null, null);  
    }  
  
    public SqlSessionFactory build(Reader reader, String environment) {  
        return build(reader, environment, null);  
    }  
  
    public SqlSessionFactory build(Reader reader, Properties properties) {  
        return build(reader, null, properties);  
    }  
  
    /**  
     * 构造 SqlSessionFactory 对象  
     *  
     * @param reader Reader 对象  
     * @param environment 环境  
     * @param properties Properties 变量  
     * @return SqlSessionFactory 对象  
     */  
    @SuppressWarnings("Duplicates")  
    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {  
        try {  
            // 创建 XMLConfigBuilder 对象  
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);  
            // 执行 XML 解析  
            // 创建 DefaultSqlSessionFactory 对象  
            return build(parser.parse());  
        } catch (Exception e) {  
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);  
        } finally {  
            ErrorContext.instance().reset();  
            try {  
                reader.close();  
            } catch (IOException e) {  
                // Intentionally ignore. Prefer previous error.  
            }  
        }  
    }  
  
    public SqlSessionFactory build(InputStream inputStream) {  
        return build(inputStream, null, null);  
    }  
  
    public SqlSessionFactory build(InputStream inputStream, String environment) {  
        return build(inputStream, environment, null);  
    }  
  
    public SqlSessionFactory build(InputStream inputStream, Properties properties) {  
        return build(inputStream, null, properties);  
    }  
  
    public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {  
        try {  
            // 创建 XMLConfigBuilder 对象  
            XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);  
            // 执行 XML 解析  
            // 创建 DefaultSqlSessionFactory 对象  
            return build(parser.parse());  
        } catch (Exception e) {  
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);  
        } finally {  
            ErrorContext.instance().reset();  
            try {  
                inputStream.close();  
            } catch (IOException e) {  
                // Intentionally ignore. Prefer previous error.  
            }  
        }  
    }  
  
    /**  
     * 创建 DefaultSqlSessionFactory 对象  
     *  
     * @param config Configuration 对象  
     * @return DefaultSqlSessionFactory 对象  
     */  
    public SqlSessionFactory build(Configuration config) {  
        return new DefaultSqlSessionFactory(config);  
    }  
  
}
```

在使用XMLConfigBuilder中，调用了父类构造方法，创建了一个Configuration。Configuration也是全局配置对象。
![[Pasted image 20230317160459.png]]

可以看一下COnfiguration
```java
public class Configuration {

  protected Environment environment;

  // 允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为false。默认为false
  protected boolean safeRowBoundsEnabled;
  // 允许在嵌套语句中使用分页（ResultHandler）。如果允许使用则设置为false
  protected boolean safeResultHandlerEnabled = true;
  // 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN
  // 到经典 Java 属性名 aColumn 的类似映射。默认false
  protected boolean mapUnderscoreToCamelCase;
  // 当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载。默认值false (true in ≤3.4.1)
  protected boolean aggressiveLazyLoading;
  // 是否允许单一语句返回多结果集（需要兼容驱动）。
  protected boolean multipleResultSetsEnabled = true;
  // 允许 JDBC 支持自动生成主键，需要驱动兼容。这就是insert时获取mysql自增主键/oracle sequence的开关。
  // 注：一般来说,这是希望的结果,应该默认值为true比较合适。
  protected boolean useGeneratedKeys;
  // 使用列标签代替列名,一般来说,这是希望的结果
  protected boolean useColumnLabel = true;
  // 是否启用缓存
  protected boolean cacheEnabled = true;
  // 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，
  // 这对于有 Map.keySet() 依赖或 null 值初始化的时候是有用的。
  protected boolean callSettersOnNulls;
  // 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的工程必须采用Java 8编译，
  // 并且加上-parameters选项。（从3.4.1开始）
  protected boolean useActualParamName = true;
  //当返回行的所有列都是空时，MyBatis默认返回null。 当开启这个设置时，MyBatis会返回一个空实例。
  // 请注意，它也适用于嵌套的结果集 (i.e. collectioin and association)。（从3.4.2开始）
  // 注：这里应该拆分为两个参数比较合适, 一个用于结果集，一个用于单记录。
  // 通常来说，我们会希望结果集不是null，单记录仍然是null
  protected boolean returnInstanceForEmptyRow;

  protected boolean shrinkWhitespacesInSql;

  // 指定 MyBatis 增加到日志名称的前缀。
  protected String logPrefix;
  // 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。一般建议指定为slf4j或log4j
  protected Class<? extends Log> logImpl;
  // 指定VFS的实现, VFS是mybatis提供的用于访问AS内资源的一个简便接口
  protected Class<? extends VFS> vfsImpl;
  protected Class<?> defaultSqlProviderType;
  // MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。
  // 默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。
  // 若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。
  protected LocalCacheScope localCacheScope = LocalCacheScope.SESSION;
  // 当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。 某些驱动需要指定列的 JDBC 类型，
  // 多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。
  protected JdbcType jdbcTypeForNull = JdbcType.OTHER;
  // 指定对象的哪个方法触发一次延迟加载。
  protected Set<String> lazyLoadTriggerMethods = new HashSet<>(Arrays.asList("equals", "clone", "hashCode", "toString"));
  // 设置超时时间，它决定驱动等待数据库响应的秒数。默认不超时
  protected Integer defaultStatementTimeout;
  // 为驱动的结果集设置默认获取数量。
  protected Integer defaultFetchSize;
  // SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）；
  // BATCH 执行器将重用语句并执行批量更新。
  protected ResultSetType defaultResultSetType;

  // 默认执行器类型
  protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
  // 指定 MyBatis 应如何自动映射列到字段或属性。
  // NONE 表示取消自动映射；
  // PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。
  // FULL 会自动映射任意复杂的结果集（无论是否嵌套）。
  protected AutoMappingBehavior autoMappingBehavior = AutoMappingBehavior.PARTIAL;
  // 指定发现自动映射目标未知列（或者未知属性类型）的行为。这个值应该设置为WARNING比较合适
  protected AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior = AutoMappingUnknownColumnBehavior.NONE;
  // settings下的properties属性
  protected Properties variables = new Properties();
  // 默认的反射器工厂,用于操作属性、构造器方便
  protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
  // 对象工厂, 所有的类resultMap类都需要依赖于对象工厂来实例化
  protected ObjectFactory objectFactory = new DefaultObjectFactory();
  // 对象包装器工厂,主要用来在创建非原生对象,比如增加了某些监控或者特殊属性的代理类
  protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

  // 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态
  protected boolean lazyLoadingEnabled = false;
  // 指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。MyBatis 3.3+使用JAVASSIST
  protected ProxyFactory proxyFactory = new JavassistProxyFactory(); // #224 Using internal Javassist instead of OGNL

  // MyBatis 可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的 databaseId 属性。
  protected String databaseId;
  /**
   * Configuration factory class.
   * Used to create Configuration for loading deserialized unread properties.
   *
   * @see <a href='https://github.com/mybatis/old-google-code-issues/issues/300'>Issue 300 (google code)</a>
   */
  protected Class<?> configurationFactory;

  protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

  // mybatis插件列表
  protected final InterceptorChain interceptorChain = new InterceptorChain();
  protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry(this);

  // 类型注册器, 用于在执行sql语句的出入参映射以及mybatis-config文件里的各种配置
  // 比如<transactionManager type="JDBC"/><dataSource type="POOLED">时使用简写
  protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
  protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();

  protected final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("Mapped Statements collection")
      .conflictMessageProducer((savedValue, targetValue) ->
          ". please check " + savedValue.getResource() + " and " + targetValue.getResource());
  protected final Map<String, Cache> caches = new StrictMap<>("Caches collection");
  protected final Map<String, ResultMap> resultMaps = new StrictMap<>("Result Maps collection");
  protected final Map<String, ParameterMap> parameterMaps = new StrictMap<>("Parameter Maps collection");
  protected final Map<String, KeyGenerator> keyGenerators = new StrictMap<>("Key Generators collection");

  protected final Set<String> loadedResources = new HashSet<>();
  protected final Map<String, XNode> sqlFragments = new StrictMap<>("XML fragments parsed from previous mappers");

  protected final Collection<XMLStatementBuilder> incompleteStatements = new LinkedList<>();
  protected final Collection<CacheRefResolver> incompleteCacheRefs = new LinkedList<>();
  protected final Collection<ResultMapResolver> incompleteResultMaps = new LinkedList<>();
  protected final Collection<MethodResolver> incompleteMethods = new LinkedList<>();

  /*
   * A map holds cache-ref relationship. The key is the namespace that
   * references a cache bound to another namespace and the value is the
   * namespace which the actual cache is bound to.
   */
  protected final Map<String, String> cacheRefMap = new HashMap<>();

  public Configuration(Environment environment) {
    this();
    this.environment = environment;
  }

```