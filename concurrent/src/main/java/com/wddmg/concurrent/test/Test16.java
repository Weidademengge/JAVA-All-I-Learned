package com.wddmg.concurrent.test;

import com.wddmg.concurrent.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test16, v 0.1 2023/04/04 13:49 duym Exp $
 */
@Slf4j
public class Test16 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            Sleeper.sleep(1);
            log.debug("烧开水");
            Sleeper.sleep(5);
        },"老王");

        Thread t2 = new Thread(()->{
            log.debug("洗茶壶");
            Sleeper.sleep(1);
            log.debug("洗茶杯");
            Sleeper.sleep(2);
            log.debug("拿茶叶");
            Sleeper.sleep(1);
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("泡茶");
        },"小王");

        t1.start();
        t2.start();
    }
}
