package com.duym.nio.c3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author duym
 * @version $ Id: TestFileChannelTransferTo, v 0.1 2023/03/23 15:24 duym Exp $
 */
public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("my-netty/my-nio/data.txt").getChannel();
                FileChannel to = new FileOutputStream("my-netty/my-nio/to.txt").getChannel()
        ) {
            // 效率高，底层会利用操作系统的零拷贝进行优化
            long size = from.size();
            // left 剩余多少字节
            for(long left = size;left > 0;){
                System.out.println("position:"+(size-left)+"left:"+left);
                //第一个参数是从哪传，第二个传多少，第三个传给谁
                left -= from.transferTo((size - left),left,to);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
