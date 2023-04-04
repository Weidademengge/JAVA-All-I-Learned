package com.wddmg.competition.week338;

/**
 * @author duym
 * @version $ Id: Test6354, v 0.1 2023/03/26 10:32 duym Exp $
 */
public class Test6354 {
    public static void main(String[] args) {
        // 示例1
//        int numOnes = 3, numZeros = 2, numNegOnes = 0, k = 2;
        // 示例2
        int numOnes = 3, numZeros = 2, numNegOnes = 0, k = 4;
        int res = kItemsWithMaximumSum(numOnes,numZeros,numNegOnes,k);
        System.out.println(res);
    }

    public static int kItemsWithMaximumSum(int numOnes, int numZeros, int numNegOnes, int k) {
        int res = 0,positive = numOnes,zero = numZeros,negative = numNegOnes;
        while(k > 0){
            if(positive > 0){
                res++;
                k--;
                positive--;
                continue;
            }
            if(zero > 0){
                k--;
                zero--;
                continue;
            }
            if (negative > 0) {
                res--;
                k--;
                negative--;
            }
        }
        return res;
    }
}
