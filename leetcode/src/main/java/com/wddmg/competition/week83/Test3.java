package com.wddmg.competition.week83;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/18 17:13 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        int n = 5;
        int n2 = 9;
        int n3 = 15;
        int n4 = 3;
        int n5 = 10;
        int n100 = 351;
        int res = consecutiveNumbersSum(n100);
        System.out.println(res);
    }

    public static int consecutiveNumbersSum(int n) {
        int res = 1;
        int maxLoop = (int) (Math.sqrt(2 * n) + 1);
        for (int i = 2; i < maxLoop; i++) {
            int avg = n / i;
            int mod = n % i;
            int leftBord = i % 2 == 0 ? i / 2 : i / 2 + 1;
            if (i % 2 == 1 && mod == 0) {
                res++;
            }
            if (i % 2 == 0 && avg - leftBord >= 0) {
                int tempSum = (i - 1) * avg + avg + i / 2;
                if (tempSum == n) {
                    res++;
                }
            }
        }
        return res;
    }
}
