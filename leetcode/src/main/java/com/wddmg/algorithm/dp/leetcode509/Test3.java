package com.wddmg.algorithm.dp.leetcode509;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/05 21:04 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        int n = 4;
        int res = fib(n);
        System.out.println(res);
    }

    public static int fib(int n) {
        if (n < 2) {
            return n;
        }
        int p = 0, q = 0, r = 1;
        for (int i = 2; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }
}
