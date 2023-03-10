IOC容器鼻祖，不继承任何接口，只被子接口继承，定义了最基础的属性

![[4e1078b69ae04b9b84487cecda3139c8.png]]
## 1.接口具体说明
有三个子接口：
HierarchicalBeanFactory提供父容器的访问功能
ListableBeanFactory提供了批量获取Bean的方法
AutowireCapableBeanFactory在BeanFactory基础上实现对已存在实例的管理

## 2.源码
看一下都有哪些最基础的属性：
```java
package org.springframework.beans.factory;  
  
import org.springframework.beans.BeansException;  
import org.springframework.core.ResolvableType;  
import org.springframework.lang.Nullable;  
  
public interface BeanFactory {  

   //引用实例，把它和工厂产生的Bean分开，如果一个FactoryBean的名字是a，那么，&a会得到那个Factory
   String FACTORY_BEAN_PREFIX = "&";  
  
   //通过名字获取Bean（以下5个均为获取Bean的方法）
   Object getBean(String name) throws BeansException;  

   <T> T getBean(String name, Class<T> requiredType) throws BeansException;  
   
   Object getBean(String name, Object... args) throws BeansException;  
  
   <T> T getBean(Class<T> requiredType) throws BeansException;  
  
   <T> T getBean(Class<T> requiredType, Object... args) throws BeansException;

   //获取bean的提供者
   <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType);  
  
   <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType);  

   //判断Bean是否存在
   boolean containsBean(String name);  

   //判断是否是单例Bean
   boolean isSingleton(String name) throws NoSuchBeanDefinitionException;  

   //判断是否是多例Bean
   boolean isPrototype(String name) throws NoSuchBeanDefinitionException;  

   //判断是否名称和类型是否匹配
   boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException;  

   boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;  

   //获取类型
   @Nullable  
   Class<?> getType(String name) throws NoSuchBeanDefinitionException;  
  
   @Nullable  
   Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException;  

   //根据名字获取别名
   String[] getAliases(String name);  
  
}
```

### 2.1BeanFactory是延迟加载
这里代码不演示了
### 2.2. 使用ApplicationContext预加载
这里代码不演示了

## 3.三个子接口

### 3.1 HierarchicalBeanFactory

两个方法：
- 获取父容器
- 判断是否包含某个bean，忽略父容器中的bean

```java

package org.springframework.beans.factory;  
  
import org.springframework.lang.Nullable;  
  
public interface HierarchicalBeanFactory extends BeanFactory {  

   //获得父容器
   @Nullable  
   BeanFactory getParentBeanFactory();  

   //判断是否包含某个bean
   boolean containsLocalBean(String name);  
  
}
```


### 3.2 ListableBeanFactory

ListableBeanFactory接口是BeanFactory接口的一个扩展。实现了此接口的类一般都有预加载bean定义功能（从XML等配置文件中），因此都有能列举其包含的所有Bean，根据名字或其它单个查找Bean的特性。
批量获得BeanName

```java

package org.springframework.beans.factory;  
  
import java.lang.annotation.Annotation;  
import java.util.Map;  
  
import org.springframework.beans.BeansException;  
import org.springframework.core.ResolvableType;  
import org.springframework.lang.Nullable;  
  
public interface ListableBeanFactory extends BeanFactory {  
   /**
   * 查看是否包含指定名字的Bean
   * 不支持向上或向下查找
   * 不支持查找非配置文件定义的单例Bean
   */
   boolean containsBeanDefinition(String beanName);  

   /**
   * 查看BeanFactory中包含的Bean数量
   */
   int getBeanDefinitionCount();  

   /**
   * 查看BeanFactory中包含的Bean的名字
   */
   String[] getBeanDefinitionNames();  
  
   <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType, boolean allowEagerInit);  
  
   <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType, boolean allowEagerInit);  

   /**
   * 返回此BeanFactory中所有指定类型的Bean的名字。判断是否是指定类型的标准有两个：a Bean定义；b FactoryBean的getObjectType方法。
   * 
   */
   String[] getBeanNamesForType(ResolvableType type);  

   
   String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit);  
  
   String[] getBeanNamesForType(@Nullable Class<?> type);  
  
   String[] getBeanNamesForType(@Nullable Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);  
  
   <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException;  
  
   <T> Map<String, T> getBeansOfType(@Nullable Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)  
         throws BeansException;  
  
   String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);  
   
   /**
   * 找到所有带有指定注解类型的Bean
   */
   Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException;  

   /**
   * 找到所有带有指定注解的Bean，返回一个以Bean的name为键，其对应的Bean实例为值的Map
   */
   @Nullable  
   <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)  
         throws NoSuchBeanDefinitionException;  

   /**
   * 在指定name对应的Bean上找指定的注解，如果没有找到的话，去指定Bean的父类或者父接口上查找
   */
   @Nullable  
   <A extends Annotation> A findAnnotationOnBean(  
         String beanName, Class<A> annotationType, boolean allowFactoryBeanInit)  
         throws NoSuchBeanDefinitionException;  
  
}
```

