package com.wddmg.competition.week341;

import java.util.Arrays;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/16 10:26 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        // 示例1
        int[][] mat = {{0,1},{1,0}};

        // 示例2
        int[][] mat2 = {{0,0,1},{0,1,1}};

        // 示例3
        int[][] mat3 = {{0,0},{1,1},{0,0}};
        int[] res = rowAndMaximumOnes(mat3);
        System.out.println(Arrays.toString(res));
    }

    public static int[] rowAndMaximumOnes(int[][] mat) {
        int index = 0;
        int count = 0;

        for (int i = 0; i < mat.length; i++) {
            int curCount = 0;
            for (int j = 0; j < mat[i].length; j++) {
                if(mat[i][j] == 1){
                    curCount++;
                }
            }
            if(count < curCount){
                count = curCount;
                index = i;
            }
        }
        return new int[] {index,count};
    }
}
