package com.duym.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.duym.netty.c4.TestByteBuf.log;

/**
 * @author duym
 * @version $ Id: TestSlice, v 0.1 2023/03/30 17:14 duym Exp $
 */
public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log(buf);

        // 在切片过程中没有发生数据复制
        ByteBuf f1 = buf.slice(0, 5);
        ByteBuf f2 = buf.slice(5, 5);
        log(f1);
        log(f2);

        System.out.println("====================");
        f1.setByte(0,'b');
        log(f1);
        log(buf);
    }
}
