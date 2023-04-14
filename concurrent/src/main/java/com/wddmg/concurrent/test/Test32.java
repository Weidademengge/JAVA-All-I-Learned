package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test32, v 0.1 2023/04/08 13:18 duym Exp $
 */
@Slf4j
public class Test32 {
    // 易变
    volatile static boolean run = true;

    public static void main(String[] args) {
        Thread t = new Thread(()->{
            while(run){
                //...

            }
        });
        t.start();
        log.debug("停止，t线程");
        run = false;
    }
}
