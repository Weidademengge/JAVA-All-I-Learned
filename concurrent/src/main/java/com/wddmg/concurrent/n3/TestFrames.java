package com.wddmg.concurrent.n3;

/**
 * @author duym
 * @version $ Id: TestFrames, v 0.1 2023/04/03 10:30 duym Exp $
 */
public class TestFrames {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            method1(20);
        },"t1");

        t1.start();
        method1(10);
    }

    private static void method1(int x){
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2(){
        Object n = new Object();
        return n;
    }
}
