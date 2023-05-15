package com.wddmg.algorithm.dp.leetcode343;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/06 9:40 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int n = 10;
        int res = integerBreak(n);
        System.out.println(res);
    }

    public static int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j < i/2; j++) {
                dp[i] = Math.max(Math.max(j * (i - j), j * dp[i - j]),dp[i]);
            }
        }
        return dp[n];
    }
}
