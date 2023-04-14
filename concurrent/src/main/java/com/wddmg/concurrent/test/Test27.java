package com.wddmg.concurrent.test;

/**
 * @author duym
 * @version $ Id: Test27, v 0.1 2023/04/07 13:49 duym Exp $
 */
public class Test27 {

    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1,5);
        new Thread(()->{
            wn.print("a",1,2);
        }).start();
        new Thread(()->{
            wn.print("b",2,3);
        }).start();
        new Thread(()->{
            wn.print("c",3,1);
        }).start();
    }
}

class WaitNotify {
    // 等待标记
    private int flag;

    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
