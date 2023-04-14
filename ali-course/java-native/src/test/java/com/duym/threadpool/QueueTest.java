package com.duym.threadpool;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

/**
 * @author duym
 * @version $ Id: QueueTest, v 0.1 2023/04/10 21:04 duym Exp $
 */
public class QueueTest {

    // 有界队列
    @Test
    public void arrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        for (int i = 0; i < 20; i++) {
            queue.put(i);
            System.out.println("队列中添加值"+i);
        }
    }

    // 无界队列
    @Test
    public void linkkedBlockingQueue() throws InterruptedException {
        LinkedBlockingDeque<Integer> queue = new LinkedBlockingDeque<>();
        for (int i = 0; i < 20; i++) {
            queue.put(i);
            System.out.println("队列中添加值"+i);
        }
    }

    // 同步移交
    @Test
    public void synchronousQueue() throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue();
        new Thread(()->{
            try {
                queue.put(1);
                System.out.println("插入成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                queue.take();
                System.out.println("删除成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000 * 60);
    }
}
