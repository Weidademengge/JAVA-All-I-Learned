就是我们`spring`最核心的内容——应用容器，`run`方法内部也基本都是针对它进行的各种初始化操作，运行完成后返回的也是它的实例。

其实就是ApplicationContext类的接口

继承了ApplicationContext、Lifecycle、Closeable三个接口
其中ApplicationContext接口比较复杂，可以看ApplicationContext接口的笔记
ApllicationContext接口

## Lifecycle

Lifecycle接口是启动/停止Bean的生命周期控制方法的通用接口，定义了三个方法：
```java
package org.springframework.context;  
  
public interface Lifecycle {  
  
   /**  
    * 启动bean
    */   
    void start();  
  
   /**  
    * 停止bean
    */   
    void stop();  
  
   /**  
    * 判断bean是否运行
    */   
    boolean isRunning();  
  
}
```

## Closeable(没什么用，可能会在持久化过程使用)

是jdk里的接口,在java.io包里,只有一个close方法,实现了Closeable接口的类的对象可以被关闭,调用 close 方法关闭后可释放对象保存的资源,比如(打开文件)

```java

public interface Closeable extends AutoCloseable {

    /**
     * 关闭此流并释放与此流关联的所有系统资源。如果已经关闭该流，则调用此方法无效
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException;
}
```