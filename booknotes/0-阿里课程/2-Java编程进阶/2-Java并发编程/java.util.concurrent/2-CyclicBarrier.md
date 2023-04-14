# CyclicBarrier

---

## 类简介

**CyclicBarrier** 的作用就是它可以构造出一个集结点，当某一个线程执行 await() 的时候，它就会到这个集结点开始等待，等待这个栅栏被撤销。直到预定数量的线程都到了这个集结点之后，这个栅栏就会被撤销，之前等待的线程就在此刻统一出发，继续去执行剩下的任务。

---

## 典型用法

```java
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        for (int i = 0; i < 8; i++) {
            new Thread(new Task(i + 1, cyclicBarrier)).start();
        }
    }
    static class Task implements Runnable {
        private int id;
        private CyclicBarrier cyclicBarrier;
        public Task(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            System.out.println("同学 " + id + " 现在从大门出发，前往停车场");
            try {
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("同学 " + id + " 到了停车场，开始等待其他人到达");
                cyclicBarrier.await();
                System.out.println("老司机 " + id + " 开始发车!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
```

---

## 执行动作

CyclicBarrier 还有一个构造函数是传入两个参数的，第一个参数依然是 parties，代表需要几个线程到齐；第二个参数是一个 Runnable 对象，它就是我们下面所要介绍的 barrierAction：`CyclicBarrier(int parties, Runnable barrierAction)`

```java
CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
    @Override
    public void run() {
        System.out.println("凑齐4人了，开始发车!");
    }
});
```

---

## CyclicBarrier 和 CountDownLatch 的异同

CyclicBarrier 和 CountDownLatch 都能阻塞一个或一组线程，直到某个预设的条件达成发生，再统一出发。

但是它们也有很多不同点，具体如下：

-   **作用对象不同**：CyclicBarrier 要等固定数量的线程都到达了栅栏位置才能继续执行，而 CountDownLatch 只需等待数字倒数到 0，也就是说 CountDownLatch 作用于事件，但 CyclicBarrier 作用于线程；CountDownLatch 是在调用了 countDown 方法之后把数字倒数减 1，而 CyclicBarrier 是在某线程开始等待后把计数减 1。
-   **可重用性不同**：CountDownLatch 在倒数到 0  并且触发门闩打开后，就不能再次使用了，除非新建一个新的实例；而 CyclicBarrier 可以重复使用，在刚才的代码中也可以看出，每 3 个同学到了之后都能出发，并不需要重新新建实例。CyclicBarrier 还可以随时调用 reset 方法进行重置，如果重置时有线程已经调用了 await 方法并开始等待，那么这些线程则会抛出 BrokenBarrierException 异常。
-   **执行动作不同**：CyclicBarrier 有执行动作 barrierAction，而 CountDownLatch 没这个功能。