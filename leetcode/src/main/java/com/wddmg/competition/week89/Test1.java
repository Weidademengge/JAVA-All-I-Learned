package com.wddmg.competition.week89;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/27 8:39 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[] arr1 = {0, 1, 0};
        int[] arr2 = {0, 2, 1, 0};
        int[] arr3 = {0, 10, 5, 2};
        int[] arr4 = {3, 4, 5, 1};

        int res = peakIndexInMountainArray(arr2);
        System.out.println(res);
    }

    public static int peakIndexInMountainArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        int res = findIndex(arr, left, right);
        return res;
    }

    private static int findIndex(int[] arr, int left, int right) {
        int mid = left + (right - left) / 2;

        if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) {
            return mid;
        } else if (arr[mid] > arr[mid - 1] && arr[mid] < arr[mid + 1]) {
            return findIndex(arr, mid, right);
        } else {
            return findIndex(arr, left, mid);
        }
    }
}
