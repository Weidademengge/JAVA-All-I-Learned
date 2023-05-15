package com.duym.dubbo2.rpc.client;
  
import cn.hutool.json.JSONUtil;
import com.duym.dubbo2.rpc.constant.ConstantPool;
import com.duym.dubbo2.rpc.constant.FuturePool;
import com.duym.dubbo2.rpc.model.RpcFuture;
import com.duym.dubbo2.rpc.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;  
  
public class RpcClientHandler extends SimpleChannelInboundHandler<String> {  
@Override  
protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {  
  
System.out.println("resp:" + msg);  
  
RpcResponse rpcResponse = JSONUtil.toBean(msg, RpcResponse.class);
  
if (ConstantPool.HEART_BEAT.equals(rpcResponse.getRequestId())) {
System.out.println("客户端接收到心跳");  
return;  
}  
  
RpcFuture future = FuturePool.get(rpcResponse.getRequestId());
  
future.setResponse(rpcResponse);  
}  
}