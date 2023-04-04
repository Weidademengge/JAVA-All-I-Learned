package com.duym.nio.c2;

import com.duym.nio.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * @author duym
 * @version $ Id: TestByteBufferRead, v 0.1 2023/03/23 14:05 duym Exp $
 */
public class TestByteBufferRead {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();

        // 从头开始读
//        buffer.get(new byte[4]);
//        debugAll(buffer);
//        buffer.rewind();
//        System.out.println((char)buffer.get());

        // mark & reset
        // mark 做一个标记，记录position位置，reset是将position重置到mark的位置
//        System.out.println((char)buffer.get());
//        System.out.println((char)buffer.get());
//        // 在索引2的位置加标记
//        buffer.mark();
//        System.out.println((char)buffer.get());
//        System.out.println((char)buffer.get());
//        // 将position重置到索引2
//        buffer.reset();
//        System.out.println((char)buffer.get());
//        System.out.println((char)buffer.get());

        // get(i)
        System.out.println((char)buffer.get(3));
        ByteBufferUtil.debugAll(buffer);

    }
}
