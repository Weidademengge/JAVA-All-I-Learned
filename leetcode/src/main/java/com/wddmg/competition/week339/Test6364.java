package com.wddmg.competition.week339;

import java.util.ArrayList;

/**
 * @author duym
 * @version $ Id: Test6364, v 0.1 2023/04/02 11:31 duym Exp $
 */
public class Test6364 {
    public static void main(String[] args) {
        int[] reward1 = {1,1,3,4};
        int[] reward2 = {4,4,1,1};

        int res = miceAndCheese(reward1,reward2,2);
        System.out.println();
    }

    public static int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int reward2Len = reward2.length;
        int remain = reward2Len - k;

        ArrayList<ArrayList<Integer>> r1 = getSubArray(reward1,reward1.length,k);
        ArrayList<ArrayList<Integer>> r2 = getSubArray(reward2,reward2Len,remain);


        return 0;

    }

    private static ArrayList<ArrayList<Integer>> getSubArray(int[] arr,int length,int k) {
        ArrayList<ArrayList<Integer>> bList=new ArrayList<>();
        int mark=0;
        int nEnd=1<<length;
        boolean bNullset=false;
        for (mark=0;mark<nEnd;mark++) {
            ArrayList<Integer> aList=new ArrayList<>();
            bNullset=true;
            for (int i=0;i<length;i++) {
                if (((1<<i)&mark)!=0) {
                    bNullset=false;
                    aList.add(arr[i]);
                }
            }
            bList.add(aList);
        }
        for (int i = 0; i < bList.size(); i++) {
            if(bList.get(i).size() != k){
                bList.remove(i);
            }
        }
        return bList;
    }

}
