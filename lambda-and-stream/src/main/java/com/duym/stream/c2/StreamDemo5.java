package com.duym.stream.c2;

import java.util.stream.Stream;

/**
 * @author duym
 * @version $ Id: StreamDemo5, v 0.1 2023/03/31 16:55 duym Exp $
 */
public class StreamDemo5 {

    public static void main(String[] args) {
        // 一堆零散数据  public static<T> Stream<T> of(T...values)          Stream接口中的静态方法

        Stream.of(1,2,3,4,5).forEach(s-> System.out.println(s));

        Stream.of("a","b","c").forEach(s-> System.out.println(s));
    }
}
