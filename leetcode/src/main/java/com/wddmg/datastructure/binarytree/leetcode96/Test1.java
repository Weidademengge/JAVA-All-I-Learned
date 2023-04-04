package com.wddmg.datastructure.binarytree.leetcode96;

/**
 * 这个题正常来说不应该放在树里，这是个动态规划的问题，之后在动态规划再说吧
 * 时间：100，空间：69.4
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/03 17:09 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int n = 3;
        int res = numTrees(3);
        System.out.println(res);
    }

    public static int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j<=i;j++){
                dp[i] += dp[j-1] * dp[i-j];
            }
        }
        return dp[n];
    }
}
