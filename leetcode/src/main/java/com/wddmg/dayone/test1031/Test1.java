package com.wddmg.dayone.test1031;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/26 13:57 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[] nums = {0,6,5,2,2,5,1,9,4};
        int f = 1;
        int s = 2;

        int[] nums2 = {3,8,1,3,2,1,8,9,0};
        int f2 = 3;
        int s2 = 2;

        int res = maxSumTwoNoOverlap(nums2, f2, s2);
        System.out.println(res);
    }

    public static int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int res = 0;
        int len = nums.length;
        int fLeft = 0;
        int fRight = firstLen - 1;

        while(fRight < len){
            int firstSum = sum(nums, fLeft, fRight);
            int sLeft = 0;
            int sRight = secondLen - 1;
            while(sLeft < len && sRight < len){
                if(sRight< fLeft || sLeft > fRight){
                    int secondSum = sum(nums,sLeft,sRight);
                    res = Math.max(res,firstSum+secondSum);
                    sLeft++;
                    sRight++;
                }else{
                    sLeft = fRight+1;
                    sRight = sLeft + secondLen - 1;
                }
            }
            fLeft++;
            fRight++;
        }
        return res;
    }

    private static int sum(int[] nums, int left, int right){
        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum+= nums[i];
        }
        return sum;
    }
}
