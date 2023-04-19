package com.wddmg.competition.week84;

import java.util.Arrays;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/04/19 16:13 duym Exp $
 */
public class Test1 {


    public static void main(String[] args) {
        int[][] arr1 = {{1,1,0,0},{1,0,0,1},{0,1,1,1},{1,0,1,0}};
        int[][] res = flipAndInvertImage(arr1);

        System.out.println(Arrays.toString(res));
    }

    public static int[][] flipAndInvertImage(int[][] image) {
        int row = image.length;
        int col = image[0].length;
        int[][] res = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                res[i][j] = image[i][row - 1 - j] == 1?0:1;
            }
        }
        return res;
    }
}
