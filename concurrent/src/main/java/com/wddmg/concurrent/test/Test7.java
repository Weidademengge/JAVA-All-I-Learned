package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test7, v 0.1 2023/04/04 9:30 duym Exp $
 */
@Slf4j
public class Test7 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("enter sleep...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt();
    }
}
