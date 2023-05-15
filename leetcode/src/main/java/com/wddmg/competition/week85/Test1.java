package com.wddmg.competition.week85;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/20 21:02 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[] rec1 = {0,0,2,2};
        int[] rec2 = {1,1,3,3};

        int[] rec11 = {0,0,1,1};
        int[] rec12 = {1,0,2,1};

        int[] rec31 = {0,0,1,1};
        int[] rec32 = {2,2,3,3};

        int[] rec41 = {7,8,13,15};
        int[] rec42 = {10,8,12,20};

        int[] rec51 = {2,17,6,20};
        int[] rec52 = {3,8,6,20};
        boolean res = isRectangleOverlap(rec1, rec2);
        System.out.println(res);
    }
    public static boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        if(rec2[0] >= rec1[2] || rec2[1] >= rec1[3] || rec2[2] <= rec1[0] || rec2[3] <= rec1[1]){
            return false;
        }
        return true;
    }
}
