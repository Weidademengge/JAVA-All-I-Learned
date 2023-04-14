```java
@SpringBootApplication //来标注一个主程序类，说明这是一个Spring Boot应用 
public class MyTestMVCApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyTestMVCApplication.class, args);
	} 
}
```

点进 run() 方法

```java
public static ConfigurableApplicationContext run(Class<?> primarySource,
String... args) {
	// 调用重载方法
	return run(new Class<?>[] { primarySource }, args);
}

public static ConfigurableApplicationContext run(Class<?>[] primarySources,
String[] args) {
	// 两件事:1.初始化SpringApplication 2.执行run方法
	return new SpringApplication(primarySources).run(args);
}
```

## 构造方法

继续查看源码， SpringApplication 实例化过程，首先是进入带参数的构造方法，最终回来到两个参数的构造方法。

```java
public SpringApplication(Class<?>... primarySources) {
    this(null, primarySources);
}
@SuppressWarnings({"unchecked", "rawtypes"})
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
    //设置资源加载器为null 
    this.resourceLoader = resourceLoader;
    //断言加载资源类不能为null
    Assert.notNull(primarySources, "PrimarySources must not be null");
    //将primarySources数组转换为List，最后放到LinkedHashSet集合中
    this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
    //【1.1 推断应用类型，后面会根据类型初始化对应的环境。常用的一般都是servlet环境 】 
    this.webApplicationType = WebApplicationType.deduceFromClasspath();
    //【1.2 初始化classpath下 META-INF/spring.factories中已配置的 ApplicationContextInitializer 】
    setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
    //【1.3 初始化classpath下所有已配置的 ApplicationListener 】
    setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
    //【1.4 根据调用栈，推断出 main 方法的类名 】
    this.mainApplicationClass = deduceMainApplicationClass();
}
```

### deduceWebApplicationType()

```java
private static final String[] WEB_ENVIRONMENT_CLASSES = {"javax.servlet.Servlet", "org.springframework.web.context.ConfigurableWebApplicationContext"};
private static final String REACTIVE_WEB_ENVIRONMENT_CLASS = "org.springframework." + "web.reactive.DispatcherHandler";
private static final String MVC_WEB_ENVIRONMENT_CLASS = "org.springframework." + "web.servlet.DispatcherServlet";
private static final String JERSEY_WEB_ENVIRONMENT_CLASS ="org.glassfish.jersey.server.ResourceConfig";
/**
 * 判断应用的类型
 * NONE: 应用程序不是web应用，也不应该用web服务器去启动
 * SERVLET: 应用程序应作为基于servlet的web应用程序运行，并应启动嵌入式servlet web(tomcat)服务器。
 * REACTIVE: 应用程序应作为 reactive web应用程序运行，并应启动嵌入式 reactive web服务器。
 * @return 
 */
private WebApplicationType deduceWebApplicationType() { 
    //classpath下必须存在org.springframework.web.reactive.DispatcherHandler 
    if (ClassUtils.isPresent(REACTIVE_WEB_ENVIRONMENT_CLASS, null) 
        	&& !ClassUtils.isPresent(MVC_WEB_ENVIRONMENT_CLASS, null)
         	&& !ClassUtils.isPresent(JERSEY_WEB_ENVIRONMENT_CLASS, null)) {
        return WebApplicationType.REACTIVE;
    }

    for (String className : WEB_ENVIRONMENT_CLASSES) {
         if (!ClassUtils.isPresent(className, null)) {
             return WebApplicationType.NONE;
         }
    }
    //classpath环境下存在javax.servlet.Servlet或者 org.springframework.web.context.ConfigurableWebApplicationContext
    return WebApplicationType.SERVLET;
}
```

返回类型是 WebApplicationType 的枚举类型，WebApplicationType 有三个枚举，具体的判断逻辑如下：

-   WebApplicationType.REACTIVE：classpath 下存在 `org.springframework.web.reactive.DispatcherHandler`
-   WebApplicationType.SERVLET：classpath 下存在 `javax.servlet.Servlet` 或者  `org.springframework.web.context.ConfigurableWebApplicationContext`
-   WebApplicationType.NONE 不满足以上条件。

### setInitializers()

初始化 classpath下 `META-INF/spring.factories` 中已配置的 ApplicationContextInitializer

```java
private <T> Collection<T> getSpringFactoriesInstances(Class<T> type) {
    return getSpringFactoriesInstances(type, new Class<?>[]{});
}
/**
 * 通过指定的classloader 从META-INF/spring.factories获取指定的Spring的工厂实例 
 * @param type
 * @param parameterTypes
 * @param args
 * @param <T>
 * @return
 */
private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
    // Use names and ensure unique to protect against duplicates 
    // 通过指定的 classLoader 从 META-INF/spring.factories 的资源文件中，读取 key 为 type.getName() 的 value
    Set<String> names = new LinkedHashSet<> (SpringFactoriesLoader.loadFactoryNames(type, classLoader)); 
    // 创建Spring工厂实例
    List<T> instances = createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names); 
    //对Spring工厂实例排序(org.springframework.core.annotation.Order注解指定的顺序) 
    AnnotationAwareOrderComparator.sort(instances);
    return instances;
}
```

看源码，有一个方法很重要 `loadFactoryNames()`，这个方法是 spring-core 中提供的从 `META-INF/spring.factories` 中获取指定的类 (key) 的同一入口方法。

在这里，获取的是 key 为 `org.springframework.context.ApplicationContextInitializer` 的类。debug 看看都获取到了哪些：

TODO:PIC

ApplicationContextInitializer 是 Spring 框架的类, 这个类的主要目的就是在ConfigurableApplicationContext 调用 refresh() 方法之前，回调这个类的 initialize() 方法。  
通过 ConfigurableApplicationContext 的实例获取容器的环境 Environment，从而实现对配置文件的修改完善等工作。

### setListeners()

初始化 classpath 下 `META-INF/spring.factories` 中已配置的 ApplicationListener。

ApplicationListener 的加载过程和上面的 ApplicationContextInitializer 类的加载过程是一样的。不多说了，至于 ApplicationListener 是 Spring 的事件监听器，这是典型的观察者模式，通过 ApplicationEvent 类和 ApplicationListener 接口，可以实现对spring容器全生命周期的监听，当然也可以自定义监听事件。

### 总结

关于 SpringApplication 类的构造过程，到这里我们就梳理完了。纵观 SpringApplication 类的实例化过程，我们可以看到，合理的利用该类，我们能在 Spring 容器创建之前做一些预备工作，和定制化的需求。比如，自定义 SpringBoot 的 Banner，比如自定义事件监听器，再比如在容器 refresh 之前通过自定义 ApplicationContextInitializer 修改配置一些配置或者获取指定的 Bean 都是可以的。

