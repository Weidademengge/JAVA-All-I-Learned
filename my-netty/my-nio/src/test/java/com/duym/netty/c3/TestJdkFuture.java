package com.duym.netty.c3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author duym
 * @version $ Id: TestJdkFuture, v 0.1 2023/03/30 14:01 duym Exp $
 */
@Slf4j
public class TestJdkFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.线程池
        ExecutorService service = Executors.newFixedThreadPool(2);
        // 2.提交任务
        Future<Integer> future = service.submit(() -> {
            log.debug("执行计算");
            Thread.sleep(1000);
            return 50;
        });
        // 3. 主线程通过future来获取结果
        log.debug("等待结果");
        log.debug("结果是{}",future.get());
    }
}
