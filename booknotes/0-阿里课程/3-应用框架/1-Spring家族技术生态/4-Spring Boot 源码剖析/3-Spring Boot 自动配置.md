Spring Boot 应用的启动入口是 `@SpringBootApplication` 注解标注类中的 main() 方法

## @SpringBootApplication

下面，查看 `@SpringBootApplication` 内部源码进行分析 ，核心代码具体如下

```java
@Target({ElementType.TYPE}) //注解的适用范围,Type表示注解可以描述在类、接口、注解或枚举中 
@Retention(RetentionPolicy.RUNTIME) //表示注解的生命周期，Runtime运行时
@Documented //表示注解可以记录在javadoc中
@Inherited //表示可以被子类继承该注解
@SpringBootConfiguration // 标明该类为配置类
@EnableAutoConfiguration // 启用自动配置功能
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
      @Filter(type = FilterType.CUSTOM, classes =  AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
    
    @AliasFor(annotation = EnableAutoConfiguration.class)
    // 根据class来排除特定的类，使其不能加入spring容器，传入参数value类型是class类型。 
    Class<?>[] exclude() default {};
    
    @AliasFor(annotation = EnableAutoConfiguration.class)
    // 根据classname来排除特定的类，使其不能加入spring容器，传入参数value类型是class的全 类名字符串数组。
    String[] excludeName() default {};
    
    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages") 
    // 指定扫描包，参数是包名的字符串数组。
    String[] scanBasePackages() default {};
    
    @AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
    // 扫描特定的包，参数类似是Class类型数组。
    Class<?>[] scanBasePackageClasses() default {};
}
```

从上述源码可以看出，`@SpringBootApplication` 注解是一个组合注解，前面 4 个是注解的元数据信息， 我们主要看后面 3 个核心注解：

-   `@SpringBootConfiguration`
-   `@EnableAutoConfiguration`
-   `@ComponentScan`

## @SpringBootConfiguration

`@SpringBootConfiguration` : Spring Boot 的配置类，标注在某个类上，表示这是一个 Spring Boot 的配置类。

查看 `@SpringBootConfiguration` 注解源码，核心代码具体如下。

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration // 配置类的作用等同于配置文件，配置类也是容器中的一个对象 
public @interface SpringBootConfiguration {
}
```

从上述源码可以看出，`@SpringBootConfiguration` 注解内部有一个核心注解 `@Configuration`，该注解是 Spring 框架提供的，表示当前类为一个配置类（XML 配置文件的注解表现形式），并可以被组件扫描器扫描。

由此可见，`@SpringBootConfiguration` 注解的作用与 `@Configuration` 注解相同，都是标识一个可以被组件扫描器扫描的配置类，只不过 `@SpringBootConfiguration` 是被 Spring Boot 进行了重新封装命名而已。

## @EnableAutoConfiguration

此注解顾名思义是**可以自动配置**，所以应该是 Spring Boot 中**最为重要的注解**。

```java
@AutoConfigurationPackage// 自动配置包
@Import(AutoConfigurationImportSelector.class)// 给容器中导入一个组件
public @interface EnableAutoConfiguration {
    
	String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration"; 
    
    // 返回不会被导入到 Spring 容器中的类
	Class<?>[] exclude() default {}; 
    
