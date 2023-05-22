package com.wddmg.competition.week346;

import java.util.Stack;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/21 10:29 duym Exp $
 */
public class Test1 {

    public static void main(String[] args) {
        String s = "ABFCACDB";
        String s2 = "D";
        int res = minLength(s2);
        System.out.println(res);
    }

    public static int minLength(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            if(!stack.isEmpty()){
                if((chars[i] == 'B' && stack.peek() == 'A') ||
                        (chars[i] == 'D' && stack.peek() == 'C')){
                    stack.pop();
                }else{
                    stack.add(chars[i]);
                }
            }else{
                stack.add(chars[i]);
            }

        }
        return stack.size();
    }
}
