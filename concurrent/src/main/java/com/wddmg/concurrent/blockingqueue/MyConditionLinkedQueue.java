package com.wddmg.concurrent.blockingqueue;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duym
 * @version $ Id: MyConditionLinkedQueue, v 0.1 2023/03/10 17:25 duym Exp $
 */
public class MyConditionLinkedQueue {

    private LinkedList<Object> data = new LinkedList<>();

    private int maxSize = 10;

    private ReentrantLock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    public void put(Object object) throws InterruptedException {
        lock.lock();
        try {
            while(data.size() == maxSize){
                notFull.await();
            }
            data.add(object);
            notEmpty.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while(data.size() == maxSize){
                notEmpty.await();
            }
            Object remove = data.remove();
            notEmpty.signalAll();
            return remove;
        } finally {
            lock.unlock();
        }
    }

}
