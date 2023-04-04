package com.duym.stream.c5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author duym
 * @version $ Id: StreamDemo12, v 0.1 2023/03/31 22:37 duym Exp $
 */
public class StreamDemo12 {
    public static void main(String[] args) {
        /**
         * 需求：
         *  创建一个ArrayList集合，并添加以下字符串，字符串前面是名字，后面是年龄
         *  "zhangsan,23"
         *  "lisi,24"
         *  "wangwu,25"
         *  保留年龄大于等于24岁的人，并将结果收集到map集合中，姓名为键，年龄为值
         */

        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,"zhangsan,23","lisi,24","wangwu,25");

        Map<String, String> resMap = list.stream()
                .filter(s -> Integer.parseInt(s.split(",")[1]) >= 24)
                .collect(Collectors.toMap(
                        s -> s.split(",")[0],
                        s -> s.split(",")[1]));
        System.out.println(resMap);
    }
}