    // 返回不会被导入到 Spring 容器中的类名
    String[] excludeName() default {};
}
```
  

Spring 中有很多以 Enable 开头的注解，其作用就是借助 `@Import` 来收集并注册特定场景相关的 Bean，并加载到 IOC 容器。

`@EnableAutoConfiguration` 就是借助 `@Import` 来收集所有符合自动配置条件的 Bean 定义，并加载到 IoC 容器。告诉 Spring Boot 开启自动配置功能，这样自动配置才能生效。

所以，我们说 Spring Boot 自动配置的核心就在 `@EnableAutoConfiguration` 注解上，这个注解通过 `@Import(AutoConfigurationImportSelector)` 来完成自动配置。

### @AutoConfigurationPackage

  

```java
@Import(AutoConfigurationPackages.Registrar.class) // 导入Registrar中注册的组件
public @interface AutoConfigurationPackage {
}
```

`@AutoConfigurationPackage`：自动配置包，它也是一个组合注解，其中最重要的注解是 `@Import(AutoConfigurationPackages.Registrar.class)` ，它是 Spring 框架的底层注解，它的作用就是给容器中导入某个组件类，将 Registrar 这个组件类导入到容器中，可查看 Registrar 类中 registerBeanDefinitions 方法：

```java
@Override
public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
	// 将注解标注的元信息传入，获取到相应的包名
    register(registry, new PackageImport(metadata).getPackageName());
}
```

在此处设置断点，Debug 模式启动 UserTestAllApplication 后会停在此处，我们对 `new PackageImport(metadata).getPackageName()` 进行检索，看看其结果是什么？

![](https://lang-image-bed.oss-cn-hangzhou.aliyuncs.com/20230404162114.png)

再看 register 方法：

```java
public static void register(BeanDefinitionRegistry registry, String... packageNames) {
    // 如果该bean已经注册，则将要注册包名称添加进去
	if (registry.containsBeanDefinition(BEAN)) {
		BeanDefinition beanDefinition = registry.getBeanDefinition(BEAN);
		ConstructorArgumentValues constructorArguments = beanDefinition.getConstructorArgumentValues();
		constructorArguments.addIndexedArgumentValue(0, addBasePackages(constructorArguments, packageNames));
	}
    // 如果该bean尚未注册，则注册该bean，参数中提供的包名称会被设置到bean定义中去
	else {
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(BasePackages.class);
		beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, packageNames);
		beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		registry.registerBeanDefinition(BEAN, beanDefinition);
	}
}
```

断点进来后，可以看到这里参数 packageNames 是使用了注解  `@SpringBootApplication` 的 Spring Boot 应用程序入口类所在的包路径：com.ebanma.cloud.usertestall

AutoConfigurationPackages.Registrar 这个类就干一个事，注册一个 Bean，这个 Bean 就是  
`org.springframework.boot.autoconfigure.AutoConfigurationPackages.BasePackages` ，它有一个参数，这个参数是使用了 `@AutoConfigurationPackage` 这个注解的类所在的包路径,保存自动配置类以供之后的使用，比如给 JPA entity 扫描器用来扫描开发人员通过注解 `@Entity` 定义的 entity 类。

### @Import(AutoConfigurationImportSelector.class)

`@Import({AutoConfigurationImportSelector.class})`：将 AutoConfigurationImportSelector  这个类导入到 Spring 容器中，此类可以帮助 Spring Boot 应用将所有符合条件的 `@Configuration` 配置都加载到当前 Spring Boot 创建并使用的 IOC 容器( ApplicationContext )中。

  

![](https://lang-image-bed.oss-cn-hangzhou.aliyuncs.com/AutoConfigurationImportSelector.png)

了解一个类，我们首先就要先看这个类的定义：

```java
public class AutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware, ResourceLoaderAware, BeanFactoryAware, EnvironmentAware, Ordered {
}
```

可以看到 AutoConfigurationImportSelector 实现了 DeferredImportSelector 接口和各种 Aware 接口（`*Aware` 分别表示在某个时机会被回调），然后 `DeferredImportSelector` 接口又继承了 `ImportSelector` 接口。这样，我们就明白了，`AutoConfigurationImportSelector` 类必定实现了 `selectImports()` 方法，这个方法应该就是 Spring Boot 能够实现自动配置的核心。

### selectImports() 方法

```java
private static final String[] NO_IMPORTS = {};

@Override
public String[] selectImports(AnnotationMetadata annotationMetadata) {
   // 判断SpringBoot是否开启自动配置
   if (!isEnabled(annotationMetadata)) {
      return NO_IMPORTS;
   }
   // 获取需要被引入的自动配置信息
   AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
   return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
}
```

selectImports() 方法的源码没有多少。isEnabled() 方法判断 Spring Boot 是否开启了自动配置。若开启就通过 getAutoConfigurationEntry() 来获取需要配置的 Bean 全限定名数组，否则就直接返回空数组。

#### isEnabled() 方法：判断 Spring Boot 是否开启自动配置

```java
protected boolean isEnabled(AnnotationMetadata metadata) {
   if (getClass() == AutoConfigurationImportSelector.class) {
      // 若调用该方法的类是AutoConfigurationImportSelector，那么就获取EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY的值，默认为true
      return getEnvironment().getProperty(EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY, Boolean.class, true);
   }
   return true;
}
```

`EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY`是什么？

我们看下它的定义：`String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";`

看到这里，我们可以猜到这就是在配置文件 application.yml 或者 application.properties 中的配置。因此，我们可以在配置文件中来决定 Spring Boot 是否开启自动配置。

当我们没有配置的时候，默认就是开启自动配置的。

#### getAutoConfigurationEntry() 方法：获取需要自动配置的 Bean 信息

