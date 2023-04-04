package com.wddmg.concurrent.n3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: TestMultiThread, v 0.1 2023/04/03 10:22 duym Exp $
 */
@Slf4j
public class TestMultiThread {

    public static void main(String[] args) {
        new Thread(()->{
            while(true){
                log.debug("running");
            }
        },"t1").start();
        new Thread(()->{
            while(true){
                log.debug("running");
            }
        },"t2").start();
    }
}
