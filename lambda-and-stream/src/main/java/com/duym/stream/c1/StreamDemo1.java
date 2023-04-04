package com.duym.stream.c1;

import java.util.Arrays;
import java.util.List;

/**
 * @author duym
 * @version $ Id: StreamDemo1, v 0.1 2023/03/31 16:20 duym Exp $
 */
public class StreamDemo1 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("张无忌","周芷若","赵敏","张强","张三丰");
        /**
         * 要张开头，名字3个字的打印出来
         */
        list.stream()
                .filter(name->name.startsWith("张"))
                .filter(name->name.length()==3)
                .forEach(name-> System.out.println(name));

    }
}
