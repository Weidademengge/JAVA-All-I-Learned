package com.wddmg.concurrent.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * @author duym
 * @version $ Id: Test34, v 0.1 2023/04/11 19:09 duym Exp $
 */
public class Test34 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(5);

//        // ++i
//        System.out.println(i.incrementAndGet());
//
//        // i++
//        System.out.println(i.getAndIncrement());
//
//        // 2,7
//        i.getAndAdd(5);
//
//        // 12,12
//        i.addAndGet(5);

//        i.updateAndGet(value -> value * 10);
//        System.out.println(i);

        updateAndGet(i, p -> p / 2);

        System.out.println(i.get());
    }

    public static int updateAndGet(AtomicInteger i, IntUnaryOperator operator) {
        while (true) {
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if (i.compareAndSet(prev, next)) {
                return next;
            }
        }
    }
}
