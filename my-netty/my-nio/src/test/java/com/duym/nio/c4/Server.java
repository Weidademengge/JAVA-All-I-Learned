package com.duym.nio.c4;

import com.duym.nio.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author duym
 * @version $ Id: Server, v 0.1 2023/03/23 17:07 duym Exp $
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        // 1.创建selector,管理多个channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc  = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // 2. 简历selector和channel的联系（注册）
        // SelectionKey 就是将来时间发生后， 通过他可以知道时间和哪个channel的事件
        // 四种事件:  accept - 会在有连接请求时触发
        //          connect - 客户端连接建立后触发
        //          read - 可读事件
        //          write - 可写事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("sscKey:{}",sscKey);
        ssc.bind(new InetSocketAddress(8080));
        while(true){
            //3. select 方法,没有事件发生，线程阻塞，有事件，线程才会恢复运行
            // select 在事件未处理时，它不会阻塞，事件发生后要么处理，要么取消，不能置之不理
            selector.select();

            //4. 处理事件,selectedKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()){
                SelectionKey key = iter.next();
                // 处理key 时，要从selectedKeys集合中删除，否则下次处理就会有问题
                iter.remove();
                log.debug("key:{}",key);
                // 5.区分事件类型
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(16);// attachment附件
                    // 将一个byteBuffer作为附件关联到selectionKey上
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",sc);
                    log.debug("scKey:{}",scKey);
                }else if(key.isReadable()){
                    try {
                        // 拿到触发事件的channel
                        SocketChannel channel = (SocketChannel) key.channel();
                        // 获取selectionKey 上关联的附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);// 如果是正常断开，read的返回值是-1
                        if(read == -1){
                            key.cancel();
                        }else {
                            split(buffer);
                            if(buffer.position() == buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        //客户端断开，因此需要将key取消（从selector的key集合中真正删除key）
                        key.cancel();
                    }

                }


            }
        }

    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整消息
            if(source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }

        source.compact();
    }
}
