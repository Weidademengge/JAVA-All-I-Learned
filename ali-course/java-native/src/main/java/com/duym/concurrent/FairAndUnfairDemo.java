package com.duym.concurrent;
  
import java.util.concurrent.locks.Lock;  
import java.util.concurrent.locks.ReentrantLock;  
  
/**  
 * 描述：演示公平锁，分别展示公平和不公平的情况，非公平锁会让现在持有锁的线程优先再次获取到锁。代码借鉴自Java并发编程实战手册2.7。  
 */  
public class FairAndUnfairDemo {  
    public static void main(String args[]) {  
        PrintQueue printQueue = new PrintQueue();  
        Thread thread[] = new Thread[10];  
        for (int i = 0; i < 10; i++) {  
            thread[i] = new Thread(new Job(printQueue), "Thread " + i);  
        }  
        for (int i = 0; i < 10; i++) {  
            thread[i].start();  
            try {  
                Thread.sleep(100);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}  
  
class Job implements Runnable {  
    private PrintQueue printQueue;  
  
    public Job(PrintQueue printQueue) {  
        this.printQueue = printQueue;  
    }  
  
    @Override  
    public void run() {  
        System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName());  
        printQueue.printJob(new Object());  
        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());  
    }  
}  
  
class PrintQueue {  
    private final Lock queueLock = new ReentrantLock(false);  
  
    public void printJob(Object document) {  
        queueLock.lock();  
        try {  
            Long duration = (long)(Math.random() * 10000);  
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",  
                Thread.currentThread().getName(), (duration / 1000));  
            Thread.sleep(duration);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } finally {  
            queueLock.unlock();  
        }  
        queueLock.lock();  
        try {  
            Long duration = (long)(Math.random() * 10000);  
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",  
                Thread.currentThread().getName(), (duration / 1000));  
            Thread.sleep(duration);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } finally {  
            queueLock.unlock();  
        }  
    }  
}