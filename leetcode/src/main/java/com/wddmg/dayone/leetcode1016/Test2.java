package com.wddmg.dayone.leetcode1016;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/05/11 9:24 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        String s = "0110";
        int n = 3;
        boolean res = queryString(s, n);
        System.out.println(res);
    }

    public static boolean queryString(String s, int n) {
        for (int i = 1; i <= n; i++) {
            if(!s.contains(Integer.toBinaryString(i))){
                return false;
            }
        }
        return true;
    }
}
