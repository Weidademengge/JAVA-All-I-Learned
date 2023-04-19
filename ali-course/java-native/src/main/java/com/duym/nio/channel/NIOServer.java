package com.duym.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author duym
 * @version $ Id: NIOServer, v 0.1 2023/04/19 20:20 duym Exp $
 */
public class NIOServer {
    public static void main(String[] args) throws IOException,
            InterruptedException {
        //1. 打开一个服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2. 绑定对应的端口号
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //3. 通道默认是阻塞的，需要设置为非阻塞
        // true 为通道阻塞 false 为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务端启动成功..........");
        while (true) {
            //4. 检查是否有客户端连接 有客户端连接会返回对应的通道 , 否则返回null
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                System.out.println("没有客户端连接...我去做别的事情");
                Thread.sleep(2000);
                continue;
            }
            //5. 获取客户端传递过来的数据,并把数据放在byteBuffer这个缓冲区中
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //返回值:
            //正数: 表示本次读到的有效字节个数.
            //0 : 表示本次没有读到有效字节.
            //-1 : 表示读到了末尾
            int read = socketChannel.read(byteBuffer);
            System.out.println("客户端消息:" +
                    new String(byteBuffer.array(), 0, read,
                            StandardCharsets.UTF_8));
            //6. 给客户端回写数据
            socketChannel.write(ByteBuffer.wrap("没钱".getBytes(StandardCharsets.UTF_8)));
            //7. 释放资源
            socketChannel.close();
        }
    }
}
