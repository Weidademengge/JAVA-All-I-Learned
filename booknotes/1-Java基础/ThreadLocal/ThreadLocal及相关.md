
## 1、强软若虚Java中的引用类型
`Java`执行 `GC(垃圾回收)`判断对象是否**存活**有两种方式，分别是**引用计数法**和**引用链法(可达性分析法)**。
`JDK 1.2`版本开始，对象的引用被划分为 4种级别，使程序能更加灵活地控制**对象的生命周期**。这 `4`种级别**由高到低**依次为：**强引用**、**软引用**、**弱引用**和**虚引用**。

### 1.1 强引用 (StrongReference)
new对象就是强引用
```java
Object o = new Object()
```

强引用具备以下**特点**：  

1. 强引用可以直接访问目标对象。  
2. 强引用所指向的对象在任何时候都不会被系统回收。JVM宁愿抛出Out Of Memory异常也不会回收强引用所指向的对象。  
3. 强引用可能导致内存泄漏

通常来说，应用程序内部的**内存泄露**有**两种**情况：
- 虚拟机中存在程序无法使用的内存区域
- 程序中存在大量存活时间过长的对象

### 1.2 软引用(SoftReference）

内存不够会回收，够就不回收

软引用是除了强引用外最强的引用类型，我们可以通过java.lang.ref.SoftReference使用软引用。一个持有软引用的对象，它不会被JVM很快回收，JVM会根据当前堆的使用情况来判断何时回收。当堆使用率临近阙值时，才会去回收软引用的对象。只要有足够的内存，软引用便可能在内存中存活相当长一段时间。通过软引用，垃圾回收器就可以在内存不足时释放软引用可达的对象所占的内存空间。保证程序正常工作。

通过一个软引用申明，JVM抛出[[OOM]]之前，清理所有的软引用对象。垃圾回收器某个时刻决定回收软可达的对象的时候，会清理软引用，并可选的把引用存放到一个引用队列（ReferenceQueue）。

```java
package com.springframework.service;  
  
import com.springframework.spring.MyAnnotationConfigApplicationContext;  
import java.lang.ref.SoftReference;  
  
public class Test {  
  
    public static void main(String[] args) {  
        
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);  
  
        System.out.println(m.get());  
        System.gc();  
        try{  
            Thread.sleep(500);  
        }catch (InterruptedException e){  
            e.printStackTrace();  
        } 
        //这里在idea中定义一个20M的内存，相当于一开始定义一个10M软引用，线程睡眠0.5s，那么再定义一个15M的数组，gc会将10M数组回收 
        System.out.println(m.get());  
                byte[] b = new byte[1024*1024*15];  
        System.out.println(m.get());  
    }  
}
```

![[u=2456602573,3984517200&fm=253&fmt=auto&app=138&f=PNG.webp]]


### 1.3 弱引用(WeakReference)
垃圾回收就没了，但是没回收还是能get到

java中使用WeakReference来表示弱引用。如果某个对象与弱引用关联，那么当JVM在进行垃圾回收时，无论内存是否充足，都会回收此类对象。

通过一个弱引用申明。类似弱引用，只不过 Java 虚拟机会尽量让软引用的存活时间长一些，迫不得已才清理。

```java
public class Test {  
  
    public static void main(String[] args) {  
        WeakReference<M> m = new WeakReference<>(new M<>);  
        System.out.println(m.get());  
        System.gc();
        //返回null  
        System.out.println(m.get());  
    }  
}
```

### 1.4 虚引用(PhantomReference)

虚引用指向的对象，被回收后会放入到队列中，也就是后面的Queue。相当于一个钩子函数
 
java中使用PhantomReference来表示虚引用。

通过一个虚引用申明。仅用来处理资源的清理问题，比Object里面的finalize机制更灵活。get方法返回的永远是null，Java虚拟机不负责清理虚引用，但是它会把虚引用放到引用队列里面。

虚引用的主要目的是在一个对象所占的内存被实际回收之前得到通知，从而可以进行一些相关的清理工作。弱引用之前的两种引用类型有很大的不同：首先虚引用在创建时必须提供一个引用队列作为参数；其次虚引用对象的get方法总是返回null，因此无法通过虚引用来获取被引用的对象。

```java
public class Test {  
  
    public static void main(String[] args) {  
        ReferenceQueue<String> queue = new ReferenceQueue<>();  
        PhantomReference<String> phantomReference = new PhantomReference<>(new String(), queue);  
  
        System.out.println(phantomReference.get());  
  
        new Thread(()->{  
            while (true){  
                List<Object> list = new LinkedList<>();  
                try{  
                    Thread.sleep(1000);  
                }catch (InterruptedException e){  
                    e.printStackTrace();  
                }  
                System.out.println(phantomReference.get());  
            }  
        }).start();  
  
        new Thread(()->{  
            while(true){  
                Reference<? extends String> poll = queue.poll();  
                if(poll != null){  
                    System.out.println("----虚引用对象被jvm回收了----"+poll);  
                }  
            }        }).start();  
  
        try{  
            Thread.sleep(500);  
        }catch (InterruptedException e){  
            e.printStackTrace();  
        }  
  
    }  
}
```

## 2、ThreadLocal

再spring中@transactional控制connection就是用的ThreadLocal，mybatis中的分页也是ThreadLocal

1. ThreadLocal是每个线程中私有的存储空间，它不能被其他线程共享，因此它能保证线程安全。
2. ThreadLocal其实相当于Thread的工具类，便于对Thread里面的ThreadLocalMap进行操作。
3. ThreadLocal只能放入一个副本值，因为ThreadLocalMap中的存储方式是（key：ThreadLocal对象，value：副本值对象），如果需要存入多个值的话，需要创建多个ThreadLocal对象。

### 2.1 ThreadLocal有几个特点：

1.  一个线程的ThreadLocal不会被其他线程访问到
2.  ThreadLocal中使用了弱引用
3.  ThreadLocal中存在内存泄漏


### 2.2 注意要点

1. ThreadLocal只能放入一个副本值，如果需要存入多个值的话，需要创建多个ThreadLocal对象。
2. 每个线程往ThreadLocal中读写数据是线程隔离，互相之间不会影响的，所以ThreadLocal无法解决共享对象的更新问题！
3. ThreadLocal对象建议使用static修饰。这个变量是针对一个线程内所有操作共享的，所以设置为静态变量，所有此类实例共享此静态变量，也就是说在类第一次被使用时装载，只分配一块存储空间，所有此类的对象（只要是这个线程内定义的）都可以操控这个变量。