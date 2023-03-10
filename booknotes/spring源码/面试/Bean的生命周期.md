![[v2-454f6090393976c96d6455972764c402_r.jpg]]

1. 实例化Bean：反射的方式生成对象
2. 填充Bean的属性：populateBean（），循环依赖的问题（三级缓存）
3. 调用aware接口相关的方法：invokeAwareMethod（完成BeanName,BeanFactory,ApplicationContextAarBeanClassLoader对象的属性设置）
	1. BeanNameAware：可以获取容器中Bean的名称
	2. BeanFactoryAware：获取当前Bean Factory，这个也可以调用容器的服务
	3. ApplicationContextAware：当前的applicationContext，这也可以调用容器的服务
	4. MessageSourceAware：获得message source，这也可以获得文本信息
	5. applicationEventPulisherAware：应用事件发布器，可以发布事件
	6. ResourceLoaderAwareL：获得资源加载器，可以获得外部资源文件的内容
4. 调用beanPostProcessor中的前置处理方法：使用较多的有（ApplicationContextPostProcessor设置ApplicationContext，Environment，ResourceLoad等对象）
5. 调用initMethod方法：invokeInitMethod(),判断是否实现了initializingBean接口，如果有，调用afterPropertiesSet方法，没有就不调用
6. 调用BeanPostProcessor的后置处理方法：spring的aop就在此处实现的，AbstractAutoProxyCreator（注册destruction相关的回调接口）
7. 获取到完整的对象，可以通过getBean的方式来进行对象的获取
8. 销毁流程：
	1. 判断是否实现了DispoableBean接口
	2. 调用destroyMethod方法
