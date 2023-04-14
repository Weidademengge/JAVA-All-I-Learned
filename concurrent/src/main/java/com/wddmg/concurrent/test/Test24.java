package com.wddmg.concurrent.test;

import com.wddmg.concurrent.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/04/07 13:24 duym Exp $
 */
@Slf4j
public class Test24 {
    static ReentrantLock ROOM = new ReentrantLock();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    static Condition waitCigaretteSet = ROOM.newCondition();
    static Condition waitTakeoutSet = ROOM.newCondition();

    public static void main(String[] args) {
        new Thread(()->{
            ROOM.lock();
            try {
                log.debug("有烟没？[{}]",hasCigarette);
                while(!hasCigarette){
                    try {
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("可以开始干活了");
            }finally {
                ROOM.unlock();
            }
        },"小南").start();

        new Thread(()->{
            ROOM.lock();
            try {
                log.debug("外卖送到没？[{}]",hasTakeout);
                while(!hasTakeout){
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("可以开始干活了");
            }finally {
                ROOM.unlock();
            }
        },"小女").start();

        Sleeper.sleep(1);
        new Thread(()->{
            ROOM.lock();
            try {
                hasTakeout = true;
                waitTakeoutSet.signal();
            }finally {
                ROOM.unlock();
            }
        },"送外卖的").start();
        Sleeper.sleep(1);
        new Thread(()->{
            ROOM.lock();
            try {
                hasCigarette = true;
                waitCigaretteSet.signal();
            }finally {
                ROOM.unlock();
            }
        },"送烟的").start();

    }
}
