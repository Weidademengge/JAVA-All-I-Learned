package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import static com.wddmg.concurrent.n2.util.Sleeper.sleep;

/**
 * @author duym
 * @version $ Id: Test11, v 0.1 2023/04/04 10:29 duym Exp $
 */
@Slf4j
public class Test11 {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            log.debug("sleep...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        t1.start();
        sleep(1);
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记：{}",t1.isInterrupted());
    }
}
