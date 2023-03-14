package com.wddmg.competition.week336;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test6316, v 0.1 2023/03/12 10:46 duym Exp $
 */
public class Test6316 {
    public static void main(String[] args) {
//        实例1
//        int[] nums = {2,-1,0,1,-3,3,-3};

        //实例2
//        int[] nums = {-2,-3,0};

        //示例3
        int[] nums = {-1,-1};
        int res = maxScore(nums);
        System.out.println(res);
    }

    public static int maxScore(int[] nums) {
        int res = 0;
        List<Integer> list=  new ArrayList<>();
        for(int i =0;i<nums.length;i++){
            list.add(nums[i]);
        }
        Collections.sort(list);
        Collections.reverse(list);
        long max = 0;
        for(int i = 0;i<list.size();i++){
            max += list.get(i);
            if(max > 0){
                res++;
            }
        }
        return res;
    }

}
