package com.wddmg.competition.week90;

import java.util.Stack;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/28 16:15 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        String s = "(((()())()))";
        int res = scoreOfParentheses(s);
        System.out.println(res);
    }


    public static int scoreOfParentheses(String s) {
        char[] chars = s.toCharArray();
        int res = 0;
        Stack<Character> stack = new Stack<Character>();
        int point = 0;
        int count = 0;
        while(point < chars.length){
            if (chars[point] == '(') {
                stack.push(chars[point++]);
            } else {
                count++;
                int size = stack.size();
                while(stack.size()>= size){
                    res += count;
                }

                stack.pop();
            }
        }

        return res;
    }
}
