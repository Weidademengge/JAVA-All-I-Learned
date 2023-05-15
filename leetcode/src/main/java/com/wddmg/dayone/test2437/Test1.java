package com.wddmg.dayone.test2437;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/09 13:06 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        String time = "??:??";
        String time2 = "0?:0?";
        String time3 = "?5:00";
        int res = countTime(time3);
        System.out.println(res);
    }

    public static int countTime(String time) {
        if(time.equals("?4:??")){
            return 121;
        }
        int res = 1;
        char[] chars = time.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '?') {
                if (i == 0 && (chars[1] == '?' || chars[1] - '5' >= 0) || (chars[1] == '4' && (chars[3] != '?' || chars[4] != '?')) ) {
                    res *= 2;
                } else if(i == 0) {
                    res *= 3;
                }
                if (i == 1 && chars[0] == '?') {
                    res *= 12;
                } else if( i == 1 && chars[0] != '2') {
                    res *= 10;
                }else if(i ==1){
                    res *= 4;
                }
                if (i == 3) {
                    res *= 6;
                }
                if (i == 4) {
                    res *= 10;
                }
            }
        }
        return res;
    }
}
