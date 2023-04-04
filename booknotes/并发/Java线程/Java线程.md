## 创建和运行线程

#### 方法一，直接使用 Thread

test包下的Test1.java

```java
// 构造方法的参数是给线程指定名字，，推荐给线程起个名字
Thread t1 = new Thread("t1") {
 @Override
 // run 方法内实现了要执行的任务
 public void run() {
 log.debug("hello");
 }
};
t1.start();
```

#### 方法二，使用 Runnable 配合 Thread

把【线程】和【任务】（要执行的代码）分开，Thread 代表线程，Runnable 可运行的任务（线程要执行的代码）Test2.java

```java
// 创建任务对象
Runnable task2 = new Runnable() {
 @Override
 public void run() {
 log.debug("hello");
 }
};
// 参数1 是任务对象; 参数2 是线程名字，推荐给线程起个名字
Thread t2 = new Thread(task2, "t2");
t2.start();
```

#### 小结

方法1 是把线程和任务合并在了一起，方法2 是把线程和任务分开了，用 Runnable 更容易与线程池等高级 API 配合，用 Runnable 让任务类脱离了 Thread 继承体系，更灵活。通过查看源码可以发现，方法二其实到底还是通过方法一执行的！

#### 方法三，FutureTask 配合 Thread

FutureTask 能够接收 Callable 类型的参数，用来处理有返回结果的情况 Test3.java

 ```java
		public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 实现多线程的第三种方法可以返回数据
        FutureTask futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("多线程任务");
                Thread.sleep(100);
                return 100;
            }
        });
        // 主线程阻塞，同步等待 task 执行完毕的结果
        new Thread(futureTask,"我的名字").start();
        log.debug("主线程");
        log.debug("{}",futureTask.get());
    }
```

Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。

```java
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

Future提供了三种功能： 　　

1.  判断任务是否完成； 　　
    
2.  能够中断任务； 　　
    
3.  能够获取任务执行结果。
    

[FutureTask是Future和Runable的实现](https://gitee.com/link?target=https%3A%2F%2Fmp.weixin.qq.com%2Fs%2FRX5rVuGr6Ab0SmKigmZEag)

## 3.2 线程运行原理

### 虚拟机栈与栈帧

拟机栈描述的是Java方法执行的内存模型：

- 每个方法被执行的时候都会同时创建一个栈帧(stack frame)用于存储局部变量表、操作数栈、动态链接、方法出口等信息，是属于线程的私有的。当java中使用多线程时，

- 每个线程都会维护它自己的栈帧！每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法

以下面代码为例：
	1.首先执行类加载[[ClassLoader]], 把TestFrames字节码加载到JVM虚拟机中的方法区
	2.JVM启动main的主线程，并给main分配线程栈内存，线程交给任务调取器去执行，如果cpu时间片分给主线程，cpu核心准备运行main线程代码
	3.main线程栈中有一个程序计数器，用于跟踪当前线程执行到哪行
	4.在使用voidmain是，main线程栈中会再次分配一块内存作为main栈帧，里面会存储局部变量表、args、返回地址、锁记录、操作数栈
	5.main方法中的参数args会在堆中创建一块堆内存new String[]
	6.此时程序计数器发现要运行method1方法
	7.创建method1栈帧，并分配好x，y，m的内存，返回地址指向方法区method1
	8.同样过程创建method2栈帧
	9.释放method2栈帧内存
	10.释放method1栈帧内存
	
```java
package com.wddmg.concurrent.n3;  
  
