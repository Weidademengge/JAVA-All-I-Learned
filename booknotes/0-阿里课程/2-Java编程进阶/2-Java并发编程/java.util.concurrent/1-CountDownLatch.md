# CountDownLatch

---

## 类简介

**CountDownLatch** 的核心思想是，等到一个设定的数值达到之后才能开始，即倒数计时器。

---

### 原理

![](https://cdn.nlark.com/yuque/0/2020/png/125693/1608876005102-fb2377ef-f79f-4b1d-b4fb-cd2daf44d8f7.png)

可以看到，最开始 CountDownLatch 设置的初始值为 3，然后 T0 线程上来就调用 await 方法，它的作用是让这个线程开始等待，等待后面的 T1、T2、T3，它们每一次调用 countDown 方法，3 这个数值就会减 1，也就是从 3 减到 2，从 2 减到 1，从 1 减到 0，一旦减到 0 之后，这个 T0 就相当于达到了自己触发继续运行的条件，于是它就恢复运行了。

---

## 主要方法

### 构造函数

`CountDownLatch(int count)` 它的构造函数是传入一个参数，该参数 count 是需要倒数的数值。

---

### await()

调用 await() 方法的线程开始等待，直到倒数结束，也就是 count 值为 0 的时候才会继续执行。

**await(long timeout, TimeUnit unit)**：await() 有一个重载的方法，里面会传入超时参数，这个方法的作用和 await() 类似，但是这里可以设置超时时间，如果超时就不再等待了。

---

### countDown()

把数值倒数 1，也就是将 count 值减 1，直到减为 0 时，之前等待的线程会被唤起。

---

## 典型用法

-   用法一：一个线程等待其他多个线程都执行完毕，再继续自己的工作
-   用法二：多个线程等待某一个线程的信号，同时开始执行

---

## 示例代码

### 用法一

```java
public class RunDemo1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println(no + " 执行完毕。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            };
            service.submit(runnable);
        }
        System.out.println("等待5个线程都执行完毕.....");
        latch.await();
        System.out.println("等待其他多个线程都执行完毕，再继续自己的工作。");
    }
}
```

---

### 用法二

```java
public class RunDemo2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("每个线程有5秒的准备时间：");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println(no + "准备完毕，");
                    try {
                        countDownLatch.await();
                        System.out.println(no + "开始执行。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.submit(runnable);
        }
        Thread.sleep(5000);
        System.out.println("多个线程等待某一个线程的信号，同时开始执行！");
        countDownLatch.countDown();
    }
}
```