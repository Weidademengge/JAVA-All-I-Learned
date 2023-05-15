package com.wddmg.competition.week342;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/23 10:30 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int arr = 13;
        int del = 11;

        int res = findDelayedArrivalTime(arr, del);
        System.out.println(res
        );
    }

    public static int findDelayedArrivalTime(int arrivalTime, int delayedTime) {
        int res = (arrivalTime+delayedTime)  % 24;
        return res;
    }
}
