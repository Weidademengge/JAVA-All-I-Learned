package com.wddmg.competition.week337;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/03/19 11:44 duym Exp $
 */
public class Test {
    public static void main(String[] args) {
        int[] arr = {1,2,3};
        List<List<Integer>> res = subSet04(arr);
        System.out.println(res);

    }
    public static List<List<Integer>> subSet04(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> set = new ArrayList<>();
//        1、从2^n-1 —— 1遍历每一位数，即每一种子集组合
        for (int i = (int)Math.pow(2,arr.length)-1; i > 0; i--) {
//            遍历该数的二进制位，构造单个子集。
            ArrayList<Integer> sub = new ArrayList<>();
            for (int j = arr.length-1; j >= 0; j--) {
                //若二进制第j+1位==1，则arr[j]加入子集
                if (((i >> j) & 1) == 1) {
                    sub.add(arr[j]);
                }
            }
            set.add(sub);
        }
        return set;
    }

}
