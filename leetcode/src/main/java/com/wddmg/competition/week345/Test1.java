package com.wddmg.competition.week345;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/14 10:25 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int n = 4, k = 4;
        int[] res = circularGameLosers(n, k);
        System.out.println(Arrays.toString(res));
    }

    public static int[] circularGameLosers(int n, int k) {

        boolean[] arr = new boolean[n + 1];
        arr[0] = true;
        arr[1] = true;
        int count = 1;
        int index = 1;
        while (count != n) {
            index = (index + k * count) % n;
            index = index == 0?n:index;
            if (arr[index] == false) {
                arr[index] = true;
                count++;
            } else {
                break;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            if (arr[i] == false) {
                list.add(i);
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}
