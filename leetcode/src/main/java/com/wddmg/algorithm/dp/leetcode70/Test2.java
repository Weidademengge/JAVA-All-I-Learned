package com.wddmg.algorithm.dp.leetcode70;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/05 21:25 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int n = 41;
        int res = climbStairs(n);
        System.out.println(res);
    }


    public static int climbStairs(int n) {
        if(n <4){
            return n;
        }
        int[] dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        for (int i = 4; i <= n; i++) {
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n];
    }
}
