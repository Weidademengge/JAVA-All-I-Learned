```java 
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {  
  
   /**  
    * 消息源Bean的名字
    */   
    public static final String MESSAGE_SOURCE_BEAN_NAME = "messageSource";  
  
   /**  
    * 生命周期Bean的名字
    */   
    public static final String LIFECYCLE_PROCESSOR_BEAN_NAME = "lifecycleProcessor";  
  
   /**  
    * 事件广播Bean的名字
    */   
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";  
  
   /**  
    * 第一步就到这，还不知道干嘛，但假装没用
    */   
    private static final boolean shouldIgnoreSpel = SpringProperties.getFlag("spring.spel.ignore");  
  
  
   static {  
	   //优先加载上下文关闭事件来防止奇怪的加载问题在应用程序关闭的时候
	   //这是webLogic热启动的一个bug，不用管他
	    ContextClosedEvent.class.getName();  
   }  
  
  
   /** 日志，先不管*/  
   protected final Log logger = LogFactory.getLog(getClass());  
  
   /** 
   * 创建唯一表示的ID值 
   * org.springframework.context.support.ClassPathXmlApplicationContext@3d24753a
   * 在createBeanFactory方法中，要返回一个DefaultListableBeanFactory类，这个生成的BeanFactory中也会有这个id值，这个ID是context的id
   */  
   private String id = ObjectUtils.identityToString(this);  
  
   /** 展示的名称和id一样 */  
   private String displayName = ObjectUtils.identityToString(this);  
  
   /** 
   * 第一遍跳过了，没赋值
   */  
   @Nullable  
   private ApplicationContext parent;  
  
   /** 
   * 第一遍跳过了，没赋值
   */  
   @Nullable  
   private ConfigurableEnvironment environment;  
  
   /** 修改BeanDefination的扩展增强集合 */  
   private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();  
  
   /** 第一遍跳过了 */  
   private long startupDate;  
  
   /** 设置当前context是激活状态还是未激活状态 */  
   private final AtomicBoolean active = new AtomicBoolean();  
  
   /** 设置当前context是激活状态还是未激活状态 */  
   private final AtomicBoolean closed = new AtomicBoolean();  
  
   /** 在refresh和destroy这两部分加锁 */  
   private final Object startupShutdownMonitor = new Object();  
  
   /** 先不管 */  
   @Nullable  
   private Thread shutdownHook;  
  
   /** 先不管 */  
   private ResourcePatternResolver resourcePatternResolver;  
  
   /** 先不管*/  
   @Nullable  
   private LifecycleProcessor lifecycleProcessor;  
  
   /** 先不管 */  
   @Nullable  
   private MessageSource messageSource;  
  
   /** 先不管 */  
   @Nullable  
   private ApplicationEventMulticaster applicationEventMulticaster;  
  
   /** 先不管 **/  
   private ApplicationStartup applicationStartup = ApplicationStartup.DEFAULT;  
  
   /** 先不管 */  
   private final Set<ApplicationListener<?>> applicationListeners = new LinkedHashSet<>();  
  
   /** 先不管 */  
   @Nullable  
   private Set<ApplicationListener<?>> earlyApplicationListeners;  
  
   /** 先不管 */  
   @Nullable  
   private Set<ApplicationEvent> earlyApplicationEvents;  
  
  
   /**  
    * 构造器了  
    */   
    public AbstractApplicationContext() {
    //  resourcePatternResolver就是解析资源的，要么是xml，要么是注解config
    // 创建了一个类PathMatchingResourcePatternresolver实现了ResourceLoader资源加载器
      this.resourcePatternResolver = getResourcePatternResolver();  
   }  

	/**  
    * 在spring中没有父子容器的概念，而在springmvc中有父子容器时，这时传入的parent不为null
    * 在springmvc再看
    */  
   public AbstractApplicationContext(@Nullable ApplicationContext parent) {  
      this();  
      
      setParent(parent);  
   }  
  
  
   @Override  
   public void setId(String id) {  
      this.id = id;  
   }  
  
   @Override  
   public String getId() {  
      return this.id;  
   }  
  
   @Override  
   public String getApplicationName() {  
      return "";  
   }  
  
   public void setDisplayName(String displayName) {  
      Assert.hasLength(displayName, "Display name must not be empty");  
      this.displayName = displayName;  
   }  
  
   @Override  
   public String getDisplayName() {  
      return this.displayName;  
   }  
  
   @Override  
   @Nullable   public ApplicationContext getParent() {  
      return this.parent;  
   }  
  
   @Override  
   public void setEnvironment(ConfigurableEnvironment environment) {  
      this.environment = environment;  
   }  
  
   @Override  
   public ConfigurableEnvironment getEnvironment() {  
      if (this.environment == null) {  
         this.environment = createEnvironment();  
      }  
      return this.environment;  
   }  
  
   protected ConfigurableEnvironment createEnvironment() {  
      return new StandardEnvironment();  
   }  
  
   @Override  
   public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {  
      return getBeanFactory();  
   }  
  
   @Override  
   public long getStartupDate() {  
      return this.startupDate;  
   }  
  
   @Override  
   public void publishEvent(ApplicationEvent event) {  
      publishEvent(event, null);  
   }  
  
   @Override  
   public void publishEvent(Object event) {  
      publishEvent(event, null);  
   }  
  
   protected void publishEvent(Object event, @Nullable ResolvableType eventType) {  
      Assert.notNull(event, "Event must not be null");  
  
      // Decorate event as an ApplicationEvent if necessary  
      ApplicationEvent applicationEvent;  
      if (event instanceof ApplicationEvent) {  
         applicationEvent = (ApplicationEvent) event;  
      }  
      else {  
         applicationEvent = new PayloadApplicationEvent<>(this, event);  
         if (eventType == null) {  
            eventType = ((PayloadApplicationEvent<?>) applicationEvent).getResolvableType();  
         }  
      }  
  
      // Multicast right now if possible - or lazily once the multicaster is initialized  
      if (this.earlyApplicationEvents != null) {  
         this.earlyApplicationEvents.add(applicationEvent);  
      }  
      else {  
        getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);  
      }  
  
      // Publish event via parent context as well...  
      if (this.parent != null) {  
         if (this.parent instanceof AbstractApplicationContext) {  
            ((AbstractApplicationContext) this.parent).publishEvent(event, eventType);  
         }  
         else {  
            this.parent.publishEvent(event);  
         }  
      }  
   }  
  
   /**  
    * Return the internal ApplicationEventMulticaster used by the context.    * @return the internal ApplicationEventMulticaster (never {@code null})  
    * @throws IllegalStateException if the context has not been initialized yet  
    */   ApplicationEventMulticaster getApplicationEventMulticaster() throws IllegalStateException {  
      if (this.applicationEventMulticaster == null) {  
         throw new IllegalStateException("ApplicationEventMulticaster not initialized - " +  
               "call 'refresh' before multicasting events via the context: " + this);  
      }  
      return this.applicationEventMulticaster;  
   }  
  
   @Override  
   public void setApplicationStartup(ApplicationStartup applicationStartup) {  
      Assert.notNull(applicationStartup, "applicationStartup should not be null");  
      this.applicationStartup = applicationStartup;  
   }  
  
   @Override  
   public ApplicationStartup getApplicationStartup() {  
      return this.applicationStartup;  
   }  
  
   /**  
    * Return the internal LifecycleProcessor used by the context.    * @return the internal LifecycleProcessor (never {@code null})  
    * @throws IllegalStateException if the context has not been initialized yet  
    */   LifecycleProcessor getLifecycleProcessor() throws IllegalStateException {  
      if (this.lifecycleProcessor == null) {  
         throw new IllegalStateException("LifecycleProcessor not initialized - " +  
               "call 'refresh' before invoking lifecycle methods via the context: " + this);  
      }  
      return this.lifecycleProcessor;  
   }  
  
   /**  
    * Return the ResourcePatternResolver to use for resolving location patterns    * into Resource instances. Default is a    * {@link org.springframework.core.io.support.PathMatchingResourcePatternResolver},  
    * supporting Ant-style location patterns.    * <p>Can be overridden in subclasses, for extended resolution strategies,  
    * for example in a web environment.    * <p><b>Do not call this when needing to resolve a location pattern.</b>  
    * Call the context's {@code getResources} method instead, which  
    * will delegate to the ResourcePatternResolver.    * @return the ResourcePatternResolver for this context  
    * @see #getResources  
    * @see org.springframework.core.io.support.PathMatchingResourcePatternResolver  
    */   protected ResourcePatternResolver getResourcePatternResolver() {  
      return new PathMatchingResourcePatternResolver(this);  
   }  
  
  
   //---------------------------------------------------------------------  
   // Implementation of ConfigurableApplicationContext interface   //---------------------------------------------------------------------  
   /**  
    * Set the parent of this application context.    * <p>The parent {@linkplain ApplicationContext#getEnvironment() environment} is  
    * {@linkplain ConfigurableEnvironment#merge(ConfigurableEnvironment) merged} with  
    * this (child) application context environment if the parent is non-{@code null} and  
    * its environment is an instance of {@link ConfigurableEnvironment}.  
    * @see ConfigurableEnvironment#merge(ConfigurableEnvironment)  
    */   @Override  
   public void setParent(@Nullable ApplicationContext parent) {  
      this.parent = parent;  
      if (parent != null) {  
         Environment parentEnvironment = parent.getEnvironment();  
         if (parentEnvironment instanceof ConfigurableEnvironment) {  
            getEnvironment().merge((ConfigurableEnvironment) parentEnvironment);  
         }  
      }  
   }  
  
   @Override  
   public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {  
      Assert.notNull(postProcessor, "BeanFactoryPostProcessor must not be null");  
      this.beanFactoryPostProcessors.add(postProcessor);  
   }  
  
   /**  
    * Return the list of BeanFactoryPostProcessors that will get applied    * to the internal BeanFactory.    */   public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {  
      return this.beanFactoryPostProcessors;  
   }  
  
   @Override  
   public void addApplicationListener(ApplicationListener<?> listener) {  
      Assert.notNull(listener, "ApplicationListener must not be null");  
      if (this.applicationEventMulticaster != null) {  
         this.applicationEventMulticaster.addApplicationListener(listener);  
      }  
      this.applicationListeners.add(listener);  
   }  
  
   /**  
    * Return the list of statically specified ApplicationListeners.    */   public Collection<ApplicationListener<?>> getApplicationListeners() {  
      return this.applicationListeners;  
   }  
  
   @Override  
   public void refresh() throws BeansException, IllegalStateException {  
      synchronized (this.startupShutdownMonitor) {  
         StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");  
  
         // Prepare this context for refreshing.  
         prepareRefresh();  
  
         // Tell the subclass to refresh the internal bean factory.  
         ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();  
  
         // Prepare the bean factory for use in this context.  
         prepareBeanFactory(beanFactory);  
  
         try {  
            // Allows post-processing of the bean factory in context subclasses.  
            postProcessBeanFactory(beanFactory);  
  
            StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");  
            // Invoke factory processors registered as beans in the context.  
            invokeBeanFactoryPostProcessors(beanFactory);  
  
            // Register bean processors that intercept bean creation.  
            registerBeanPostProcessors(beanFactory);  
            beanPostProcess.end();  
  
            // Initialize message source for this context.  
            initMessageSource();  
  
            // Initialize event multicaster for this context.  
            initApplicationEventMulticaster();  
  
            // Initialize other special beans in specific context subclasses.  
            onRefresh();  
  
            // Check for listener beans and register them.  
            registerListeners();  
  
            // Instantiate all remaining (non-lazy-init) singletons.  
            finishBeanFactoryInitialization(beanFactory);  
  
            // Last step: publish corresponding event.  
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
            // might not ever need metadata for singleton beans anymore...            resetCommonCaches();  
            contextRefresh.end();  
         }  
      }  
   }  
  
   /**  
    * Prepare this context for refreshing, setting its startup date and    * active flag as well as performing any initialization of property sources.    */   protected void prepareRefresh() {  
      // Switch to active.  
      this.startupDate = System.currentTimeMillis();  
      this.closed.set(false);  
      this.active.set(true);  
  
      if (logger.isDebugEnabled()) {  
         if (logger.isTraceEnabled()) {  
            logger.trace("Refreshing " + this);  
         }  
         else {  
            logger.debug("Refreshing " + getDisplayName());  
         }  
      }  
  
      // Initialize any placeholder property sources in the context environment.  
      initPropertySources();  
  
      // Validate that all properties marked as required are resolvable:  
      // see ConfigurablePropertyResolver#setRequiredProperties      getEnvironment().validateRequiredProperties();  
  
      // Store pre-refresh ApplicationListeners...  
      if (this.earlyApplicationListeners == null) {  
         this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListeners);  
      }  
      else {  
         // Reset local application listeners to pre-refresh state.  
         this.applicationListeners.clear();  
         this.applicationListeners.addAll(this.earlyApplicationListeners);  
      }  
  
      // Allow for the collection of early ApplicationEvents,  
      // to be published once the multicaster is available...      this.earlyApplicationEvents = new LinkedHashSet<>();  
   }  
  
   /**  
    * <p>Replace any stub property sources with actual instances.  
    * @see org.springframework.core.env.PropertySource.StubPropertySource  
    * @see org.springframework.web.context.support.WebApplicationContextUtils#initServletPropertySources  
    */   protected void initPropertySources() {  
      // For subclasses: do nothing by default.  
   }  
  
   /**  
    * Tell the subclass to refresh the internal bean factory.    * @return the fresh BeanFactory instance  
    * @see #refreshBeanFactory()  
    * @see #getBeanFactory()  
    */   protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {  
      refreshBeanFactory();  
      return getBeanFactory();  
   }  
  
   /**  
    * Configure the factory's standard context characteristics,    * such as the context's ClassLoader and post-processors.    * @param beanFactory the BeanFactory to configure  
    */   protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {  
      // Tell the internal bean factory to use the context's class loader etc.  
      beanFactory.setBeanClassLoader(getClassLoader());  
      if (!shouldIgnoreSpel) {  
         beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));  
      }  
      beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));  
  
      // Configure the bean factory with context callbacks.  
      beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));  
      beanFactory.ignoreDependencyInterface(EnvironmentAware.class);  
      beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);  
      beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);  
      beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);  
      beanFactory.ignoreDependencyInterface(MessageSourceAware.class);  
      beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);  
      beanFactory.ignoreDependencyInterface(ApplicationStartupAware.class);  
  
      // BeanFactory interface not registered as resolvable type in a plain factory.  
      // MessageSource registered (and found for autowiring) as a bean.      beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);  
      beanFactory.registerResolvableDependency(ResourceLoader.class, this);  
      beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);  
      beanFactory.registerResolvableDependency(ApplicationContext.class, this);  
  
      // Register early post-processor for detecting inner beans as ApplicationListeners.  
      beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));  
  
      // Detect a LoadTimeWeaver and prepare for weaving, if found.  
      if (!NativeDetector.inNativeImage() && beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {  
         beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));  
         // Set a temporary ClassLoader for type matching.  
         beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));  
      }  
  
      // Register default environment beans.  
      if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {  
         beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());  
      }  
      if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {  
         beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());  
      }  
      if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {  
         beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());  
      }  
      if (!beanFactory.containsLocalBean(APPLICATION_STARTUP_BEAN_NAME)) {  
         beanFactory.registerSingleton(APPLICATION_STARTUP_BEAN_NAME, getApplicationStartup());  
      }  
   }  
  
   /**  
    * Modify the application context's internal bean factory after its standard    * initialization. All bean definitions will have been loaded, but no beans    * will have been instantiated yet. This allows for registering special    * BeanPostProcessors etc in certain ApplicationContext implementations.    * @param beanFactory the bean factory used by the application context  
    */   protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {  
   }  
  
   /**  
    * Instantiate and invoke all registered BeanFactoryPostProcessor beans,    * respecting explicit order if given.    * <p>Must be called before singleton instantiation.  
    */   protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {  
      PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());  
  
      // Detect a LoadTimeWeaver and prepare for weaving, if found in the meantime  
      // (e.g. through an @Bean method registered by ConfigurationClassPostProcessor)      if (!NativeDetector.inNativeImage() && beanFactory.getTempClassLoader() == null && beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {  
         beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));  
         beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));  
      }  
   }  
  
   /**  
    * Instantiate and register all BeanPostProcessor beans,    * respecting explicit order if given.    * <p>Must be called before any instantiation of application beans.  
    */   protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {  
      PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);  
   }  
  
   /**  
    * Initialize the MessageSource.    * Use parent's if none defined in this context.    */   protected void initMessageSource() {  
      ConfigurableListableBeanFactory beanFactory = getBeanFactory();  
      if (beanFactory.containsLocalBean(MESSAGE_SOURCE_BEAN_NAME)) {  
         this.messageSource = beanFactory.getBean(MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);  
         // Make MessageSource aware of parent MessageSource.  
         if (this.parent != null && this.messageSource instanceof HierarchicalMessageSource) {  
            HierarchicalMessageSource hms = (HierarchicalMessageSource) this.messageSource;  
            if (hms.getParentMessageSource() == null) {  
               // Only set parent context as parent MessageSource if no parent MessageSource  
               // registered already.               hms.setParentMessageSource(getInternalParentMessageSource());  
            }  
         }  
         if (logger.isTraceEnabled()) {  
            logger.trace("Using MessageSource [" + this.messageSource + "]");  
         }  
      }  
      else {  
         // Use empty MessageSource to be able to accept getMessage calls.  
         DelegatingMessageSource dms = new DelegatingMessageSource();  
         dms.setParentMessageSource(getInternalParentMessageSource());  
         this.messageSource = dms;  
         beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);  
         if (logger.isTraceEnabled()) {  
            logger.trace("No '" + MESSAGE_SOURCE_BEAN_NAME + "' bean, using [" + this.messageSource + "]");  
         }  
      }  
   }  
  
   /**  
    * Initialize the ApplicationEventMulticaster.    * Uses SimpleApplicationEventMulticaster if none defined in the context.    * @see org.springframework.context.event.SimpleApplicationEventMulticaster  
    */   protected void initApplicationEventMulticaster() {  
      ConfigurableListableBeanFactory beanFactory = getBeanFactory();  
      if (beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME)) {  
         this.applicationEventMulticaster =  
               beanFactory.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);  
         if (logger.isTraceEnabled()) {  
            logger.trace("Using ApplicationEventMulticaster [" + this.applicationEventMulticaster + "]");  
         }  
      }  
      else {  
         this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);  
         beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);  
         if (logger.isTraceEnabled()) {  
            logger.trace("No '" + APPLICATION_EVENT_MULTICASTER_BEAN_NAME + "' bean, using " +  
                  "[" + this.applicationEventMulticaster.getClass().getSimpleName() + "]");  
         }  
      }  
   }  
  
   /**  
    * Initialize the LifecycleProcessor.    * Uses DefaultLifecycleProcessor if none defined in the context.    * @see org.springframework.context.support.DefaultLifecycleProcessor  
    */   protected void initLifecycleProcessor() {  
      ConfigurableListableBeanFactory beanFactory = getBeanFactory();  
      if (beanFactory.containsLocalBean(LIFECYCLE_PROCESSOR_BEAN_NAME)) {  
         this.lifecycleProcessor =  
               beanFactory.getBean(LIFECYCLE_PROCESSOR_BEAN_NAME, LifecycleProcessor.class);  
         if (logger.isTraceEnabled()) {  
            logger.trace("Using LifecycleProcessor [" + this.lifecycleProcessor + "]");  
         }  
      }  
      else {  
         DefaultLifecycleProcessor defaultProcessor = new DefaultLifecycleProcessor();  
         defaultProcessor.setBeanFactory(beanFactory);  
         this.lifecycleProcessor = defaultProcessor;  
         beanFactory.registerSingleton(LIFECYCLE_PROCESSOR_BEAN_NAME, this.lifecycleProcessor);  
         if (logger.isTraceEnabled()) {  
            logger.trace("No '" + LIFECYCLE_PROCESSOR_BEAN_NAME + "' bean, using " +  
                  "[" + this.lifecycleProcessor.getClass().getSimpleName() + "]");  
         }  
      }  
   }  
  
   /**  
    * Template method which can be overridden to add context-specific refresh work.    * Called on initialization of special beans, before instantiation of singletons.    * <p>This implementation is empty.  
    * @throws BeansException in case of errors  
    * @see #refresh()  
    */   protected void onRefresh() throws BeansException {  
      // For subclasses: do nothing by default.  
   }  
  
   /**  
    * Add beans that implement ApplicationListener as listeners.    * Doesn't affect other listeners, which can be added without being beans.    */   protected void registerListeners() {  
      // Register statically specified listeners first.  
      for (ApplicationListener<?> listener : getApplicationListeners()) {  
         getApplicationEventMulticaster().addApplicationListener(listener);  
      }  
  
      // Do not initialize FactoryBeans here: We need to leave all regular beans  
      // uninitialized to let post-processors apply to them!      String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);  
      for (String listenerBeanName : listenerBeanNames) {  
         getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);  
      }  
  
      // Publish early application events now that we finally have a multicaster...  
      Set<ApplicationEvent> earlyEventsToProcess = this.earlyApplicationEvents;  
      this.earlyApplicationEvents = null;  
      if (!CollectionUtils.isEmpty(earlyEventsToProcess)) {  
         for (ApplicationEvent earlyEvent : earlyEventsToProcess) {  
            getApplicationEventMulticaster().multicastEvent(earlyEvent);  
         }  
      }  
   }  
  
   /**  
    * Finish the initialization of this context's bean factory,    * initializing all remaining singleton beans.    */   protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {  
      // Initialize conversion service for this context.  
      if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME) &&  
            beanFactory.isTypeMatch(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class)) {  
         beanFactory.setConversionService(  
               beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));  
      }  
  
      // Register a default embedded value resolver if no BeanFactoryPostProcessor  
      // (such as a PropertySourcesPlaceholderConfigurer bean) registered any before:      // at this point, primarily for resolution in annotation attribute values.      if (!beanFactory.hasEmbeddedValueResolver()) {  
         beanFactory.addEmbeddedValueResolver(strVal -> getEnvironment().resolvePlaceholders(strVal));  
      }  
  
      // Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.  
      String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);  
      for (String weaverAwareName : weaverAwareNames) {  
         getBean(weaverAwareName);  
      }  
  
      // Stop using the temporary ClassLoader for type matching.  
      beanFactory.setTempClassLoader(null);  
  
      // Allow for caching all bean definition metadata, not expecting further changes.  
      beanFactory.freezeConfiguration();  
  
      // Instantiate all remaining (non-lazy-init) singletons.  
      beanFactory.preInstantiateSingletons();  
   }  
  
   /**  
    * Finish the refresh of this context, invoking the LifecycleProcessor's    * onRefresh() method and publishing the    * {@link org.springframework.context.event.ContextRefreshedEvent}.  
    */   @SuppressWarnings("deprecation")  
   protected void finishRefresh() {  
      // Clear context-level resource caches (such as ASM metadata from scanning).  
      clearResourceCaches();  
  
      // Initialize lifecycle processor for this context.  
      initLifecycleProcessor();  
  
      // Propagate refresh to lifecycle processor first.  
      getLifecycleProcessor().onRefresh();  
  
      // Publish the final event.  
      publishEvent(new ContextRefreshedEvent(this));  
  
      // Participate in LiveBeansView MBean, if active.  
      if (!NativeDetector.inNativeImage()) {  
         LiveBeansView.registerApplicationContext(this);  
      }  
   }  
  
   /**  
    * Cancel this context's refresh attempt, resetting the {@code active} flag  
    * after an exception got thrown.    * @param ex the exception that led to the cancellation  
    */   protected void cancelRefresh(BeansException ex) {  
      this.active.set(false);  
   }  
  
   /**  
    * Reset Spring's common reflection metadata caches, in particular the    * {@link ReflectionUtils}, {@link AnnotationUtils}, {@link ResolvableType}  
    * and {@link CachedIntrospectionResults} caches.  
    * @since 4.2  
    * @see ReflectionUtils#clearCache()  
    * @see AnnotationUtils#clearCache()  
    * @see ResolvableType#clearCache()  
    * @see CachedIntrospectionResults#clearClassLoader(ClassLoader)  
    */   protected void resetCommonCaches() {  
      ReflectionUtils.clearCache();  
      AnnotationUtils.clearCache();  
      ResolvableType.clearCache();  
      CachedIntrospectionResults.clearClassLoader(getClassLoader());  
   }  
  
  
   /**  
    * Register a shutdown hook {@linkplain Thread#getName() named}  
    * {@code SpringContextShutdownHook} with the JVM runtime, closing this  
    * context on JVM shutdown unless it has already been closed at that time.    * <p>Delegates to {@code doClose()} for the actual closing procedure.  
    * @see Runtime#addShutdownHook  
    * @see ConfigurableApplicationContext#SHUTDOWN_HOOK_THREAD_NAME  
    * @see #close()  
    * @see #doClose()  
    */   @Override  
   public void registerShutdownHook() {  
      if (this.shutdownHook == null) {  
         // No shutdown hook registered yet.  
         this.shutdownHook = new Thread(SHUTDOWN_HOOK_THREAD_NAME) {  
            @Override  
            public void run() {  
               synchronized (startupShutdownMonitor) {  
                  doClose();  
               }  
            }  
         };  
         Runtime.getRuntime().addShutdownHook(this.shutdownHook);  
      }  
   }  
  
   /**  
    * Callback for destruction of this instance, originally attached    * to a {@code DisposableBean} implementation (not anymore in 5.0).  
    * <p>The {@link #close()} method is the native way to shut down  
    * an ApplicationContext, which this method simply delegates to.    * @deprecated as of Spring Framework 5.0, in favor of {@link #close()}  
    */   @Deprecated  
   public void destroy() {  
      close();  
   }  
  
   /**  
    * Close this application context, destroying all beans in its bean factory.    * <p>Delegates to {@code doClose()} for the actual closing procedure.  
    * Also removes a JVM shutdown hook, if registered, as it's not needed anymore.    * @see #doClose()  
    * @see #registerShutdownHook()  
    */   @Override  
   public void close() {  
      synchronized (this.startupShutdownMonitor) {  
         doClose();  
         // If we registered a JVM shutdown hook, we don't need it anymore now:  
         // We've already explicitly closed the context.         if (this.shutdownHook != null) {  
            try {  
               Runtime.getRuntime().removeShutdownHook(this.shutdownHook);  
            }  
            catch (IllegalStateException ex) {  
               // ignore - VM is already shutting down  
            }  
         }  
      }  
   }  
  
   /**  
    * Actually performs context closing: publishes a ContextClosedEvent and    * destroys the singletons in the bean factory of this application context.    * <p>Called by both {@code close()} and a JVM shutdown hook, if any.  
    * @see org.springframework.context.event.ContextClosedEvent  
    * @see #destroyBeans()  
    * @see #close()  
    * @see #registerShutdownHook()  
    */   @SuppressWarnings("deprecation")  
   protected void doClose() {  
      // Check whether an actual close attempt is necessary...  
      if (this.active.get() && this.closed.compareAndSet(false, true)) {  
         if (logger.isDebugEnabled()) {  
            logger.debug("Closing " + this);  
         }  
  
         if (!NativeDetector.inNativeImage()) {  
            LiveBeansView.unregisterApplicationContext(this);  
         }  
  
         try {  
            // Publish shutdown event.  
            publishEvent(new ContextClosedEvent(this));  
         }  
         catch (Throwable ex) {  
            logger.warn("Exception thrown from ApplicationListener handling ContextClosedEvent", ex);  
         }  
  
         // Stop all Lifecycle beans, to avoid delays during individual destruction.  
         if (this.lifecycleProcessor != null) {  
            try {  
               this.lifecycleProcessor.onClose();  
            }  
            catch (Throwable ex) {  
               logger.warn("Exception thrown from LifecycleProcessor on context close", ex);  
            }  
         }  
  
         // Destroy all cached singletons in the context's BeanFactory.  
         destroyBeans();  
  
         // Close the state of this context itself.  
         closeBeanFactory();  
  
         // Let subclasses do some final clean-up if they wish...  
         onClose();  
  
         // Reset local application listeners to pre-refresh state.  
         if (this.earlyApplicationListeners != null) {  
            this.applicationListeners.clear();  
            this.applicationListeners.addAll(this.earlyApplicationListeners);  
         }  
  
         // Switch to inactive.  
         this.active.set(false);  
      }  
   }  
  
   /**  
    * Template method for destroying all beans that this context manages.    * The default implementation destroy all cached singletons in this context,    * invoking {@code DisposableBean.destroy()} and/or the specified  
    * "destroy-method".    * <p>Can be overridden to add context-specific bean destruction steps  
    * right before or right after standard singleton destruction,    * while the context's BeanFactory is still active.    * @see #getBeanFactory()  
    * @see org.springframework.beans.factory.config.ConfigurableBeanFactory#destroySingletons()  
    */   protected void destroyBeans() {  
      getBeanFactory().destroySingletons();  
   }  
  
   /**  
    * Template method which can be overridden to add context-specific shutdown work.    * The default implementation is empty.    * <p>Called at the end of {@link #doClose}'s shutdown procedure, after  
    * this context's BeanFactory has been closed. If custom shutdown logic    * needs to execute while the BeanFactory is still active, override    * the {@link #destroyBeans()} method instead.  
    */   protected void onClose() {  
      // For subclasses: do nothing by default.  
   }  
  
   @Override  
   public boolean isActive() {  
      return this.active.get();  
   }  
  
   /**  
    * Assert that this context's BeanFactory is currently active,    * throwing an {@link IllegalStateException} if it isn't.  
    * <p>Invoked by all {@link BeanFactory} delegation methods that depend  
    * on an active context, i.e. in particular all bean accessor methods.    * <p>The default implementation checks the {@link #isActive() 'active'} status  
    * of this context overall. May be overridden for more specific checks, or for a    * no-op if {@link #getBeanFactory()} itself throws an exception in such a case.  
    */   protected void assertBeanFactoryActive() {  
      if (!this.active.get()) {  
         if (this.closed.get()) {  
            throw new IllegalStateException(getDisplayName() + " has been closed already");  
         }  
         else {  
            throw new IllegalStateException(getDisplayName() + " has not been refreshed yet");  
         }  
      }  
   }  
  
  
   //---------------------------------------------------------------------  
   // Implementation of BeanFactory interface   //---------------------------------------------------------------------  
   @Override  
   public Object getBean(String name) throws BeansException {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBean(name);  
   }  
  
   @Override  
   public <T> T getBean(String name, Class<T> requiredType) throws BeansException {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBean(name, requiredType);  
   }  
  
   @Override  
   public Object getBean(String name, Object... args) throws BeansException {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBean(name, args);  
   }  
  
   @Override  
   public <T> T getBean(Class<T> requiredType) throws BeansException {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBean(requiredType);  
   }  
  
   @Override  
   public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBean(requiredType, args);  
   }  
  
   @Override  
   public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanProvider(requiredType);  
   }  
  
   @Override  
   public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanProvider(requiredType);  
   }  
  
   @Override  
   public boolean containsBean(String name) {  
      return getBeanFactory().containsBean(name);  
   }  
  
   @Override  
   public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {  
      assertBeanFactoryActive();  
      return getBeanFactory().isSingleton(name);  
   }  
  
   @Override  
   public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {  
      assertBeanFactoryActive();  
      return getBeanFactory().isPrototype(name);  
   }  
  
   @Override  
   public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {  
      assertBeanFactoryActive();  
      return getBeanFactory().isTypeMatch(name, typeToMatch);  
   }  
  
   @Override  
   public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {  
      assertBeanFactoryActive();  
      return getBeanFactory().isTypeMatch(name, typeToMatch);  
   }  
  
   @Override  
   @Nullable   public Class<?> getType(String name) throws NoSuchBeanDefinitionException {  
      assertBeanFactoryActive();  
      return getBeanFactory().getType(name);  
   }  
  
   @Override  
   @Nullable   public Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {  
      assertBeanFactoryActive();  
      return getBeanFactory().getType(name, allowFactoryBeanInit);  
   }  
  
   @Override  
   public String[] getAliases(String name) {  
      return getBeanFactory().getAliases(name);  
   }  
  
  
   //---------------------------------------------------------------------  
   // Implementation of ListableBeanFactory interface   //---------------------------------------------------------------------  
   @Override  
   public boolean containsBeanDefinition(String beanName) {  
      return getBeanFactory().containsBeanDefinition(beanName);  
   }  
  
   @Override  
   public int getBeanDefinitionCount() {  
      return getBeanFactory().getBeanDefinitionCount();  
   }  
  
   @Override  
   public String[] getBeanDefinitionNames() {  
      return getBeanFactory().getBeanDefinitionNames();  
   }  
  
   @Override  
   public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType, boolean allowEagerInit) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanProvider(requiredType, allowEagerInit);  
   }  
  
   @Override  
   public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType, boolean allowEagerInit) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanProvider(requiredType, allowEagerInit);  
   }  
  
   @Override  
   public String[] getBeanNamesForType(ResolvableType type) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanNamesForType(type);  
   }  
  
   @Override  
   public String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanNamesForType(type, includeNonSingletons, allowEagerInit);  
   }  
  
   @Override  
   public String[] getBeanNamesForType(@Nullable Class<?> type) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanNamesForType(type);  
   }  
  
   @Override  
   public String[] getBeanNamesForType(@Nullable Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanNamesForType(type, includeNonSingletons, allowEagerInit);  
   }  
  
   @Override  
   public <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeansOfType(type);  
   }  
  
   @Override  
   public <T> Map<String, T> getBeansOfType(@Nullable Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)  
         throws BeansException {  
  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeansOfType(type, includeNonSingletons, allowEagerInit);  
   }  
  
   @Override  
   public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeanNamesForAnnotation(annotationType);  
   }  
  
   @Override  
   public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType)  
         throws BeansException {  
  
      assertBeanFactoryActive();  
      return getBeanFactory().getBeansWithAnnotation(annotationType);  
   }  
  
   @Override  
   @Nullable   public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)  
         throws NoSuchBeanDefinitionException {  
  
      assertBeanFactoryActive();  
      return getBeanFactory().findAnnotationOnBean(beanName, annotationType);  
   }  
  
   @Override  
   @Nullable   public <A extends Annotation> A findAnnotationOnBean(  
         String beanName, Class<A> annotationType, boolean allowFactoryBeanInit)  
         throws NoSuchBeanDefinitionException {  
  
      assertBeanFactoryActive();  
      return getBeanFactory().findAnnotationOnBean(beanName, annotationType, allowFactoryBeanInit);  
   }  
  
  
   //---------------------------------------------------------------------  
   // Implementation of HierarchicalBeanFactory interface   //---------------------------------------------------------------------  
   @Override  
   @Nullable   public BeanFactory getParentBeanFactory() {  
      return getParent();  
   }  
  
   @Override  
   public boolean containsLocalBean(String name) {  
      return getBeanFactory().containsLocalBean(name);  
   }  
  
   /**  
    * Return the internal bean factory of the parent context if it implements    * ConfigurableApplicationContext; else, return the parent context itself.    * @see org.springframework.context.ConfigurableApplicationContext#getBeanFactory  
    */   @Nullable  
   protected BeanFactory getInternalParentBeanFactory() {  
      return (getParent() instanceof ConfigurableApplicationContext ?  
            ((ConfigurableApplicationContext) getParent()).getBeanFactory() : getParent());  
   }  
  
  
   //---------------------------------------------------------------------  
   // Implementation of MessageSource interface   //---------------------------------------------------------------------  
   @Override  
   public String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale) {  
      return getMessageSource().getMessage(code, args, defaultMessage, locale);  
   }  
  
   @Override  
   public String getMessage(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException {  
      return getMessageSource().getMessage(code, args, locale);  
   }  
  
   @Override  
   public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {  
      return getMessageSource().getMessage(resolvable, locale);  
   }  
  
   /**  
    * Return the internal MessageSource used by the context.    * @return the internal MessageSource (never {@code null})  
    * @throws IllegalStateException if the context has not been initialized yet  
    */   private MessageSource getMessageSource() throws IllegalStateException {  
      if (this.messageSource == null) {  
         throw new IllegalStateException("MessageSource not initialized - " +  
               "call 'refresh' before accessing messages via the context: " + this);  
      }  
      return this.messageSource;  
   }  
  
   /**  
    * Return the internal message source of the parent context if it is an    * AbstractApplicationContext too; else, return the parent context itself.    */   @Nullable  
   protected MessageSource getInternalParentMessageSource() {  
      return (getParent() instanceof AbstractApplicationContext ?  
            ((AbstractApplicationContext) getParent()).messageSource : getParent());  
   }  
  
  
   //---------------------------------------------------------------------  
   // Implementation of ResourcePatternResolver interface   //---------------------------------------------------------------------  
   @Override  
   public Resource[] getResources(String locationPattern) throws IOException {  
      return this.resourcePatternResolver.getResources(locationPattern);  
   }  
  
  
   //---------------------------------------------------------------------  
   // Implementation of Lifecycle interface   //---------------------------------------------------------------------  
   @Override  
   public void start() {  
      getLifecycleProcessor().start();  
      publishEvent(new ContextStartedEvent(this));  
   }  
  
   @Override  
   public void stop() {  
      getLifecycleProcessor().stop();  
      publishEvent(new ContextStoppedEvent(this));  
   }  
  
   @Override  
   public boolean isRunning() {  
      return (this.lifecycleProcessor != null && this.lifecycleProcessor.isRunning());  
   }  
  
  
   //---------------------------------------------------------------------  
   // Abstract methods that must be implemented by subclasses   //---------------------------------------------------------------------  
   /**  
    * Subclasses must implement this method to perform the actual configuration load.    * The method is invoked by {@link #refresh()} before any other initialization work.  
    * <p>A subclass will either create a new bean factory and hold a reference to it,  
    * or return a single BeanFactory instance that it holds. In the latter case, it will    * usually throw an IllegalStateException if refreshing the context more than once.    * @throws BeansException if initialization of the bean factory failed  
    * @throws IllegalStateException if already initialized and multiple refresh  
    * attempts are not supported    */   protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;  
  
   /**  
    * Subclasses must implement this method to release their internal bean factory.    * This method gets invoked by {@link #close()} after all other shutdown work.  
    * <p>Should never throw an exception but rather log shutdown failures.  
    */   protected abstract void closeBeanFactory();  
  
   /**  
    * Subclasses must return their internal bean factory here. They should implement the    * lookup efficiently, so that it can be called repeatedly without a performance penalty.    * <p>Note: Subclasses should check whether the context is still active before  
    * returning the internal bean factory. The internal factory should generally be    * considered unavailable once the context has been closed.    * @return this application context's internal bean factory (never {@code null})  
    * @throws IllegalStateException if the context does not hold an internal bean factory yet  
    * (usually if {@link #refresh()} has never been called) or if the context has been  
    * closed already    * @see #refreshBeanFactory()  
    * @see #closeBeanFactory()  
    */   @Override  
   public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;  
  
  
   /**  
    * Return information about this context.    */   @Override  
   public String toString() {  
      StringBuilder sb = new StringBuilder(getDisplayName());  
      sb.append(", started on ").append(new Date(getStartupDate()));  
      ApplicationContext parent = getParent();  
      if (parent != null) {  
         sb.append(", parent: ").append(parent.getDisplayName());  
      }  
      return sb.toString();  
   }  
  
}
```