---

## run() 方法

经过深入分析后，大家会发现 SpringBoot 也就是给 Spring 包了一层皮，事先替我们准备好Spring 所需要的环境及一些基础参数

```java
/**
 * Run the Spring application, creating and refreshing a new {@link ApplicationContext}.
 *
 * @param args the application arguments (usually passed from a Java main method)
 * @return a running {@link ApplicationContext}
 *
 * 运行 Spring 应用，并刷新一个新的 ApplicationContext(Spring的上下文)
 * ConfigurableApplicationContext 是 ApplicationContext 接口的子接口。
 * 在 ApplicationContext 基础上增加了配置上下文的工具。
 */
public ConfigurableApplicationContext run(String... args) { 
    // 记录程序运行时间
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    // ConfigurableApplicationContext Spring 的上下文 
    ConfigurableApplicationContext context = null; 
    Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
    configureHeadlessProperty(); 
    // 从META-INF/spring.factories 中获取监听器
    // 1、获取并启动监听器
    SpringApplicationRunListeners listeners = getRunListeners(args); 
    listeners.starting();
    try {
    	ApplicationArguments applicationArguments = new DefaultApplicationArguments(args); 
        // 2、构造应用上下文环境
        ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
        // 处理需要忽略的Bean
        configureIgnoreBeanInfo(environment);
        // 打印banner
        Banner printedBanner = printBanner(environment); 
        // 3、初始化应用上下文
        context = createApplicationContext(); 
        //实例化SpringBootExceptionReporter.class，用来支持报告关于启动的错误 
        exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class, new Class[]{ConfigurableApplicationContext.class}, context); 
        // 4、刷新应用上下文前的准备阶段
        prepareContext(context, environment, listeners, applicationArguments, printedBanner);
        // 5、刷新应用上下文
        refreshContext(context); 
        // 6、刷新应用上下文后的扩展接口 
        afterRefresh(context, applicationArguments); 
        //时间记录停止
        stopWatch.stop();
        if (this.logStartupInfo) {
            new StartupInfoLogger(this.mainApplicationClass) .logStarted(getApplicationLog(), stopWatch);
        }
        //发布容器启动完成事件 listeners.started(context); callRunners(context, applicationArguments);
    } catch (Throwable ex) {
        handleRunFailure(context, ex, exceptionReporters, listeners);
        throw new IllegalStateException(ex);
    }
    try {
        listeners.running(context);
    } catch (Throwable ex) {
        handleRunFailure(context, ex, exceptionReporters, null);
        throw new IllegalStateException(ex);
    }
    return context;
}
```

在以上的代码中，启动过程中的重要步骤共分为六步

