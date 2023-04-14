# 是什么

一句话概括：并发编程中既然可以多线程操作共享资源，那么也会有线程之间资源隔离，每个线程只能访问自己的数据不能访问别的线程中的数据。Synchronized用于多线程间的数据共享，而ThreadLocal则用于线程间的数据隔离，通过ThreadLocal在同一线程不同组件中传递公共变量。

# 前置知识

Java中的4种引用类型-强软弱虚

## 强引用

JVM进行GC垃圾回收的时候针对强引用对象是不会被回收的，在Java中最常见的就是强引用，把一个对象赋值给一个引用变量，这个引用变量就是一个强引用。当一个对象被强引用对象应用时，它处于可达状态，它是不可能被垃圾回收机制回收的，即使该对象永远都不会被用到JVM也不会回收。如果堆内存不足时那么就会出现OOM（内存溢出）。一般来说我们创建的对象都是强引用对象。

```java
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object obj1 = new Object(); //这样的定义默认就是强引用
        Object obj2 = obj1; //obj2引用赋值
        obj1=null; //置空
        System.gc(); //触发gc
        System.out.println(obj1); //obj1
        System.out.println(obj2); //obj2依然还存在
    }
}
```

结论：当内存不足，JVM开始垃圾回收，对于强引用对象，就算是出现了OOM也不会对该对象进行回收，死都不收。强引用对象是我们最常见的普通的对象引用，只要还有强引用指向对象，就表明对象还活着，垃圾回收器不会碰这个对象。

## 软引用

当系统内存充足的时候不会被回收，当系统内存不足的时候就会被回收。

```java
public class SoftReferenceDemo {
    public static void main(String[] args) {
        SoftReference<byte[]>  m = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println(m.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());
        //在分配一个数组，设置JVM堆内存最大20M，做实验  -Xmx20M。那么新创建的byte[]原有空间一定装不下
        //这时候系统就会垃圾回收，先回收一次，如果不够会把软引用也回收掉
        byte[] b = new byte[1024*1024*15];
        System.out.println(m.get());
    }
}
```

结论：非常适合做缓存。系统资源足够的时候就从缓存中取资源，不够了时候把缓存干掉

## 弱引用（重点）

弱引用的对象，只要垃圾回收机制一运行，就会被干掉。

```java
public class WeakReferenceDemo {
    public static void main(String[] args) {
        WeakReference<Object> weakReference = new WeakReference<Object>(new Object());
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
    }
}
```

有啥用？下文会跟ThreadLocal源码一起分析，也就是接下来的重点。

## 虚引用（基本用不到）

首先说明虚引用不是本文重点，工作场景也基本没有，为了知识体系完整性才写出来。减轻大家脑力，可以不看。

虚引用，get不到。弱引用虽然弱，但是可以拿到。没有被回收之前，虚引用回不回收都拿不到。

作用：主要是操作系统内管理直接内存DirectByteBuffer。

# ThreadLocal是什么？

前置知识说完了，我们先通过一个场景来引出ThreadLccal。

假设一种场景：方法内传递全局参数，方法里面经历了好多次深层调用之后，我想拿到这个变量怎么办？

-   方法1：把这个变量一层一层传进来

**优点**：大多数情况下确实可以解决上面的问题，比较简单

**缺点**：无法解决一些特定场景，比如调用别人的类库中的方法，就传递不了了。而且多层方法都要加个参数，太累了

-   方法2：我直接把变量定义成static，全局都可以调度。但是这种方法在多线程访问情形下是不安全的。
-   方法3：还有没有更加好的方法：ThreadLocal。

ThreadLocal 为解决多线程程序的并发问题提供了一种新的思路。使用这个工具类可以很简洁地编写出优美的多线程程序。

当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。

从线程的角度看，目标变量就象是线程的本地变量，这也是类名中“Local”所要表达的意思。

所以，在Java中编写线程局部变量的代码相对来说要笨拙一些，因此造成线程局部变量没有在Java开发者中得到很好的普及。

# ThreadLocal原理

既然ThreadLocal则用于线程间的数据隔离，每个线程都可以独立的操作自己独立的变量副本而不会影响别的线程中的变量。先用一个简单的代码演示一下结论：

```java
public class threadLocalDemo {
    static ThreadLocal<Person> tl = new ThreadLocal<Person>();
    
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        },"A").start();
​
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        },"B").start();
    }
    
    static class Person {
        String name = "yangguo";
    }
}
```

运行结果：null

一个简单的ThreadLocal演示，开启两个线程t1、t2，线程t2对threadLocal进行了set，但是并没有改变线程t1本地的threadlocal变量值。明明两个线程调用的同一变量啊，为什么结果不同呢？threadLocal对线程之间进行了隔离。

先给个set的流程图，然后在看源码会更加清晰：

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653402622618-a7a3c219-3657-4735-a5da-6fb6bacc1f4b.png)

  

扒扒底层源码看看到底做了什么？进入threadLocal.set()方法，源码如下：

