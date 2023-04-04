package com.duym.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author duym
 * @version $ Id: HelloClient, v 0.1 2023/03/28 14:09 duym Exp $
 */
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        // 2.带有Future，Promise的类型都是和一部方法配套使用，用来处理结果
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    // 5.连接建立后被调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1.连接到服务器
                // 异步非阻塞，main发起了调用，真正执行connect是nio线程
                .connect(new InetSocketAddress("localhost", 8080));
//        // 2.1使用sycn方法同步处理结果
//        // 阻塞住当前线程，直到nio线程连接建立完毕
//        channelFuture.sync();
//        // 无阻塞向下执行获取 channel
//        Channel channel = channelFuture.channel();
//        log.debug("{}",channel);
//        // 2.向服务器发送数据
//        channel.writeAndFlush("hello world");

        // 2.2使用addListener（回调对象）方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            // 在nio线程连接建立好之后，会调用operationComplete
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
                log.debug("{}",channel);
                channel.writeAndFlush("hello world");
            }
        });



    }
}
