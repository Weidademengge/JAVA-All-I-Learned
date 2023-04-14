package com.wddmg.concurrent.n4;

import com.wddmg.concurrent.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: TestMultiLock, v 0.1 2023/04/07 9:51 duym Exp $
 */
public class TestMultiLock {

    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(()->{
            bigRoom.study();
        },"小南").start();

        new Thread(()->{
            bigRoom.sleep();
        },"小女").start();
    }
}

@Slf4j
class BigRoom{

    private final Object studyRoom = new Object();
    private final Object sleepRoom = new Object();


    public void sleep(){
        synchronized (sleepRoom){
            log.debug("sleep 2小时");
            Sleeper.sleep(2);
        }
    }

    public void study(){
        synchronized (studyRoom){
            log.debug("学习 1小时");
            Sleeper.sleep(1);
        }
    }
}
