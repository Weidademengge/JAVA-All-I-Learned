package com.wddmg.competition.week87;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/22 10:37 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        String s = "ab#c";
        String t = "ad#c";

        String s1 ="ab##";
        String t1 = "c#d#";

        String s2 = "a#c";
        String t2 = "b";
        boolean res = backspaceCompare(s2, t2);
        System.out.println(res);
    }

    public static boolean backspaceCompare(String s, String t) {
        StringBuffer sbs = new StringBuffer();
        StringBuffer sbt = new StringBuffer();
        int sLen = s.length();
        int tLen = t.length();
        char[] charsS = s.toCharArray();
        char[] charsT = t.toCharArray();
        trans(charsS);
        trans(charsT);
        for (int i = 0; i < sLen; i++) {
            if(charsS[i] != '1'){
                sbs.append(charsS[i]);
            }
        }
        for (int i = 0; i < tLen; i++) {
            if(charsT[i] != '1'){
                sbt.append(charsT[i]);
            }
        }
        return sbs.toString().equals(sbt.toString());
    }

    private static void trans(char[] chars){
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] == '#'){
                int curPoint = i - 1;
                while(curPoint >=0 && chars[curPoint] == '1' ){
                    curPoint--;
                }
                if(curPoint >= 0){
                    chars[curPoint] = '1';
                }
                chars[i] = '1';
            }
        }
    }
}
