package com.duym.dubbo.rpc.api.common;

/**
 * @author duym
 * @version $ Id: RpcRequest, v 0.1 2023/05/15 9:18 duym Exp $
 */

import lombok.Data;

/**
 * 封装的请求对象
 */
@Data
public class RpcRequest {

    /**
     * 请求对象的ID
     */
    private String requestId;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参
     */
    private Object[] parameters;

}
