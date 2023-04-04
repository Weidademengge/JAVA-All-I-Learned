package com.duym.protocol;

import com.duym.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author duym
 * @version $ Id: TestMessageCodec, v 0.1 2023/03/31 14:16 duym Exp $
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        // 工人1 1234
        // 工人2 1234
        LengthFieldBasedFrameDecoder FRAME_DECODER = new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0);
        LoggingHandler loggingHandler = new LoggingHandler();
        EmbeddedChannel channel = new EmbeddedChannel(
                FRAME_DECODER,
                loggingHandler,
                new MessageCodec());

        // encode
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");
        channel.writeOutbound(message);

        // decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buf);

        // 入站
        channel.writeInbound(buf);
    }
}