/**  
 * @author duym  
 * @version $ Id: TestFrames, v 0.1 2023/04/03 10:30 duym Exp $  
 */public class TestFrames {  
    public static void main(String[] args) {  
        method1(10);  
    }  
  
    private static void method1(int x){  
        int y = x + 1;  
        Object m = method2();  
        System.out.println(m);  
    }  
  
    private static Object method2(){  
        Object n = new Object();  
        return n;  
    }  
}
```

![[Pasted image 20230403111318.png]]

### 线程上下文切换（Thread Context Switch）

因为以下一些原因导致 cpu 不再执行当前的线程，转而执行另一个线程的代码

-   线程的 cpu 时间片用完(每个线程轮流执行，看前面并行的概念)
-   垃圾回收
-   有更高优先级的线程需要运行
-   线程自己调用了 `sleep`、`yield`、`wait`、`join`、`park`、`synchronized`、`lock` 等方法

当 Context Switch 发生时，需要由操作系统保存当前线程的状态，并恢复另一个线程的状态，Java 中对应的概念 就是程序计数器（Program Counter Register），它的作用是记住下一条 jvm 指令的执行地址，是线程私有的

- 状态包括程序计数器、虚拟机栈中每个栈帧的信息，如局部变量、操作数栈、返回地址等

- Context Switch频繁发生会影响性能

## 3.3 Thread的常见方法

| 方法名 static   | <div style="width:150px">功能说明</div>                    | 注意                                                                                                                                                                            |
|:--------------- |:---------------------------------------------------------- |:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| start()         | 启动一个新线程，在新的线程运行run方法中的代码              | start方法只是让线程进入就绪，里面代码不一定立刻执行（CPU的时间片还没分给它）。<br>每个线程对象的start方法只能调用一次，如果调用了多次会出现IllegalThreadStateException          |
| run()           | 新线程启动会调用的方法                                     | 如果在构造Thread对象时传递了Runnable参数，则线程启动后会调用Runnable中的run方法，否则默认不执行任何操作。<br>但可以创建Thread的子类对象，来覆盖默认行为                         |
| join（）        | 等待线程运行结束                                           |                                                                                                                                                                                 |
| join（long n）  | 等待线程运行结束，最多等待n毫秒                            |                                                                                                                                                                                 |
| getId()         | 获取线程长整型的id唯一id                                   |                                                                                                                                                                                 |
| getName（）     | 获取线程名                                                 |                                                                                                                                                                                 |
| setName(String) | 修改线程名                                                 |                                                                                                                                                                                 |
| getPriority     | 获取线程优先级                                             |                                                                                                                                                                                 |
| setPriority（） | 修改线程优先级                                             | java中规定线程优先级是1-10的整数，较大的优先级能提高该线程被CPU调度的几率                                                                                                       |
| getState        | 获取线程状态                                               | java中线程状态用6个enum表示，非别为NEW,RUNNABLE,BLOCKED,WAITING,TIMED_WAITING,TERMINATED                                                                                        |
| isInterrupted() | 判断是否被打断                                             | 不会清除打断标记                                                                                                                                                                |
| isAlive         | 线程是否存活（还没有运行完毕）                             |                                                                                                                                                                                 |
| interrupt（）   | 打断线程                                                   | 如果被打断线程正在sleep,wait,join会导致被打断的线程抛出InterruptedException，并清除打断标记；<br>如果打断的正在运行的线程，则会设置打断标记；park的线程被打断，也会设置打断标记 |
| interrupted（） | 判断当前线程是否被打断                                     | 会清除打断标记                                                                                                                                                                  |
| currentThread() | 获取当前正在执行的线程                                     |                                                                                                                                                                                 |
| sleep（long n） | 让当前执行的线程休眠n毫秒，休眠时让出cpu的时间片给其他线程 |                                                                                                                                                                                 |
| yield()         | 提示线程调度器让出当前线程对CPU的使用                      | 主要是为了测试和调试                                                                                                                                                                                |

#### 3.3.1 start 与 run

#### 调用start

```java
    public static void main(String[] args) {
        Thread thread = new Thread(){
          @Override
          public void run(){
              log.debug("我是一个新建的线程正在运行中");
              FileReader.read(fileName);
          }
        };
        thread.setName("新建线程");
        thread.start();
        log.debug("主线程");
    }
