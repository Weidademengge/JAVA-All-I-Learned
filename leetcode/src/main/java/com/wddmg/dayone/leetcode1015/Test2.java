package com.wddmg.dayone.leetcode1015;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/10 9:36 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int k = 3;
        int res = smallestRepunitDivByK(k);
        System.out.println(res);
    }
    public static int smallestRepunitDivByK(int k) {
        if(k % 2 == 0 || k % 5 == 0){
            return -1;
        }
        int mod = 1 % k,len =1;
        while(mod != 0){
            mod = (mod *10 +1) % k;
            len++;
        }
        return len;
    }
}
