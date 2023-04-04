# 一、类加载器
ClassLoader的具体作用就是将class文件加载到jvm虚拟机中去，程序就可以正确运行了，但是jvm启动的时候，并不会一次性加载所有的class文件，而是根据需要动态加载，如果一次性加载那么多jar包那么多class，那内存消耗极大；


java自带的三个类加载器

![[Pasted image 20230306100238.png]]

**Bootstrap ClassLoader** 最顶层的加载类，主要加载核心类库，如：
%JRE_HOME%\lib下的rt.jar、resources.jar、charsets.jar和class等；

**Extention ClassLoader** 扩展的类加载器，加载目录%JRE_HOME%\lib\ext目录下的jar包和class文件；

**Appclass Loader** 也成为SystemAppClass 加载当前应用的classpath下的所有类，就是maven里的类

## 1.1 双亲委派机制

```java
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }
    //              -----??-----
    protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
            // 首先，检查是否已经被类加载器加载过
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    // 存在父加载器，递归的交由父加载器
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        // 直到最上面的Bootstrap类加载器
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }
 
                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    c = findClass(name);
                }
            }
            return c;
    }
```

![[image.png]]

从上图中我们就更容易理解了，当一个Hello.class这样的文件要被加载时。不考虑我们自定义类加载器，首先会在AppClassLoader中检查是否加载过，如果有那就无需再加载了。如果没有，那么会拿到父加载器，然后调用父加载器的loadClass方法。父类中同理也会先检查自己是否已经加载过，如果没有再往上。注意这个类似递归的过程，直到到达Bootstrap classLoader之前，都是在检查是否加载过，并不会选择自己去加载。直到BootstrapClassLoader，已经没有父加载器了，这时候开始考虑自己是否能加载了，如果自己无法加载，会下沉到子加载器去加载，一直到最底层，如果没有任何加载器能加载，就会抛出ClassNotFoundException。那么有人就有下面这种疑问了？

## 1.2 为什么要设计这种机制

这种设计有个好处是，如果有人想替换系统级别的类：String.java。篡改它的实现，在这种机制下这些系统的类已经被Bootstrap classLoader加载过了（为什么？因为当一个类需要加载的时候，最先去尝试加载的就是BootstrapClassLoader），所以其他类加载器并没有机会再去加载，从一定程度上防止了危险代码的植入。

# 二、类的生命周期

![[1589983077302-5b5bd986-a917-4ab8-8e2b-f10c81dd942d.png]]