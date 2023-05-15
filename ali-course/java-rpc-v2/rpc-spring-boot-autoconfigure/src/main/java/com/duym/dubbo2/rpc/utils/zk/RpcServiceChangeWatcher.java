package com.duym.dubbo2.rpc.utils.zk;

import com.duym.dubbo2.rpc.client.NettyClientGroup;
import com.duym.dubbo2.rpc.config.properties.RpcProperties;
import lombok.Data;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Data
public class RpcServiceChangeWatcher implements Watcher, ApplicationContextAware {

    // IOC容器
    private ApplicationContext applicationContext;

    private RpcProperties rpcProperties;
    private NettyClientGroup nettyClientGroup;
    private ZKServer zkServer;

    private String listenProviderPath;

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);

        // 实际业务
        try {
            nettyClientGroup.refreshProviders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 重新监听
        String providersPath = rpcProperties.getPath() + rpcProperties.getProviderPath();
        try {
            zkServer.getZk().getChildren(providersPath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.rpcProperties = applicationContext.getBean(RpcProperties.class);
        this.nettyClientGroup = applicationContext.getBean(NettyClientGroup.class);
        this.zkServer = applicationContext.getBean(ZKServer.class);
    }
}