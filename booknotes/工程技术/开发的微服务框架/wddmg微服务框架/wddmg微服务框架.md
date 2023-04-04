以集团公司微服务框架为模板，结合阿里框架、ruoyi-cloud框架，开发的wddmg微服务框架。

1、分为以下几个模块


2、各个依赖的版本号

依赖的版本号是根据ruoyi-cloud最新的可使用版的版本，以下是各个版本号。在spring-cloud-alibaba中，已经记录了对应的nacos、sentinel、seata、rocketmq等。但没有dubbo版本号
| 依赖名称                     | 版本号     |
|:---------------------------- |:---------- |
| spring-cloud.version         | 2021.0.5   |
| spring-cloud-alibaba.version | 2021.0.4.0 |
| spring-boot-admin.version    | 2.7.10           |

dubbo？？？？在api中，等会儿再说


集团公司的框架问题：

版本太老，所有的pom都依赖于qm-common，太臃肿了

阿里框架问题，与集团的分层机构完全不同

我的框架，用的是最新的cloud、每个模块pom用什么依赖什么、maven helper处理所有的冲突依赖、使用了ruoyi中的权限、文件、excel和代码生成。替换了ruoyi中的mybatis，使用mp处理。集团和若依中使用的是自带的openfeign，改为dubbo远程调用。增加了guava和hutool等util框架。

舍弃了ry-cloud中的主从数据源配置

|      | 阿里脚手架 | RuoYi-cloud  | 集团框架   | wddmg |
|:-----|:-----|:-----|:-----|:-----|
|   数据源   |  单一数据源 | 多数据源 | 单一数据源 | 单一数据源 |
|      |      |      |      |      |




在腾讯云服务器上的nacos必须把9848端口同样打开，否则启动报错
