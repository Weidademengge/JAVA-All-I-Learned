package com.duym.dubbo2.rpc.model;

import com.duym.dubbo2.rpc.constant.ConstantPool;
import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse implements Serializable {

    private String requestId;

    private Object returnValue;

    /**
     * 没有服务提供者
     *
     * @return
     */
    public static RpcResponse NO_SERVICE() {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId("404Service");
        rpcResponse.setReturnValue("没有服务提供者");
        return rpcResponse;
    }

    /**
     * 超时TimeOut
     *
     * @return
     */
    public static RpcResponse TIME_OUT(String requestId) {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(requestId);
        rpcResponse.setReturnValue("超时TimeOut");
        return rpcResponse;
    }

    /**
     * 心跳
     *
     * @return
     */
    public static RpcResponse HEART_BEAT() {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(ConstantPool.HEART_BEAT);
        return rpcResponse;
    }


}