package com.duym.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duym
 * @version $ Id: NettyChatClientHandler, v 0.1 2023/04/19 20:50 duym Exp $
 */
public class NettyChatClientHandler extends
        SimpleChannelInboundHandler<String> {

    /**
     * 通道读取就绪事件
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {
        System.out.println(msg);
    }
}
