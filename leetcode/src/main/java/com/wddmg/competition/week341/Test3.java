package com.wddmg.competition.week341;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/16 10:26 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        String word = "abc";

        String word2 = "b";

        String word3 ="aaa";
        int res = addMinimum(word);
        System.out.println(res);

    }

    public static int addMinimum(String word) {
        int res = 0;
        char[] chars2 = new char[word.length() + 1];
        chars2[0] = 'c';
        for (int i = 1; i < chars2.length; i++) {
            chars2[i] = word.charAt(i - 1);
            int pre = i - 1;
            int cur = i;
            if (chars2[cur] - chars2[pre] == -1) {
                res++;
            } else if (chars2[cur] - chars2[pre] == 0) {
                res += 2;
            } else if (chars2[cur] - chars2[pre] == 2) {
                res++;
            }
        }
        if (chars2[chars2.length - 1] == 'b') {
            res++;
        } else if (chars2[chars2.length - 1] == 'a') {
            res += 2;
        }
        return res;
    }

}
