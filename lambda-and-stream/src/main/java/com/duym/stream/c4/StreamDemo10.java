package com.duym.stream.c4;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author duym
 * @version $ Id: StreamDemo10, v 0.1 2023/03/31 21:10 duym Exp $
 */
public class StreamDemo10 {
    public static void main(String[] args) {
        /**
         * collect(Collector  collector)        收集流中的数据，放到集合中（List Set Map）
         *
         * 注意点：
         *      键不能重复
         */
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,"张无忌-男-15","周芷若-女-14","赵敏-女-13","张强-男-20","张三丰-男-100","张翠山-男-40","张良-男-35","王二麻子-男-37");

        //收集list集合中
        // 需求:
        // 我要把所有的男性收集起来
        List<String> newList = list.stream()
                .filter(s -> "男".equals(s.split("-")[1]))
                .collect(Collectors.toList());

        System.out.println(newList);

        //收集Set集合中
        // 需求:
        // 我要把所有的男性收集起来
        Set<String> newList2 = list.stream()
                .filter(s -> "男".equals(s.split("-")[1]))
                .collect(Collectors.toSet());

        System.out.println(newList2);

        // 收集Map集合中
        // 需求:
        // 我要把所有的男性收集起来
        // key：姓名，值：年龄
        Map<String, Integer> map = list.stream()
                .filter(s -> "男".equals(s.split("-")[1]))
                .collect(Collectors.toMap(
                        s -> s.split("-")[0],
                        s -> Integer.parseInt(s.split("-")[2])
                ));
        System.out.println(map);
    }
}
