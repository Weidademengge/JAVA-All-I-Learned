package com.duym.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author duym
 * @version $ Id: WriteClient, v 0.1 2023/03/27 10:53 duym Exp $
 */
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
        int count = 0;
        // 3.接收数据
        while(true){
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            count += sc.read(buffer);
            System.out.println(count);
            buffer.clear();
        }

    }
}
