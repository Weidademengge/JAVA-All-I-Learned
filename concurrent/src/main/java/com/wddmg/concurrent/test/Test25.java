package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test25, v 0.1 2023/04/07 13:42 duym Exp $
 */
@Slf4j
public class Test25 {

    static final  Object lock = new Object();

    // 表示t2是否运行过
    static boolean t2runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock){
                while(!t2runned){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock){
                log.debug("2");
                t2runned = true;
                lock.notify();
            }
        }, "t2");

        t1.start();;
        t2.start();

    }
}
