package com.duym.dubbo2.rpc.annotation.processor;

import com.alibaba.fastjson.JSON;
import com.duym.dubbo2.rpc.client.NettyClient;
import com.duym.dubbo2.rpc.client.NettyClientBizGroup;
import com.duym.dubbo2.rpc.client.NettyClientGroup;
import com.duym.dubbo2.rpc.model.RpcRequest;
import com.duym.dubbo2.rpc.model.RpcResponse;
import com.duym.dubbo2.rpc.utils.Class2String;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.UUID;

public class RpcServiceProxy<T> implements InvocationHandler {

    private Class<T> proxyInterface;
    // 这里可以维护一个缓存，存这个接口的方法抽象的对象
    private NettyClientGroup nettyClientGroup;

    private String serviceName;

    public RpcServiceProxy(
            Class<T> proxyInterface, String serviceName, NettyClientGroup nettyClient) {
        this.serviceName = serviceName;
        this.proxyInterface = proxyInterface;
        this.nettyClientGroup = nettyClient;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("invoke");

        Map<String, NettyClientBizGroup> providers = nettyClientGroup.getProviders();

        NettyClientBizGroup nettyClientBizGroup = providers.get(serviceName);

        if (null == nettyClientBizGroup) {
            RpcResponse response = RpcResponse.NO_SERVICE();
            return response.getReturnValue();
        }

        NettyClient nettyClient = nettyClientBizGroup.next();

        if (null == nettyClient) {
            RpcResponse response = RpcResponse.NO_SERVICE();
            return response.getReturnValue();
        }

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(UUID.randomUUID().toString().substring(0, 8));
        // 设置服务名称
        rpcRequest.setServiceName(serviceName);
        // 设置是哪个类
        rpcRequest.setClazzName(proxyInterface.getName());
        // 设置哪个方法
        rpcRequest.setMethodName(method.getName());
        // 设置参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterTypeString = Class2String.class2String(parameterTypes);
        rpcRequest.setParameterTypeStrings(parameterTypeString);

        // 设置参数
        rpcRequest.setParameters(args);

        // 发送消息
        RpcResponse response = nettyClient.sendMessage(rpcRequest);

        if (response == null) {
            response = RpcResponse.TIME_OUT(rpcRequest.getRequestId());
        }

        //return JSONUtil.toBean(response.getReturnValue().toString(), method.getReturnType());
        return JSON.parseObject(response.getReturnValue().toString(), method.getReturnType());
    }

    public T getProxy() {
        return (T)
                Proxy.newProxyInstance(proxyInterface.getClassLoader(), new Class[]{proxyInterface}, this);
    }
}