package com.duym.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author duym
 * @version $ Id: HelloClient, v 0.1 2023/03/28 14:09 duym Exp $
 */
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        // 1.启动类
        new Bootstrap()
                // 2.添加EventLoop
                .group(new NioEventLoopGroup())
                // 3.选择客户端 channel实现
                .channel(NioSocketChannel.class)
                // 4.添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>(){
                    // 5.连接建立后被调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 5.连接到服务器
                .connect(new InetSocketAddress("localhost",8080))
                // 阻塞方法，知道连接建立
                .sync()
                // 代表连接对象
                .channel()
                  // 6.想服务器发送数据
                .writeAndFlush("hello,world");
    }
}
