package com.duym.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static com.duym.nio.ByteBufferUtil.debugAll;

/**
 * @author duym
 * @version $ Id: MultiThreadServer, v 0.1 2023/03/28 8:56 duym Exp $
 */
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss,0,null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-"+i);
        }
        AtomicInteger index = new AtomicInteger();
        // 创建固定数量worker 并初始化
        Worker worker = new Worker("worker-0");

        while(true){
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();;
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}",sc.getRemoteAddress());
                    // 2.关联 selector
                    log.debug("before register...{}",sc.getRemoteAddress());
                    // round robin轮询
                    workers[index.getAndIncrement() % workers.length].register(sc);
                    log.debug("after register...{}",sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();
        private String name;

        private volatile boolean start = false;//还未初始化

        public Worker(String name) {
            this.name = name;
        }

        // 初始化线程，和selector
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                thread = new Thread(this,name);
                selector = Selector.open();
                thread.start();
                start = true;
            }
            // 向队列添加了任务，但任务并没有立刻执行
            queue.add(()->{
                try {
                    sc.register(selector,SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup(); // 唤醒selector

        }

        @Override
        public void run() {
            while(true){
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if(task != null){
                        task.run();// 执行了sc.register(selector,SelectionKey.OP_READ,null)
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            log.debug("read...{}",channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
