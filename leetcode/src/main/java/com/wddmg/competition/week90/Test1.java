package com.wddmg.competition.week90;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/28 15:36 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        String s = "ab";
        String goal = "ba";

        String s2 = "ab";
        String goal2 = "ab";

        String s3 ="aa";
        String goal3 = "aa";

        String s4 = "abcaa";
        String goal4 = "abcbb";
        boolean res = buddyStrings(s4, goal4);
        System.out.println(res);
    }

    public static boolean buddyStrings(String s, String goal) {
        if(s.length() != goal.length()){
            return false;
        }
        char[] chars = s.toCharArray();
        int len = chars.length;
        char[] charGoal = goal.toCharArray();
        int[] arr = new int[len];
        int count = 0;
        Set<Character> set = new HashSet<>();
        char[] charArr = new char[4];
        int temp = 0;


        for (int i = 0; i < len; i++) {
            arr[i] = chars[i] ^ charGoal[i];
            set.add(chars[i]);
            if(arr[i] != 0){
                count++;
                if(count>2){
                    return false;
                }
                charArr[temp++] = chars[i];
                charArr[temp++] = charGoal[i];
            }
        }
        if(count == 2 || set.size() < len ){
            if( charArr[0] == charArr[3] && charArr[1] == charArr[2]){
                return true;
            }
            return false;
        }
        return false;
    }
}
