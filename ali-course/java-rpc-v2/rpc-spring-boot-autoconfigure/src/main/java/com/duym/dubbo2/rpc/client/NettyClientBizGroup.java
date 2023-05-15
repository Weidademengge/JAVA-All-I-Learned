package com.duym.dubbo2.rpc.client;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class NettyClientBizGroup {

    /**
     * 下一个下标
     */
    private AtomicInteger index = new AtomicInteger(0);

    /**
     * 服务提供者名称
     */
    private String providerName;

    /**
     * 服务提供者列表
     */
    List<NettyClient> providerList = new ArrayList<>();

    /**
     * key = 服务提供者ip:port
     * value = NettyClient
     */
    Map<String, NettyClient> providerMap = new HashMap<>();

    public NettyClientBizGroup(String providerName, List<String> providerStringList) {
        this.providerName = providerName;

        for (String s : providerStringList) {
            System.out.println(s);
            String[] split = s.split(":");
            String ip = split[0];
            Integer port = Integer.parseInt(split[1]);

            NettyClient nettyClient = new NettyClient(ip, port);
            nettyClient.start();

            providerList.add(nettyClient);
            providerMap.put(s, nettyClient);
        }
    }

    /**
     * 获取下一个客户端，轮询操作
     *
     * @return
     */
    public NettyClient next() {
        if (providerList.size() == 0) {
            return null;
        }
        return providerList.get(index.getAndIncrement() % providerList.size());
    }
}