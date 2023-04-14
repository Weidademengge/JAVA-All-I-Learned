package com.duym.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        for (int i = 0; i < 8; i++) {
            new Thread(new Task(i + 1, cyclicBarrier)).start();
        }
    }
    static class Task implements Runnable {
        private int id;
        private CyclicBarrier cyclicBarrier;
        public Task(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            System.out.println("同学 " + id + " 现在从大门出发，前往停车场");
            try {
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("同学 " + id + " 到了停车场，开始等待其他人到达");
                cyclicBarrier.await();
                System.out.println("老司机 " + id + " 开始发车!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}