package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author duym
 * @version $ Id: Test10, v 0.1 2023/04/04 10:03 duym Exp $
 */
@Slf4j
public class Test10 {
    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
        }, "t1");
        t1.start();
        t1.join();
        log.debug("结果为:{}",r);
        log.debug("结束");
    }
}
