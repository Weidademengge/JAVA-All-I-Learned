package com.wddmg.competition.week345;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/05/14 10:25 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        int[][] grid = {{2, 4, 3, 5}, {5, 4, 9, 3}, {3, 4, 2, 11}, {10, 9, 13, 15}};
        int res = maxMoves(grid);
        System.out.println(res);
    }

    public static int maxMoves(int[][] grid) {
        int row = grid.length;
        int count = 0;
        int res = 0;
        for (int i = 0; i < row; i++) {
            res = dfsCount(grid, i, 0, grid[i][0], 0);
            count = Math.max(count, res);
        }
        return count;
    }

    public static int dfsCount(int[][] grid, int i, int j, int preValue, int count) {
        int row = grid.length;
        int col = grid[0].length;

        if (i < 0 || i > row - 1 || j > col - 1) {
            return count;
        }
        if (i - 1 >= 0 && j + 1 < col && preValue < grid[i - 1][j + 1]) {
            dfsCount(grid, i - 1, j + 1, grid[i-1][j+1], count+1);
        }
        if (j + 1 < col && preValue < grid[i][j + 1]) {
            dfsCount(grid, i, j + 1, grid[i][j+1], count+1);
        }
        if (i + 1 < row && j + 1 < col && preValue < grid[i + 1][j + 1]) {
            dfsCount(grid, i + 1, j + 1, grid[i + 1][j + 1], count + 1);
        }

        return count;
    }
}
