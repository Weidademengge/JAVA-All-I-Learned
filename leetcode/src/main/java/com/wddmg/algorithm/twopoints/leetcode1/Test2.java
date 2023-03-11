package com.wddmg.algorithm.twopoints.leetcode1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. 两数之和
 * esay
 * 双循环遍历
 * 25.9%;81.66%
 */
public class Test2 {
    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum(nums,target)));

    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            if(map.containsKey(target - nums[i])){
                return new int[]{i,map.get(target- nums[i])};
            }
            map.put(nums[i],i);
        }
        return null;
    }
}
