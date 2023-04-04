package com.duym.nio.c2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author duym
 * @version $ Id: TestGatheringWrites, v 0.1 2023/03/23 14:32 duym Exp $
 */
public class TestGatheringWrites {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("你好");

        try (FileChannel channel = new RandomAccessFile("my-netty/my-nio/words.txt","rw").getChannel()){
            channel.write((new ByteBuffer[]{b1,b2,b3}));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
