**总**
控制反转：理论思想，原来的对象是由使用者来进行控制，有了spring之后，可以把整个对象交给Spring来帮我们管理。
DI：依赖注入，把对应的属性的值注入到具体的对象中，@Autowired，populateBean完成属性值的注入

容器：存储对象，使用map结构来存储，在spring中一般存在三级缓存，singletonObject存放完整的Bean对象、整个Bean的生命周期从创建到销毁的过程全部都是有由容器来管理（bean的生命周期）

**分**
1. 一般聊IOC容器的时候要涉及到容器的创建过程（beanFactory，DefaultListableBeanFactory），向Bean工厂中设置一些参数（BeanPostProcessor、Aware接口的子类）等等属性
2. 加载解析bean对象，准备要创建的Bean对象的定义对象BeanDefination，（XML，或者注解的解析）
3. beanFactoryPostProcessor的处理，此处是扩展点，PlaceholderConfigurerSupport（处理占位符）、ConfigurationClassPostProcessor
4. BeanPostProcessor注册功能，方便后续对bean对象完成具体的扩展点功能
5. 通过反射的方式将BeanDefination对象实例化具体的Bean对象
6. Bean对象的初始化过程（填充属性、调用aware子类的方法、调用BeanPostProcessor前置处理方法、调用init-method方法、调用BeanPostProcessor后置处理方法）
7. 生成完整的Bean对象，通过getBean方法可以直接获取
8. 销毁过程


