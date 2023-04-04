package com.duym.nio.c2;


import java.nio.ByteBuffer;

/**
 * @author duym
 * @version $ Id: TestByteBUfferAllocate, v 0.1 2023/03/23 13:57 duym Exp $
 */
public class TestByteBufferAllocate {

    public static void main(String[] args) {
        // class java.nio.HeapByteBuffer  - java 堆内存，读写效率较低，会受到gc影响
        System.out.println(ByteBuffer.allocate(16).getClass());
        // class java.nio.DirectByteBuffer  - java 直接内存，读写效率高（少一次数据拷贝），不会受到gc影响，分配效率低
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
    }
}
