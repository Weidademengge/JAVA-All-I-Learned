package com.duym.alinettywebchat.netty;

/**
 * @author duym
 * @version $ Id: aa, v 0.1 2023/04/19 21:30 duym Exp $
 */

import com.duym.alinettywebchat.config.NettyConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通道初始化对象
 */
@Component
public class WebSocketChannelInit extends ChannelInitializer {
    @Autowired
    NettyConfig nettyConfig;
    @Autowired
    WebSocketHandler webSocketHandler;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //对http协议的支持.
        pipeline.addLast(new HttpServerCodec());
        // 对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //post请求分三部分. request line / request header / message body
        // HttpObjectAggregator将多个信息转化成单一的request或者response对象
        pipeline.addLast(new HttpObjectAggregator(8000));
        // 将http协议升级为ws协议. websocket的支持
        pipeline.addLast(new
                WebSocketServerProtocolHandler(nettyConfig.getPath()));
        // 自定义处理handler
        pipeline.addLast(webSocketHandler);
    }
}
