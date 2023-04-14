package com.wddmg.concurrent.n3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: TestState, v 0.1 2023/04/04 13:29 duym Exp $
 */
@Slf4j
public class TestState {
    public static void main(String[] args) {
        // 没start，只是创建对象，所以为new
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        // 一直在运行，runnable
        Thread t2 = new Thread("t2"){
            @Override
            public void run() {
                while(true){

                }
            }
        };
        t2.start();

        // 一下就运行完了，TERMINATED
        Thread t3 = new Thread("t3"){
            @Override
            public void run() {
                log.debug("running...");
            }
        };
        t3.start();

        // TIMED_WAITING 有时限的等待，等待超时或者收到通知就可以执行
        Thread t4 = new Thread("t4"){
            @Override
            public void run() {
                synchronized (TestState.class){
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t4.start();
        //WAITING 等待其他线程的通知，wait、join、park
        Thread t5 = new Thread("t5"){
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t5.start();
        // BLOCKED 等待锁
        Thread t6 = new Thread("t6"){
            @Override
            public void run() {
                synchronized (TestState.class){
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t6.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state {}",t1.getState());
        log.debug("t2 state {}",t2.getState());
        log.debug("t3 state {}",t3.getState());
        log.debug("t4 state {}",t4.getState());
        log.debug("t5 state {}",t5.getState());
        log.debug("t6 state {}",t6.getState());
    }
}
