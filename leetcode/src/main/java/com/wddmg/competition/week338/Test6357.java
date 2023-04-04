package com.wddmg.competition.week338;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test6357, v 0.1 2023/03/26 11:39 duym Exp $
 */
public class Test6357 {
    public static void main(String[] args) {
        // 示例1
//        int[] nums = {3,1,6,8};
//        int[] queries = {1,5};
        // 示例2
//        int[] nums = {2,9,6,3};
//        int[] queries = {10};

        // 示例3
        int[] nums = {47,50,97,58,87,72,41,63,41,51,17,21,7,100,69,66,79,92,84,9,57,26,26,28,83,38};
//        int[] queries = {50,84,76,41,64,82,20,22,64,7,38,92,39,28,22,3,41,46,47,50,88,51,9,49,38,67,26,65,89,27,71,25,77,72,65,41,84,68,51,26,84,24,79,41,96,83,92,9,93,84,35,70,74,79,37,38,26,26,41,26};
        int[] queries = {7};
        List<Long> res = minOperations(nums,queries);
        System.out.println(res);
    }

    public static List<Long> minOperations(int[] nums, int[] queries) {
        int numsLen = nums.length,qryLen = queries.length;
        Arrays.sort(nums);
        int max = nums[numsLen -1];
        int min = nums[0];
        int maxTemp = 0;
//        int minTemp = 0;
        for(int i = 0;i<numsLen;i++){
            maxTemp += Math.abs(max - nums[i]);
//            minTemp += Math.abs(min - nums[i]);
        }
        List<Long> res = new ArrayList<>();

        for(int i = 0;i < qryLen;i++){
            long tempSum = 0;
            for(int j = 0;j < numsLen;j++){
                if(queries[i] > max){
                    tempSum += maxTemp + (queries[i] - max) * numsLen;
                    break;
                }
//                else if(queries[i] < min){
//                    tempSum += minTemp + (queries[i] - min) * numsLen;
//                    break;
//                }
                tempSum += Math.abs(nums[j] - queries[i]);
            }
            res.add(tempSum);
        }
        return res;
    }
}
