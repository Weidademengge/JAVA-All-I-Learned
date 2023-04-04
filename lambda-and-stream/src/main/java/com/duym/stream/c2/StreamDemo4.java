package com.duym.stream.c2;

import java.util.Arrays;

/**
 * @author duym
 * @version $ Id: StreamDemo4, v 0.1 2023/03/31 16:53 duym Exp $
 */
public class StreamDemo4 {
    public static void main(String[] args) {
        // 数组        public static <T> Stream<T> stream(T[] array)       Arrays工具类中的静态方法

        // 1.创建数组
        int[] arr = {1,2,3,4,5,6,7,8,9,10};

        String[] arr2 = {"a","b","c","d","e"};

        // 2.获取stream流
        Arrays.stream(arr2).forEach(s-> System.out.println(s));
    }
}