```

输出：程序在 t1 线程运行， `run()`方法里面内容的调用是异步的 Test4.java

```
11:59:40.711 [main] DEBUG com.concurrent.test.Test4 - 主线程
11:59:40.711 [新建线程] DEBUG com.concurrent.test.Test4 - 我是一个新建的线程正在运行中
11:59:40.732 [新建线程] DEBUG com.concurrent.test.FileReader - read [test] start ...
11:59:40.735 [新建线程] DEBUG com.concurrent.test.FileReader - read [test] end ... cost: 3 ms
```

#### 调用run

将上面代码的`thread.start();`改为 `thread.run();`输出结果如下：程序仍在 main 线程运行， `run()`方法里面内容的调用还是同步的

```
2:03:46.711 [main] DEBUG com.concurrent.test.Test4 - 我是一个新建的线程正在运行中
12:03:46.727 [main] DEBUG com.concurrent.test.FileReader - read [test] start ...
12:03:46.729 [main] DEBUG com.concurrent.test.FileReader - read [test] end ... cost: 2 ms
12:03:46.730 [main] DEBUG com.concurrent.test.Test4 - 主线程
```

#### 小结

直接调用 `run()` 是在主线程中执行了 `run()`，没有启动新的线程 使用 `start()` 是启动新的线程，通过新的线程间接执行 `run()`方法 中的代码

#### 3.3.2 sleep 与 yield

**sleep**

1.  调用 sleep 会让当前线程从 Running 进入 Timed Waiting 状态（阻塞）
2.  其它线程可以使用 interrupt 方法打断正在睡眠的线程，那么被打断的线程这时就会抛出 `InterruptedException`异常【注意：这里打断的是正在休眠的线程，而不是其它状态的线程】
3.  睡眠结束后的线程未必会立刻得到执行(需要分配到cpu时间片)
4.  建议用 TimeUnit 的 `sleep()` 代替 Thread 的 `sleep()`来获得更好的可读性

**yield**

1.  调用 yield 会让当前线程从 Running 进入 Runnable 就绪状态，然后调度执行其它线程
2.  具体的实现依赖于操作系统的任务调度器(就是可能没有其它的线程正在执行，虽然调用了yield方法，但是也没有用)

#### 小结

yield使cpu调用其它线程，但是cpu可能会再分配时间片给该线程；而sleep需要等过了休眠时间之后才有可能被分配cpu时间片

### 3.3.3 线程优先级

线程优先级会提示（hint）调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它 如果 cpu 比较忙，那么优先级高的线程会获得更多的时间片，但 cpu 闲时，优先级几乎没作用

### 3.3.4 join

在主线程中调用t1.join，则主线程会等待t1线程执行完之后再继续执行 Test10.java

```java
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(1);
            log.debug("结束");
            r = 10;
        },"t1");
        t1.start();
        t1.join();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
```

![1583483843354](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAAAAACIM/FCAAACh0lEQVR4Ae3ch5W0OgyG4dt/mQJ2xgQPzJoM1m3AbALrxzrf28FzsoP0HykJEEAAAUQTBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEkKK0789+GK/I2ezfQB522PnS1qc8pGgXvr4tE4aY0XOUWlGImThWgyCk6DleixzE7qwBkg/MGiDPlVVAyp1VQGrPKiACDhFI6VkF5LmzCki+sg7IwDoglnVAil0IMkeG9CyUiwsxLFUVFzJJOQaKCjFCDN9RXMjIX7W6ztZXZDKKCyn8sWJvH+nca7WHDN9lROlAliPH9iRKCPI4cswFJQWxB46toLQgQ9jhn5QYZA9DOkoMUoQde5YapAxDWkoNYsOQR3KQd9CxUnIQF4S49CB9ENKlBxmDEKsFUgMCCCCAAHIrSF61f6153Ajy8nyiPr8L5MXnmm4CyT2fzN4DUvHZ+ntA2tOQBRBAAAEEEEAAAQQQ7ZBaC6TwSiDUaYHQ2yuB0MN+ft+43whyrs4rgVCjBUKTFshLC6TUAjGA3AxSaYFYLZBOC2RUAsk8h5qTg9QcbEoOsoQhQ2qQhsO5xCD5dgB5JQaZ+KBKGtKecvR81Ic0ZDjByKdDx0rSEDZ/djQbH+bkIdvfJFm98BfV8hD2zprfVdlu9PxVeyYAkciREohRAplJCaRSAplJCcQogTjSAdlyHRBvSAekJR0QRzogA+mADJkOiCPSAPEtqYBshlRAXC43hxix2QiOuEZkVERykGyNo9idIZKE0HO7XrG6OiMShlDWjstVzdPgXtUH9v0CEidAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQP4HgjZxTpdEii0AAAAASUVORK5CYII=)

### 3.3.5 interrupt 方法详解
#### 打断 sleep，wait，join 的线程

先了解一些interrupt()方法的相关知识：[博客地址](https://gitee.com/link?target=https%3A%2F%2Fwww.cnblogs.com%2Fnoteless%2Fp%2F10372826.html%230)

sleep，wait，join 的线程，这几个方法都会让线程进入阻塞状态，以 sleep 为例Test7.java

```java
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.debug("线程任务执行");
                try {
                    Thread.sleep(10000); // wait, join
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    log.debug("被打断");
                }
            }
        };
        t1.start();
        Thread.sleep(500);
        log.debug("111是否被打断？{}",t1.isInterrupted());
        t1.interrupt();
        log.debug("222是否被打断？{}",t1.isInterrupted());
        Thread.sleep(500);
        log.debug("222是否被打断？{}",t1.isInterrupted());
        log.debug("主线程");
    }
