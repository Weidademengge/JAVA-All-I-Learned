package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.wddmg.concurrent.n2.util.Sleeper.sleep;

/**
 * @author duym
 * @version $ Id: Test22, v 0.1 2023/04/07 10:56 duym Exp $
 */
@Slf4j
public class Test22 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁");
            try {
                if(!lock.tryLock(1,TimeUnit.SECONDS)){
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("获取不到锁");
                return;
            }
            try{
                log.debug("获得到锁");
            }finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        log.debug("获得到锁");
        t1.start();
        sleep(1);
        log.debug("释放了锁");
        lock.unlock();
    }

}