```java
// 获取符合条件的自动配置类，避免加载不必要的自动配置类从而造成内存浪费
protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
   // 判断是否开启自动配置
   if (!isEnabled(annotationMetadata)) {
      return EMPTY_ENTRY;
   }
   // 获取@EnableAutoConfiguration注解的属性
   AnnotationAttributes attributes = getAttributes(annotationMetadata);
   // 【1】从spring.factories文件中获取配置类的全限定类名列表
   List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
   // 利用LinkedHashSet移除重复的配置类
   configurations = removeDuplicates(configurations);
   // 获取注解中exclude或excludeName排除的类集合
   Set<String> exclusions = getExclusions(annotationMetadata, attributes);
   // 检查被排除类是否可以实例化，是否被自动配置所使用，否则抛出异常
   checkExcludedClasses(configurations, exclusions);
   // 【2】将要排除的配置类移除
   configurations.removeAll(exclusions);
   // 【3】因为从spring.factories文件获取的自动配置类太多，如果有些不必要的自动配置类都加载进内存，会造成内存浪费，因此这里需要进行过滤
   // 注意这里会调用AutoConfigurationImportFilter的match方法来判断是否符合@ConditionalOnBean、@ConditionalOnClass或@ConditionalOnWebApplication，后面会重点分析一下
   configurations = getConfigurationClassFilter().filter(configurations);
   // 【4】获取了符合条件的自动配置类后，此时触发AutoConfigurationImportEvent事件，目的是告诉ConditionEvaluationReport条件评估报告器对象来记录符合条件的自动配置类  
   // 该事件什么时候会被触发? --> 在刷新容器时调用invokeBeanFactoryPostProcessors后置处理器时触发
   fireAutoConfigurationImportEvents(configurations, exclusions);
   // 【5】将符合条件和要排除的自动配置类封装进AutoConfigurationEntry对象，并返回
   return new AutoConfigurationEntry(configurations, exclusions);
}
```

以上，我们分析了 `AutoConfigurationImportSelector` 类是 `ImportSelector` 的实现类，实现了 `selectImports()` 方法。`selectImports()` 方法又调用 `getAutoConfigurationEntry()` 方法，该方法从 spring.factories 文件中读取配置类的全限定名类名列表，并进行过滤，最终得到需要自动配置的类全限定名列表。

看到这里，你应该会觉得自动配置的实现就是通过这个 `selectImports()` 方法，但实际上这个方法通常并不会被调用到，而是会调用该类的内部类 `AutoConfigurationGroup` 的 `process()` 和`selectImports()` 方法，前者同样是通过 getAutoConfigurationEntry 拿到所有的自动配置类，而后者则是过滤排序并包装后返回。

---

### group.process() 方法

为什么要重点关注 `AutoConfigurationImportSelector.AutoConfigurationGroup#process` 方法？因为 Spring Boot 启动时会调用该方法进行自动装配，下一章节我们会讲到以下的调用链：

```java
SpringApplication#run >
	SpringApplication#refreshContext >
	ConfigurationClassParser#parse >
	AutoConfigurationImportSelector.AutoConfigurationGroup#process
```

因此 process() 方法的代码就是我们分析的重中之重，自动配置的相关的绝大部分逻辑全在这里了，而它主要的职责就是选择一些符合条件的自动配置类，过滤掉一些不符合条件的自动配置类，就是这么个事情。

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

#### getAutoConfigurationEntry() 方法：获取需要自动配置的 Bean 信息

上面代码中我们再来看标 【1】 的方法 getAutoConfigurationEntry ，这个方法主要是用来获取符合条件的自动配置类，避免加载不必要的自动配置类从而造成内存浪费。它承担了自动配置的主要逻辑，直接上代码：

