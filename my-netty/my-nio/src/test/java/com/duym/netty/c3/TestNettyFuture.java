package com.duym.netty.c3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author duym
 * @version $ Id: TestNettyFuture, v 0.1 2023/03/30 14:09 duym Exp $
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(() -> {
            log.debug("执行计算");
            Thread.sleep(1000);
            return 70;
        });

//        log.debug("等待结果");
//        log.debug("结果是{}",future.get());
        future.addListener(future1 -> log.debug("接受结果:{}", future1.getNow()));
    }
}
