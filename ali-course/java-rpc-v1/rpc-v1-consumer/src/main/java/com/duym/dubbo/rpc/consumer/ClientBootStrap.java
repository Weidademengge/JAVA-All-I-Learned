package com.duym.dubbo.rpc.consumer;


import com.duym.dubbo.rpc.api.UserApi;
import com.duym.dubbo.rpc.api.dto.UserInfoDTO;
import com.duym.dubbo.rpc.consumer.proxy.RpcClientProxy;

/**
 * 测试类
 */
public class ClientBootStrap {
    public static void main(String[] args) {
        UserApi userService = (UserApi) RpcClientProxy.createProxy(UserApi.class);
        UserInfoDTO user = userService.getById(1);
        System.out.println(user);
    }
}