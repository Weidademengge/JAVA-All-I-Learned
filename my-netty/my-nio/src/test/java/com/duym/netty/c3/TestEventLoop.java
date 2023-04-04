package com.duym.netty.c3;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author duym
 * @version $ Id: TestEventLoop, v 0.1 2023/03/29 10:25 duym Exp $
 */
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        // 1.创建时间循环组
        EventLoopGroup group = new NioEventLoopGroup(2);// io事件，普通任务，定时任务
//        DefaultEventLoop group = new DefaultEventLoop();// 普通任务，定时任务
        // 2.获取下一个事件的循环对象
//        System.out.println(group.next());
//        System.out.println(group.next());
//        System.out.println(group.next());
//        System.out.println(group.next());

        // 3.执行普通任务
        group.next().submit(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("ok");
        });

        // 4.执行定时任务
        group.next().scheduleAtFixedRate(()->{
            log.debug("ok");
        },0,1, TimeUnit.SECONDS);

        log.debug("main");
    }
}
