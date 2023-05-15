package com.duym.dubbo2.rpc.config;

import com.duym.dubbo2.rpc.annotation.processor.RcpServiceProcessor;
import com.duym.dubbo2.rpc.client.NettyClientGroup;
import com.duym.dubbo2.rpc.config.properties.RpcClientProperties;
import com.duym.dubbo2.rpc.config.properties.RpcProperties;
import com.duym.dubbo2.rpc.config.properties.RpcServerProperties;
import com.duym.dubbo2.rpc.server.NettyServer;
import com.duym.dubbo2.rpc.utils.zk.DefaultWatcher;
import com.duym.dubbo2.rpc.utils.zk.RpcServiceChangeWatcher;
import com.duym.dubbo2.rpc.utils.zk.ZKServer;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
// 让使用了@ConfigurationProperties注解的类生效,并且将该类注入到IOC容器中,交由IOC容器进行管理  
@EnableConfigurationProperties({
        RpcProperties.class,
        RpcServerProperties.class,
        RpcClientProperties.class
})
// 如果application.yml或者properties中没有rpc.register-address属性，则此类RpcAutoConfiguration不注入IOC容器  
@ConditionalOnProperty(
        prefix = "rpc",
        name = "register-address",
        matchIfMissing = false)
public class RpcAutoConfiguration {

    /**
     * 创建连接zk的客户端
     *
     * @return
     */
    @Bean
    public ZKServer ZKServer() {
        ZKServer server = new ZKServer();
        return server;
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.client", name = "consumer-name", matchIfMissing = false)
    @Primary
    public Watcher rpcServiceChangeWatcher() {
        RpcServiceChangeWatcher rpcServiceChangeWatcher = new RpcServiceChangeWatcher();
        return rpcServiceChangeWatcher;
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.server", name = "provider-name", matchIfMissing = false)
    public Watcher defaultWatcher() {
        DefaultWatcher defaultWatcher = new DefaultWatcher();
        return defaultWatcher;
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.server", name = "provider-name", matchIfMissing = false)
    public NettyServer nettyServer(@Autowired RpcServerProperties rpcServerProperties) {
        return new NettyServer(rpcServerProperties.getProviderPort());
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.client", name = "consumer-name", matchIfMissing = false)
    public NettyClientGroup nettyClientGroup() {
        return new NettyClientGroup();
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.client", name = "consumer-name", matchIfMissing = false)
    public RcpServiceProcessor rcpServiceProcessor() {
        return new RcpServiceProcessor();
    }
}