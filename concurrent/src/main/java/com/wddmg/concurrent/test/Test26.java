package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author duym
 * @version $ Id: Test26, v 0.1 2023/04/07 13:47 duym Exp $
 */
@Slf4j
public class Test26 {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);
        }, "t2");
        t1.start();
        t2.start();
    }
}