-   [第一步：获取并启动监听器](https://aliyuque.antfin.com/faw-tech/cdgkfq/adzoxkp8c4732ic8#%E7%AC%AC%E4%B8%80%E6%AD%A5%EF%BC%9A%E8%8E%B7%E5%8F%96%E5%B9%B6%E5%90%AF%E5%8A%A8%E7%9B%91%E5%90%AC%E5%99%A8)
-   [第二步：构造应用上下文环境](https://aliyuque.antfin.com/faw-tech/cdgkfq/adzoxkp8c4732ic8#%E7%AC%AC%E4%BA%8C%E6%AD%A5%EF%BC%9A%E6%9E%84%E9%80%A0%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87%E7%8E%AF%E5%A2%83)
-   [第三步：初始化应用上下文](https://aliyuque.antfin.com/faw-tech/cdgkfq/adzoxkp8c4732ic8#%E7%AC%AC%E4%B8%89%E6%AD%A5%EF%BC%9A%E5%88%9D%E5%A7%8B%E5%8C%96%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87)
-   [第四步：刷新应用上下文前的准备阶段](https://aliyuque.antfin.com/faw-tech/cdgkfq/adzoxkp8c4732ic8#%E7%AC%AC%E5%9B%9B%E6%AD%A5%EF%BC%9A%E5%88%B7%E6%96%B0%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87%E5%89%8D%E7%9A%84%E5%87%86%E5%A4%87%E9%98%B6%E6%AE%B5)（重点）
-   [第五步：刷新应用上下文](https://aliyuque.antfin.com/faw-tech/cdgkfq/adzoxkp8c4732ic8#%E7%AC%AC%E4%BA%94%E6%AD%A5%EF%BC%9A%E5%88%B7%E6%96%B0%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87)（重点）
-   [第六步：刷新应用上下文后的扩展接口](https://aliyuque.antfin.com/faw-tech/cdgkfq/adzoxkp8c4732ic8#%E7%AC%AC%E5%85%AD%E6%AD%A5%EF%BC%9A%E5%88%B7%E6%96%B0%E5%BA%94%E7%94%A8%E4%B8%8A%E4%B8%8B%E6%96%87%E5%90%8E%E7%9A%84%E6%89%A9%E5%B1%95%E6%8E%A5%E5%8F%A3)

### 第一步：获取并启动监听器

事件机制在 Spring 是很重要的一部分内容，通过事件机制我们可以监听Spring容器中正在发生的一些事 件，同样也可以自定义监听事件。Spring的事件为Bean和Bean之间的消息传递提供支持。当一个对象 处理完某种任务后，通知另外的对象进行某些处理，常用的场景有进行某些操作后发送通知，消息、邮件等情况。

```java
private SpringApplicationRunListeners getRunListeners(String[] args) {
     Class<?>[] types = new Class<?>[]{SpringApplication.class, String[].class};
     return new SpringApplicationRunListeners(logger, getSpringFactoriesInstances(SpringApplicationRunListener.class, types, this, args));
}
```

在这里面是不是看到一个熟悉的方法 getSpringFactoriesInstances()，可以看下面的注释，前面 我们已经详细介绍过该方法是怎么一步步的获取到 META-INF/spring.factories 中的指定的 key 的 value，获取到以后怎么实例化类的。

```java
/**  
 * 通过指定的classloader 从META-INF/spring.factories获取指定的Spring的工厂实例 
 * @param type
 * @param parameterTypes
 * @param args
 * @param <T>
 * @return
*/
private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {  
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
    // Use names and ensure unique to protect against duplicates 
    // 通过指定的classLoader从 META-INF/spring.factories 的资源文件中，读取 key 为 type.getName() 的 value  
    Set<String> names = new LinkedHashSet<>(SpringFactoriesLoader.loadFactoryNames(type, classLoader)); 
    // 创建Spring工厂实例  
    List<T> instances = createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names); 
    // 对Spring工厂实例排序(org.springframework.core.annotation.Order注解指定的顺序) 
    AnnotationAwareOrderComparator.sort(instances);  
    return instances;
}
```

回到 run() 方法，debug 这个代码 `SpringApplicationRunListeners listeners = getRunListeners(args);` 看 一下获取的是哪个监听器：

-   EventPublishingRunListener 监听器是 Spring 容器的启动监听器。
-   listeners.starting() 开启了监听事件。

### 第二步：构造应用上下文环境

应用上下文环境包括什么呢?包括计算机的环境，Java环境，Spring的运行环境，Spring项目的配 置(在SpringBoot中就是那个熟悉的application.properties/yml)等等。

首先看一下prepareEnvironment()方法。

```java
private ConfigurableEnvironment prepareEnvironment(SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments) {
    // Create and configure the environment
    // 创建并配置相应的环境
    ConfigurableEnvironment environment = getOrCreateEnvironment(); 
    // 根据用户配置，配置 environment系统环境
    configureEnvironment(environment, applicationArguments.getSourceArgs());
    // 启动相应的监听器，其中一个重要的监听器 ConfigFileApplicationListener 就是加载项目配置文件的监听器。
    listeners.environmentPrepared(environment);
    bindToSpringApplication(environment);
    if (this.webApplicationType == WebApplicationType.NONE) {
        environment = new EnvironmentConverter(getClassLoader()).convertToStandardEnvironmentIfNecessary(environment);
    }
    ConfigurationPropertySources.attach(environment);
    return environment;
}
```

看上面的注释，方法中主要完成的工作，首先是创建并按照相应的应用类型配置相应的环境，然后根据用户的配置，配置系统环境，然后启动监听器，并加载系统配置文件。

#### getOrCreateEnvironment()

```java
private ConfigurableEnvironment getOrCreateEnvironment() {
    if (this.environment != null) {
        return this.environment;
    }

// 如果应用类型是SERVLET, 则实例化StandardServletEnvironment  
if (this.webApplicationType == WebApplicationType.SERVLET) {
        return new StandardServletEnvironment();
    }
    return new StandardEnvironment();
}
```

通过代码可以看到根据不同的应用类型初始化不同的系统环境实例，当是web项目的时候，环境上会多一些关于web环境的配置。

#### configureEnvironment()

```java
protected void configureEnvironment(ConfigurableEnvironment environment, String[] args) {
	// 将main函数的args封装成 SimpleCommandLinePropertySource 加入环境中。
	configurePropertySources(environment, args);  
	// 激活相应的配置文件  
	configureProfiles(environment, args);
}
```

-   在 `configurePropertySources(environment, args);` 中将args封装成了 SimpleCommandLinePropertySource 并加入到了environment中。
-   `configureProfiles(environment, args);` 根据启动参数激活了相应的配置文件。

#### environmentPrepared()

进入到方法一路跟下去就到了 SimpleApplicationEventMulticaster 类的 multicastEvent() 方法。

```java
public void multicastEvent(ApplicationEvent event) {
	multicastEvent(event, resolveDefaultEventType(event));
}
```

查看 getApplicationListeners(event, type) 执行结果，发现一个重要的监听器 ConfigFileApplicationListener。查看注释发现，这个监听器默认的从注释中标签所示的几个位置加载配置文件，并将其加入上下文的 environment 变量中。当然也可以通过配置指定。

debug 跳过 `listeners.environmentPrepared(environment);` 这一行，查看 environment 属性，果真如上面所说的，配置文件的配置信息已经添加上来了。

### 第三步：初始化应用上下文

在 SpringBoot 工程中，应用类型分为三种，如下代码所示。

```java
public enum WebApplicationType {
    /**
     * 应用程序不是web应用，也不应该用web服务器去启动
     */
    NONE, 
    /**
     * 应用程序应作为基于servlet的web应用程序运行，并应启动嵌入式servlet web(tomcat)服务器。
     */
    SERVLET, 
    /**
     * 应用程序应作为 reactive web应用程序运行，并应启动嵌入式 reactive web服务器。
     */
    REACTIVE
}
```

对应三种应用类型，SpringBoot 项目有三种对应的应用上下文，我们以 web 工程为例，即其上下 文为 AnnotationConfigServletWebServerApplicationContext。

```java
public static final String DEFAULT_WEB_CONTEXT_CLASS = "org.springframework.boot." + "web.servlet.context.AnnotationConfigServletWebServerApplicationContext";
public static final String DEFAULT_REACTIVE_WEB_CONTEXT_CLASS = "org.springframework." + "boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext";
public static final String DEFAULT_CONTEXT_CLASS = "org.springframework.context." + "annotation.AnnotationConfigApplicationContext";

protected ConfigurableApplicationContext createApplicationContext() {
    Class<?> contextClass = this.applicationContextClass;
    if (contextClass == null) {
        try {
            switch (this.webApplicationType) {
                case SERVLET:
                    contextClass = Class.forName(DEFAULT_WEB_CONTEXT_CLASS);
                    break;
                case REACTIVE:
                    contextClass = Class.forName(DEFAULT_REACTIVE_WEB_CONTEXT_CLASS);
                    break;
                default:
                    contextClass = Class.forName(DEFAULT_CONTEXT_CLASS);
            }
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException(
                    "Unable create a default ApplicationContext, "
                            + "please specify an ApplicationContextClass", ex);
    return (ConfigurableApplicationContext)BeanUtils.instantiateClass(contextClass);
}
```
  

`AnnotationConfigServletWebServerApplicationContext` 应用上下文可以理解成 IoC 容器的高级表现形式，应用上下文确实是在IoC容器的基础上丰富了一些高级功能。应用上下文对 IoC 容器是持有的关系。他的一个属性 beanFactory 就是 IoC 容器 (`DefaultListableBeanFactory`)。所以他们之间是持有和扩展的关系。

接下来看 GenericApplicationContext 类

```java
public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {
    private final DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }
}
```

beanFactory 正是在 AnnotationConfigServletWebServerApplicationContext 实现的接口 GenericApplicationContext 中定义的。在上面 createApplicationContext() 方法中的， BeanUtils.instantiateClass(contextClass) 这个方法中，不但初始化了 AnnotationConfigServletWebServerApplicationContext 类，也就是我们的上下文context，同样也触发了 GenericApplicationContext 类的构造函数，从而IoC容器也创建了。

仔细看他的构造函数，有没有发现一个很熟悉的类 DefaultListableBeanFactory，没错， DefaultListableBeanFactory 就是IoC容器真实面目了。在后面的refresh()方法分析中， DefaultListableBeanFactory 是无处不在的存在感。

### 第四步：刷新应用上下文前的准备阶段

#### prepareContext()

```java
private void prepareContext(ConfigurableApplicationContext context, ConfigurableEnvironment environment, SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments, Banner printedBanner) { 
    // 设置容器环境
    context.setEnvironment(environment);
    // 执行容器后置处理
    postProcessApplicationContext(context);
    // 执行容器中的 ApplicationContextInitializer 包括spring.factories和通过三种方式自定义的
    applyInitializers(context); 
    // 向各个监听器发送容器已经准备好的事件 
    listeners.contextPrepared(context); 
    if (this.logStartupInfo) {
        logStartupInfo(context.getParent() == null);
        logStartupProfileInfo(context);
    }
    // Add boot specific singleton beans 
    // 将main函数中的args参数封装成单例Bean，注册进容器 
    context.getBeanFactory().registerSingleton("springApplicationArguments", applicationArguments);
    // 将printedBanner也封装成单例，注册进容器 
    if (printedBanner != null) {
        context.getBeanFactory().registerSingleton("springBootBanner", printedBanner);
    }
    // Load the sources
    Set<Object> sources = getAllSources(); 
    Assert.notEmpty(sources, "Sources must not be empty"); 
    // 加载我们的启动类，将启动类注入容器
    load(context, sources.toArray(new Object[0])); 
    // 发布容器已加载事件
    listeners.contextLoaded(context);
}
```

首先看这行 `Set sources = getAllSources();` 在 getAllSources() 中拿到了我们的启动类。 我们重点讲解这行 `load(context, sources.toArray(new Object[0]));`，其他的方法请参阅注释。 跟进load()方法，看源码：

```java
protected void load(ApplicationContext context, Object[] sources) {
    if (logger.isDebugEnabled()) {
        logger.debug("Loading source " + StringUtils.arrayToCommaDelimitedString(sources));
    }
	//创建 BeanDefinitionLoader
	BeanDefinitionLoader loader = createBeanDefinitionLoader(getBeanDefinitionRegistry(context), sources);
    if (this.beanNameGenerator != null) {
        loader.setBeanNameGenerator(this.beanNameGenerator);
    }
    if (this.resourceLoader != null) {
        loader.setResourceLoader(this.resourceLoader);
    }
    if (this.environment != null) {
        loader.setEnvironment(this.environment);
	}
    loader.load();
}
```

#### getBeanDefinitionRegistry()

```java
private BeanDefinitionRegistry getBeanDefinitionRegistry(ApplicationContext
context) {
     if (context instanceof BeanDefinitionRegistry) {
         return (BeanDefinitionRegistry) context;
	}
}
```

这里将我们前文创建的上下文强转为 BeanDefinitionRegistry，他们之间是有继承关系的。 BeanDefinitionRegistry 定义了很重要的方法 registerBeanDefinition()，该方法将 BeanDefinition 注册进 DefaultListableBeanFactory 容器的 beanDefinitionMap 中。

#### createBeanDefinitionLoader()

继续看 createBeanDefinitionLoader() 方法，最终进入了 BeanDefinitionLoader 类的构造方法，如下：

```java
BeanDefinitionLoader(BeanDefinitionRegistry registry, Object... sources) {
    Assert.notNull(registry, "Registry must not be null");
    Assert.notEmpty(sources, "Sources must not be empty");
    this.sources = sources;

	// 注解形式的Bean定义读取器 比如：@Configuration @Bean @Component @Controller @Service等等
	this.annotatedReader = new AnnotatedBeanDefinitionReader(registry); 
	// XML形式的Bean定义读取器  
	this.xmlReader = new XmlBeanDefinitionReader(registry);  
	if (isGroovyPresent()) {
        this.groovyReader = new GroovyBeanDefinitionReader(registry);
    }

	// 类路径扫描器  
	this.scanner = new ClassPathBeanDefinitionScanner(registry); 
	// 扫描器添加排除过滤器  
	this.scanner.addExcludeFilter(new ClassExcludeFilter(sources));
}
```

先记住上面的三个属性，上面三个属性在 BeanDefinition 的 Resource 定位，和B eanDefinition 的注册中起到了很重要的作用。

#### loader.load()

```java
private int load(Object source) { 
	Assert.notNull(source, "Source must not be null"); 
	// 从Class加载  
	if (source instanceof Class<?>) {
	        return load((Class<?>) source);
	    }
	
	// 从Resource加载  
	if (source instanceof Resource) {
	        return load((Resource) source);
	    }
	
	// 从Package加载  
	if (source instanceof Package) {
	        return load((Package) source);
	    }
	
	// 从 CharSequence 加载 ???  
	if (source instanceof CharSequence) {
	        return load((CharSequence) source);
	    }
    throw new IllegalArgumentException("Invalid source type " +
source.getClass());
}
```

当前我们的主类会按 Class 加载 ，继续跟进 `load((Class<?>) source)` 方法：

```java
private int load(Class<?> source) {
    if (isGroovyPresent() && GroovyBeanDefinitionSource.class.isAssignableFrom(source)) {
        // Any GroovyLoaders added in beans{} DSL can contribute beans here        
        GroovyBeanDefinitionSource loader = BeanUtils.instantiateClass(source, GroovyBeanDefinitionSource.class);
        load(loader);
    }

	if (isComponent(source)) {  
		//将启动类的 BeanDefinition注册进 beanDefinitionMap 
		this.annotatedReader.register(source);  
		return 1;
	}
	return 0; 
}
```

`isComponent(source)` 判断主类是不是存在 `@Component` 注解，主类 `@SpringBootApplication`是一个组合注解，包含 `@Component`。

`this.annotatedReader.register(source);` 跟进register()方法，最终进到 AnnotatedBeanDefinitionReader 类的 doRegisterBean() 方法：

```java
<T> void doRegisterBean(Class<T> annotatedClass, @Nullable Supplier<T>
                        instanceSupplier, @Nullable String name,
                        @Nullable Class<? extends Annotation>[] qualifiers,
                        BeanDefinitionCustomizer... definitionCustomizers) {
    // 将指定的类封装为AnnotatedGenericBeanDefinition
    AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(annotatedClass);
    if (this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
        return;
    }
    abd.setInstanceSupplier(instanceSupplier); 
    // 获取该类的 scope 属性
    ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
    abd.setScope(scopeMetadata.getScopeName());
    String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry));
    AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
    if (qualifiers != null) {
        for (Class<? extends Annotation> qualifier : qualifiers) {
            if (Primary.class == qualifier) {
                abd.setPrimary(true);
            }
            else if (Lazy.class == qualifier) {
                abd.setLazyInit(true);
            } else {
                abd.addQualifier(new AutowireCandidateQualifier(qualifier));
            }
        } 
    }
    for (BeanDefinitionCustomizer customizer : definitionCustomizers) {
        customizer.customize(abd);
    }
    BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
    definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder,  this.registry);
    // 将该BeanDefinition注册到IoC容器的beanDefinitionMap中
    BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
}
```

在该方法中将主类封装成 AnnotatedGenericBeanDefinition

`BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);` 方法将 BeanDefinition 注册进 beanDefinitionMap：

```java
public static void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) throws BeanDefinitionStoreException {
    // Register bean definition under primary name. 
    // primary name 其实就是id吧
    String beanName = definitionHolder.getBeanName(); 
    registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());
    // Register aliases for bean name, if any.
    // 然后就是注册别名
    String[] aliases = definitionHolder.getAliases(); 
    if (aliases != null) {
        for (String alias : aliases) {
            registry.registerAlias(beanName, alias);
        } 
    }
}
```

继续跟进 registerBeanDefinition() 方法：

TODO

最终来到 DefaultListableBeanFactory 类的 registerBeanDefinition() 方法， DefaultListableBeanFactory 类还熟悉吗？

相信大家一定非常熟悉这个类了，DefaultListableBeanFactory 是 IoC 容器的具体产品。

仔细看这个方法 registerBeanDefinition()，首先会检查是否已经存在，如果存在并且不允许被覆盖则直接抛出异常。不存在的话就直接注册进 beanDefinitionMap 中。

debug跳过prepareContext()方法，可以看到，启动类的BeanDefinition已经注册进来了。

### 第五步：刷新应用上下文

首先我们要知道到IoC容器的初始化过程，主要分下面三步：

1.  BeanDefinition的Resource定位
2.  BeanDefinition的载入
3.  向IoC容器注册BeanDefinition

接下来我们主要从refresh()方法中总结IoC容器的初始化过程。 从run方法的，refreshContext()方法一路跟下去，最终来到AbstractApplicationContext类的 refresh()方法。

```java
@Override
public void refresh() throws BeansException, IllegalStateException {
    synchronized (this.startupShutdownMonitor) {
        // Prepare this context for refreshing.
        // 刷新上下文环境
        prepareRefresh();

        // Tell the subclass to refresh the internal bean factory.
        // 这里是在子类中启动 refreshBeanFactory() 的地方
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // Prepare the bean factory for use in this context.
        // 准备bean工厂，以便在此上下文中使用
        prepareBeanFactory(beanFactory);

        try {
            // Allows post-processing of the bean factory in context subclasses.
            // 设置 beanFactory 的后置处理
            postProcessBeanFactory(beanFactory);

            // Invoke factory processors registered as beans in the context.
            // 调用 BeanFactory 的后处理器，这些处理器是在Bean 定义中向容器注册的
            invokeBeanFactoryPostProcessors(beanFactory);

            // Register bean processors that intercept bean creation.
            // 注册Bean的后处理器，在Bean创建过程中调用
            registerBeanPostProcessors(beanFactory);

            // Initialize message source for this context.
            // 对上下文中的消息源进行初始化
            initMessageSource();

            // Initialize event multicaster for this context.
            // 初始化上下文中的事件机制
            initApplicationEventMulticaster();

            // Initialize other special beans in specific context subclasses.
            // 初始化其他特殊的Bean
            onRefresh();

            // Check for listener beans and register them.
            // 检查监听Bean并且将这些监听Bean向容器注册
            registerListeners();

            // Instantiate all remaining (non-lazy-init) singletons.
            // 实例化所有的(non-lazy-init)单件
            finishBeanFactoryInitialization(beanFactory);

            // Last step: publish corresponding event.
            // 发布容器事件，结束Refresh过程
            finishRefresh();
        }

        catch (BeansException ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("Exception encountered during context initialization - " +
                        "cancelling refresh attempt: " + ex);
            }

            // Destroy already created singletons to avoid dangling resources.
            destroyBeans();

            // Reset 'active' flag.
            cancelRefresh(ex);

            // Propagate exception to caller.
            throw ex;
        }

        finally {
            // Reset common introspection caches in Spring's core, since we
            // might not ever need metadata for singleton beans anymore...
            resetCommonCaches();
        }
    }
}
```

从以上代码中我们可以看到，refresh() 方法中所作的工作也挺多，我们没办法面面俱到，主要根据 IoC 容器的初始化步骤进行分析，所以我们主要介绍重要的方法，其他的请看注释。

#### obtainFreshBeanFactory()

在启动流程的第三步：初始化应用上下文。中我们创建了应用的上下文，并触发了 GenericApplicationContext 类的构造方法如下所示，创建了 beanFactory，也就是创建了 DefaultListableBeanFactory 类。

```java
public GenericApplicationContext() {
    this.beanFactory = new DefaultListableBeanFactory();
}
```

关于 obtainFreshBeanFactory() 方法，其实就是拿到我们之前创建的 beanFactory。

```java
protected ConfigurableListableBeanFactory obtainFreshBeanFactory() { 
	//刷新BeanFactory  
	refreshBeanFactory();  
	//获取beanFactory
    ConfigurableListableBeanFactory beanFactory = getBeanFactory();
    if (logger.isDebugEnabled()) {
        logger.debug("Bean factory for " + getDisplayName() + ": " + beanFactory);
	}
    return beanFactory;
}
```

从上面代码可知，在该方法中主要做了三个工作，刷新 beanFactory，获取 beanFactory，返回  beanFactory。

#### prepareBeanFactory()

从字面意思上可以看出准备 BeanFactory。 看代码具体看看做了哪些准备工作，这个方法不是重点，看注释吧。

```java
protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    // Tell the internal bean factory to use the context's class loader etc. 
    // 配置类加载器:默认使用当前上下文的类加载器 
    beanFactory.setBeanClassLoader(getClassLoader());
    // 配置EL表达式:在Bean初始化完成，填充属性的时候会用到 
	beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
	// 添加属性编辑器 PropertyEditor 
	beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));
    // Configure the bean factory with context callbacks. 
    // 添加Bean的后置处理器 
    beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
	// 忽略装配以下指定的类 
	beanFactory.ignoreDependencyInterface(EnvironmentAware.class); 
	beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class); 
	beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
	beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
	beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
	beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);
	// BeanFactory interface not registered as resolvable type in a plain factory.
    // MessageSource registered (and found for autowiring) as a bean.
    // 将以下类注册到 beanFactory(DefaultListableBeanFactory) 的 resolvableDependencies属性中
    beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
	beanFactory.registerResolvableDependency(ResourceLoader.class, this);
	beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
	beanFactory.registerResolvableDependency(ApplicationContext.class, this);
	// Register early post-processor for detecting inner beans as ApplicationListeners.
    // 将早期后处理器注册为application监听器，用于检测内部bean 
	beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));
    // Detect a LoadTimeWeaver and prepare for weaving, if found. 
	// 如果当前BeanFactory包含loadTimeWeaver Bean，说明存在类加载期织入AspectJ， 
	// 则把当前BeanFactory交给类加载期BeanPostProcessor实现类LoadTimeWeaverAwareProcessor来处理，
    // 从而实现类加载期织入AspectJ的目的。
    if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
        beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
        // Set a temporary ClassLoader for type matching.
        beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
    }
    // Register default environment beans.
    // 将当前环境变量(environment) 注册为单例bean
    if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
        beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
    }
    // 将当前系统配置(systemProperties) 注册为单例Bean
    if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
        beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
    }
    // 将当前系统环境 (systemEnvironment) 注册为单例Bean
    if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
        beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
    } 
}
```

#### postProcessBeanFactory()

postProcessBeanFactory() 方法向上下文中添加了一系列的 Bean 的后置处理器。

后置处理器工作的时机是在所有的 BeanDenifition 加载完成之后，Bean 实例化之前执行。简单来 说 Bean 的后置处理器可以修改 BeanDefinition 的属性信息。

#### invokeBeanFactoryPostProcessors()

IoC 容器的初始化过程包括三个步骤，在 invokeBeanFactoryPostProcessors() 方法中完成了 IoC 容器初始化过程的三个步骤：

##### 1. Resource定位

在 SpringBoot 中，我们都知道他的包扫描是从主类所在的包开始扫描的，prepareContext() 方法中，会先将主类解析成 BeanDefinition，然后在 refresh()方法的  invokeBeanFactoryPostProcessors() 方法中解析主类的 BeanDefinition 获取 basePackage 的路径，这样就完成了定位的过程。

其次 SpringBoot 的各种 starter 是通过 SPI 扩展机制实现的自动装配，SpringBoot 的自动装配同样也是在 invokeBeanFactoryPostProcessors() 方法中实现的。

还有一种情况，在 SpringBoot 中有很多的 `@EnableXXX` 注解，细心点进去看的应该就知道其底层是 `@Import` 注解，在 invokeBeanFactoryPostProcessors() 方法中也实现了对该注解指定的配置类的定位加载。

根据上述总结了常规的在 SpringBoot 中有三种实现定位方式：

1.  主类所在包的
2.  SPI扩展机制实现的自动装配（比如各种 starter）
3.  `@Import` 注解指定的类

##### 2. BeanDefinition的载入

在第一步中说了三种 Resource 的定位情况，定位后紧接着就是 BeanDefinition 的分别载入。 所谓的载入就是通过上面的定位得到的 basePackage，SpringBoot 会将该路径拼接成：`classpath:com/ebanma/cloud/**/.class` 这样的形式，然后一个叫做 xPathMatchingResourcePatternResolver 的类会将该路径下所有的 .class 文件都加载进来，然后遍历判断是不是有 `@Component` 注解，如果有的话，就是我们要装载的 BeanDefinition。

TIPS:  
@Configuration，@Controller，[@Service](/Service) 等注解底层都是 @Component注解，只不过包装了一层罢了!

##### 3. 注册BeanDefinition

这个过程通过调用上文提到的 BeanDefinitionRegister 接口的实现来完成。这个注册过程把载入 过程中解析得到的 BeanDefinition 向 IoC 容器进行注册。通过上文的分析，我们可以看到，在 IoC容器中将 BeanDefinition 注入到一个 ConcurrentHashMap 中，IoC 容器就是通过这个 HashMap 来持有这些 BeanDefinition 数据的。比如 DefaultListableBeanFactory 中的beanDefinitionMap 属性，接下来我们通过代码看看具体是怎么实现的。

```java
invokeBeanFactoryPostProcessors > 
	PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors >
	PostProcessorRegistrationDelegate.invokeBeanDefinitionRegistryPostProcessors >
	ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry >
	ConfigurationClassPostProcessor.processConfigBeanDefinitions >
	ConfigurationClassParser.parse
```

一路跟踪调用栈，来到了 ConfigurationClassParser 类的 parse() 方法：

```java
public void parse(Set<BeanDefinitionHolder> configCandidates) {
    for (BeanDefinitionHolder holder : configCandidates) {
        BeanDefinition bd = holder.getBeanDefinition();
        try {
	        // 如果是SpringBoot项目进来的，bd其实就是前面主类封装成的 AnnotatedGenericBeanDefinition(AnnotatedBeanDefinition接口的实现类)
            if (bd instanceof AnnotatedBeanDefinition) {
                parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());
            }
            else if (bd instanceof AbstractBeanDefinition && ((AbstractBeanDefinition) bd).hasBeanClass()) {
                parse(((AbstractBeanDefinition) bd).getBeanClass(), holder.getBeanName());
            }
            else {
                parse(bd.getBeanClassName(), holder.getBeanName());
            }
        }
        catch (BeanDefinitionStoreException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new BeanDefinitionStoreException(
                    "Failed to parse configuration class [" + bd.getBeanClassName() + "]", ex);
        }
    }
	// 加载默认的配置(对SpringBoot项目来说这里就是自动装配的入口了)
    this.deferredImportSelectorHandler.process();
}
```

继续沿着 `parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());` 方法跟下去：

```java
protected void processConfigurationClass(ConfigurationClass configClass, Predicate<String> filter) throws IOException {
    if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
        return;
    }

    ConfigurationClass existingClass = this.configurationClasses.get(configClass);
    if (existingClass != null) {
        if (configClass.isImported()) {
            if (existingClass.isImported()) {
                existingClass.mergeImportedBy(configClass);
            }
            // Otherwise ignore new imported config class; existing non-imported class overrides it.
            return;
        }
        else {
            // Explicit bean definition found, probably replacing an import.
            // Let's remove the old one and go with the new one.
            this.configurationClasses.remove(configClass);
            this.knownSuperclasses.values().removeIf(configClass::equals);
        }
    }

    // Recursively process the configuration class and its superclass hierarchy.
    // 递归地处理配置类及其父类层次结构。
    SourceClass sourceClass = asSourceClass(configClass, filter);
    do {
	    // 递归处理Bean，如果有父类，递归处理，直到顶层父类
        sourceClass = doProcessConfigurationClass(configClass, sourceClass, filter);
    }
    while (sourceClass != null);

    this.configurationClasses.put(configClass, configClass);
}
```

看 doProcessConfigurationClass() 方法（SpringBoot的包扫描的入口方法，重点）

```java
@Nullable
protected final SourceClass doProcessConfigurationClass(
        ConfigurationClass configClass, SourceClass sourceClass, Predicate<String> filter)
        throws IOException {

    if (configClass.getMetadata().isAnnotated(Component.class.getName())) {
        // Recursively process any member (nested) classes first
        // 首先递归处理内部类(SpringBoot项目的主类一般没有内部类)
        processMemberClasses(configClass, sourceClass, filter);
    }

    // Process any @PropertySource annotations
    // 针对 @PropertySource 注解的属性配置处理
    for (AnnotationAttributes propertySource : AnnotationConfigUtils.attributesForRepeatable(
            sourceClass.getMetadata(), PropertySources.class,
            org.springframework.context.annotation.PropertySource.class)) {
        if (this.environment instanceof ConfigurableEnvironment) {
            processPropertySource(propertySource);
        }
        else {
            logger.info("Ignoring @PropertySource annotation on [" + sourceClass.getMetadata().getClassName() +
                    "]. Reason: Environment must implement ConfigurableEnvironment");
        }
    }

    // Process any @ComponentScan annotations
    // 根据 @ComponentScan 注解，扫描项目中的Bean (SpringBoot启动类上有该注解)
    Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(
            sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);
    if (!componentScans.isEmpty() &&
            !this.conditionEvaluator.shouldSkip(sourceClass.getMetadata(), ConfigurationPhase.REGISTER_BEAN)) {
        for (AnnotationAttributes componentScan : componentScans) {
            // The config class is annotated with @ComponentScan -> perform the scan immediately
            // 立即执行扫描 (SpringBoot项目为什么是从主类所在的包扫描，这就是关键)
            Set<BeanDefinitionHolder> scannedBeanDefinitions =
                    this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
            // Check the set of scanned definitions for any further config classes and parse recursively if needed
            for (BeanDefinitionHolder holder : scannedBeanDefinitions) {
                BeanDefinition bdCand = holder.getBeanDefinition().getOriginatingBeanDefinition();
                if (bdCand == null) {
                    bdCand = holder.getBeanDefinition();
                }
                // 检查是否是ConfigurationClass(是否有configuration/component 两个注解)，如果是，递归查找该类相关联的配置类。
				// 所谓相关的配置类，比如@Configuration中的@Bean定义的bean。或者在 有@Component注解的类上继续存在@Import注解。
                if (ConfigurationClassUtils.checkConfigurationClassCandidate(bdCand, this.metadataReaderFactory)) {
                    parse(bdCand.getBeanClassName(), holder.getBeanName());
                }
            }
        }
    }

    // Process any @Import annotations
    // 递归处理 @Import 注解(SpringBoot项目中经常用的各种@Enable*** 注解基本都是封装 的@Import)
    processImports(configClass, sourceClass, getImports(sourceClass), filter, true);

    // Process any @ImportResource annotations
    AnnotationAttributes importResource =
            AnnotationConfigUtils.attributesFor(sourceClass.getMetadata(), ImportResource.class);
    if (importResource != null) {
        String[] resources = importResource.getStringArray("locations");
        Class<? extends BeanDefinitionReader> readerClass = importResource.getClass("reader");
        for (String resource : resources) {
            String resolvedResource = this.environment.resolveRequiredPlaceholders(resource);
            configClass.addImportedResource(resolvedResource, readerClass);
        }
    }

    // Process individual @Bean methods
    Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(sourceClass);
    for (MethodMetadata methodMetadata : beanMethods) {
        configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
    }

    // Process default methods on interfaces
    processInterfaces(configClass, sourceClass);

    // Process superclass, if any
    if (sourceClass.getMetadata().hasSuperClass()) {
        String superclass = sourceClass.getMetadata().getSuperClassName();
        if (superclass != null && !superclass.startsWith("java") &&
                !this.knownSuperclasses.containsKey(superclass)) {
            this.knownSuperclasses.put(superclass, configClass);
            // Superclass found, return its annotation metadata and recurse
            return sourceClass.getSuperClass();
        }
    }

    // No superclass -> processing is complete
    return null;
}
```

我们大致说一下这个方法里面都干了什么：

-   `for (AnnotationAttributes propertySource : AnnotationConfigUtils.attributesForRepeatable(...) {}` 获取主类上的 `@PropertySource` 注解，解析该注解并将该注解指定的 properties 配置文件中的值存储到Spring 的 Environment 中， Environment 接口提供方法去读取配置文件中的值，参数是properties 文件中定义的 key 值。
-   `Set componentScans = AnnotationConfigUtils.attributesForRepeatable( sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);`解析主类上的 `@ComponentScan` 注解，后面的代码将会解析该注解并进行包扫描。
-   `processImports(configClass, sourceClass, getImports(sourceClass), true);` 解析主类上的 `@Import` 注解，并加载该注解指定的配置类。

TIPS:  
在spring中好多注解都是一层一层封装的，比如

-   @EnableXXX，是对 [@Import](/Import) 注解的二次封装。
-   @SpringBootApplication注解 = [@ComponentScan](/ComponentScan) + @EnableAutoConfiguration + [@Import](/Import) + [@Configuration](/Configuration) + [@Component](/Component)
-   @Configuration，@Controller，[@Service](/Service) 等等是对 [@Component](/Component) 的二次封装

继续向下看:  
`Set scannedBeanDefinitions = this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());`

```java
// ComponentScanAnnotationParser类  
public Set<BeanDefinitionHolder> parse(AnnotationAttributes componentScan, final String declaringClass) {

    ClassPathBeanDefinitionScanner scanner = new
ClassPathBeanDefinitionScanner(this.registry, componentScan.getBoolean("useDefaultFilters"), this.environment,
this.resourceLoader);

...
	// 根据 declaringClass (如果是SpringBoot项目，则参数为主类的全路径名) 
	if (basePackages.isEmpty()) {
        basePackages.add(ClassUtils.getPackageName(declaringClass));
    }
...

	// 根据basePackages扫描类
    return scanner.doScan(StringUtils.toStringArray(basePackages));
}
```

到这里 IoC 容器初始化三个步骤的第一步：Resource定位就完成了，成功定位到了主类所在的 包。接着往下看 `return scanner.doScan(StringUtils.toStringArray(basePackages));` Spring 是如何进行类扫描的，进入doScan()方法：

```java
// ComponentScanAnnotationParser类
protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
    Assert.notEmpty(basePackages, "At least one base package must be specified");
    Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
    for (String basePackage : basePackages) {
	    // 从指定的包中扫描需要装载的Bean
        Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
        for (BeanDefinition candidate : candidates) {
            ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
            candidate.setScope(scopeMetadata.getScopeName());
            String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
            if (candidate instanceof AbstractBeanDefinition) {
                postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
            }
            if (candidate instanceof AnnotatedBeanDefinition) {
                AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
            }
            if (checkCandidate(beanName, candidate)) {
                BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
                definitionHolder =
                        AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
                beanDefinitions.add(definitionHolder);
                // 将该Bean注册进IoC容器(beanDefinitionMap)
                registerBeanDefinition(definitionHolder, this.registry);
            }
        }
    }
    return beanDefinitions;
}
```

我们重点看两行有注释的地方，也就是说在这个方法中完成了 IoC 容器初始化过程的第二三步，BeanDefinition 的载入，和 BeanDefinition 的注册。

###### findCandidateComponents()

```java
ComponentScanAnnotationParser.doScan >
	ClassPathScanningCandidateComponentProvider.findCandidateComponents >
	ClassPathScanningCandidateComponentProvider.scanCandidateComponents
```

  

```java
private Set<BeanDefinition> scanCandidateComponents(String basePackage) {
    Set<BeanDefinition> candidates = new LinkedHashSet<>();
    try {
	    // 拼接扫描路径
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePackage) + '/' + this.resourcePattern;
        // 从packageSearchPath路径中扫描所有的类
        Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
        boolean traceEnabled = logger.isTraceEnabled();
        boolean debugEnabled = logger.isDebugEnabled();
        for (Resource resource : resources) {
            if (traceEnabled) {
                logger.trace("Scanning " + resource);
            }
            if (resource.isReadable()) {
                try {
                    MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                    //判断该类是不是@Component注解标注的类，并且不是需要排除掉的类
                    if (isCandidateComponent(metadataReader)) {
	                    // 将该类封装成ScannedGenericBeanDefinition类(BeanDefinition接口的实现类)
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                        sbd.setSource(resource);
                        if (isCandidateComponent(sbd)) {
                            if (debugEnabled) {
                                logger.debug("Identified candidate component class: " + resource);
                            }
                            candidates.add(sbd);
                        }
                        else {
                            if (debugEnabled) {
                                logger.debug("Ignored because not a concrete top-level class: " + resource);
                            }
                        }
                    }
                    else {
                        if (traceEnabled) {
                            logger.trace("Ignored because not matching any filter: " + resource);
                        }
                    }
                }
                catch (Throwable ex) {
                    throw new BeanDefinitionStoreException(
                            "Failed to read candidate component class: " + resource, ex);
                }
            }
            else {
                if (traceEnabled) {
                    logger.trace("Ignored because not readable: " + resource);
                }
            }
        }
    }
    catch (IOException ex) {
        throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
    }
    return candidates;
}

```

IoC 容器的 BeanDefinition 载入到这里就结束了。

###### registerBeanDefinition(definitionHolder, this.registry)

查看 registerBeanDefinition()方法 是不是有点眼熟，在前面介绍 prepareContext() 方法时， 我们详细介绍了主类的 BeanDefinition 是怎么一步一步的注册进 DefaultListableBeanFactory 的 beanDefinitionMap 中的。完成了 BeanDefinition 的注册，就完成了 IoC 容器的初始化过程。

此时，在使用的 IoC 容器 DefaultListableFactory 中已经建立了整个Bean的配置信息，而这些  BeanDefinition 已经可以被容器使用了。他们都在 BeanbefinitionMap 里被检索和使用。容器的作用就是对这些信息进行处理和维护，这些信息是容器控制反转的基础。

到这里 IoC 容器的初始化过程的三个步骤就梳理完了。当然这只是针对 SpringBoot 的包扫描的定位方式的 BeanDefinition 的定位、加载和注册过程。前面我们说过，还有两种方式 [@Import](/Import) 和SPI 扩展实现的 starter 的自动装配。

##### [@Import](/Import) 注解的解析过程

现在大家也应该知道了，各种 `@EnableXXX` 注解，很大一部分都是对 `@Import`的二次封装，其实也是为了解耦，比如当 `@Import` 导入的类发生变化时，我们的业务系统也不需要改任何代码。

我们又要回到上文中的 ConfigurationClassParser 类的 doProcessConfigurationClass 方法的 `processImports(configClass, sourceClass, getImports(sourceClass), true);`，跳跃性比较大。上面解释过，我们只针对主类进行分析，因为这里有递归。该方法的入参 configClass 和 sourceClass 都是和主类相对应的。

首先看 `getImports(sourceClass);`

```java
private Set<SourceClass> getImports(SourceClass sourceClass) throws IOException {
    Set<SourceClass> imports = new LinkedHashSet<>();
    Set<SourceClass> visited = new LinkedHashSet<>();
    collectImports(sourceClass, imports, visited);
    // 记得在return这里设置断点，看看返回的是什么
    return imports;
}
```

接着从主方法跟踪下去，最后会来到 `AutoConfigurationImportSelector.AutoConfigurationGroup.process()`

这里就回到了Spring Boot源码中去了：

```java
@Override
public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
    Assert.state(deferredImportSelector instanceof AutoConfigurationImportSelector,
            () -> String.format("Only %s implementations are supported, got %s",
                    AutoConfigurationImportSelector.class.getSimpleName(),
                    deferredImportSelector.getClass().getName()));
    // 【1】,调用getAutoConfigurationEntry方法得到自动配置类放入 autoConfigurationEntry对象中
    AutoConfigurationEntry autoConfigurationEntry = ((AutoConfigurationImportSelector) deferredImportSelector)
            .getAutoConfigurationEntry(getAutoConfigurationMetadata(), annotationMetadata);
    // 【2】，又将封装了自动配置类的autoConfigurationEntry对象装进 autoConfigurationEntries集合
    this.autoConfigurationEntries.add(autoConfigurationEntry);
    // 【3】，遍历刚获取的自动配置类
    for (String importClassName : autoConfigurationEntry.getConfigurations()) {
        // 这里符合条件的自动配置类作为key，annotationMetadata作为值放进entries集合
        this.entries.putIfAbsent(importClassName, annotationMetadata);
    }
}
```

### 第六步：刷新应用上下文后的扩展接口

```java
protected void afterRefresh(ConfigurableApplicationContext context,
        ApplicationArguments args) {

}
```

扩展接口，设计模式中的模板方法，默认为空实现。如果有自定义需求，可以重写该方法。比如打 印一些启动结束log，或者一些其它后置处理。