package com.duym.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author duym
 * @version $ Id: TestByteBuffer, v 0.1 2023/03/23 10:02 duym Exp $
 */
@Slf4j
public class TestByteBuffer {

    public static void main(String[] args) {
        //FileChannel
        //1、输入输出流， 2、RandomAccessFile
        try (FileChannel channel = new FileInputStream("my-netty/my-nio/data.txt").getChannel()) {
            //准备缓冲器
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while(true){
                //从channel读取数据，向buffer写入
                int len = channel.read(buffer);
                log.debug("读取到的字节{}",len);
                if(len == -1){
                    break;
                }

                //切换至读模式
                buffer.flip();
                //是否还有剩余未读
                while(buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.debug("实际字节{}",(char) b );
//                    System.out.println((char) b);
                }
                //切换为写模式
                buffer.clear();
            }
        } catch (IOException e) {
        }
    }
}