### 3.3 AutowireCapableBeanFactory

自动装配接口
正常情况下，不要使用此接口，应该更倾向于使用BeanFactory或者ListableBeanFactory接口。此接口主要是针对框架之外，没有向Spring托管Bean的应用。通过暴露此功能，Spring框架之外的程序，具有自动装配等Spring的功能。   
需要注意的是，ApplicationContext接口并没有实现此接口，因为应用代码很少用到此功能，如果确实需要的话，可以调用ApplicationContext的getAutowireCapableBeanFactory方法，来获取此接口的实例。

```java

package org.springframework.beans.factory.config;  
  
import java.util.Set;  
  
import org.springframework.beans.BeansException;  
import org.springframework.beans.TypeConverter;  
import org.springframework.beans.factory.BeanFactory;  
import org.springframework.beans.factory.NoSuchBeanDefinitionException;  
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;  
import org.springframework.lang.Nullable;  
  
public interface AutowireCapableBeanFactory extends BeanFactory {  

   /**
     * 表明工厂没有自动装配的Bean
     */
   int AUTOWIRE_NO = 0;  

   /**
     * 表明根据名称自动装配
     */
   int AUTOWIRE_BY_NAME = 1;  

   /**
     * 表明根据类型自动装配
     */
   int AUTOWIRE_BY_TYPE = 2;  

   /**
     * 表明根据构造方法快速装配
     */
   int AUTOWIRE_CONSTRUCTOR = 3;  

   // 表明通过Bean的class的内部来自动装配 Spring3.0被弃用。
   @Deprecated  
   int AUTOWIRE_AUTODETECT = 4;  


   String ORIGINAL_INSTANCE_SUFFIX = ".ORIGINAL";  
  
   /**
     * 通过调用给定Bean的after-instantiation及post-processing接口，对bean进行配置
     */
   void autowireBean(Object existingBean) throws BeansException;  

   /**
     * 自动装配属性,填充属性值,使用诸如setBeanName,setBeanFactory这样的工厂回调填充属性,最好还要调用post processor
     */
   Object configureBean(Object existingBean, String beanName) throws BeansException;  
  
   /**
     * 创建一个指定class的实例，通过参数可以指定其自动装配模式（by-name or by-type）.
     */
   Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;  

   /**
     * 通过指定的自动装配策略来初始化一个Bean
     */
   Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;  

   /**
     * 通过指定的自动装配方式来对给定的Bean进行自动装配
     */
   void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck)  throws BeansException;  
   
   /**
     * 将参数中指定了那么的Bean，注入给定实例当中
     */
   void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException;  
   
   /**
     * 初始化参数中指定的Bean，调用任何其注册的回调函数如setBeanName、setBeanFactory等。
     */
   Object initializeBean(Object existingBean, String beanName) throws BeansException;  

   /**
     * 调用参数中指定Bean的postProcessBeforeInitialization方法
     */
   Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)  throws BeansException;  

   /**
     * 调用参数中指定Bean的postProcessAfterInitialization方法
     */
   Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)  throws BeansException;  

   /**
     * 销毁参数中指定的Bean，同时调用此Bean上的DisposableBean和DestructionAwareBeanPostProcessors方法
     */
   void destroyBean(Object existingBean);  
  
  /**
     * 查找唯一符合指定类的实例，如果有，则返回实例的名字和实例本身
     * 底层依赖于：BeanFactory中的getBean(Class)方法
     */
   <T> NamedBeanHolder<T> resolveNamedBean(Class<T> requiredType) throws BeansException;  

   /**
     * 解析出在Factory中与指定Bean有指定依赖关系的Bean（@Autowired依赖注入的核心方法）
     */
   Object resolveBeanByName(String name, DependencyDescriptor descriptor) throws BeansException;  
  
   @Nullable  
   Object resolveDependency(DependencyDescriptor descriptor, @Nullable String requestingBeanName) throws BeansException;  
  
   @Nullable  
   Object resolveDependency(DependencyDescriptor descriptor, @Nullable String requestingBeanName, 

   @Nullable Set<String> autowiredBeanNames, @Nullable TypeConverter typeConverter) throws BeansException;  
  
}
```