package com.wddmg.algorithm.graph.leetcode200;

/**
 * 岛屿问题
 * 深度优先
 *
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/08 11:11 duym Exp $
 */
public class Test2 {

    public static void main(String[] args) {
        char[][] grid = {{'1','1','1','1','0'}
                        ,{'1','1','0','1','0'}
                        ,{'1','1','0','0','0'}
                        ,{'0','0','0','0','0'}};

        int res = numIslands(grid);
        System.out.println(res);
    }

    public static int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0){
            return 0;
        }
        int gr = grid.length;
        int gc = grid[0].length;
        int count = 0;
        for (int i = 0; i < gr; i++) {
            for (int j = 0; j < gc; j++) {
                if(grid[i][j] == '1'){
                    count++;
                    dfs(grid,i,j);
                }
            }
        }
        return count;
    }

    private static void dfs(char[][] grid, int r, int c) {
        int gr = grid.length;
        int gc = grid[0].length;

        if (r < 0 || c < 0 || r >= gr || c >= gc || grid[r][c] == '0') {
            return;
        }
        grid[r][c] = '0';
        dfs(grid, r - 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
        dfs(grid, r + 1, c);
    }
}
