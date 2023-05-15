package com.wddmg.competition.week87;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/22 10:59 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int[] arr = {2, 1, 4, 7, 3, 2, 5};
        int[] arr2 = {2, 2, 2};
        int[] arr3 = {0, 2, 0, 2, 1, 2, 3, 4, 4, 1};
        int[] arr4 = {875, 884, 239, 731, 723, 685};
        int[] arr5 = {1,2,3,3,4,5,3};
        int res = longestMountain(arr5);
        System.out.println(res);
    }

    public static int longestMountain(int[] arr) {
        int maxLen = 0;
        int len = arr.length;
        int cur = 0;
        int next = 1;
        Boolean flag = true;
        loop:
        while (cur < len && next < len) {
            if (arr[next] <= arr[cur]) {
                cur = next;
                next++;
                continue;
            } else {
                while (cur < len && next < len) {
                    if (arr[next - 1] < arr[next] && flag) {
                        next++;
                        continue;
                    } else if (arr[next - 1] > arr[next]) {
                        flag = false;
                        next++;
                    } else if (arr[next - 1] < arr[next] && !flag) {
                        maxLen = Math.max(maxLen, next - cur);
                        cur = next - 1;
                        flag = true;
                        continue loop;
                    } else{
                        cur = next - 1;
                        flag = true;
                        continue loop;
                    }
                    maxLen = Math.max(maxLen, next - cur);
                }
            }
        }
        return maxLen;
    }
}
