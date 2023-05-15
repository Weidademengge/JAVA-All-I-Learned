package com.wddmg.competition.week342;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test4, v 0.1 2023/04/23 11:29 duym Exp $
 */
public class Test4 {
    public static void main(String[] args) {
        int[] nums = {2,6,3,4};
        int res = minOperations(nums);
        System.out.println(res);
    }

    public static int minOperations(int[] nums) {
        int res = -1;
        int len = nums.length;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < len; i++) {
            if (nums[i] == 1) {
                int count = len - 1;
                for (int j = i + 1; j < len; j++) {
                    if(nums[j] == 1){
                        count--;
                    }
                }
                return count;
            }else if((nums[i] & 2) == 0){
                
                continue;
            }else {
                if(set.size() > 2){
                    int count = len;
                    for (int j = i + 1; j < len; j++) {
                        if(nums[j] == 1){
                            count--;
                        }
                    }
                    return count;
                }
                for (Integer num : set) {
                    if(nums[i] % num != 0){
                        set.add(nums[i]);
                    }
                }
            }
        }
        return res;
    }
}
