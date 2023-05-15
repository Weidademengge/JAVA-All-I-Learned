package com.wddmg.competition.week342;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/23 10:38 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        int[] nums = {1,-1,-3,-2,3};
        int k = 3;
        int x = 2;


        int[] nums1 = {-1,-2,-3,-4,-5};
        int k1 = 2;
        int x1 = 2;

        int[] nums2 = {-3,1,2,-3,0,-3};
        int k2 = 2;
        int x2 = 1;


        int[] res = getSubarrayBeauty(nums2, k2, x2);
        System.out.println(Arrays.toString(res));
    }

    public static int[] getSubarrayBeauty(int[] nums, int k, int x) {
        int len = nums.length;
        int[] res = new int[len - k + 1];
        int count = 0;
        int left = 0;
        int right = k - 1;
        while(right < len){
            int[] tempArr = new int[right+1-left];
            int tempCount = 0;
            for (int i = left; i <= right; i++) {
                tempArr[tempCount++] = nums[i];
            }
            Arrays.sort(tempArr);
            int curNum = tempArr[x - 1];
            res[count++] = curNum > 0? 0 : tempArr[x - 1];
            left++;
            right++;
        }
        return res;
    }
}
