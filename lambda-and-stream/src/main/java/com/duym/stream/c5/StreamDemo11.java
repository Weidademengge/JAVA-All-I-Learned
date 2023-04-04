package com.duym.stream.c5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duym
 * @version $ Id: StreamDemo11, v 0.1 2023/03/31 22:33 duym Exp $
 */
public class StreamDemo11 {
    public static void main(String[] args) {

        /**
         * 需求： 定义一个集合，并添加一些整数1,2,3,4,5,6,7,8,9,10
         * 过滤奇数，只留下偶数
         * 并将结果保存起来
         */

        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list,1,2,3,4,5,6,7,8,9,10);

        List<Integer> resList = list.stream()
                .filter(value -> (value & 1) == 0)
                .collect(Collectors.toList());
        System.out.println(resList);
    }
}
