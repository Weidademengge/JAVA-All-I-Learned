package com.duym.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author duym
 * @version $ Id: NIOClient, v 0.1 2023/04/19 20:22 duym Exp $
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //1.打开通道
        SocketChannel socketChannel = SocketChannel.open();
        //2.设置连接IP和端口号
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        //3.写出数据
        socketChannel.write(ByteBuffer.wrap("老板, 该还钱拉!".getBytes(StandardCharsets.UTF_8)));
        //4.读取服务器写回的数据
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int read=socketChannel.read(readBuffer);
        System.out.println("服务端消息:" + new String(readBuffer.array(), 0, read,
                StandardCharsets.UTF_8));
        //5.释放资源
        socketChannel.close();
    }
}
