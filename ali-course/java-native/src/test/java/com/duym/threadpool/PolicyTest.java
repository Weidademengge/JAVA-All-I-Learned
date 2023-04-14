package com.duym.threadpool;

import org.junit.After;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duym
 * @version $ Id: PolicyTest, v 0.1 2023/04/10 21:11 duym Exp $
 */
public class PolicyTest {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,
            3,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5));

    class Task implements Runnable {
        // 任务名称
        private String taskName;

        public Task(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            System.out.println("线程[ " + Thread.currentThread().getName()
                    + " ]正在执行[ " + this.taskName + " ]任务...");

            try {
                Thread.sleep(1000L * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程[ " + Thread.currentThread().getName()
                    + " ]已执行完[ " + this.taskName + " ]任务！！！");
        }
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(1000 * 100);
    }

    /**
     * 线程池的执行过程
     *
     * 2个核心线程
     * 5个任务的队列
     * 3个最大线程：1个线程可用
     *
     * 前2个任务，会占用2个核心线程
     * 第3个到第7个任务，会暂存到任务队列中
     * 第8个任务，会启动最大线程，去执行
     * 第9个任务，没有线程可以去执行~~~
     */
    @Test
    public void abortPolicyTest() {
        // 设置饱和策略为 终止策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 1; i <= 10; i++) {
            try {
                // 提交10个线程任务
                executor.execute(new Task("线程任务" + i));
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        // 关闭线程池
        executor.shutdown();
    }

    @Test
    public void discardPolicyTest() {
        // 设置饱和策略为 抛弃策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 1; i <= 10; i++) {
            try {
                // 提交10个线程任务
                executor.execute(new Task("线程任务" + i));
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        // 关闭线程池
        executor.shutdown();
    }

    @Test
    public void discardOldestPolicyTest() {
        // 设置饱和策略为 抛弃旧任务策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 1; i <= 10; i++) {
            try {
                // 提交10个线程任务
                executor.execute(new Task("线程任务" + i));
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        // 关闭线程池
        executor.shutdown();
    }

    @Test
    public void callerRunsPolicyTest() {
        // 设置饱和策略为 调用者运行策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 1; i <= 10; i++) {
            try {
                // 提交10个线程任务
                executor.execute(new Task("线程任务" + i));
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        // 关闭线程池
        executor.shutdown();
    }
}
