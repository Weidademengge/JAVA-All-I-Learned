package com.wddmg.dayone.leetcode2441;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/22 10:04 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
       int[] nums = {-1,10,6,7,-7,1};
       int[] nums2 = {-10,8,6,7,-2,-3};

        int res = findMaxK(nums2);
        System.out.println(res);
    }

    public static int findMaxK(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int len = nums.length;
        int res = -1;
        for (int i = 0; i < len; i++) {
            if (set.contains(-1 * nums[i])) {
                res = Math.max(res,Math.abs(nums[i]));
            }else{
                set.add(nums[i]);
            }
        }
        return res;
    }
}
