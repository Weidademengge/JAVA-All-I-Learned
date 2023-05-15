package com.wddmg.competition.week86;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/21 19:29 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[][] grid = {{4,3,8,4},{9,5,1,9},{2,7,6,2}};
        int res = numMagicSquaresInside(grid);
        System.out.println(res);
    }

    public static int numMagicSquaresInside(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int res = 0;
        for (int i = 0; i < row - 2; i++) {
            for (int j = 0; j < col - 2; j++) {
                if (grid[i + 1][j + 1] != 5) {
                    continue;
                } else {
                    Set<Integer> set = new HashSet<>();
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if(grid[i+k][j+l] < 10 && grid[i+k][j+l] > 0){
                                set.add(grid[i+k][j+l]);
                            }
                        }
                    }
                    if (set.size() == 9
                            && grid[i][j] + grid[i + 1][j] + grid[i + 2][j] == 15
                            && grid[i][j + 1] + grid[i + 1][j + 1] + grid[i + 2][j + 1] == 15
                            && grid[i][j + 2] + grid[i + 1][j + 2] + grid[i + 2][j + 2] == 15
                            && grid[i][j] + grid[i][j + 1] + grid[i][j + 2] == 15
                            && grid[i + 1][j] + grid[i + 1][j + 1] + grid[i + 1][j + 2] == 15
                            && grid[i + 2][j] + grid[i + 2][j + 1] + grid[i + 2][j + 2] == 15
                            && grid[i][j] + grid[i + 1][j + 1] + grid[i + 2][j + 2] == 15
                            && grid[i][j + 2] + grid[i + 1][j + 1] + grid[i + 2][j] == 15
                    ){
                        res++;
                    }
                }
            }
        }
        return res;
    }
}
