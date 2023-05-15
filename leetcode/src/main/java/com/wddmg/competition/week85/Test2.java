package com.wddmg.competition.week85;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/20 22:07 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        String s = ".L.R...LR..L..";
        String s2 = "RR.L";
        String s3 = ".L.R.";
        String s4 = "R";
        String s5 = "R.R.L";
        String res = pushDominoes(s5);
        System.out.println(res);
    }

    public static String pushDominoes(String dominoes) {
        char[] chars = dominoes.toCharArray();
        int len = chars.length;
        StringBuffer sb = new StringBuffer();
        int[] arr = new int[len];
        int pre = 0;
        int cur = 0;
        while (cur < len) {
            if (chars[cur] == 'L') {
                sb.append('L');
                cur++;
            } else if (chars[cur] == 'R') {
                sb.append('R');
                pre = cur + 1;
                if(pre < len && chars[pre] == 'R'){
                    cur++;
                    continue;
                }
                while (pre < len && chars[pre] != 'L') {
                    if(chars[pre] == 'R'){
                        for (int i = cur; i < pre; i++) {
                            sb.append('R');
                        }
                        cur = pre;
                    }
                    pre++;
                }
                if ((pre - cur) % 2 == 0 && pre != len) {
                    for (int i = 0; i < (pre - 1 - cur) / 2; i++) {
                        sb.append('R');
                    }
                    sb.append('.');
                    for (int i = 0; i < (pre - 1 - cur) / 2; i++) {
                        sb.append('L');
                    }
                    sb.append('L');
                } else if((pre - cur) % 2 == 1 && pre != len) {
                    for (int i = 0; i < (pre - 1 - cur) / 2; i++) {
                        sb.append('R');
                    }
                    for (int i = 0; i < (pre - 1 - cur) / 2; i++) {
                        sb.append('L');
                    }
                    sb.append('L');
                }
                if (pre == len && chars[pre - 1] != 'L') {
                    for (int i = cur; i < pre - 1; i++) {
                        sb.append('R');
                    }
                }
                cur = pre + 1;
            } else {
                pre = cur + 1;
                while (pre <= len) {
                    if (pre == len || chars[pre] == 'R') {
                        for (int i = cur; i < pre; i++) {
                            sb.append(".");
                        }
                        cur = pre;
                        break;
                    }
                    if (chars[pre] == 'L') {
                        for (int i = cur; i < pre; i++) {
                            sb.append("L");
                        }
                        cur = pre;
                        break;
                    }
                    pre++;
                }
            }
        }
        return sb.toString();
    }
}
