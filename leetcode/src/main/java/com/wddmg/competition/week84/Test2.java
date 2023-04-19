package com.wddmg.competition.week84;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/19 16:21 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        String s = "abcd";
        int[] indices = {0, 2};
        String[] sources = {"a", "cd"};
        String[] targets = {"eee", "ffff"};
        findReplaceString(s, indices, sources, targets);

    }

    public static String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        StringBuffer sb = new StringBuffer();
        String res = s;
        for (int i = 0; i < indices.length; i++) {
            int curSourcesLen = sources[i].length();
//            System.out.println(s.substring(indices[i], indices[i] + curSourcesLen));
            if(s.substring(indices[i], indices[i] + curSourcesLen).equals(sources[i])){

            }
        }
        return null;
    }
}
