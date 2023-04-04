package com.duym.stream.c2;

import java.util.HashMap;

/**
 * @author duym
 * @version $ Id: StreamDemo3, v 0.1 2023/03/31 16:49 duym Exp $
 */
public class StreamDemo3 {
    public static void main(String[] args) {
        //双列集合     无                                                  无法直接使用stream流
        
        // 1.创建双列集合
        HashMap<String, Integer> map = new HashMap<>();
        // 2.添加数据
        map.put("aaa",111);
        map.put("bbb",222);
        map.put("ccc",333);
        map.put("ddd",444);
        map.put("eee",555);
        // 3.第一种获取stream流
        map.keySet().stream().forEach(s-> System.out.println(s));

        // 4.第二种获取stream流
        map.entrySet().stream().forEach(s-> System.out.println(s));

    }
}