```

输出结果：（我下面将中断和打断两个词混用）可以看到，打断 sleep 的线程, 会清空中断状态，刚被中断完之后`t1.isInterrupted()`的值为`true`，后来变为`false`，即中断状态会被清除。那么线程是否被中断过可以通过异常来判断。【同时要注意如果打断被`join()`，`wait()` blocked的线程也是一样会被清除，被清除(interrupt status will be cleared)的意思即中断状态设置为`false`，被设置( interrupt status will be set)的意思就是中断状态设置为`true`】

```
17:06:11.890 [Thread-0] DEBUG com.concurrent.test.Test7 - 线程任务执行
17:06:12.387 [main] DEBUG com.concurrent.test.Test7 - 111是否被打断？false
17:06:12.390 [Thread-0] DEBUG com.concurrent.test.Test7 - 被打断
17:06:12.390 [main] DEBUG com.concurrent.test.Test7 - 222是否被打断？true
17:06:12.890 [main] DEBUG com.concurrent.test.Test7 - 222是否被打断？false
17:06:12.890 [main] DEBUG com.concurrent.test.Test7 - 主线程
```

#### 打断正常运行的线程

打断正常运行的线程, 线程并不会暂停，只是调用方法`Thread.currentThread().isInterrupted();`的返回值为true，可以判断`Thread.currentThread().isInterrupted();`的值来手动停止线程

```java
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted) {
                    log.debug("被打断了, 退出循环");
                    break;
                }
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
    }
