package com.wddmg.competition.week346;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/05/21 10:29 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        String s = "egcfe";
        String s2 = "abcd";
        String s3 = "seven";
        String res = makeSmallestPalindrome(s3);
        System.out.println(res);
    }

    public static String makeSmallestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int length = chars.length;
        int p = 0;
        int q = 0;
        if ((length & 1) == 0) {
            int mid = length / 2;
            p = mid - 1;
            q = mid;
        }else{
            int mid = length / 2;
            p = q = mid;
        }
        while(p >= 0 && q < length){
            if(chars[p] != chars[q]){
                if(chars[p] - chars[q] < 0){
                    chars[q--] = chars[p++];
                }else{
                    chars[p++] = chars[q--];
                }
            }else{
                p--;
                q++;
            }
        }
        return new String(chars);
    }
}
