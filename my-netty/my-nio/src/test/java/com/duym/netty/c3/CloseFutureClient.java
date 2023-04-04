package com.duym.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author duym
 * @version $ Id: CloseFutureClient, v 0.1 2023/03/30 11:23 duym Exp $
 */

@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));
        Channel channel = channelFuture.sync().channel();
        log.debug("{}",channel);
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while(true){
                String line = scanner.nextLine();
                if("q".equals(line)){
                    // close异步操作
                    channel.close();
                    break;
                }
                channel.writeAndFlush(line);
            }
        },"input").start();

//        // 获取closedeFuture对象 1） 同步处理关闭，2）异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
//        System.out.println("waiting...closeFuture()");
//        closeFuture.sync();
//        log.debug("处理关闭之后的操作");

        closeFuture.addListener((ChannelFutureListener) future -> {
            log.debug("处理关闭之后的操作");
            group.shutdownGracefully();
        });
    }
}
