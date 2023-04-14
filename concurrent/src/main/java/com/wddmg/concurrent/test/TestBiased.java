package com.wddmg.concurrent.test;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import static com.wddmg.concurrent.n2.util.Sleeper.sleep;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/04/05 16:46 duym Exp $
 */
@Slf4j
public class TestBiased {
    public static void main(String[] args) {
        Dog d = new Dog();
        new Thread(()->{
            log.debug(ClassLayout.parseInstance(d).toPrintable(d));
            synchronized (d){
                log.debug(ClassLayout.parseInstance(d).toPrintable(d));
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable(d));

            synchronized (TestBiased.class){
                TestBiased.class.notify();
            }
        },"t1").start();
        new Thread(()->{
            synchronized (TestBiased.class){
                try {
                    TestBiased.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable(d));
            synchronized (d){
                log.debug(ClassLayout.parseInstance(d).toPrintable(d));
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable(d));
        },"t2").start();
    }
}

class Dog{

}