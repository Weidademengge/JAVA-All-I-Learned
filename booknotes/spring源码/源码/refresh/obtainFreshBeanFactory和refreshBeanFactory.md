
```java
protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
   //初始化BeanFactory，并进行XML文件读取，并将获得的BeanFactory记录在当前实体的属性中
   refreshBeanFactory();  
   //返回当前实体的BeanFactory属性
   return getBeanFactory();  
}
```

在AbstractRefreshableApplicationContext中实现了refreshBeanFactory方法
```java
@Override  
protected final void refreshBeanFactory() throws BeansException {
   //如果存在beanFactory，则销毁beanFactory
   if (hasBeanFactory()) {  
      destroyBeans();  
      closeBeanFactory();  
   }
   //重新创建  
   try {
      //beanFactory  
      DefaultListableBeanFactory beanFactory = createBeanFactory();
      //为了序列化指定id，可以从id反序列化到beanFactory对象  
      beanFactory.setSerializationId(getId());
      //定制beanFactory，设置相关属性，包括是否允许覆盖同名称的不同定义的对象以及循环依赖  
      customizeBeanFactory(beanFactory);
      //初始化documentReader，并进行XML文件读取及解析  
      //加载bean的定义信息，相当于加载xml中bean标签中的属性
      loadBeanDefinitions(beanFactory);  
      this.beanFactory = beanFactory;  
   }  
   catch (IOException ex) {  
      throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);  
   }  
}
```




```java
@Override  
protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {  
   //创建一个xml的beanDefinitionReader，并通过回调设置到beanFactory中
   XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);  
  
   //给reader对象设置环境对象
   //稍微说细点，这里还挺有意思，如果是写xml文件，比如<property name ="username" value = ${username}>，这里如果报错，是因为在setEnviroment把环境变量中的username赋值了，所以报错。所以要改的话改成<property name ="username" value = ${jdbc.username}>
   beanDefinitionReader.setEnvironment(this.getEnvironment());  
   beanDefinitionReader.setResourceLoader(this); 
   //设置实体处理器， 
   beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));  
  
   // 初始化beanDefinitionReader对象，此处设置配置文件是否要进行验证
   initBeanDefinitionReader(beanDefinitionReader);  
   // 开始完成beanDefinition的加载（和上面的同名方法传入的参数是不同的）
   loadBeanDefinitions(beanDefinitionReader);  
}
```

``` java
//再上一个方法中设置实体处理器中的ResourceEntityResolver
public DelegatingEntityResolver(@Nullable ClassLoader classLoader) {
   //这个简单，xml使用的是dtd格式，就用dtdResolver
   this.dtdResolver = new BeansDtdResolver();
   //  这个是XSD处理器，
   this.schemaResolver = new PluggableSchemaResolver(classLoader);  
}
```

```java
public PluggableSchemaResolver(@Nullable ClassLoader classLoader) {  
   this.classLoader = classLoader;
   //指出关键路径，这里指的就是"META-INF/spring.schemas"，这里面全是网络上各个xsd解析器以及映射的本地xsd解析器
   //这里有第二个问题，在debug过程中，会自动调用tostring方法， 在tostring方法中调用了getSchemaMappings方法，所以将xml中bean标签中的bean全部放入到schemaMappingLocation中
   this.schemaMappingsLocation = DEFAULT_SCHEMA_MAPPINGS_LOCATION;  
}
```

```java
private Map<String, String> getSchemaMappings() {  
   Map<String, String> schemaMappings = this.schemaMappings;  
   if (schemaMappings == null) {  
      synchronized (this) {  
         schemaMappings = this.schemaMappings;  
         if (schemaMappings == null) {  
            if (logger.isTraceEnabled()) {  
               logger.trace("Loading schema mappings from [" + this.schemaMappingsLocation + "]");  
            }  
            try {  
            //在这里加的
               Properties mappings = PropertiesLoaderUtils.loadAllProperties(this.schemaMappingsLocation, this.classLoader);  
               if (logger.isTraceEnabled()) {  
                  logger.trace("Loaded schema mappings: " + mappings);  
               }  
               schemaMappings = new ConcurrentHashMap<>(mappings.size());  
               CollectionUtils.mergePropertiesIntoMap(mappings, schemaMappings);  
               this.schemaMappings = schemaMappings;  
            }  
            catch (IOException ex) {  
               throw new IllegalStateException(  
                     "Unable to load schema mappings from location [" + this.schemaMappingsLocation + "]", ex);  
            }  
         }  
      }  
   }  
   return schemaMappings;  
}
```