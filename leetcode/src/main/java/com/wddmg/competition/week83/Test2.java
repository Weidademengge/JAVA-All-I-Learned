package com.wddmg.competition.week83;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/18 16:34 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        String s = "AB@qq.com";
        String s2 = "LeetCode@LeetCode.com";
        String s3 = "1(234)567-890";
        String s4 = "86-(10)12345678";
        String res = maskPII(s4);
        System.out.println(res);
    }

    public static String maskPII(String s) {
        StringBuffer sb = new StringBuffer();
        if (s.contains("@")) {
            String[] sArr = s.split("@");
            sArr[0] = Character.toString(sArr[0].charAt(0)).toLowerCase() + "*****" + Character.toString(sArr[0].charAt(sArr[0].length() - 1)).toLowerCase();
            sArr[1] = sArr[1].toLowerCase();
            sb.append(sArr[0]).append("@").append(sArr[1]);
        }else{
            String tel = s.replaceAll("[-()+ ]","");
            int telLen = tel.length();
            if(telLen == 10){
                sb.append("***-***-").append(tel.substring(6,10));
            }else{
                sb.append("+");
                for (int i = 1; i <= telLen - 10; i++) {
                    sb.append("*");
                }
                sb.append("-***-***-").append(tel.substring(telLen-4,telLen));
            }
        }
        return sb.toString();
    }
}