```java
public void set(T value) {
    //获取当前线程
    Thread t = Thread.currentThread();  
    
    //根据当前线程获取ThreadLocalMap
    ThreadLocalMap map = getMap(t);      
    if (map != null) 
        //给ThreadLocalMap赋值(ThreadLocal,value)
        map.set(this, value);           
    else
        createMap(t, value);
}

ThreadLocalMap getMap(Thread t) {
    //从当前Thread类中获取到ThreadLocalMap
    return t.threadLocals;       
}

//map.set源码：将map中的key, value组装成一个Entry
//而在Entry中继承了WeakReference弱引用
private void set(ThreadLocal<?> key, Object value) {
    Entry[] tab = table;
    int len = tab.length;
    int i = key.threadLocalHashCode & (len-1);
    for (Entry e = tab[i]; e != null; e = tab[i = nextIndex(i, len)]) {
        ThreadLocal<?> k = e.get();
        if (k == key) {
            e.value = value;
            return;
        }
        if (k == null) {
            replaceStaleEntry(key, value, i);
            return;
        }
    }
    tab[i] = new Entry(key, value);
    int sz = ++size;
    if (!cleanSomeSlots(i, sz) && sz >= threshold)
        rehash();
}

//Entry源码：继承了弱引用
static class Entry extends WeakReference<ThreadLocal<?>> {
    /** The value associated with this ThreadLocal. */
    Object value;
    Entry(ThreadLocal<?> k, Object v) {
        super(k);
        value = v;
    }
}
```

扒完源码之后整理一下threadLocal中set方法的过程。

过程：

1、获取到当前线程

2、从当前线程中获取到自己内部的ThreadLocalMap，别的线程无法访问这个map，

3、往ThreadLocalMap值塞值（ThreadLocal，value），塞值的过程中将key和value组装成了一个Entry，继承了弱引用。防止ThreadLocal引用的对象内存泄漏。

所以对于不同的线程，每次获取副本值时，别的线程并不能获取到当前线程的副本值，形成了副本的隔离，互不干扰。

# 能干嘛

典型例子：

## Spring中@Transacion注解中使用到

Spring的事务管理器通过AOP切入业务代码，在实际开发中使用注解把某个方法标记为支持事务，方法里面调用别的方法，想支持事务所有的这些方法必须要拿到同一个数据库连接，那么这个Connection怎么拿到呢。数据库连接池随便拿一个显然是不行的，上个方法可能拿的另外一个Connection。

所以Connection放到了ThreadLocal里面，每个线程都记录一份数据，只要是同一个线程可以随时拿到ThreadLocal，这样无论涉及多少方法的调用，我们都可以保证同一个Connection。

当然其实Spring源码中有很多的ThreadLocal，这里只是拿出一个较为由体感的。

## 传递数据

多层方法间调用线程数据传递。

# ThreadLocalMap的问题

## 为什么ThreadLocalMap中的key是弱引用?

**内存泄漏的第一种场景**

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653403220056-c34f917d-006d-4b2c-a175-6724e627b721.png)

上图中，若是用强引用，即使t1=null，但ThreadLocalMap的key的引用仍然指向ThreadLocal对象，GC时只会把t1给回收掉，ThreadLocal由于被引用了不会被回收，所以如果这里使用强引用的话会导致内存泄漏。无论怎么样这个key都不会被回收了。

所以图示上面为虚线，代表虚引用。

## **为什么threadLocal用完必须要进行remove?**

**内存泄漏的第二种场景**

由于ThreadLocalMap的中key是弱引用，而Value是强引用。这就导致了一个问题，ThreadLocal在没有外部对象强引用时，发生GC时弱引用Key会被回收，而Value不会回收，形成了Entry里面的元素就会出现<null,value>的情况。如果创建ThreadLocal的线程一直持续运行，那么这个Entry对象中的value就有可能一直得不到回收，就会发生内存泄露。

![](https://intranetproxy.alipay.com/skylark/lark/0/2022/png/128606/1653403632410-07ade440-8fa3-40d8-8178-46cea0d5c788.png)

get和set源码虽然它们是把整个map key为null的值全部清理掉，但是还需要remove，因为线程里面可能长期不执行get ，服务器线程一天24h都在跑。另外，假如线程从线程池来的，工作一段在回来，如果ThreadLocal没被清理掉，如果使用ThreadLocal的set方法之后，没有显示的调用remove方法，就有可能发生内存泄露，所以养成良好的编程习惯十分重要，使用完ThreadLocal之后，**记得调用remove方法**:

```java
ThreadLocal<Person> threadLocal = new ThreadLocal<Person>();
try {
     threadLocal.set(new Person());
} finally {
     threadLocal.remove();   //threadlocal用完必须要进行remove，不然会导致内存泄漏。
}
```

# 总结

-   每个ThreadLocal只能保存一个变量副本，如果想要上线一个线程能够保存多个副本以上，就需要创建多个ThreadLocal
-   ThreadLocal内部的ThreadLocalMap键为弱引用，会有内存泄漏的风险。
-   适用于无状态，副本变量独立后不影响业务逻辑的高并发场景。如果如果业务逻辑强依赖于副本变量，则不适合用ThreadLocal解决，需要另寻解决方案。