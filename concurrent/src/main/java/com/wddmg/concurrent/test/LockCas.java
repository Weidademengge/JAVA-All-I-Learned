package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.wddmg.concurrent.n2.util.Sleeper.sleep;

/**
 * @author duym
 * @version $ Id: LockCas, v 0.1 2023/04/12 11:06 duym Exp $
 */
@Slf4j
public class LockCas {
    // 0 没加锁
    // 1 加了锁
    private AtomicInteger state = new AtomicInteger(0);

    public static void main(String[] args) {
        LockCas lock = new LockCas();
        new Thread(()->{
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
                sleep(1);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(()->{
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
            } finally {
                lock.unlock();
            }
        }).start();
    }

    public void lock(){
        while(true){
            if(state.compareAndSet(0,1)){
                break;
            }
        }
    }

    public void unlock(){
        log.debug("unlock");
        state.set(0);
    }

}
