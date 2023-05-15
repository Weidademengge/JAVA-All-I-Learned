package com.wddmg.algorithm.dp.leetcode62;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/06 8:41 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int m = 3, n = 7;
        int res = uniquePaths(m, n);
        System.out.println(res);
    }

    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        dp[0][1] = 1;
        dp[1][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                }else{
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}
