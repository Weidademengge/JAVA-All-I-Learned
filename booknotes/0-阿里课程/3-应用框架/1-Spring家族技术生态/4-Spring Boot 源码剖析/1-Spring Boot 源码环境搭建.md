## 下载源码

-   [spring-boot-2.2.9.RELEASE](https://github.com/spring-projects/spring-boot/releases/tag/v2.2.9.RELEASE) （打开页面的最下方，如下图所示）
![](https://intranetproxy.alipay.com/skylark/lark/0/2023/png/128606/1680749065887-0c782ac3-43b2-4b85-99a6-1915319cb471.png)

-   在 2.3.0 中对 Spring Boot 进行了相当重大的更改，这是使用 Gradle 而非 Maven 构建项目的第一个版本，切换的主要原因是**为了减少构建项目所需的时间**。

## 编译源码

-   将源码解压后拷贝到我们的项目目录之下（alibaba-faw-source）
-   进入Spring Boot 源码目录后，执行mvn命令: `mvn clean install -DskipTests -Pfast` （跳过测试用例）

![](https://lang-image-bed.oss-cn-hangzhou.aliyuncs.com/20230404143046.png)

## 导入 IDEA

1.  打开 IDEA ，自动会加载 spring-boot-2.2.9.RELEASE 项目
2.  需要通过右侧的 Maven 插件将新项目加入到 Maven 的管理之下
3.  最后打开主 pom.xml 关闭 Maven 的代码检查配置：

1.  在`<properties></properties>`代码块中增加一段 `**<disable.checks>true</disable.checks>**`，如下所示

<properties>
  <revision>2.2.9.RELEASE</revision>
  <main.basedir>${basedir}</main.basedir>
  <disable.checks>true</disable.checks>
</properties>

## 最终效果

修改 user-test-all 项目的主 pom 文件中的 Spring Boot 版本号为 2.2.9.RELEASE

如图所示：

![](https://lang-image-bed.oss-cn-hangzhou.aliyuncs.com/20230406102746.png)