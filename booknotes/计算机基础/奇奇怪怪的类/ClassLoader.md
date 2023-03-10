类加载器：主要和配置文件有关，要想得到配置文件，就需要找到配置文件的位置

-   我们用`Java`编写的文件都是`.java`文件，而想要运行，还需将其编译成`.class`字节码文件才可被`JVM`运行；这就需要`JVM`先找到对应的`.class`才行，这也就是要找到对应的`classpath`。
-   `JVM`会在编译项目时，会主动将 `.java`文件编译成 `.class`文件 并和 `resources`目录下的静态文件一起放在 `target/classes`(如果是`test`下的类，便会放于`/target/test-classes`下)目录下

所以在使用ClassLoader.getResource(Path)方法时，会return一个targe的全路径

```java
public class MyAnnotationConfigApplicationContext {  
  
    private Class configClass;  
  
    public MyAnnotationConfigApplicationContext(Class configClass) {  
        this.configClass = configClass;  

        ClassLoader classLoader = MyAnnotationConfigApplicationContext.class.getClassLoader();  
            URL resource = classLoader.getResource(path);  
            File file = new File(resource.getFile());  
  
            System.out.println(file);  
            //D:\java_project\bilibili\spring-demo\target\classes\com\springframework\service
        }  
    }  
}
```