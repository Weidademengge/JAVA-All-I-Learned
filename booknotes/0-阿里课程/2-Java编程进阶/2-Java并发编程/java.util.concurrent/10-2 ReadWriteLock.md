## 接口简介

在没有读写锁之前，我们假设使用普通的 ReentrantLock，那么虽然我们保证了线程安全，但是也浪费了一定的资源，因为如果多个读操作同时进行，其实并没有线程安全问题，我们可以允许让多个读操作并行，以便提高程序效率。

读写锁 ReadWriteLock 设定了一套规则，既可以保证多个线程同时读的效率，同时又可以保证有写入操作时的线程安全。

---

## 方法纵览

整体思路是它有两把锁，第 1 把锁是写锁，第 2 把锁是读锁。

### writeLock()

-   获得写锁之后，既可以读数据又可以修改数据

---

### readLock()

-   获得读锁之后，只能查看数据，不能修改数据。读锁可以被多个线程同时持有，所以多个线程可以同时查看数据。

---

## 获取规则

-   读读共享
-   其他都互斥（写写互斥、读写互斥、写读互斥）

---

## 适用场合

适用于读多写少的情况，合理使用可以进一步提高并发效率。

---

## 使用方法

``` java
/**
* 描述：演示读写锁用法
*/
public class ReadWriteLockDemo {
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    private static final ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到读锁，正在读取");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }
    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到写锁，正在写入");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> read()).start();
        new Thread(() -> read()).start();
        new Thread(() -> write()).start();
        new Thread(() -> write()).start();
    }
}
```

---

### 运行结果

```
Thread-0得到读锁，正在读取
Thread-1得到读锁，正在读取
Thread-0释放读锁
Thread-1释放读锁
Thread-2得到写锁，正在写入
Thread-2释放写锁
Thread-3得到写锁，正在写入
Thread-3释放写锁
```

可以看出，读锁可以同时被多个线程获得，而写锁不能。