package com.wddmg.competition.week88;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/26 14:48 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        String s = "abc";
        int[] shifts = {3, 5, 9};

        String s2 = "z";
        int[] shifts2 = {52};
        String res = shiftingLetters(s2, shifts2);
        System.out.println(res);
    }

    public static String shiftingLetters(String s, int[] shifts) {

        char[] chars = s.toCharArray();
        int len = chars.length;
        long sum = 0;
        for (int i = 0; i < shifts.length; i++) {
            sum += shifts[i];
        }
        long zero = chars[0] + sum % 26;
        zero = zero < 123 ? zero : zero % 123 + 97;
        chars[0] = (char) zero;
        for (int i = 1; i < len; i++) {
            sum = sum - shifts[i-1];
            long temp = sum % 26;
            long curNum = chars[i] + temp;
            curNum = curNum < 123 ? curNum : curNum % 123 + 97;
            chars[i] = (char) curNum;
        }
        return new String(chars);
    }
}
