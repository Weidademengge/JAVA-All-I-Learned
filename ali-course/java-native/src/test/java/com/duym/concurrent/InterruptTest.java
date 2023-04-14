package com.duym.concurrent;

import org.junit.Test;

/**
 * @author duym
 * @version $ Id: InterruptTest, v 0.1 2023/04/11 14:55 duym Exp $
 */
public class InterruptTest {

    @Test
    public void interrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                System.out.println("中断状态：" + interrupted);
            }
        });

        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        Thread.sleep(100);

        System.exit(0);
    }

    @Test
    public void interruptAndStop() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                System.out.println("中断状态：" + interrupted);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        Thread.sleep(100);

        System.exit(0);
    }
}
