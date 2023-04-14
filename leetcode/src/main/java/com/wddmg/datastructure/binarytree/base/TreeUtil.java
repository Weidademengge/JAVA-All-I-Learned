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
        // 计算深度为depth的满二叉树需要的打印区域:叶子节点需要的打印区域，恰好为奇数
        // 同一个节点左右孩子间隔3个空格
        // 相邻节点至少间隔一个空格
        int printArea = (int) (3 * (Math.pow(2, (height - 1))) - 1);
        // 每一个节点前面的空格树
        int nodeSpace = (printArea / 2) + 2;

        LinkedList<TreeNode> queue = new LinkedList<>();
        List<String> level = new ArrayList<>();
        StringBuffer sb = printSpace(nodeSpace);
        Map<TreeNode, Integer> map = new HashMap<>();
        sb.append(root.val);

        int leftSpace = nodeSpace;
        int rightSpace = nodeSpace;
        map.put(root, nodeSpace);
        level.add(sb.toString());
        queue.add(root);
        while (!queue.isEmpty()) {
            StringBuffer ganggang = new StringBuffer();
            StringBuffer nextNode = new StringBuffer();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.pop();
                int curNodeSpace = map.get(cur);
                if (cur.left != null) {
                    leftSpace = curNodeSpace - 1;
                    if (ganggang.length() != 0) {
                        ganggang.append(printSpace(2));
                    } else {
                        ganggang.append(printSpace(leftSpace));
                    }
                    ganggang.append("/");

                }
                if (cur.right != null) {
                    rightSpace = curNodeSpace + 1;
                    if (ganggang.length() != 0) {
                        ganggang.append(printSpace(2));
                    } else {
                        ganggang.append(printSpace(rightSpace));
                    }
                    ganggang.append("\\");
                }
                level.add(ganggang.toString());

                if (cur.left != null) {
                    queue.add(cur.left);
                    leftSpace--;
                    map.put(cur.left, leftSpace);
                    if (nextNode.length() != 0) {
                        nextNode.append(printSpace(2));
                    } else {
                        nextNode.append(printSpace(leftSpace));
                    }
                    nextNode.append(cur.left.val);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                    rightSpace++;
                    map.put(cur.right, rightSpace);
                    if (nextNode.length() != 0) {
                        nextNode.append(printSpace(2));
                    } else {
                        nextNode.append(printSpace(rightSpace));
                    }
                    nextNode.append(cur.right.val);
                }
                level.add(nextNode.toString());
            }
        }
        for (String s : level) {
            if (s.length() != 0) {
                System.out.println(s);
            }
        }
    }


    private static StringBuffer printSpace(int count) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(" ");
        }
        return sb;
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
