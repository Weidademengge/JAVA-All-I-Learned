package com.duym.protocol;

import com.duym.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 必须和LengthFieldBasedFrameDecoder一起使用，确保接到的Bytebuf是完整的
 * @author duym
 * @version $ Id: MessageCodecSharable, v 0.1 2023/03/31 14:59 duym Exp $
 */

@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outlist) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 1.4字节的魔数
        out.writeBytes(new byte[]{1,2,3,4});
        // 2. 1字节的版本,0 jdk; 1 json
        out.writeByte(1);
        // 3. 1字节的序列化方式 jdk 0,json 1
        out.writeByte(0);
        // 4. 1字节的指令类型
        out.writeByte(msg.getMessageType());
        // 5. 4个字节请求序号
        out.writeInt(msg.getSequenceId());
        // 无意义，填充
        out.writeByte(0xff);
        // 6. 获取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        // 7.长度
        out.writeInt(bytes.length);
        // 8.写入内容
        out.writeBytes(bytes);
        outlist.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes,0,length);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) ois.readObject();
        log.debug("{},{},{},{},{},{}",magicNum,version,serializerType,messageType,sequenceId,length);
        log.debug("{}",message);
        out.add(message);
    }
}
