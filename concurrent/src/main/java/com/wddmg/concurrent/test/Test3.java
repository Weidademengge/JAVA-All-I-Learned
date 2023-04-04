package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/03 10:16 duym Exp $
 */
@Slf4j
public class Test3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(()->{
            log.debug("running");
            Thread.sleep(1000);
            return 100;
        });
        Thread t1 = new Thread(task,"t1");
        t1.start();

        log.debug("{}",task.get());
    }
}
