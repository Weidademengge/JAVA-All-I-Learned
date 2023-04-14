# Condition

---

## 接口简介

我们假设线程 1 需要等待某些条件满足后，才能继续运行，这个条件会根据业务场景不同，有不同的可能性，比如等待某个时间点到达或者等待某些任务处理完毕。在这种情况下，我们就可以执行 Condition 的 await 方法，一旦执行了该方法，这个线程就会进入 WAITING 状态。

通常会有另外一个线程，我们把它称作线程 2，它去达成对应的条件，直到这个条件达成之后，那么，线程 2 调用 Condition 的 signal 方法（或 signalAll 方法），代表“**这个条件已经达成了，之前等待这个条件的线程现在可以苏醒了**”。这个时候，JVM 就会找到等待该 Condition 的线程，并予以唤醒，根据调用的是 signal 方法或 signalAll 方法，会唤醒 1 个或所有的线程。于是，线程 1 在此时就会被唤醒，然后它的线程状态又会回到 Runnable 可执行状态。

---

## 示例代码

```java
public class ConditionDemo {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    void method1() throws InterruptedException {
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+":条件不满足，开始await");
            condition.await();
            System.out.println(Thread.currentThread().getName()+":条件满足了，开始执行后续的任务");
        }finally {
            lock.unlock();
        }
    }
    void method2() throws InterruptedException {
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+":需要5秒钟的准备时间");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName()+":准备工作完成，唤醒其他的线程");
            condition.signal();
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ConditionDemo conditionDemo = new ConditionDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    conditionDemo.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        conditionDemo.method1();
    }
}
```

在这个代码中，有以下三个方法。

-   **method1**，它代表主线程将要执行的内容，首先获取到锁，打印出“条件不满足，开始 await”，然后调用 condition.await() 方法，直到条件满足之后，则代表这个语句可以继续向下执行了，于是打印出“条件满足了，开始执行后续的任务”，最后会在 finally 中解锁。
-   **method2**，它同样也需要先获得锁，然后打印出“需要 5 秒钟的准备时间”，接着用 sleep 来模拟准备时间；在时间到了之后，则打印出“准备工作完成”，最后调用 condition.signal() 方法，把之前已经等待的线程唤醒。
-   **main 方法**，它的主要作用是执行上面这两个方法，它先去实例化我们这个类，然后再用子线程去调用这个类的 method2 方法，接着用主线程去调用 method1 方法。

---

### 执行结果

```
main:条件不满足，开始 await
Thread-0:需要 5 秒钟的准备时间
Thread-0:准备工作完成，唤醒其他的线程
main:条件满足了，开始执行后续的任务
```

---

### 注意点

-   **线程 2 解锁后，线程 1 才能获得锁并继续执行**

线程 2 对应刚才代码中的子线程，而线程 1 对应主线程。这里需要额外注意，并不是说子线程调用了 signal 之后，主线程就可以立刻被唤醒去执行下面的代码了，而是说在调用了 signal 之后，还需要等待子线程完全退出这个锁，即执行 unlock 之后，这个主线程才有可能去获取到这把锁，并且当获取锁成功之后才能继续执行后面的任务。刚被唤醒的时候主线程还没有拿到锁，是没有办法继续往下执行的。

-   **signalAll() 和 signal() 区别**

signalAll() 会唤醒所有正在等待的线程，而 signal() 只会唤醒一个线程。

---

## Condition 和 Object 的关系

实际上，如果说 Lock 是用来代替 synchronized 的，那么 Condition 就是用来代替相对应的 Object 的 wait/notify/notifyAll，所以在用法和性质上几乎都一样。

Condition 把 Object 的 wait/notify/notifyAll 转化为了一种相应的对象，其实现的效果基本一样，但是把更复杂的用法，变成了更直观可控的对象方法，是一种升级。

await 方法会自动释放持有的 Lock 锁，和 Object 的 wait 一样，不需要自己手动释放锁。

另外，调用 await 的时候必须持有锁，否则会抛出异常，这一点和 Object 的 wait 一样。