```java
// 获取符合条件的自动配置类，避免加载不必要的自动配置类从而造成内存浪费
protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
   // 判断是否开启自动配置
   if (!isEnabled(annotationMetadata)) {
      return EMPTY_ENTRY;
   }
   // 获取@EnableAutoConfiguration注解的属性
   AnnotationAttributes attributes = getAttributes(annotationMetadata);
   // 【1】从spring.factories文件中获取配置类的全限定类名列表
   List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
   // 利用LinkedHashSet移除重复的配置类
   configurations = removeDuplicates(configurations);
   // 获取注解中exclude或excludeName排除的类集合
   Set<String> exclusions = getExclusions(annotationMetadata, attributes);
   // 检查被排除类是否可以实例化，是否被自动配置所使用，否则抛出异常
   checkExcludedClasses(configurations, exclusions);
   // 【2】将要排除的配置类移除
   configurations.removeAll(exclusions);
   // 【3】因为从spring.factories文件获取的自动配置类太多，如果有些不必要的自动配置类都加载进内存，会造成内存浪费，因此这里需要进行过滤
   // 注意这里会调用AutoConfigurationImportFilter的match方法来判断是否符合@ConditionalOnBean、@ConditionalOnClass或@ConditionalOnWebApplication，后面会重点分析一下
   configurations = getConfigurationClassFilter().filter(configurations);
   // 【4】获取了符合条件的自动配置类后，此时触发AutoConfigurationImportEvent事件，目的是告诉ConditionEvaluationReport条件评估报告器对象来记录符合条件的自动配置类  
   // 该事件什么时候会被触发? --> 在刷新容器时调用invokeBeanFactoryPostProcessors后置处理器时触发
   fireAutoConfigurationImportEvents(configurations, exclusions);
   // 【5】将符合条件和要排除的自动配置类封装进AutoConfigurationEntry对象，并返回
   return new AutoConfigurationEntry(configurations, exclusions);
}
```

#### getCandidateConfigurations()

```java
protected List<String> getCandidateConfigurations(AnnotationMetadata
metadata, AnnotationAttributes attributes) {

	// 这个方法需要传入两个参数getSpringFactoriesLoaderFactoryClass()和 getBeanClassLoader()
	// getSpringFactoriesLoaderFactoryClass()这个方法返回的是 EnableAutoConfiguration.class
	// getBeanClassLoader()这个方法返回的是beanClassLoader(类加载器) 
	List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
	
	Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
	+ "are using a custom packaging, make sure that file is correct.");
	return configurations;
}
```

这个方法中有一个重要方法 `SpringFactoriesLoader.loadFactoryNames()` 目的是去加载一些组件的名字，我们继续点到 loadFactoryNames() 方法里的 loadSpringFactories() 方法：

```java
private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
    MultiValueMap<String, String> result = cache.get(classLoader);
    if (result != null) {
        return result;
    }

    try {
	    // 如果类加载器不为null，则加载类路径下spring.factories文件，将其中设置的配置类的全路径信息封装为Enumeration类对象
        Enumeration<URL> urls = (classLoader != null ?
                classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
        result = new LinkedMultiValueMap<>();
        // 循环Enumeration类对象，根据相应的节点信息生成Properties对象，通过传入的键获取值，在将值切割为一个个小的字符串转化为Array，方入result集合中
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            UrlResource resource = new UrlResource(url);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            for (Map.Entry<?, ?> entry : properties.entrySet()) {
                String factoryTypeName = ((String) entry.getKey()).trim();
                for (String factoryImplementationName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
                    result.add(factoryTypeName, factoryImplementationName.trim());
                }
            }
        }
        cache.put(classLoader, result);
        return result;
    }
    catch (IOException ex) {
        throw new IllegalArgumentException("Unable to load factories from location [" +
                FACTORIES_RESOURCE_LOCATION + "]", ex);
    }
}
```

从代码中我们可以知道，在这个方法中会遍历整个ClassLoader中所有jar包下的spring.factories文件。 spring.factories里面保存着springboot的默认提供的自动配置类：

