package com.wddmg.competition.week340;

import java.math.BigInteger;
import java.util.*;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/09 10:28 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        // 示例1
        int[] nums = {1, 3, 1, 1, 2};
        // 示例2
        int[] nums2 = {0, 5, 3};
        long[] res = distance(nums);
        System.out.println(Arrays.toString(res));

    }

    public static long[] distance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int len = nums.length;
        long[] res = new long[len];
        for (int i = 0; i < len; i++) {
            if (map.get(nums[i]) == null) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(nums[i], list);
            } else {
                List<Integer> list = map.get(nums[i]);
                list.add(i);
            }
        }
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            List<Integer> list = map.get(iterator.next());
            for (int i = 0; i < list.size(); i++) {
                res[list.get(i)] = culDis(list.get(i), list);
            }
        }
        return res;
    }

    private static long culDis(int x, List<Integer> list) {
        long dis = 0;
        for (int i = 0; i < list.size(); i++) {
            if (x != list.get(i)) {
                dis += Math.abs(list.get(i) - x);
            }
        }
        return dis;
    }


}
