package com.wddmg.competition.week338;

/**
 * @author duym
 * @version $ Id: Test6355, v 0.1 2023/03/26 10:44 duym Exp $
 */
public class Test6355 {
    public static void main(String[] args) {
        // 示例1
//        int[] nums = {4,9,6,10};

        // 示例2
//        int[] nums = {6,8,11,12};

        // 示例3
//        int[] nums = {5,8,3};

        // 示例4
//        int[] nums = {8,19,3,4,9};

        // 示例5
        int[] nums = {98,21,9,53,72,13,94,81,68,67};

//        int r = findMaxPrime(3);

        boolean res = primeSubOperation(nums);
        System.out.println(res);
    }

    public static boolean primeSubOperation(int[] nums) {
        if(isIncrease(nums)){
            return true;
        }
        int len = nums.length;
        nums[0] = nums[0] - findMaxPrime(nums[0],0);
        for(int i = 1;i < len;i++){
            nums[i] = nums[i] - findMaxPrime(nums[i],nums[i - 1]);
            if(isIncrease(nums)){
                return true;
            }
        }
        return false;
    }

    private static boolean isIncrease(int[] nums){
        int pre = 0,next = 1,len = nums.length;
        while(next < len){
            if(nums[pre] < nums[next]){
                pre++;
                next++;
                continue;
            }else{
                return false;
            }
        }
        return true;
    }
    private static int findMaxPrime(int num, int pre){
        int res = 0;
        for(int i = num - 1;i > 0;i--){
            boolean flag = true;
            for(int j = 2;j < i;j++){
                if(i % j == 0){
                    flag = false;
                    break;
                }
            }
            if(!flag){
                continue;
            }else if(flag && i != 1 && num - i > pre){
                res = i;
                return res;
            }
        }
        return 0;
    }
}
