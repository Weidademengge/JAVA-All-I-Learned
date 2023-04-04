package com.wddmg.competition.week337;

import java.util.*;

/**
 * @author duym
 * @version $ Id: Test6321, v 0.1 2023/03/19 11:23 duym Exp $
 */
public class Test6321 {
    public static void main(String[] args) {
        int[] nums = {2,4,6};
        int k = 2;
        int res = beautifulSubsets(nums,k);
        System.out.println(res);
    }

    public static int beautifulSubsets(int[] nums, int k) {
        int len = nums.length;
        if(len == 1 && nums[0] == k){
            return 0;
        }
        List<List<Integer>> list = new ArrayList<>();
        for (int i = (int)Math.pow(2,nums.length)-1; i > 0; i--) {
            ArrayList<Integer> sub = new ArrayList<>();
            for (int j = nums.length-1; j >= 0; j--) {
                if (((i >> j) & 1) == 1) {
                    sub.add(nums[j]);
                }
            }
            list.add(sub);
        }
        int listSize = list.size();
        for(int i = 0;i<listSize;i++){
            List<Integer> temp = list.get(i);
            for(int j = 0;j<temp.size();j++){
                if(temp.contains(temp.get(j)+k)){
                    temp.clear();
                }
            }
        }
        int res = listSize;
        for(int i = 0;i<listSize;i++){
            if(list.get(i).size() == 0){
                res--;
            }
        }
        return res;
    }
}
