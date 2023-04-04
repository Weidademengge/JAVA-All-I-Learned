package com.duym.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.duym.netty.c4.TestByteBuf.log;

/**
 * @author duym
 * @version $ Id: TestCompositeByteBuf, v 0.1 2023/03/30 17:36 duym Exp $
 */
public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1,2,3,4,5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{6,7,8,9,10});

        // 发生了两次数据复制
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(buf1).writeBytes(buf2);
//        log(buffer);

        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        buffer.addComponents(true,buf1,buf2);
        log(buffer);
    }
}
