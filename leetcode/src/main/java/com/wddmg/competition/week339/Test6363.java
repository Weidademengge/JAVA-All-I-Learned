package com.wddmg.competition.week339;

import java.util.*;

/**
 * @author duym
 * @version $ Id: Test6363, v 0.1 2023/04/02 10:54 duym Exp $
 */
public class Test6363 {
    public static void main(String[] args) {
        // 示例1
//        int[] nums = {1,3,4,1,2,3,1};

        // 示例2
        int[] nums = {1,3,4,2};

        List<List<Integer>> res = findMatrix(nums);
        System.out.println(res);
    }

    public static List<List<Integer>> findMatrix(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> numsList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            numsList.add(nums[i]);
        }

        while(!numsList.isEmpty()){
            Set<Integer> set = new HashSet<>();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < numsList.size(); i++) {
                int temp = numsList.get(i);
                if(!set.contains(temp) && temp != 0){
                    list.add(temp);
                    set.add(temp);
                    numsList.set(i,0);
                }
            }
            for (int i = 0; i < numsList.size(); i++) {
                if(numsList.get(i) == 0){
                    numsList.remove(i);
                }
            }
            if(list.size() != 0){
                res.add(list);
            }
        }

        return res;
    }
}
