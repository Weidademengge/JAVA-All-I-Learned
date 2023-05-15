package com.wddmg.algorithm.graph.leetcode200;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 岛屿问题
 * 广度优先
 *
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/08 11:11 duym Exp $
 */
public class Test1 {

    public static void main(String[] args) {
        char[][] grid = {{'1', '1', '1', '1', '0'}
                , {'1', '1', '0', '1', '0'}
                , {'1', '1', '0', '0', '0'}
                , {'0', '0', '0', '0', '0'}};

        int res = numIslands(grid);
        System.out.println(res);
    }

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int gr = grid.length;
        int gc = grid[0].length;
        int count = 0;

        for (int i = 0; i < gr; i++) {
            for (int j = 0; j < gc; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    grid[i][j] = '0';
                    Queue<Integer> queue = new LinkedList<>();
                    queue.add(i * gc + j);
                    while (!queue.isEmpty()) {
                        Integer index = queue.remove();
                        int r = index / gc;
                        int c = index % gc;
                        if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                            queue.add((r - 1) * gc + c);
                            grid[r - 1][c] = '0';
                        }
                        if (r + 1 < gr && grid[r + 1][c] == '1') {
                            queue.add((r + 1) * gc + c);
                            grid[r + 1][c] = '0';
                        }
                        if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                            queue.add(r * gc + c - 1);
                            grid[r][c - 1] = '0';
                        }
                        if (c + 1 < gc && grid[r][c + 1] == '1') {
                            queue.add(r * gc + c + 1);
                            grid[r][c + 1] = '0';
                        }
                    }
                }
            }
        }
        return count;
    }

}
