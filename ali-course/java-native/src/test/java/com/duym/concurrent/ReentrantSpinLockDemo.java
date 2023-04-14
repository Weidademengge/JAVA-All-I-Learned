package com.duym.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author duym
 * @version $ Id: ReentrantSpinLockDemo, v 0.1 2023/04/11 15:10 duym Exp $
 */
public class ReentrantSpinLockDemo {


    private AtomicReference<Thread> owner = new AtomicReference<>();
    private int count = 0;

    public void lock(){
        Thread thread = Thread.currentThread();
        if(thread == owner.get()){
            ++ count;
            return;
        }
        // 自旋
        while(!owner.compareAndSet(null,thread)){
            System.out.println(thread.getName()+"自旋");
        }
    }

    public void unlock(){
        Thread thread = Thread.currentThread();
        if(thread == owner.get()){
            if(count >0){
                --count;
            }else{
                owner.set(null);
            }
        }
    }

}
