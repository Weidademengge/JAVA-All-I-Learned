相同点：都是用来创建bean对象的
不同点：当使用BeanFactory创建对象的时候，必须要遵循严格的生命周期流程，太复杂了，如果想要简单的自定义某个对象的创建，同时创建完成的对象香蕉给spring来管理，那么就需要实现FactoryBean接口了
- isSingleton：是否式单例对象
- getObjectType：获取返回对象的类型
- getObject：自定义创建对象的过程（new，反射，动态代理）
