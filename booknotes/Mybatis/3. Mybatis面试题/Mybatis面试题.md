
## 1.#{}与${}的区别

- #{}：是sql的参数占位符，Mybatis会将sql中的#{}替换为?，按序给sql的?占位符设置参数值。
- \${}:是在数据库配置文件中的变量占位符，属于静态文本替换，比如${Driver}会被静态替换为com.mysql.jdbc.Driver。

## 2.Xml映射文件中，除了常见的select/Inserct/update/delete标签之外，还有那些标签？

还有\<resultMap>,\<parameterMap>,\<sql>,\<include>,\<selectKey>,加上动态sql的9个标签等。\<include>是可以引入其他sql片段，\<selectKey>为不支持自增的主键生成策略标签。

## 3.最佳实践中，通常一个Xml映射文件，都会写一个Dao接口与之对应，请问，这个Dao接口的工作原理是什么？Dao接口里的方法，参数不同时，方法能重载吗？

Dao接口，就是人们常说的Mapper接口，接口的全限名，就是映射文件中的namespace的值，接口的方法名，就是映射文件中MappedStatement的id值，接口方法内的参数，就是传递给sql的参数。Mapper接口是没有实现类的，当调用接口方法时，接口全限名+方法名拼接字符串作为key值，可唯一定位一个MappedStatement。

Dao接口里的方法，是不能重载的，因为是全限名+方法名的保存和寻找策略。

Dao接口的工作原理是JDK动态代理，MyBatis运行时会使用JDK动态代理为Dao接口生成代理proxy独享，代理对象proxy会拦截接口方法，转而执行MappedStatement所代表的sql，然后将sql执行结果返回。

## 4.Mybatis是如何进行分页的？分页插件的原理是什么？

1.Mybatis使用RowBounds对象进行分页，它是针对ResultSet结果集执行的内存分离，而非物理分页；
RowBounds分页（不使用SQL），不再使用SQL实现分页
2.直接在sql内直接书写limit进行分页；
```sql
Select    *  from   user  limit  2;#[0,n]
```
【0,2】出来的时候第一个和第二个。所以0代表的第一个。
3.使用分页插件来完成物理分页。

## 5.Mybatis动态sql是做什么？都有哪些动态sql?能简述一下动态sql的执行原理不？

Mybatis动态sql可以让我们在xml映射文件内，以标签的形式编写动态sql，完成逻辑判断和动态拼接sql的功能，Mybatis提供了9种动态sql标签

其执行原理为，使用OGNL从sql参数对象中计算表达式的值，根据表达式的值动态拼接sql，以此来完成动态sql的功能。

## 6.Mybatis是如何将sql执行结果封装为目标对象并返回的？都有那些映射形式？

第一种是用\<resultMap>标签，逐一定义列明和对象属性名之间的映射关系。

第二种是使用sql列的别名功能，将列名书写为对象属性名。

有了列名和属性名的映射关系后，Mybatis通过反射创建对象，同时使用反射给对象的属性逐一赋值并返回，那些找不到映射关系的属性，是无法完成赋值的。

## 7.Mybatis能执行一对一、一对多的关联查询吗？都有那些实现方式，以及他们之间的区别？

Mybatis不仅可以执行一对一、一对多的关联查询，还可以执行多对一，多对多的关联查询，多对一查询，其实就是一对一查询，只需要把selectOne()修改为selectList()即可；多对多查询，其实就是一对多查询，只需要把selectOne()修改为selectList()即可。

关联对象查询，有两种方式：

一种是单独发送一个sql去查询关联对象resultMap，赋给主对象，然后返回主对象；

按照查询嵌套处理（子查询）

另一种是使用嵌套查询，嵌套查询的含义为使用join查询，一部分列是A对象的属性值，另外一部分是关联对象B的属性值，好处是只发一个sql查询，就可以把主对象和其关联对象查出来。

按照查询嵌套处理（联表查询）

## 8.Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？

Mybatis仅支持association关联对象和collection关联集合对象的延迟加载，association指的是一对一，collection值的就是一对多查询。在Mybatis配置文件中，可以配置是否启用延迟加载lazyLoadingEnabled=truefalse。

它的原理是，使用CGLIB创建目标的代理对象，当调用目标方法时，进入拦截器方法，比如调用a.getB.getName(),拦截器invoke()方法a.getB()是null值，那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，然后调用a.setB(b)，于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。这就是延迟加载的基本原理。

## 9.Mybatis的XML映射文件中，不同的XML映射文件，id是否可以重复？

不同的XML映射文件，如果配置了Namespace，那么Id可以重复；如果没有配置namespace，那么Id不能重复；毕竟Namespace不是必须加的，只是最佳实践而已。

原因就是namespace+id是Map<String.MappedStatement>的key使用的，如果没有Namespace，就剩下Id,那么id重复会导致数据互相覆盖。有了namespace，自然id就可以重复，namespcae不同，namespace+id自然也就不同。

## 10.Mybatis都有哪些Executor执行器？他们之间的区别是什么？

有三种：SimpleExecutor、ReuseExector、BatchExector。

SimpleExecutor:每执行一次update或select，就会开启Statement对象，用完立刻关闭Statement对象。

ReuseExecutor:执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map<String,Statement>内，供一次使用。简言之，重复使用Statement对象。

BatchExecutor:执行Update(没有select,JDBC批处理不支持select),将所有sql都添加到批处理中(addBatch)，等待统一执行(executorBatch())，它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理，与JDBC批处理相同。

## 11.Mybatis中如何制定使用哪一种Executor执行器？

在Mybatis配置文件中，可以指定默认的ExecutorType执行器类型，也可以手动给DefalutSqlSessionFactory的创建SqlSession的方法传递ExecutorType类型参数。

## 12.Mybatis是否可以映射Enum枚举类？

Mybatis可以映射枚举类，不但可以映射枚举类，Mybatis可以映射任何对象到表的一列上。映射方法为自定义一个TypeHandler，实现TypeHandler的setParameter()和getResult()接口方法。TypeHandler有两个作用，一是完成从javaType至jdbcType的转换，二是完成jdbcType至javaType的转换，体现为setParameter()和getResult()两个方法，分别代表设置sql问好占位符参数和获取列查询结果。

## 13.Mybatis映射文件中，如果A标签通过include引用了B标签的内容，请问B标签的内容是否定义在A标签之后，还是必须定义为A之前？

虽然Mybatis解析Xml映射文件是按顺序接续的，但是，被引用的B标签依然可以定义在任何地方，Mybatis都可以正确识别。

原理是，当我A包含了B的时候，Mybatis解析到A的时候，发现B还没有，会将A设置为未解析状态，然后继续解析下面的标签，待所有标签都解析完全后，再去解析一次未解析的标签。