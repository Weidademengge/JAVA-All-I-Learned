package com.duym.nio.c2;

import com.duym.nio.ByteBufferUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author duym
 * @version $ Id: TestScatteringReads, v 0.1 2023/03/23 14:27 duym Exp $
 */
public class TestScatteringReads {

    public static void main(String[] args) {

        try (FileChannel channel = new RandomAccessFile("my-netty/my-nio/words.txt", "r").getChannel()){
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{b1,b2,b3});
            b1.flip();
            b2.flip();
            b3.flip();
            ByteBufferUtil.debugAll(b1);
            ByteBufferUtil.debugAll(b2);
            ByteBufferUtil.debugAll(b3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
