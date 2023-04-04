package com.wddmg.competition.doubleweek102;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test6327, v 0.1 2023/04/01 22:31 duym Exp $
 */
public class Test6327 {
    public static void main(String[] args) {
    }

    public int minNumber(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        int min = 10;
        Boolean f= false;
        for(int i = 0;i <nums1.length;i++){
            set.add(nums1[i]);
        }
        for(int i = 0;i<nums2.length;i++){
            if(set.contains(nums2[i])){
                if(min > nums2[i]){
                    f =true;
                    min = nums2[i];
                }
            }
        }
        if(f){
            return min;
        }
        int min1 = findMin(nums1);
        int min2 = findMin(nums2);
        return min1 < min2 ? min1 * 10 + min2 : min2 * 10 + min1;
    }

    private static int findMin(int[] nums) {
        int min = 10;
        for (int i = 0; i < nums.length; i++) {
            if (min > nums[i]) {
                min = nums[i];
            }
        }
        return min;
    }
}
