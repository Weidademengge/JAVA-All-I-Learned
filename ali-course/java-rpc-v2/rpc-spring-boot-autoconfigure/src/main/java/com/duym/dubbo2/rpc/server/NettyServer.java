package com.duym.dubbo2.rpc.server;

import com.duym.dubbo2.rpc.config.properties.RpcProperties;
import com.duym.dubbo2.rpc.config.properties.RpcServerProperties;
import com.duym.dubbo2.rpc.utils.zk.ZKServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;

public class NettyServer implements ApplicationContextAware {

    private final Integer port;

    private ApplicationContext applicationContext;

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public NettyServer(int port) {
        this.port = port;
    }

    /**
     * 启动 Netty 服务器
     */
    @PostConstruct
    public void postConstruct() {
// 向ZK注册服务及端口  
        doRegister();
// 异步开启 Netty 服务器  
        new Thread(this::run).start();
    }

    public void run() {
        try {
            //1.创建服务端启动助手
            ServerBootstrap b = new ServerBootstrap();
            //2.设置线程组
            b.group(bossGroup, workerGroup)
                    //3.设置参数
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                                    socketChannel.pipeline().addLast(new RcpServerHandler(applicationContext));
                                }
                            });

            // 4.启动（绑定端口）
            ChannelFuture f = b.bind(port).sync();
            System.out.println("服务端 Netty服务器启动成功:" + port);
            // 5.等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭，释放线程池资源
            close();
        }
    }

    /**
     * 资源关闭--在容器销毁是关闭
     */
    @PreDestroy
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    /**
     * 向ZK注册服务及端口
     */
    private void doRegister() {
        ZKServer zkServer = applicationContext.getBean(ZKServer.class);
        RpcServerProperties rpcServerProperties = applicationContext.getBean(RpcServerProperties.class);
        RpcProperties rpcProperties = applicationContext.getBean(RpcProperties.class);

        String providerGroupDir = rpcProperties.getPath() + rpcProperties.getProviderPath();
        providerGroupDir = providerGroupDir + "/" + rpcServerProperties.getProviderName();

        try {
            // 创建服务名目录（用于集群）
            // providerGroupDir = /rpc/provider/myProviderName
            zkServer.createPathPermanent(providerGroupDir, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String providerAddress = InetAddress.getLocalHost().getHostAddress();
            String providerInstance = providerAddress + ":" + rpcServerProperties.getProviderPort();
            // key(path) = /rpc/provider/myProviderName/127.0.0.1:8080 value:127.0.0.1:8080
            zkServer.createPathTemp(providerGroupDir + "/" + providerInstance, providerInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}