package com.wddmg.concurrent;

import com.wddmg.concurrent.blockingqueue.MyArrayQueue;
import com.wddmg.concurrent.blockingqueue.MyConditionLinkedQueue;
import org.junit.Test;

/**
 * @author duym
 * @version $ Id: MyconditionLinkedQueueTest, v 0.1 2023/03/10 17:37 duym Exp $
 */
public class MyConditionLinkedQueueTest {
    @Test
    public void p2c() throws InterruptedException {
        MyConditionLinkedQueue queue = new MyConditionLinkedQueue();
        for(int i = 0;i< 5;i++){
            new Thread(()->{
                int index = 0;
                while(true){
                    String tmp = Thread.currentThread().getName() +"生产数据" + index++;
                    try {
                        queue.put(tmp);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(tmp);
                    try {
                        Thread.sleep((long)(Math.random()*1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

            new Thread (()->{
                while(true){
                    Object take = null;
                    try {
                        take = queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t\t消费数据" +take);
                    try {
                        Thread.sleep((long)(Math.random()*1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        Thread.sleep(5000);
        System.exit(0);
    }
}
