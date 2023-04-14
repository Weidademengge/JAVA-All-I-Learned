package com.wddmg.concurrent.test;

import com.wddmg.concurrent.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.wddmg.concurrent.n2.util.Sleeper.sleep;

/**
 * @author duym
 * @version $ Id: Test14, v 0.1 2023/04/04 11:26 duym Exp $
 */
@Slf4j
public class Test14 {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}",Thread.interrupted());

            LockSupport.park();
            log.debug("unpark...");
        },"t1");
        t1.start();



        sleep(1);
        t1.interrupt();
    }
}
