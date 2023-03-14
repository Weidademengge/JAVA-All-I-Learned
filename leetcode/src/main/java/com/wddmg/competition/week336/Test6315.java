package com.wddmg.competition.week336;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test6315, v 0.1 2023/03/12 10:32 duym Exp $
 */
public class Test6315 {
    public static void main(String[] args) {
        String[] strArr = {"are","amy","u"};
        int res = vowelStrings(strArr,0,2);
        System.out.println(res);
    }

    public static int vowelStrings(String[] words, int left, int right) {
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("e");
        set.add("i");
        set.add("o");
        set.add("u");
        int res = 0;
        for(int i = left;i <= right;i++){
            int len  = words[i].length();
            if(set.contains(String.valueOf(words[i].charAt(0))) && set.contains(String.valueOf(words[i].charAt(len -1)))){
                res++;
            }
        }
        return res;
    }
}