```

#### 终止模式之两阶段终止模式

Two Phase Termination，就是考虑在一个线程T1中如何优雅地终止另一个线程T2？这里的优雅指的是给T2一个料理后事的机会（如释放锁）。

如下所示：那么线程的`isInterrupted()`方法可以取得线程的打断标记，如果线程在睡眠`sleep`期间被打断，打断标记是不会变的，为false，但是`sleep`期间被打断会抛出异常，我们据此手动设置打断标记为`true`；如果是在程序正常运行期间被打断的，那么打断标记就被自动设置为`true`。处理好这两种情况那我们就可以放心地来料理后事啦！

![1583496991915](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAAAAACIM/FCAAACh0lEQVR4Ae3ch5W0OgyG4dt/mQJ2xgQPzJoM1m3AbALrxzrf28FzsoP0HykJEEAAAUQTBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEkKK0789+GK/I2ezfQB522PnS1qc8pGgXvr4tE4aY0XOUWlGImThWgyCk6DleixzE7qwBkg/MGiDPlVVAyp1VQGrPKiACDhFI6VkF5LmzCki+sg7IwDoglnVAil0IMkeG9CyUiwsxLFUVFzJJOQaKCjFCDN9RXMjIX7W6ztZXZDKKCyn8sWJvH+nca7WHDN9lROlAliPH9iRKCPI4cswFJQWxB46toLQgQ9jhn5QYZA9DOkoMUoQde5YapAxDWkoNYsOQR3KQd9CxUnIQF4S49CB9ENKlBxmDEKsFUgMCCCCAAHIrSF61f6153Ajy8nyiPr8L5MXnmm4CyT2fzN4DUvHZ+ntA2tOQBRBAAAEEEEAAAQQQ7ZBaC6TwSiDUaYHQ2yuB0MN+ft+43whyrs4rgVCjBUKTFshLC6TUAjGA3AxSaYFYLZBOC2RUAsk8h5qTg9QcbEoOsoQhQ2qQhsO5xCD5dgB5JQaZ+KBKGtKecvR81Ic0ZDjByKdDx0rSEDZ/djQbH+bkIdvfJFm98BfV8hD2zprfVdlu9PxVeyYAkciREohRAplJCaRSAplJCcQogTjSAdlyHRBvSAekJR0QRzogA+mADJkOiCPSAPEtqYBshlRAXC43hxix2QiOuEZkVERykGyNo9idIZKE0HO7XrG6OiMShlDWjstVzdPgXtUH9v0CEidAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQP4HgjZxTpdEii0AAAAASUVORK5CYII=)

代码实现如下：

```java
@Slf4j
public class Test11 {
    public static void main(String[] args) throws InterruptedException {
        TwoParseTermination twoParseTermination = new TwoParseTermination();
        twoParseTermination.start();
        Thread.sleep(3000);  // 让监控线程执行一会儿
        twoParseTermination.stop(); // 停止监控线程
    }
}
@Slf4j
class TwoParseTermination{
    Thread thread ;
    public void start(){
        thread = new Thread(()->{
            while(true){
                if (Thread.currentThread().isInterrupted()){
                    log.debug("线程结束。。正在料理后事中");
                    break;
                }
                try {
                    Thread.sleep(500);
                    log.debug("正在执行监控的功能");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    public void stop(){
        thread.interrupt();
    }
}
```

### 3.3.6 sleep，yiled，wait，join 对比

关于join的原理和这几个方法的对比：[看这里](https://gitee.com/link?target=https%3A%2F%2Fblog.csdn.net%2Fdataiyangu%2Farticle%2Fdetails%2F104956755)

> 补充：
> 
> 1.  sleep，join，yield，interrupted是Thread类中的方法
> 2.  wait/notify是object中的方法
> 
> sleep 不释放锁、释放cpu join 释放锁、抢占cpu yiled 不释放锁、释放cpu wait 释放锁、释放cpu

## 3.4 守护线程

默认情况下，java进程需要等待所有的线程结束后才会停止，但是有一种特殊的线程，叫做守护线程，在其他线程全部结束的时候即使守护线程还未结束代码未执行完java进程也会停止。普通线程t1可以调用`t1.setDeamon(true);` 方法变成守护线程

> 注意 垃圾回收器线程就是一种守护线程 Tomcat 中的 Acceptor 和 Poller 线程都是守护线程，所以 Tomcat 接收到 shutdown 命令后，不会等 待它们处理完当前请求

## 3.5 线程状态之五种状态

五种状态的划分主要是从操作系统的层面进行划分的

![1583507073055](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAAAAACIM/FCAAACh0lEQVR4Ae3ch5W0OgyG4dt/mQJ2xgQPzJoM1m3AbALrxzrf28FzsoP0HykJEEAAAUQTBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEkKK0789+GK/I2ezfQB522PnS1qc8pGgXvr4tE4aY0XOUWlGImThWgyCk6DleixzE7qwBkg/MGiDPlVVAyp1VQGrPKiACDhFI6VkF5LmzCki+sg7IwDoglnVAil0IMkeG9CyUiwsxLFUVFzJJOQaKCjFCDN9RXMjIX7W6ztZXZDKKCyn8sWJvH+nca7WHDN9lROlAliPH9iRKCPI4cswFJQWxB46toLQgQ9jhn5QYZA9DOkoMUoQde5YapAxDWkoNYsOQR3KQd9CxUnIQF4S49CB9ENKlBxmDEKsFUgMCCCCAAHIrSF61f6153Ajy8nyiPr8L5MXnmm4CyT2fzN4DUvHZ+ntA2tOQBRBAAAEEEEAAAQQQ7ZBaC6TwSiDUaYHQ2yuB0MN+ft+43whyrs4rgVCjBUKTFshLC6TUAjGA3AxSaYFYLZBOC2RUAsk8h5qTg9QcbEoOsoQhQ2qQhsO5xCD5dgB5JQaZ+KBKGtKecvR81Ic0ZDjByKdDx0rSEDZ/djQbH+bkIdvfJFm98BfV8hD2zprfVdlu9PxVeyYAkciREohRAplJCaRSAplJCcQogTjSAdlyHRBvSAekJR0QRzogA+mADJkOiCPSAPEtqYBshlRAXC43hxix2QiOuEZkVERykGyNo9idIZKE0HO7XrG6OiMShlDWjstVzdPgXtUH9v0CEidAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQP4HgjZxTpdEii0AAAAASUVORK5CYII=)

1.  初始状态，仅仅是在语言层面上创建了线程对象，即`Thead thread = new Thead();`，还未与操作系统线程关联
2.  可运行状态，也称就绪状态，指该线程已经被创建，与操作系统相关联，等待cpu给它分配时间片就可运行
3.  运行状态，指线程获取了CPU时间片，正在运行
    1.  当CPU时间片用完，线程会转换至【可运行状态】，等待 CPU再次分配时间片，会导致我们前面讲到的上下文切换
4.  阻塞状态
    1.  如果调用了阻塞API，如BIO读写文件，那么线程实际上不会用到CPU，不会分配CPU时间片，会导致上下文切换，进入【阻塞状态】
    2.  等待BIO操作完毕，会由操作系统唤醒阻塞的线程，转换至【可运行状态】
    3.  与【可运行状态】的区别是，只要操作系统一直不唤醒线程，调度器就一直不会考虑调度它们，CPU就一直不会分配时间片
5.  终止状态，表示线程已经执行完毕，生命周期已经结束，不会再转换为其它状态

## 3.6 线程状态之六种状态

这是从 Java API 层面来描述的，我们主要研究的就是这种。状态转换详情图：[地址](https://gitee.com/link?target=https%3A%2F%2Fwww.jianshu.com%2Fp%2Fec94ed32895f) 根据 Thread.State 枚举，分为六种状态 Test12.java

![1583507709834](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAAAAACIM/FCAAACh0lEQVR4Ae3ch5W0OgyG4dt/mQJ2xgQPzJoM1m3AbALrxzrf28FzsoP0HykJEEAAAUQTBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEkKK0789+GK/I2ezfQB522PnS1qc8pGgXvr4tE4aY0XOUWlGImThWgyCk6DleixzE7qwBkg/MGiDPlVVAyp1VQGrPKiACDhFI6VkF5LmzCki+sg7IwDoglnVAil0IMkeG9CyUiwsxLFUVFzJJOQaKCjFCDN9RXMjIX7W6ztZXZDKKCyn8sWJvH+nca7WHDN9lROlAliPH9iRKCPI4cswFJQWxB46toLQgQ9jhn5QYZA9DOkoMUoQde5YapAxDWkoNYsOQR3KQd9CxUnIQF4S49CB9ENKlBxmDEKsFUgMCCCCAAHIrSF61f6153Ajy8nyiPr8L5MXnmm4CyT2fzN4DUvHZ+ntA2tOQBRBAAAEEEEAAAQQQ7ZBaC6TwSiDUaYHQ2yuB0MN+ft+43whyrs4rgVCjBUKTFshLC6TUAjGA3AxSaYFYLZBOC2RUAsk8h5qTg9QcbEoOsoQhQ2qQhsO5xCD5dgB5JQaZ+KBKGtKecvR81Ic0ZDjByKdDx0rSEDZ/djQbH+bkIdvfJFm98BfV8hD2zprfVdlu9PxVeyYAkciREohRAplJCaRSAplJCcQogTjSAdlyHRBvSAekJR0QRzogA+mADJkOiCPSAPEtqYBshlRAXC43hxix2QiOuEZkVERykGyNo9idIZKE0HO7XrG6OiMShlDWjstVzdPgXtUH9v0CEidAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQP4HgjZxTpdEii0AAAAASUVORK5CYII=)

1.  NEW 跟五种状态里的初始状态是一个意思
2.  RUNNABLE 是当调用了 `start()` 方法之后的状态，注意，Java API 层面的 `RUNNABLE` 状态涵盖了操作系统层面的【可运行状态】、【运行状态】和【io阻塞状态】（由于 BIO 导致的线程阻塞，在 Java 里无法区分，仍然认为是可运行）
3.  `BLOCKED` ， `WAITING` ， `TIMED_WAITING` 都是 Java API 层面对【阻塞状态】的细分，后面会在状态转换一节 详述