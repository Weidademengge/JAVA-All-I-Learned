
```java
@Override  
public void refresh() throws BeansException, IllegalStateException {  
   synchronized (this.startupShutdownMonitor) {  
      StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");  
  
      /**  
       * 刷新前的准备  
       * 1.设置容器的启动时间  
       * 2.设置活跃状态为true  
       * 3.设置关闭状态为false  
       * 4.获取Environment对象，并加载当前系统的属性值为Environment对象中  
       * 5.准备监听器和事件的集合对象，默认为空的集合  
       */  
      prepareRefresh();  
  
      // 加载XMl配置文件的属性值到当前工厂中，最重要的就是BeanDefinition
      ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();  
  
      /**  
       * beanFactory的准备工作，对各种属性进行填充  
       */  
      prepareBeanFactory(beanFactory);  
  
      try {  
         /**  
          * 子类覆盖方法做额外的处理，此处我们自己一般不做任何扩展工作，但是可以查看web中的值  
          */  
         postProcessBeanFactory(beanFactory);  
  
         StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");  
         /**  
          * 调用各种beanFactory处理器  
          */  
         invokeBeanFactoryPostProcessors(beanFactory);  
  
         // Register bean processors that intercept bean creation.  
         /**  
          * 注册bean处理器，这里只是注册共呢个，真正调用的是getBean方法  
          */  
         registerBeanPostProcessors(beanFactory);  
         beanPostProcess.end();  
  
         // Initialize message source for this context.  
         /**  
          * 为上下文初始化message源，即不同语言的消息题，国际化处理，在springmvc的时候用  
          */  
         initMessageSource();  
  
         // Initialize event multicaster for this context.  
         /**  
          * 初始化事件监听多路广播器  
          */  
         initApplicationEventMulticaster();  
  
         // Initialize other special beans in specific context subclasses.  
         /**  
          * 留给子类来初始化其他的bean  
          */         onRefresh();  
  
         // Check for listener beans and register them.  
         /**  
          * 在所有注册的bean中查找listener bean，注册到消息广播其中  
          */  
         registerListeners();  
  
         // Instantiate all remaining (non-lazy-init) singletons.  
         /**  
          * 实例化剩下的单实例（非懒加载）bean的生命周期（进行bean对象的创建工作）  
          */  
         finishBeanFactoryInitialization(beanFactory);  
  
         // Last step: publish corresponding event.  
         /**  
          * 完成刷新过程，通知生命周期处理器lifecycleProcessor刷新过程，同时发出。。。  
          */  
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
         // might not ever need metadata for singleton beans anymore...         resetCommonCaches();  
         contextRefresh.end();  
      }  
   }  
}
```