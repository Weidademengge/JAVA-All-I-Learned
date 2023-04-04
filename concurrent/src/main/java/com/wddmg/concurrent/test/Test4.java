package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test4, v 0.1 2023/04/04 8:43 duym Exp $
 */
@Slf4j
public class Test4 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        t1.start();
    }
}
