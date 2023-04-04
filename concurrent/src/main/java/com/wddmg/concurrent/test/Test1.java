package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/03 10:07 duym Exp $
 */
@Slf4j
public class Test1 {

    public static void main(String[] args) {


        Thread t = new Thread("aaa"){
            @Override
            public void run() {
                log.debug("hello");
            }
        };
        t.start();

        log.debug("world");
    }
}
