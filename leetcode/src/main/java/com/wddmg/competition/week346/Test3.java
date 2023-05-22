package com.wddmg.competition.week346;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/05/21 10:56 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        int n = 45;
        checkPunish(n);
        int res = punishmentNumber(n);
        System.out.println(res);
    }

    public static int punishmentNumber(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += checkPunish(i);
        }
        return res;
    }

    private static int checkPunish(int x) {
        int pow = x * x;
        String s = Integer.toString(pow);
        char[] chars = s.toCharArray();
        int len = chars.length;
        for (int p = 0; p < len; p++) {
            Loop:
            for (int q = p; q < len; q++) {
                int temp = 0;
                for (int i = 0; i < len; i++) {
                    if (i < p) {
                        temp += Character.getNumericValue(chars[i]);
                    } else if (i > q) {
                        temp += Character.getNumericValue(chars[i]);
                    } else {
                        if (p == q) {
                            temp += Character.getNumericValue(chars[p]);
                        } else {
                            temp += Integer.parseInt(s.substring(p,q+1));
                            i = q;
                        }
                    }
                    if(temp > x){
                        break Loop;
                    }
                }
                if(temp == x){
                    return pow;
                }
            }
        }
        return 0;
    }
}
