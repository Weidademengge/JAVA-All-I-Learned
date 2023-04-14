package com.duym.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ExecutorsDemo {
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    private static ExecutorService pool = new ThreadPoolExecutor(5, 200,
                            0L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>(1024),
                            namedThreadFactory, 
                            new ThreadPoolExecutor.AbortPolicy());
    
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) { 
            pool.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
    }
}