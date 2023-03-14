package com.wddmg.concurrent;

import com.wddmg.concurrent.blockingqueue.MyArrayQueue;
import org.junit.Test;

/**
 * @author duym
 * @version $ Id: MyArrayQueueTest, v 0.1 2023/03/10 16:36 duym Exp $
 */
public class MyArrayQueueTest {

    @Test
    public void p2c() throws InterruptedException {
        MyArrayQueue queue = new MyArrayQueue();
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

                }
            }).start();

            new Thread (()->{
                while(true){
                    String take = null;
                    try {
                        take = queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t\t消费数据" +take);
                }
            }).start();
        }


        Thread.sleep(10);
        System.exit(0);
    }
}
