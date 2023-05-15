package com.wddmg.dayone.leetcode1016;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/11 8:41 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        String s = "0110";
        int n = 3;
        boolean res = queryString(s, n);
        System.out.println(res);
    }

    public static boolean queryString(String s, int n) {
        Set<Integer> seen = new HashSet<>();
        char[] chars = s.toCharArray();
        for (int i = 0, m = chars.length; i < m; ++i) {
            int x = chars[i] - '0';
            if (x == 0) {
                continue;
            }
            for (int j = i + 1; x <= n; j++) {
                seen.add(x);
                if (j == m) {
                    break;
                }
                x = (x << 1) | (chars[j] - '0');
            }
        }
        return seen.size() == n;
    }
}
