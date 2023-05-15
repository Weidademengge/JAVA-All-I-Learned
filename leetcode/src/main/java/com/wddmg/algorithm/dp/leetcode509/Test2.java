package com.wddmg.algorithm.dp.leetcode509;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/05 21:04 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int n = 4;
        int res = fib(n);
        System.out.println(res);
    }

    public static int fib(int n) {
        int[] dp = new int[n+1];
        dp[0] = 0;
        if(n == 0){
            return 0;
        }
        dp[1] = 1;
        if(n == 1){
            return 1;
        }
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n];
    }
}
