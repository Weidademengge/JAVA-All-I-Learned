package com.wddmg.competition.week83;

import java.util.*;

/**
 * @author duym
 * @version $ Id: Test4, v 0.1 2023/04/18 17:13 duym Exp $
 */
public class Test4 {
    public static void main(String[] args) {
        String s = "ABC";
        String s2 = "ABA";
        String s3 = "LEETCODE";
        String s4 = "DELQGVWNZKIJJPSXOVWWIZUXCEGWSQLESNSRBMKZARFPAXSVWQEZDENDAHNNI" +
                "BHGHTFDLPGDLFXMIYRFNLMXHNPIFUAXINXPXLCTTJNLGGMKJIOEWBECNOFQPVCIKIAZMNGHEHF" +
                "MCPWSMJTMGVSXTOGCGUYKFMNCGLCBRAFJLJVPIVDOLJBURULPGXBVDCEWXXXLTRMSHPKSPFDGNVOCZW" +
                "DXJUWVNAREDOKTZMIUDKDQWWWSAEUUDBHMWZELOSBIHMAYJEMGZPMDOOGSCKLVHTGMETHUISCLJKDOQEWGVBULE" +
                "MUXGTRKGXYFDIZTZWMLOFTCANBGUARNWQEQWGMIKMORVQUZANJNRNPMJWYLVHWKDFLDDBBMILAKGFROEQAMEVONUV" +
                "HOHGPKLBPNYZFPLXNBCIFENCGIMIDCXIIQJWPVVCOCJTSKSHVMQJNLHSQTEZQTTMOXUSKBMUJEJDBJQNXECJGSZUDENJ" +
                "CPTTSREKHPRIISXMWBUGMTOVOTRKQCFSDOTEFPSVQINYLHXYVZTVAMWGPNKIDLOPGAMWSKDXEPLPPTKUHEKBQAWEBMORRZHBL" +
                "OGIYLTPMUVBPGOOOIEBJEGTKQKOUURHSEJCMWMGHXYIAOGKJXFAMRLGTPNSLERNOHSDFSSFASUJTFHBDMGBQOKZRBRAZEQQ" +
                "VWFRNUNHBGKRFNBETEDJIWCTUBJDPFRRVNZENGRANELPHSDJLKVHWXAXUTMPWHUQPLTLYQAATEFXHZARFAUDLI" +
                "UDEHEGGNIYICVARQNRJJKQSLXKZZTFPVJMOXADCIGKUXCVMLPFJGVXMMBEKQXFNXNUWOHCSZSEZWZ" +
                "HDCXPGLROYPMUOBDFLQMTTERGSSGVGOURDWDSEXONCKWHDUOVDHDESNINELLCTURJHGCJWVI" +
                "PNSISHRWTFSFNRAHJAJNNXKKEMESDWGIYIQQRLUUADAXOUEYURQRVZBCSHXXFLYWFHDZKPHAGYOCTYGZNPALAUZSTOU";
        int res = uniqueLetterString(s3);
        System.out.println(res);
    }

    public static int uniqueLetterString(String s) {
        List<String> list = getSubString(s);
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            String temp = list.get(i);
            int len = temp.length();
            int cur = 0;
            Map<Character, Integer> map = new HashMap<>();
            for (int j = 0; j < len; j++) {
                if (map.containsKey(temp.charAt(j))) {
                    map.put(temp.charAt(j), map.get(temp.charAt(j)) + 1);
                } else {
                    map.put(temp.charAt(j), 1);
                }
            }
            for (int count : map.values()) {
                if (count == 1) {
                    cur++;
                }
            }
            sum += cur;
        }
        return sum;
    }

    public static List<String> getSubString(String s) {
        List<String> res = new ArrayList<>();
        int len = s.length();
        for (int i = 0; i <= len ; i++) {
            for (int j = i + 1; j <= len; j++) {
                String temp = s.substring(i, j);
                res.add(temp);
            }
        }
        return res;
    }
}
