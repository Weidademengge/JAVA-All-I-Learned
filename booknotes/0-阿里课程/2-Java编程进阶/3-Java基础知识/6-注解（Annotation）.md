# 前言

最近在做WebX的PandoraBoot升级，众所周知，SpringBoot是以注解行天下的，那么极其有必了解一下注解背后的原理、常见的使用方式，这样在面对SpringBoot众多注解时，**既可以快速地了解其作用，也可以快速地找到入口，以此为切点深入了解相关知识。**  
关键三点：1.注解具有继承机制 2.注解默认继承Annotation 3.注解和注解处理器配合工作

# 重要的类和接口

## AnnotatedElement

Represents an annotated element of the program currently running in this VM. This interface allows annotations to be read reflectively.  
表示目前正在此 VM 中运行的程序的一个已注释元素。这个接口允许通过反射地方式读取注释（注解）。第二句话，明示我们可以通过反射读取任何实现了AnnotatedElement接口的类上的注释。由下图的继承关系。

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653459718270-176c6d62-e4ae-4c92-b8d2-a9a5f9622066.png)

  

该接口定义了如下方法：（感兴趣的同学，可以研究下各实现类的实现方式）

```java
<T extends Annotation> T getAnnotation(Class<T> annotationClass) 
   // 如果该元素（AnnotatedElement）存在指定类型的注解，则返回这些注解，否则返回 null
Annotation[] getAnnotations()
Annotation[] getDeclaredAnnotations() 忽略继承的注释
boolean isAnnotationPresent(Class<? extends Annotation> annotationClass)
```

## Annotation

The common interface extended by all annotation types.  
所有注解的父亲接口.  
为什么这么说？举个栗子。我们写个如下的注解，编译以后进行反编译一下。

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653459774696-3d9240b6-62c7-46b0-814f-600478274b1a.png)


![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653459805009-d75d548a-93d4-4fc8-b75e-13f3091b56ff.png)

可以看到，当我们使用@interface声明一个注解，Java编译器在帮我们编译时，自动继承了Annotation接口，所以说，Annotation是所有注解的父亲接口。所有的类都继承自Object，只不过实现方式有所不同。思考一下，为什要这么做？

# 四种基本元注解

负责注释其它注解，编译器使用元注解作为基本桥梁和普通注解进行沟通。法律的法律叫宪法，注解的注释叫元注解。  
@Documented –注解是否将包含在JavaDoc中  
@Retention –什么时候使用该注解  
@Target –注解用于什么地方  
@Inherited – 是否允许子类继承该注解

# 原理探析

## JVM规范

a.编译器将源码编译成Class文件时，会对注解符号进行处理，并附加到class结构中  
b.JVM规范：Class文件结构严格有序，有且仅有attribute属性可以附加信息  
c.类、字段、方法，在Class结构中都有各自特定的表结构以及各自的attribute属性  
d.编译器将注解信息附加到相应的类、字段、方法的attribute属性上

### 举个栗子

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653579599851-4f682fd1-4d1c-41fe-a02e-7a5bce17c06a.png)

我们把上面的自定义注解添加一些注释(元注解)，然后观察下编译后的Class文件，如图中所示，由于我们使用了四种元注解注释了MyAnnotation类，所以图中红色位置，标出了该类的注解信息。尝试解读一下该信息：

#7() 代表 Documented; #9(#10=[e#11.#12,e#11.#13,e#11.#14,e#11.#15]) 代表 (value=[ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.TYPE,ElementType.LOCAL_VARIABLE])

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653579737633-f6871ced-1e39-481f-97db-7f9ecc196131.png)

### 再举个爪子

定义一个默认值为defaultValue的PropertyName注解

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653579770035-4be24fdb-ceeb-4f9e-bf91-1ae6d3480780.png)

然后定义一个Monkey，该类的name字段上使用了PropertyName注解，看下编译后的Class文件

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653579780024-f542c3c2-5a81-43c9-beed-458629fbf09c.png)

可以看到，编译器将PropertyName的注解信息包括“monkey”值附加到了name字段的RuntimeVisibleAnnotations属性中。

## 注解获取

Java编译器将注解信息附加到Class文件的相应RuntimeVisibleAnnotations属性中，那么JVM加载Class文件后，又如何处理？我们要怎么样才能获取到附加的信息？

### JVM的加载

JVM加载Class文件时，会将RuntimeVisibleAnnotation属性保存到相应的类、字段、方法的Class对象中. 因此，我们可以通过相应的类、字段、方法的Class对象（Class、Method、Field等实现了AnnotatedElement接口），获取到注解对象，进而获取到注解中的属性值。本例中，我们可以通过如下代码实现：

```java
public static void main(String[] args) {
    try {
        PropertyName propertyName  = Monkey.class.getDeclaredField("name").getAnnotation(PropertyName.class);
        System.out.println(propertyName);
    } catch (NoSuchFieldException e) {

    }
}
```

### 背后隐藏的东西

通过上文，我们知道注解本质是一个继承了Annotation的特殊接口，而其具体实现类是Java运行时生成的动态代理类。通过反射获取注解时，返回的是Java运行时生成的动态代理对象$Proxy1。通过代理对象调用自定义注解（接口）的方法，会最终调用AnnotationInvocationHandler的invoke方法，该方法会从memberValues这个Map中索引出对应的值，memberValues的来源则是Java常量池。

# 注解处理器

## 揭开注解的面纱

尽管我们知道可以通过反射的方式获取到注解的值，也经常使用注解，但是却少有自己去开发注解。那么，如何去开发一个自己的注解呢？或者说，看到一堆注解，是谁在背后帮我们把注解的信息注入到我们所需要的地方？答案是，注解处理器。比如，Spring注解。

Spring注解比较多，以SpringBoot的ConfigurationProperties注解为例。

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653579989631-c333a248-2b43-44c2-b0c1-53e12673089a.png)

该注解上面已经给我们标识了注解处理器有哪些，建议以后大家开发注解及注解处理器时也使用这种规范。

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653579999690-18af8b9c-98e8-44aa-878c-6a20be8d6329.png)

处理逻辑，显而易见，ConfigurationPropertiesBindingPostProcessor实现了postProcessBeforeInitialization方法，在Spring bean初始化之前，获取bean上的ConfigurationProperties注解的值，进行各种处理，最后将解析后的值注入到bean的属性上。