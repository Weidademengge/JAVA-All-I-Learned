package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test6, v 0.1 2023/04/04 9:01 duym Exp $
 */
@Slf4j
public class Test6 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        log.debug("t1 state:{}",t1.getState());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state:{}",t1.getState());
    }
}
