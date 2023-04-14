## 接口简介

Lock 接口是 Java 5 引入的，最常见的实现类是 ReentrantLock，可以起到“锁”的作用。

Lock 和 synchronized 是两种最常见的锁，锁是一种工具，用于控制对共享资源的访问，而  Lock 和 synchronized 都可以达到线程安全的目的，但是在使用上和功能上又有较大的不同。所以 Lock 并不是用来代替 synchronized 的，而是当使用 synchronized 不合适或不足以满足要求的时候，Lock 可以用来提供更高级功能的。

通常情况下，Lock 只允许一个线程来访问这个共享资源。不过有的时候，一些特殊的实现也可允许并发访问，比如 ReadWriteLock 里面的 ReadLock。

---

## 方法纵览

```java
public interface Lock {
    void lock();
    void lockInterruptibly() throws InterruptedException;
    boolean tryLock();
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
    void unlock();
    Condition newCondition();
}
```

---

### lock() 方法

lock() 方法是最基础的获取锁的方法。在线程获取锁时如果锁已被其他线程获取，则进行等待，是最初级的获取锁的方法。

```java
Lock lock = new ReentrantLock();
lock.lock();
try{
    //获取到了被本锁保护的资源，处理任务
    //捕获异常
}finally{
    lock.unlock();   //释放锁
}
```

---

### tryLock() 方法

tryLock() 方法用来尝试获取锁，如果当前锁没有被其他线程占用，则获取成功，返回 true，否则返回 false，代表获取锁失败。相比于 lock()，这样的方法显然功能更强大，我们可以根据是否能获取到锁来决定后续程序的行为。

```java
Lock lock = new ReentrantLock();
if(lock.tryLock()) {
    try{
        //处理任务
    }finally{
        lock.unlock();   //释放锁
    } 
}else {
    //如果不能获取锁，则做其他事情
}
```

---

### tryLock(long time, TimeUnit unit) 方法

这个方法解决了 lock() 方法容易发生死锁的问题，使用 tryLock(long time, TimeUnit unit) 时，在等待了一段指定的超时时间后，线程会主动放弃这把锁的获取，避免永久等待；在等待的期间，也可以随时中断线程，这就避免了死锁的发生。

---

### lockInterruptibly() 方法

lockInterruptibly() 方法是可以响应中断的。相比于不能响应中断的 synchronized 锁，lockInterruptibly() 可以让程序更灵活，可以在获取锁的同时，保持对中断的响应。

```java
public void lockInterruptibly() {
    try {
        lock.lockInterruptibly();
        try {
            System.out.println("操作资源");
        } finally {
            lock.unlock();
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

---

### unlock() 方法

unlock() 方法是用于解锁的，方法比较简单，对于 ReentrantLock 而言，执行 unlock() 的时候，内部会把锁的“被持有计数器”减 1，直到减到 0 就代表当前这把锁已经完全释放了，如果减 1 后计数器不为 0，说明这把锁之前被“重入”了，那么锁并没有真正释放，仅仅是减少了持有的次数。