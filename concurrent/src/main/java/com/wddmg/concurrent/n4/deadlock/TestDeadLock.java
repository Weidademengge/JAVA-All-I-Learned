package com.wddmg.concurrent.n4.deadlock;

import lombok.extern.slf4j.Slf4j;

import static com.wddmg.concurrent.n2.util.Sleeper.sleep;

/**
 * @author duym
 * @version $ Id: TestDeadLock, v 0.1 2023/04/07 10:02 duym Exp $
 */
@Slf4j
public class TestDeadLock {
    public static void main(String[] args) {
        test1();
    }

    private static void test1(){
        Object A = new Object();
        Object B = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (A) {
                log.debug("lock A");
                sleep(1);
                synchronized (B) {
                    log.debug("lock B");
                    log.debug("操作...");
                }
            }
        }, "t1");

        Thread t2 = new Thread(()->{
            synchronized (B) {
                log.debug("lock B");
                sleep(0.5);
                synchronized (A) {
                    log.debug("lock A");
                    log.debug("操作...");
                }
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
