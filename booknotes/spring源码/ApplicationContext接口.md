
## 1.ApplicationContext接口的定义
Spring 框架带有两个 IOC 容器—— BeanFactory和ApplicationContext。BeanFactory是 IOC 容器的最基本版本，ApplicationContext扩展了BeanFactory的特性。

其实就是所有的实现类都用的是ApplicationContext

ApplicationContext继承了EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,   MessageSource, ApplicationEventPublisher, ResourcePatternResolver接口

## 2.继承的接口


### 2.1 EnvironmentCapable

环境上下文，环境对象`Environment`是应用配置的核心。主要有两个方面的功能，一个是关于应用Property的配置，另一方面是关于Profile的配置。

- Property可以读取系统的环境变量
- profile功能是环境的运行描述
```java

package org.springframework.core.env;  
  
public interface EnvironmentCapable {  
  
   Environment getEnvironment();  
  
}
```

## 2.2 ListableBeanFactory
[[BeanFactory接口]]ListableBeanFactory，不再重复

### 2.3 HierarchicalBeanFactory
[[BeanFactory接口]]HierarchicalBeanFactory，不再重复

### 2.4 MessageSource
实现国际化，细节不看了

### 2.5 ApplicationEventPublisher
事件发布功能的接口
事件机制是基于监听者设计模式的实现，监听者模式包括三个部分：
-   **事件源**：具体事件源，用于发布事件
-   **事件对象**：封装事件源对象和事件相关信息，用于在事件源和监听器之间传递信息
-   **事件监听器**：监听事件，用于对事件进行处理

Spring 提供了 `ApplicationEventPublisher` 接口用于发布事件。
可以通过继承 `ApplicationEvent` 抽象类来定义事件对象。
可以通过实现 `ApplicationListener<E>` 接口来定义事件监听器。

![[v2-c7093847f55fc880b97884a28ba10e94_720w.webp]]

