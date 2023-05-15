package com.wddmg.competition.week343;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/05/02 12:38 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int[] arr = {2,8,7,4,1,3,5,6,9};
        int[][] mat = {{3,2,5},{1,4,6},{8,7,9}};

//        int[] arr1 = {2,8,7,4,1,3,5,6,9};
//        int[][] mat2 = {{3,2,5},{1,4,6},{8,7,9}};

        int res = firstCompleteIndex(arr, mat);
        System.out.println(res);
    }
    private static List<Long> rowSum = new ArrayList<>();
    private static List<Long> colSum = new ArrayList<>();
    public static int firstCompleteIndex(int[] arr, int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        for (int i = 0; i < m; i++) {
            long sum = 0;
            for (int j = 0; j < n; j++) {
                sum += mat[i][j];
            }
            rowSum.add(sum);
        }
        for (int i = 0; i < n; i++) {
            long sum = 0;
            for (int j = 0; j < m; j++) {
                sum += mat[j][i];
            }
            colSum.add(sum);
        }
        for (int k = 0; k < arr.length; k++) {
            for (int i = 0; i < m; i++) {
                long rs = 0;
                for (int j = 0; j < n; j++) {
                    rs += mat[i][j];
                }

            }
        }
        return -1;
    }
}
