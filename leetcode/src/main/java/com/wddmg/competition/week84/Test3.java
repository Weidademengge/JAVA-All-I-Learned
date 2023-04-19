package com.wddmg.competition.week84;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/19 16:48 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        int[][] img1 = {{1, 1, 0}, {0, 1, 0}, {0, 1, 0}};
        int[][] img2 = {{0, 0, 0}, {0, 1, 1}, {0, 0, 1}};
        int[][] img11 = {{0, 0, 0, 0, 1}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0},{0, 0, 0, 0, 0},{0, 0, 0, 0, 0}};
        int[][] img22 = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0},{0, 0, 0, 0, 0},{1, 0, 0, 0, 0}};

        int[][] img31 = {{0,1},{1,1}};
        int[][] img32 = {{1,1},{1,1}};
        int res = largestOverlap(img1, img2);
        System.out.println(res);
    }

    public static int largestOverlap(int[][] img1, int[][] img2) {
        int res = 0;
        int n = img1.length;
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img2[i][j] == 1) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    list.add(temp);
                }
            }

        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) {
                    for (List<Integer> tempList : list) {
                        int count = trans(img1, tempList.get(1) - j, tempList.get(0) - i, img2);
                        if (res < count) {
                            res = count;
                        }
                    }
                }
            }
        }
        return res;
    }

    public static int trans(int[][] img1, int hor, int vat,int[][] img2) {
        int n = img1.length;
        int[][] temp = new int[n][n];
        int[][] img = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = img1[i][j];
                img[i][j] = img1[i][j];
            }
        }
        // 左移
        if (hor < 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j - hor < n) {
                        img[i][j] = temp[i][j - hor];

                    } else {
                        img[i][j] = 0;
                    }
                }
            }
        }
        // 右移
        if (hor > 0) {

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j - hor < 0) {
                        img[i][j] = 0;
                    } else {
                        img[i][j] = temp[i][j - hor];
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = img[i][j];
            }
        }
        //上移
        if (vat < 0) {
            for (int i = 0; i < n; i++) {
                if (i - vat < n) {
                    img[i] = temp[i - vat];
                } else {
                    for (int j = 0; j < n; j++) {
                        img[i][j] = 0;
                    }
                }
            }
        }
        //下移
        if (vat > 0) {
            for (int i = 0; i < n; i++) {
                if (i - vat < 0) {
                    for (int j = 0; j < n; j++) {
                        img[i][j] = 0;
                    }
                } else {
                    img[i] = temp[i - vat];
                }
            }
        }
        int count = count(img, img2);
        return count;
    }

    public static int count(int[][] img1, int[][] img2) {
        int n = img1.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1 && img1[i][j] == img2[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
}
