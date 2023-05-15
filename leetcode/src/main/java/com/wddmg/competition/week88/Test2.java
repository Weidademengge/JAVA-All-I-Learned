package com.wddmg.competition.week88;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/26 15:36 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        int[] seats = {1, 0, 0, 0, 1, 0, 1};

        int[] seats2 = {1, 0, 0, 0};

        int[] seats3 = {0, 1};

        int res = maxDistToClosest(seats);
        System.out.println(res);
    }

    public static int maxDistToClosest(int[] seats) {
        int res = 0;
        int len = seats.length;
        for (int i = 0; i < len; i++) {
            if (seats[i] != 1) {
                int left = i - 1;
                int right = i + 1;
                int leftDist = 0;
                int rightDist = 0;
                while ((left >= 0 && seats[left] == 0) || (right < len && seats[right] == 0)) {
                    boolean leftFlag = false;
                    if (left >= 0 && seats[left] == 0) {
                        left--;
                        leftDist++;
                    }
                    if (right < len && seats[right] == 0) {
                        right++;
                        rightDist++;
                    }
                    leftDist = leftDist == 0 ? Integer.MAX_VALUE : leftDist;
                    rightDist = rightDist == 0 ? Integer.MAX_VALUE : rightDist;
                    res = Math.max(res, Math.min(leftDist, rightDist));
                }
            }
        }
        return res == 1? res : res + 1;
    }
}
