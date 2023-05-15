package com.wddmg.competition.week344;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/07 10:26 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5};
        int[] res = distinctDifferenceArray(nums);
        System.out.println(Arrays.toString(res));
    }
    public static int[] distinctDifferenceArray(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = diffCount(nums,i);
        }
        return res;
    }

    private static int diffCount(int[] nums,int index){
        Set<Integer> preSet = new HashSet<>();
        Set<Integer> postSet = new HashSet<>();
        for (int i = 0; i <= index; i++) {
            preSet.add(nums[i]);
        }
        for(int i = index+1;i<nums.length;i++){
            postSet.add(nums[i]);
        }
        return preSet.size() - postSet.size();
    }
}
