package com.wddmg.competition.week337;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test6319, v 0.1 2023/03/19 10:30 duym Exp $
 */
public class Test6319 {
    public static void main(String[] args) {
        // 示例1
        int n = 17;

        // 示例2
//        int n = 2;

        int[] res = evenOddBit(n);
        System.out.println(Arrays.toString(res));
    }

    public static int[] evenOddBit(int n) {
        String binary = Integer.toBinaryString(n);
        char[] c = binary.toCharArray();
        reverse(c);
        int len = c.length;
        int even = 0;
        int odd = 0;
        for(int i = 0;i < len;i++){
            if(c[i] == '1' && i % 2 == 0){
                even++;
            }else if (c[i] == '1' && i % 2 == 1){
                odd++;
            }
        }
        return new int[]{even,odd};
    }

    private static char[] reverse(char[] c) {
        int left = 0;
        int right = c.length - 1;
        while(left<right){
         char temp = c[left];
         c[left] = c[right];
         c[right] = temp;
         left++;
         right--;
        }
        return c;
    }
}
