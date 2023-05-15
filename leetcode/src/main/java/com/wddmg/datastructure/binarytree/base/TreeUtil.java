package com.wddmg.datastructure.binarytree.base;

import java.util.*;

/**
 * @author duym
 * @version $ Id: TreeUtil, v 0.1 2023/04/07 16:37 duym Exp $
 */
public class TreeUtil {


    public static void print(TreeNode root) {
        if (root == null) {
            return;
        }

        int height = maxDepth(root);
        // 最后一行的宽度为2的（n - 1）次方乘3，再加1
        // 作为整个二维数组的宽度
        int arrayHeight = height * 2 - 1;
        int arrayWidth = (2 << (height - 2)) * 3 + 1;

        // 用一个字符串数组来存储每个位置应显示的元素
        String[][] res = new String[arrayHeight][arrayWidth];
        // 对数组进行初始化，默认为一个空格
        for (int i = 0; i < arrayHeight; i ++) {
            for (int j = 0; j < arrayWidth; j ++) {
                res[i][j] = " ";
            }
        }

        // 从根节点开始，递归处理整个树
        writeArray(root, 0, arrayWidth/2, res, height);

        // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
        for (String[] line: res) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length; i ++) {
                sb.append(line[i]);
                if (line[i].length() > 1 && i <= line.length - 1) {
                    i += line[i].length() > 4 ? 2: line[i].length() - 1;
                }
            }
            System.out.println(sb.toString());
        }
    }

    private static void writeArray(TreeNode currNode, int rowIndex, int columnIndex, String[][] res, int height) {
        // 保证输入的树不为空
        if (currNode == null) {
            return;
        }
        res[rowIndex][columnIndex] = String.valueOf((currNode.val));
        // 计算当前位于树的第几层
        int currLevel = ((rowIndex + 1) / 2);
        // 若到了最后一层，则返回
        if (currLevel == height) {
            return;
        }
        // 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
        int gap = height - currLevel - 1;
        // 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
        if (currNode.left != null) {
            res[rowIndex + 1][columnIndex - gap] = "/";
            writeArray(currNode.left, rowIndex + 2, columnIndex - gap * 2, res, height);
        }

        // 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
        if (currNode.right != null) {
            res[rowIndex + 1][columnIndex + gap] = "\\";
            writeArray(currNode.right, rowIndex + 2, columnIndex + gap * 2, res, height);
        }

    }

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftTree = maxDepth(root.left);
            int rightTree = maxDepth(root.right);
            return Math.max(leftTree, rightTree) + 1;
        }

    }

    public static void createTree(TreeNode[] arr) {
        int len = arr.length;
        int p = 0;
        for (int i = 0; i < len; i++) {
            if (arr[i] != null) {
                if ((2 * p + 1) < len) {
                    arr[i].left = arr[2 * p + 1];
                }
                if ((2 * p + 2) < len) {
                    arr[i].right = arr[2 * p + 2];
                }
                p++;
            }
        }
    }
}
