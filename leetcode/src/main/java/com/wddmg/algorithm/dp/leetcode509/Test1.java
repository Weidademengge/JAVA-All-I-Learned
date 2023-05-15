package com.wddmg.algorithm.dp.leetcode509;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/05 21:04 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int n = 4;
        int res = fib(n);
        System.out.println(res);
    }

    public static int fib(int n) {
        if(n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        return fib(n-1)+fib(n-2);
    }
}
