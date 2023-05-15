package com.duym.dubbo.rpc.consumer.proxy;

import com.alibaba.fastjson.JSON;
import com.duym.dubbo.rpc.api.common.RpcRequest;
import com.duym.dubbo.rpc.api.common.RpcResponse;
import com.duym.dubbo.rpc.consumer.client.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author duym
 * @version $ Id: RpcClientProxy, v 0.1 2023/05/15 9:51 duym Exp $
 */
public class RpcClientProxy {
    public static Object createProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //1.封装request请求对象
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setRequestId(UUID.randomUUID().toString());
                rpcRequest.setClassName(method.getDeclaringClass().getName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);
                //2.创建RpcClient对象
                RpcClient rpcClient = new RpcClient("127.0.0.1", 8899);
                try {
                    //3.发送消息
                    Object responseMsg = rpcClient.send(JSON.toJSONString(rpcRequest));
                    RpcResponse rpcResponse = JSON.parseObject(responseMsg.toString(), RpcResponse.class);

                    //4.返回结果
                    Object result = rpcResponse.getReturnValue();
                    return JSON.parseObject(result.toString(), method.getReturnType());
                } catch (Exception e) {
                    throw e;
                } finally {
                    rpcClient.close();
                }
            }
        });
    }
}
