
## 1. MyBatis的架构设计

![[Pasted image 20230314153129.png]]

	mybatis架构四层作用是什么呢？

- Api接口层：提供API 增加、删除、修改、查询等接口，通过API接口对数据库进行操作。

- 数据处理层：主要负责SQL的 查询、解析、执行以及结果映射的处理，主要作用解析sql根据调用请求完成一次数据库操作.

- 框架支撑层：负责通用基础服务支撑,包含事务管理、连接池管理、缓存管理等共用组件的封装，为上层提供基础服务支撑.

- 引导层：引导层是配置和启动MyBatis 配置信息的方式

# 2 MyBatis主要组件及其相互关系

组件关系如下图所示：
![[Pasted image 20230315131134.png]]

# 3 主要组件

项目文件可以看[[Mybatis源码]]
打开源码后，可以看到mybatis的目录结构
![[Pasted image 20230317103008.png]]

各个包对应的功能说明：
| 包名        | 说明                                   |
|:----------- |:-------------------------------------- |
| annotations | Mapper映射器接口中使用到的注解         |
| binding     | Mapper映射器接口与映射语句关系绑定构建 |
| builder     | Configuration配置的构建包              |
| cache       | 缓存实现与定义（含一级/二级缓存）        |
| cursor      |  游标（针对查询结果集的获取与遍历等）          |
| datasource  |  数据源/连接池                           |
| exceptions  |  异常包                              |
| executor    |  语句执行器（含参数/结果集/语句处理等）    |
| io          |  资源读取辅助包                       |
| jdbc        |  Mybatis内部的sql脚本运行的测试包         |
| logging     |  一套日志接口和适配器包                  |
| mappingn    |  Mapper映射器相关参数/语句/结果/类型等对象包          |
| parsing     |   Xml解析包（例如#{}占位符解析）          |
| plugin      |   插件包                                     |
| reflection  |   反射处理工具包                                     |
| scripting   |   Sql执行脚本的解析处理包                 |
| session     |   数据库连接会话核心包（会话创建/管理/调用）    |
| transaction |   事务                                     |
| type        |   类型处理器（定义bean与数据库类型的转换关系）                    |

