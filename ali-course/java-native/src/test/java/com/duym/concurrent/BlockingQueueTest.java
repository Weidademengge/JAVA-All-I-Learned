package com.duym.concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author duym
 * @version $ Id: BlockingQueueTest, v 0.1 2023/04/11 15:45 duym Exp $
 */
public class BlockingQueueTest {
    private final Random random = new Random();

    @Test
    public void p2c() throws InterruptedException {

        BlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);

        new Thread(() -> {
            int index = 0;
            while (true) {
                String tmp = "生产数据：" + index;
                try {
                    queue.put(tmp);
                    System.out.println(tmp);
                    index++;
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Object take = queue.take();
                    System.out.println("\t\t消费的数据：" + take);
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        Thread.sleep(10000);
        // 进程结束
        System.exit(0);
    }
}
