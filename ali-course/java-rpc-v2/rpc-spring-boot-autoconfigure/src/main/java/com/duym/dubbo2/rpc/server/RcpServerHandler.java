package com.duym.dubbo2.rpc.server;

import cn.hutool.json.JSONUtil;
import com.duym.dubbo2.rpc.constant.ConstantPool;
import com.duym.dubbo2.rpc.model.RpcRequest;
import com.duym.dubbo2.rpc.model.RpcResponse;
import com.duym.dubbo2.rpc.utils.String2Class;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class RcpServerHandler extends SimpleChannelInboundHandler<String> {

    private final ApplicationContext applicationContext;

    public RcpServerHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);

// msg = "{\"methodName\":\"getId\",  
// \"serviceName\":\"provider01\",  
// \"requestId\":\"d53815a0\",  
// \"parameters\":[1],  
// \"clazzName\":\"com.ebanma.cloud.rpc.api.UserApi\",  
// \"parameterTypeStrings\":[\"java.lang.Integer\"]}";  
        RpcRequest requestBean = JSONUtil.toBean(msg, RpcRequest.class);

// 心跳逻辑  
        if (ConstantPool.HEART_BEAT.equals(requestBean.getRequestId())) {
            System.out.println("服务端接收到心跳");
            RpcResponse rpcResponse = RpcResponse.HEART_BEAT();
            String s = JSONUtil.toJsonStr(rpcResponse);
            ctx.channel().writeAndFlush(s);
            return;
        }

        try {
            String[] parameterTypeStrings = requestBean.getParameterTypeStrings();
            Class<?>[] parameterTypes = String2Class.string2Class(parameterTypeStrings);
            requestBean.setParameterTypes(parameterTypes);

            String clazzName = requestBean.getClazzName();
            Class<?> aClass = Class.forName(clazzName);
            Object bean = applicationContext.getBean(aClass);
            Method method =
                    aClass.getMethod(requestBean.getMethodName(), requestBean.getParameterTypes());

            Object invoke = method.invoke(bean, requestBean.getParameters());

            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.setRequestId(requestBean.getRequestId());
            rpcResponse.setReturnValue(invoke);

            String s = JSONUtil.toJsonStr(rpcResponse);

            ctx.channel().writeAndFlush(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");
        super.channelRegistered(ctx);
    }
}