![](https://lang-image-bed.oss-cn-hangzhou.aliyuncs.com/20230412013955.png)

总结了 getAutoConfigurationEntry() 方法主要的逻辑后，我们再来细看一下 AutoConfigurationImportSelector 的 filter() 方法：

```java
private List<String> filter(List<String> configurations, AutoConfigurationMetadata autoConfigurationMetadata) {
    long startTime = System.nanoTime();
    // 将从spring.factories中获取的自动配置类转出字符串数组
    String[] candidates = StringUtils.toStringArray(configurations);
    // 定义skip数组，是否需要跳过。注意skip数组与candidates数组顺序一一对应
    boolean[] skip = new boolean[candidates.length];
    boolean skipped = false;
    // getAutoConfigurationImportFilters方法：拿到OnBeanCondition，OnClassCondition和OnWebApplicationCondition  
	// 然后遍历这三个条件类去过滤从spring.factories加载的大量配置类
    for (AutoConfigurationImportFilter filter : getAutoConfigurationImportFilters()) {
	    // 调用各种aware方法，将beanClassLoader,beanFactory等注入到filter对象中，这里的filter对象即OnBeanCondition，OnClassCondition或OnWebApplicationCondition
        invokeAwareMethods(filter);
        // 判断各种filter来判断每个candidate(这里实质要通过candidate(自动配置类)拿到其标注的 @ConditionalOnClass,@ConditionalOnBean和@ConditionalOnWebApplication里 面的注解值)是否匹配，【主线，重点关注】
        boolean[] match = filter.match(candidates, autoConfigurationMetadata);
        // 遍历match数组，注意match顺序跟candidates的自动配置类一一对应
        for (int i = 0; i < match.length; i++) {
		    // 若有不匹配的话
            if (!match[i]) {
	            // 不匹配的将记录在skip数组，标志skip[i]为true，也与candidates数组一一对应
                skip[i] = true;
                // 因为不匹配，将相应的自动配置类置空
                candidates[i] = null;
                // 标注skipped为true
                skipped = true;
            }
        }
    }
    // 这里表示若所有自动配置类经过OnBeanCondition，OnClassCondition和 OnWebApplicationCondition过滤后，全部都匹配的话，则全部原样返回
    if (!skipped) {
        return configurations;
    }
    // 建立result集合来装匹配的自动配置类
    List<String> result = new ArrayList<>(candidates.length);
    for (int i = 0; i < candidates.length; i++) {
	    // 若skip[i]为false，则说明是符合条件的自动配置类，此时添加到result集合中
        if (!skip[i]) {
            result.add(candidates[i]);
        }
    }
    if (logger.isTraceEnabled()) {
        int numberFiltered = configurations.size() - result.size();
        logger.trace("Filtered " + numberFiltered + " auto configuration class in "
                + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");
    }
    // 最后返回符合条件的自动配置类
    return new ArrayList<>(result);
}
```

  

### group.selectImports() 方法

进一步有选择的导入自动配置类，直接看代码：

```java
@Override
public Iterable<Entry> selectImports() {
    if (this.autoConfigurationEntries.isEmpty()) {
        return Collections.emptyList();
    }
    // 这里得到所有要排除的自动配置类的set集合
    Set<String> allExclusions = this.autoConfigurationEntries.stream()
            .map(AutoConfigurationEntry::getExclusions).flatMap(Collection::stream).collect(Collectors.toSet());
    // 这里得到经过滤后所有符合条件的自动配置类的set集合
    Set<String> processedConfigurations = this.autoConfigurationEntries.stream()
            .map(AutoConfigurationEntry::getConfigurations).flatMap(Collection::stream)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    // 移除掉要排除的自动配置类
    processedConfigurations.removeAll(allExclusions);
	// 对标注有@Order注解的自动配置类进行排序
    return sortAutoConfigurations(processedConfigurations, getAutoConfigurationMetadata()).stream()
            .map((importClassName) -> new Entry(this.entries.get(importClassName), importClassName))
            .collect(Collectors.toList());
}
```
  

可以看到， selectImports() 方法主要是针对经过排除掉 exclude 的和被 AutoConfigurationImportFilter 接口过滤后的满足条件的自动配置类再进一步排除 exclude 的自动配置类，最后再排序。

### 精髓

1.  SpringBoot 启动会加载大量的自动配置类
2.  我们看我们需要实现的功能有没有 SpringBoot 默认写好的自动配置类
3.  我们再来看这个自动配置类中到底配置了哪些组件（只要有我们要用的组件，我们就不需要再来配置了）
4.  给容器中自动配置类添加组件的时候，会从 properties 类中获取某些属性，我们就可以在配置文件中指定这些属性的值。

## [@ComponentScan](/ComponentScan)

主要是从定义的扫描路径中，找出标识了需要装配的类自动装配到 Spring 的 Bean 容器中。

常用属性如下:

-   basePackages、value：指定扫描路径，如果为空则以 `@ComponentScan` 注解的类所在的包为基本的扫描路径
-   basePackageClasses：指定具体扫描的类
-   includeFilters：指定满足Filter条件的类
-   excludeFilters：指定排除Filter条件的类

includeFilters 和 excludeFilters 的 FilterType 可选：ANNOTATION(注解类型，默认)、 ASSIGNABLE_TYPE(指定固定类)、ASPECTJ(ASPECTJ类型)、REGEX(正则表达式)、CUSTOM(自定义类型)，自定义的 Filter 需要实现 TypeFilter 接口

```java
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),  
@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
```

借助 excludeFilters 将 TypeExcludeFillter 及 FilterType 这两个类进行排除，当前 `@ComponentScan` 注解没有标注 basePackages 和 value，所以扫描路径默认为`@ComponentScan` 注解的类所在的包为基本的扫描路径，也就是标注了 `@SpringBootApplication` 注解的项目启动类所在的路径。