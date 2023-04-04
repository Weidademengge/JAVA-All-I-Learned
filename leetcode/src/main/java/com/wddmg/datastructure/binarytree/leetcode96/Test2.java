package com.wddmg.datastructure.binarytree.leetcode96;

/**
 * 数学：卡塔兰数
 * 时间：100%，空间：79.83%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/03 17:19 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int n = 3;
        int res = numTrees(3);
        System.out.println(res);
    }

    public static int numTrees(int n) {
        long C =1;
        for(int i =0;i<n;i++){
            C = C*2*(2*i+1)/(i+2);
        }
        return (int)C;
    }
}
