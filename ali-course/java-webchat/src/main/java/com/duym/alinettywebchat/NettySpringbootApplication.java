package com.duym.alinettywebchat;

import com.duym.alinettywebchat.netty.NettyWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NettySpringbootApplication implements CommandLineRunner {
    @Autowired
    NettyWebSocketServer nettyWebSocketServer;
    public static void main(String[] args) {
        SpringApplication.run(NettySpringbootApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        new Thread(nettyWebSocketServer).start();
    }
}
