## 类简介

-   最典型的**有界队列**，其内部是用数组存储元素的，利用 ReentrantLock 实现线程安全。

---

## 构造函数

```java
ArrayBlockingQueue(int capacity, boolean fair)
```

-   第一个参数是容量：我们在创建它的时候就需要指定它的容量，之后也不可以再扩容了
-   第二个参数是是否公平：正如 ReentrantLock 一样，如果 ArrayBlockingQueue 被设置为非公平的，那么就存在插队的可能；如果设置为公平的，那么等待了最长时间的线程会被优先处理，其他线程不允许插队，不过这样的公平策略同时会带来一定的性能损耗，因为非公平的吞吐量通常会高于公平的情况。

---

## 源码分析

### 重要属性

```java
/** 用于存放元素的数组 */  
final Object[] items;  
  
/** 下一次读取操作的位置 */  
int takeIndex;  
  
/** 下一次写入操作的位置 */  
int putIndex;  
  
/** 队列中的元素数量 */  
int count;  
  
/*  
 * Concurrency control uses the classic two-condition algorithm * found in any textbook. */  
/** Main lock guarding all access */  
final ReentrantLock lock;  
  
/** Condition for waiting takes */  
private final Condition notEmpty;  
  
/** Condition for waiting puts */  
private final Condition notFull;
```

最后三个变量非常关键，第一个就是一个 ReentrantLock，而下面两个 Condition 分别是由 ReentrantLock 产生出来的，这三个变量就是我们实现线程安全最核心的工具。

---

### 并发安全原理

ArrayBlockingQueue 实现并发同步的原理就是利用 ReentrantLock 和它的两个 Condition，读操作和写操作都需要先获取到 ReentrantLock 独占锁才能进行下一步操作。进行读操作时如果队列为空，线程就会进入到读线程专属的 notEmpty 的 Condition 的队列中去排队，等待写线程写入新的元素；同理，如果队列已满，这个时候写操作的线程会进入到写线程专属的 notFull 队列中去排队，等待读线程将队列元素移除并腾出空间。

---

### put()

```java
public void put(E e) throws InterruptedException {
    checkNotNull(e);
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
        while (count == items.length)
            notFull.await();
        enqueue(e);
    } finally {
        lock.unlock();
    }
}
```

在 put 方法中，首先用 checkNotNull 方法去检查插入的元素是不是 null。如果不是 null，我们会用 ReentrantLock 上锁，并且上锁方法是 lock.lockInterruptibly()。这个方法我们在锁接口中介绍过，在获取锁的同时是可以响应中断的，这也正是我们的阻塞队列在调用 put 方法时，在尝试获取锁但还没拿到锁的期间可以响应中断的底层原因。

紧接着 ，是一个非常经典的 try-finally 代码块，finally 中会去解锁，try 中会有一个 while 循环，它会检查当前队列是不是已经满了，也就是 count 是否等于数组的长度。如果等于就代表已经满了，于是我们便会进行等待，直到有空余的时候，我们才会执行下一步操作，调用 enqueue 方法让元素进入队列，最后用 unlock 方法解锁。