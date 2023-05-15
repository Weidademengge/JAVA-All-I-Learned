package com.wddmg.competition.week341;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/16 10:26 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        String word = "abc";

        String word2 = "b";

        String word3 ="aaa";
        int res = addMinimum(word);
        System.out.println(res);

    }

    public static int addMinimum(String word) {
        int res = 0;
        char[] chars2 = new char[word.length() + 1];
        chars2[0] = 'c';
        for (int i = 1; i < chars2.length; i++) {
            chars2[i] = word.charAt(i - 1);
            int pre = i - 1;
            int cur = i;
            if (chars2[cur] - chars2[pre] == -1) {
                res++;
            } else if (chars2[cur] - chars2[pre] == 0) {
                res += 2;
            } else if (chars2[cur] - chars2[pre] == 2) {
                res++;
            }
        }
        if (chars2[chars2.length - 1] == 'b') {
            res++;
        } else if (chars2[chars2.length - 1] == 'a') {
            res += 2;
        }
        return res;
    }

    public static int quickSortFindRaidx(int a[], int left, int right) {
        int pivot = a[left];

        int i = left;

        int j = right;
        while (i != j) {
            //找右边第一个小于基准点的数字
            while (a[j] >= pivot && i < j) {
                j--;
            }
            //做右边第一个大于基准点的数字
            while (a[i] <= pivot && i < j) {
                i++;
            }
            if (i < j) {
            //进行交换int temp = a[i];a[i] = a[j];a[j] = temp;
            }
        }
        //基准归位
        a[left] = a[i];a[i] = pivot;
        return i;
    }

    public static int findKthSmall
            (int a[],

             int left, int right, int k
            ) {
        if (k <= 0 || k > a.length) {
            return -1;
            //超出查询范围，直接返回-1
        }
        //返回基准点的下标，从0开始
        int pivotIndex = quickSortFindRaidx(a, left, right);

        //包含基准点在内的左边的数字个数
        int leftNumCount = pivotIndex + 1;
        //说明当前基准下标的值就是我们要找的
        if (leftNumCount == k)

        {
            return a[pivotIndex];

        }//说明要找的数，在基准点的左边，继续在左边部分递归查找
        if (leftNumCount > k) {
            return findKthSmall(a, left, pivotIndex, k);
        } else {//说明要找的数，在基准点的右边，继续在右边部分递归查找
            return findKthSmall
                    (a, pivotIndex + 1, right, k);

        }
    }

}
