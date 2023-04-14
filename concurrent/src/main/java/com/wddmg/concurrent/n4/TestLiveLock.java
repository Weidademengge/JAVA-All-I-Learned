package com.wddmg.concurrent.n4;

import lombok.extern.slf4j.Slf4j;

import static com.wddmg.concurrent.n2.util.Sleeper.sleep;

/**
 * @author duym
 * @version $ Id: TestLiveLock, v 0.1 2023/04/07 10:26 duym Exp $
 */
@Slf4j
public class TestLiveLock {

    static volatile int count = 10;
    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            // 期望见到0退出循环
            while (count > 0) {
                sleep(0.2);
                count--;
                log.debug("count:{}", count);
            }
        }, "t1").start();

        new Thread(() -> {
            // 期望见到0退出循环
            while (count < 20) {
                sleep(0.2);
                count++;
                log.debug("count:{}", count);
            }
        }, "t2").start();
    }
}
