package com.wddmg.competition.week345;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/05/14 10:25 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int[] derived = {1,1,0};
        int[] derived2 = {1,1};
        int[] derived3 = {1,0};
        boolean res = doesValidArrayExist(derived3);
        System.out.println(res);
    }

    public static boolean doesValidArrayExist(int[] derived) {
        int len = derived.length;
        int[] ori = new int[len];
        for (int i = 0; i < len - 1; i++) {
            if (derived[i] == 1) {
                if (ori[i] == 0) {
                    ori[i + 1] = 1;
                } else {
                    ori[i + 1] = 0;
                }
            } else {
                if (ori[i] == 0) {
                    ori[i + 1] = 0;
                } else {
                    ori[i + 1] = 1;
                }
            }
        }
        if(derived[len - 1] == 0 && ori[0] == ori[len - 1]){
            return true;
        }else if(derived[len - 1] == 1 && ori[0] != ori[len - 1]){
            return true;
        }
        return false;
    }
}
