package com.duym.netty.c3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author duym
 * @version $ Id: TestNettyPromise, v 0.1 2023/03/30 14:17 duym Exp $
 */
@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.准备EventLoop对象
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 2.可以主动创建promise,结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(()->{
            // 3.任意一个线程执行计算，计算完毕后向promise填充结果
            log.debug("开始计算");
            try{
                int i = 1/0;
                Thread.sleep(1000);
                promise.setSuccess(80);
            }catch (InterruptedException e){
                e.printStackTrace();
                promise.setFailure(e);
            }

        }).start();
        // 4.接受结果的线程
        log.debug("等待结果...");
        log.debug("结果是：{}",promise.get());
    }
}
