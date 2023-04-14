## 为什么导入依赖时不需要指定版本?

在 Spring Boot 入门程序中，项目 pom.xml 文件有两个核心依赖，分别是 spring-boot-starter- parent 和 spring-boot-starter-web，关于这两个依赖的相关介绍具体如下

### spring-boot-starter-parent

```java
<parent>  
	<groupId>org.springframework.boot</groupId>  
	<artifactId>spring-boot-starter-parent</artifactId>  
	<version>2.2.9.RELEASE</version>
	<relativePath/> <!-- lookup parent from repository -->  
</parent>
```

在上述代码中，将 spring-boot-starter-parent 依赖作为 Spring Boot 项目的统一父项目依赖管理，并将项目版本号统一为 2.2.9.RELEASE，该版本号根据实际开发需求是可以修改的。

使用 Ctrl+鼠标左键”进入并查看spring-boot-starter-parent底层源文件，先看spring-boot- starter-parent做了哪些事

首先看 spring-boot-starter-parent 的 properties 节点

```java
<properties>  
	<java.version>1.8</java.version>  
	<resource.delimiter>@</resource.delimiter>  
	<maven.compiler.source>${java.version}</maven.compiler.source>  
	<maven.compiler.target>${java.version}</maven.compiler.target>  
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>  
	<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>  
</properties>
```

在这里 spring-boot-starter-parent 定义了:

1.  工程的 Java 版本为 1.8 。
2.  工程代码的编译源文件编码格式为 UTF-8
3.  工程编译后的文件编码格式为 UTF-8
4.  Maven 打包编译的版本

再来看 spring-boot-starter-parent 的「build」节点  
接下来看POM的 build 节点，分别定义了 resources 资源和 pluginManagement

```java
<resources>  
	<resource>  
		<directory>${basedir}/src/main/resources</directory>  
		<filtering>true</filtering>  
		<includes>  
			<include>**/application*.yml</include>  
			<include>**/application*.yaml</include>  
			<include>**/application*.properties</include>  
		</includes>  
	</resource>  
	<resource>  
		<directory>${basedir}/src/main/resources</directory>  
		<excludes>  
			<exclude>**/application*.yml</exclude>  
			<exclude>**/application*.yaml</exclude>  
			<exclude>**/application*.properties</exclude>  
		</excludes>  
	</resource>  
</resources>
```

我们详细看一下 resource 节点，里面定义了资源过滤，针对 application 的 yml 、 properties格式进行了过滤，可以支持支持不同环境的配置，比如 application-dev.yml 、application-test.yml 、application-prod.yml  等等。

pluginManagement 则是引入了相应的插件和对应的版本依赖

最后来看 spring-boot-starter-parent 的父依赖 spring-boot-dependencies 的 properties节点

我们看定义 POM，这个才是 Spring Boot 项目的真正管理依赖的项目，里面定义了 Spring Boot 相关的版本

spring-boot-starter-parent 通过继承 spring-boot-dependencies 从而实现了 Spring Boot 的版本依赖管理，所以我们的 Spring Boot 工程继承 spring-boot-starter-parent 后已经具备版本锁定等配置了，这也就是在 Spring Boot 项目中部分依赖不需要写版本号的原因。