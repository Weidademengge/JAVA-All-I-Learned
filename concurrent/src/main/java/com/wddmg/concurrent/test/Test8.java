package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author duym
 * @version $ Id: Test8, v 0.1 2023/04/04 9:34 duym Exp $
 */
@Slf4j
public class Test8 {
    public static void main(String[] args) throws InterruptedException {
        log.debug("enter");
        TimeUnit.SECONDS.sleep(1);
        log.debug("end");
    }
}
