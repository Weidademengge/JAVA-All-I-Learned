package com.wddmg.concurrent.n4;

import lombok.extern.slf4j.Slf4j;

import static com.wddmg.concurrent.n2.util.Sleeper.sleep;

/**
 * @author duym
 * @version $ Id: TestWaitNotify, v 0.1 2023/04/06 11:24 duym Exp $
 */
@Slf4j
public class TestWaitNotify {
    final static Object obj = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (obj){
                log.debug("执行...");
                try {
                    // 让线程在obj上一直等待下去
                    obj.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                log.debug("其他代码...");
            }
        },"t1").start();
        new Thread(()->{
            synchronized (obj){
                log.debug("执行...");
                try {
                    // 让线程在obj上一直等待下去
                    obj.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                log.debug("其他代码...");
            }
        },"t2").start();

        sleep(2);
        log.debug("唤醒obj上其他线程");
        synchronized (obj){
            // 唤醒obj上一个线程
            obj.notify();
            // 唤醒obj上所有等待线程
//            obj.notifyAll();
        }
    }
}
