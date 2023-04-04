package com.duym.nio.c2;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.duym.nio.ByteBufferUtil.debugAll;

/**
 * @author duym
 * @version $ Id: TestByteBufferString, v 0.1 2023/03/23 14:15 duym Exp $
 */
public class TestByteBufferString {

    public static void main(String[] args) {

        // 1. 字符串转为ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        debugAll(buffer);

        // 2. Charset,自动切换到读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer2);

        // 3. wrap方法 也是自动切换到读模式
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer3);

        // 反过来拿string
        String str1 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str1);

        // 如果是直接转第一种的读模式，由于没有切换到写模式，position没有归位，所以输出为空，所以使用前需要使用flip
        String str2 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(str1);

    }
}
