package com.wddmg.algorithm.dp.leetcode746;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/05 21:37 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[] cost = {10, 15, 20};
        int[] cost1 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        int res = minCostClimbingStairs(cost);
        System.out.println(res);
    }

    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        if(n == 2){
            return Math.min(cost[0],cost[1]);
        }
        int[] dp = new int[n + 1];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < n - 1; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        dp[n-1] = Math.min(dp[n-2],dp[n-3]+cost[n-1]);
        return dp[n - 1];
    }
}
