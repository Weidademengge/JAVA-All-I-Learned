package com.wddmg.algorithm.dp.leetcode63;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/05/06 9:04 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[][] obstacleGrid = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        int[][] obstacleGrid2 = {{0, 1}, {0, 0}};
        int[][] obstacleGrid3 = {{0, 0}};
        int[][] obstacleGrid4 = {{1, 0}};
        int[][] obstacleGrid5 = {{1},{0}};
        int[][] obstacleGrid6 = {{0},{0}};
        int res = uniquePathsWithObstacles(obstacleGrid5);
        System.out.println(res);
    }

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        int[][] dp = new int[row + 1][col + 1];
        boolean rowFlag = false;
        boolean colFlag = false;
        if(obstacleGrid[0][0] == 1){
            return 0;
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(obstacleGrid[i][j] == 1 && i == 0){
                    rowFlag = true;
                    dp[i][j] = 0;
                }else if(obstacleGrid[i][j] == 1 && j == 0){
                    colFlag = true;
                    dp[i][j] = 0;
                }else if(obstacleGrid[i][j] == 1){
                    dp[i][j] = 0;
                }else if((i == 0 && rowFlag) || (j ==0 && colFlag)){
                    dp[i][j] = 0;
                }else if(i == 0 || j == 0){
                    dp[i][j] = 1;
                }else{
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[row-1][col-1];
    }
}
