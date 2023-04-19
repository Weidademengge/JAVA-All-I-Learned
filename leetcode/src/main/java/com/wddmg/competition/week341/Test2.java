package com.wddmg.competition.week341;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/16 10:26 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {

        int[] nums = {4,7,9,3,9};
        int[] divisors = {5,2,3};

        int[] nums2 = {20,14,21,10};
        int[] divisors2 = {5,7,5};

        int[] nums3 = {12};
        int[] divisors3 = {10,16};
        int res = maxDivScore(nums, divisors);
        System.out.println(res);
    }

    public static int maxDivScore(int[] nums, int[] divisors) {
        int res = 0;
        int score = -1;
        for (int i = 0; i < divisors.length; i++) {
            int curScore = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] % divisors[i] == 0) {
                    curScore++;
                }
            }
            if (score < curScore) {
                score = curScore;
                res = divisors[i];
            }else if( score == curScore){
                if(res > divisors[i]){
                    res = divisors[i];
                }
            }
        }
        return res;
    }
}
