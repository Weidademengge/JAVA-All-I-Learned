package com.duym.concurrent;
  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.Semaphore;  
  
public class SemaphoreDemo {  
  
    static Semaphore semaphore = new Semaphore(3);  
    public static void main(String[] args) {  
        ExecutorService service = Executors.newFixedThreadPool(50);  
        for (int i = 0; i < 1000; i++) {  
            service.submit(new Task2());  
        }  
        service.shutdown();  
    }  
    static class Task1 implements Runnable {  
        @Override  
        public void run() {  
            System.out.println(Thread.currentThread().getName() + "调用了慢服务");  
            try {  
                //模拟慢服务  
                Thread.sleep(3000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    static class Task2 implements Runnable {  
        @Override  
        public void run() {  
            try {  
                semaphore.acquire();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            System.out.println(Thread.currentThread().getName() + "拿到了许可证，花费2秒执行慢服务");  
            try {  
                Thread.sleep(2000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            System.out.println("慢服务执行完毕，" + Thread.currentThread().getName() + "释放了许可证");  
            semaphore.release();  
        }  
    }  
}