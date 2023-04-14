package com.wddmg.concurrent.test;

import com.wddmg.concurrent.n2.util.Sleeper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duym
 * @version $ Id: Test30, v 0.1 2023/04/07 14:04 duym Exp $
 */
public class Test30 {
    public static void main(String[] args) {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();

        new Thread(()->{
            awaitSignal.print("a",a,b);
        },"t1").start();
        new Thread(()->{
            awaitSignal.print("b",b,c);
        },"t2").start();
        new Thread(()->{
            awaitSignal.print("c",c,a);
        },"t3").start();
        Sleeper.sleep(1);
        awaitSignal.lock();
        try {
            a.signal();
        }finally {
            awaitSignal.unlock();
        }
    }
}

class AwaitSignal extends ReentrantLock{
    private int loopNumber;

    public  AwaitSignal(int loopNumber){
        this.loopNumber = loopNumber;
    }

    public void print(String str,Condition current,Condition next){
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                current.await();
                System.out.print(str);
                next.signal();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                unlock();
            }
        }
    }
}