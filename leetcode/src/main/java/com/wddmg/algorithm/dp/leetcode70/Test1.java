package com.wddmg.algorithm.dp.leetcode70;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/05 21:25 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int n = 41;
        int res = climbStairs(n);
        System.out.println(res);
    }


    public static int climbStairs(int n) {
        if(n <4){
            return n;
        }
        return climbStairs(n-1)+climbStairs(n-2);
    }
}
