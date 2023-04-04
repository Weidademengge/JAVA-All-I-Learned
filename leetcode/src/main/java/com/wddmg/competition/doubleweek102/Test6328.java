//package com.wddmg.competition.doubleweek101;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author duym
// * @version $ Id: Test6328, v 0.1 2023/04/01 22:44 duym Exp $
// */
//public class Test6328 {
//    public static void main(String[] args) {
////        String s = "adaa";
////        String chars = "d";
////        int[] vals = {-1000};
////        int res = maximumCostSubstring(s,chars,vals);
//
////        String s = "abc";
////        String chars = "abc";
////        int[] vals = {-1,-1,-1};
////        int res = maximumCostSubstring(s,chars,vals);
//
//        String s = "kqqqqqkkkq";
//        String chars = "kq";
//        int[] vals = {-6,6};
//        int res = maximumCostSubstring(s,chars,vals);
//        System.out.println(res);
//    }
//
//    public static int maximumCostSubstring(String s, String chars, int[] vals) {
//        char[] arrS = s.toCharArray();
//        char[] arrChars = chars.toCharArray();
//        int len = arrS.length;
//        List<List<Character>> charList = new ArrayList<>();
//
//        for(int i = 0; i < s.length(); i++ ) {
//            List<Character> list = new ArrayList<>();
//            for(int j = i+1; j<=s.length(); j++) {
//                String substr = s.substring(i,j).toCh;
//                list.add();
//                int num = Integer.parseInt(substr);
//                sum += num;
//            }
//        }
//
//        for (int i = 0; i < len; i++) {
//            List<Character> list = new ArrayList<>();
//            for (int j = i; j < len; j++) {
//                list.add(arrS[j]);
//            }
//            charList.add(list);
//        }
//        int res = 0;
//        for (List<Character> list : charList) {
//            int temp = calculate(list, arrChars, vals);
//            if(res < temp){
//                res = temp;
//            }
//        }
//        return res;
//    }
//
//    private static int calculate(List<Character> list, char[] arrChars, int[] vals) {
//        int sum = 0;
//        for (char c : list) {
//            int index = containAndReturnIndex(c, arrChars);
//            if (index != -1) {
//                sum += vals[index];
//            } else {
//                sum += new Integer(c) - 96;
//            }
//        }
//
//        return sum < 0 ? 0 : sum;
//    }
//
//
//    private static int containAndReturnIndex(char c, char[] arrChars) {
//        for (int i = 0; i < arrChars.length; i++) {
//            if (arrChars[i] == c) {
//                return i;
//            }
//        }
//        return -1;
//    }
//}
