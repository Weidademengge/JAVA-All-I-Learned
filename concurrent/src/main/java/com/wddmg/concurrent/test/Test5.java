package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test5, v 0.1 2023/04/04 8:53 duym Exp $
 */
@Slf4j
public class Test5 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("t1");
            }
        };
        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState());
        t1.run();
    }
}
