package com.wddmg.competition.week337;

/**
 * @author duym
 * @version $ Id: Test6320, v 0.1 2023/03/19 10:47 duym Exp $
 */
public class Test6320 {
    public static void main(String[] args) {
        // 示例1
//        int[][] grid = {{0,11,16,5,20},{17,4,19,10,15},{12,1,8,21,6},{3,18,23,14,9},{24,13,2,7,22}};
        // 示例2
        int[][] grid ={{0,5,16,7,10,3},{34,26,1,4,17,8},{24,15,6,9,2,11},{27,35,25,20,31,18},{14,23,32,29,12,21},{33,28,13,22,19,30}};
        boolean res = checkValidGrid(grid);
        // 示例3

        System.out.println(res);
    }
    public static boolean checkValidGrid(int[][] grid) {
        if(grid[0][0] != 0){
            return false;
        }
        int cur = 1;
        int[] zeroPosition = find(grid,0);
        int[] onePosition = find(grid, 1);
        int distance = calculateDistance(zeroPosition,onePosition);
        int n = grid.length;
        while(cur < n * n - 1){
            int[] curPosition = find(grid,cur);
            int[] nextPosition = find(grid,cur + 1);
            int tempDistance = calculateDistance(curPosition,nextPosition);
            if(distance == tempDistance){
                cur++;
            }else{
                return false;
            }
        }
        return true;

    }

    private static int calculateDistance(int[] curPosition, int[] nextPosition) {
        if(curPosition[0] == nextPosition[0] || curPosition[1] == nextPosition[1]){
            return 0;
        }
        return (int) (Math.pow(nextPosition[0] - curPosition[0],2) + Math.pow(nextPosition[1] - curPosition[1],2));
    }

    private static int[] find(int[][] grid, int cur) {
        for(int i = 0;i<grid.length;i++){
            for(int j = 0;j<grid[i].length;j++){
                if(grid[i][j] == cur){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }


}
