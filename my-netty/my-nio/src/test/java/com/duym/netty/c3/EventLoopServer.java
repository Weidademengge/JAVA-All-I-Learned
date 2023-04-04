package com.duym.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author duym
 * @version $ Id: EventLoopServer, v 0.1 2023/03/29 10:44 duym Exp $
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        // 细分2：创建一个独立的EventLoopGroup
        EventLoopGroup group = new DefaultEventLoop();
        new ServerBootstrap()
                // boss 和 worker
                // boss 只负责ServerSocketChannel 上accept时间 worker自负责socketChannel上的读写
                .group(new NioEventLoopGroup(),new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("handler1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf)msg;
                                log.debug(buf.toString(Charset.defaultCharset()));
                                // 让消息传递给下一个handler
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast(group,"handler2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf)msg;
                                log.debug(buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
