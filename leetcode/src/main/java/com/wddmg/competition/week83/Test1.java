package com.wddmg.competition.week83;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/18 16:03 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        String s = "abbxxxxzzy";
        String s2 = "abcdddeeeeaabbbcd";
        String s3 = "aaaa";
        List<List<Integer>> lists = largeGroupPositions(s3);
        System.out.println(lists);
    }

    public static List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> res = new ArrayList<>();
        char[] charArr = s.toCharArray();
        int pre = 0;
        int cur = 0;
        int count = 0;
        while (cur < charArr.length) {
            if (charArr[cur] != charArr[pre]) {
                if (count >= 3){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(pre);
                    temp.add(cur - 1);
                    res.add(temp);
                }
                count = 0;
                pre = cur;
            } else {
                cur++;
                count++;
                if(count >= 3 && cur == charArr.length){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(pre);
                    temp.add(cur - 1);
                    res.add(temp);
                }
            }
        }
        return res;
    }
}
