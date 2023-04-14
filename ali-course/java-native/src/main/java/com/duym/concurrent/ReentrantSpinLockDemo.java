package com.duym.concurrent;
  
import java.util.concurrent.atomic.AtomicReference;  
  
public class ReentrantSpinLockDemo {  
    private AtomicReference<Thread> owner = new AtomicReference<>();  
  
    //重入次数  
    private int count = 0;  
  
    public void lock() {  
        Thread thread = Thread.currentThread();  
        if (thread == owner.get()) {  
            ++ count;  
            System.out.println(thread.getName() + "重入次数:" + count);  
            return;  
        }  
        // 自旋获取锁  
        while (!owner.compareAndSet(null, thread)) {  
            System.out.println(thread.getName() + "自旋了");  
        }  
    }  
  
    public void unlock() {  
        Thread thread = Thread.currentThread();  
        // 只有持有锁的线程才能解锁  
        if (thread == owner.get()) {  
            if (count > 0) {  
                -- count;  
                System.out.println(thread.getName() + "重入次数:" + count);  
            } else {  
                owner.set(null);  
            }  
        }  
    }  
  
    public static void main(String[] args) {  
        ReentrantSpinLockDemo spinLock = new ReentrantSpinLockDemo();  
        Runnable runnable = () -> {  
            System.out.println(Thread.currentThread().getName() + "开始尝试获取自旋锁");  
            spinLock.lock();  
            System.out.println(Thread.currentThread().getName() + "获取成功");  
            try {  
                Thread.sleep(100);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            } finally {  
                spinLock.unlock();  
                System.out.println(Thread.currentThread().getName() + "释放成功");  
            }  
        };  
  
        new Thread(runnable).start();  
        new Thread(runnable).start();  
    }  
}