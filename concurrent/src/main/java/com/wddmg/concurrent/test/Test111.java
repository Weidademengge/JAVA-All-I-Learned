package com.wddmg.concurrent.test;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author duym
 * @version $ Id: Test111, v 0.1 2023/04/12 15:20 duym Exp $
 */
public class Test111 {
    public static void main(String[] args) {
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map1 = new HashMap<>();
        Map<String,String> map2 = new HashMap<>();
        Map<String,String> map3 = new HashMap<>();
        Map<String,String> map4 = new HashMap<>();
        Map<String,String> map5 = new HashMap<>();
        Map<String,String> map6 = new HashMap<>();
        map1.put("1","a");
        map2.put("1","b");
        map3.put("1","a");
        map4.put("1","b");
        map5.put("1","c");
        map6.put("1","c");
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);

        List<String> collect = list.stream().map(t -> t.get("1")).distinct().collect(Collectors.toList());
        if(collect.size() == list.size()){
            System.out.println(true);
        }
        System.out.println(collect);
    }
}
