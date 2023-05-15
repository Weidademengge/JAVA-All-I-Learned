package com.duym.dubbo.rpc.api.common;

/**
 * @author duym
 * @version $ Id: RpcResponse, v 0.1 2023/05/15 9:18 duym Exp $
 */

import lombok.Data;

/**
 * 封装的响应对象
 */
@Data
public class RpcResponse {

    /**
     * 响应ID
     */
    private String requestId;

    /**
     * 返回的结果
     */
    private Object returnValue;

}
