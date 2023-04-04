package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/03 10:09 duym Exp $
 */
@Slf4j
public class Test2 {

    public static void main(String[] args) {

        Runnable r = () -> log.debug("running");

        Thread t = new Thread(r,"t2");
        t.start();
        log.debug("main");
    }
}
