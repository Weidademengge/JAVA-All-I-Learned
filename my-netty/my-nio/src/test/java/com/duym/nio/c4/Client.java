package com.duym.nio.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author duym
 * @version $ Id: Client, v 0.1 2023/03/23 17:44 duym Exp $
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
        SocketAddress address = sc.getLocalAddress();
        sc.write(Charset.defaultCharset().encode("0123456789abcdef3333"));
        System.in.read();
    }
}
