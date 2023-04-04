package com.duym.nio.c2;

import com.duym.nio.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * @author duym
 * @version $ Id: TestByteBufferReadWrite, v 0.1 2023/03/23 13:29 duym Exp $
 */
public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //'a'
        buffer.put((byte) 0x61);
        ByteBufferUtil.debugAll(buffer);

        // 'b','c','d'
        buffer.put(new byte[]{0x62,0x63,0x64});
        ByteBufferUtil.debugAll(buffer);

        buffer.flip();
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);

        buffer.compact();
        ByteBufferUtil.debugAll(buffer);

        buffer.put(new byte[]{0x65,0x66});
        ByteBufferUtil.debugAll(buffer);
    }

}
