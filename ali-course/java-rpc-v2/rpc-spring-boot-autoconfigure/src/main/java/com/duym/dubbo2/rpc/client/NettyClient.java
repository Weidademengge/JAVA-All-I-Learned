package com.duym.dubbo2.rpc.client;

import cn.hutool.json.JSONUtil;
import com.duym.dubbo2.rpc.constant.FuturePool;
import com.duym.dubbo2.rpc.model.RpcFuture;
import com.duym.dubbo2.rpc.model.RpcRequest;
import com.duym.dubbo2.rpc.model.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private final EventLoopGroup group = new NioEventLoopGroup();
    private ChannelFuture f = null;

    private final String ip;
    private final Integer port;

    public NettyClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * 启动 Netty 客户端
     */
    public void start() {
        new Thread(this::run).start();
    }

    public void run() {
        try {
            //1. 创建客户端启动助手
            Bootstrap b = new Bootstrap();
            //2. 设置线程组
            b.group(group)
                    //3.设置参数
                    .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            // TimeClientHandler是自己定义的方法
                            socketChannel.pipeline().addLast(new RpcClientHandler());
                            // 添加 Netty 自带心跳机制
                            // 在客户端会每隔10秒来检查一下channelRead方法被调用的情况，如果在10秒内该链上的channelRead方法都没有被触发，就会调用userEventTriggered方法。
                            socketChannel.pipeline().addLast(new IdleStateHandler(10, 10, 10, TimeUnit.SECONDS));
                            // 添加心跳机制触发器
                            socketChannel.pipeline().addLast(new IdleStateTriggerHandler());
                        }
                    });
            //4. 启动客户端,等待连接服务端,同时将异步改为同步
            f = b.connect(ip, port).sync();
            //5. 关闭通道和关闭连接池
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public RpcResponse sendMessage(RpcRequest msg) {
        // 存起来
        RpcFuture<RpcResponse> future = new RpcFuture<>();
        FuturePool.put(msg.getRequestId(), future);

        RpcResponse rpcResponse = null;
        try {
            String s = JSONUtil.toJsonStr(msg);
            f.channel().writeAndFlush(s);

            rpcResponse = future.get(2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FuturePool.remove(msg.getRequestId());
        }

        return rpcResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NettyClient that = (NettyClient) o;
        return Objects.equals(ip, that.ip) && Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}