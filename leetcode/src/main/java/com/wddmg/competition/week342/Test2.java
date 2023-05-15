package com.wddmg.competition.week342;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/23 10:33 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int n = 10;
        int res = sumOfMultiples(n);
        System.out.println(res);
    }
    public static int sumOfMultiples(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if(i % 3 == 0 || i % 5 == 0 || i % 7 ==0){
                res += i;
            }
        }
        return res;
    }
}
