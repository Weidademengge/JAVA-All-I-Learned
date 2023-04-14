package com.wddmg.concurrent.test;

import com.wddmg.concurrent.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duym
 * @version $ Id: Test23, v 0.1 2023/04/07 13:12 duym Exp $
 */
public class Test23 {
    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");

        new Philosopher("苏格拉底",c1,c2).start();
        new Philosopher("柏拉图",c2,c3).start();
        new Philosopher("亚里士多德",c3,c4).start();
        new Philosopher("赫拉克利特",c4,c5).start();
        new Philosopher("阿基米德",c5,c1).start();
    }
}


@Slf4j
class Philosopher extends Thread{
    Chopstick left;
    Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    public void run(){
        while(true){
            if(left.tryLock()){
                try {
                    if(right.tryLock()){
                        try {
                            eat();
                        }finally {
                            right.unlock();
                        }
                    }
                }finally {
                    left.unlock();
                }
            }
        }
    }

    private void eat(){
        log.debug("eating...");
        Sleeper.sleep(1);
    }
}





class Chopstick extends ReentrantLock {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + "}";
    }
}
