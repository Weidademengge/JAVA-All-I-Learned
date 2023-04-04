package com.wddmg.datastructure.binarytree.base;

import java.util.Stack;

/**
 * 是否是搜索二叉树，最简单的方法，中序遍历，放到list中，看是否升序
 * 下面两个是递归和非递归的两种写法
 * @author duym
 * @version $ Id: TestBST, v 0.1 2023/03/30 20:50 duym Exp $
 */
public class TestBST {

    public static int preval = Integer.MIN_VALUE;


    public static void main(String[] args) {
        TreeNode node5 = new TreeNode(5);
        TreeNode node3 = new TreeNode(3);
        TreeNode node7 = new TreeNode(7);
        TreeNode node2 = new TreeNode(2);
        TreeNode node4 = new TreeNode(4);
        TreeNode node6 = new TreeNode(6);
        TreeNode node8 = new TreeNode(8);
        TreeNode node1 = new TreeNode(1);

        node5.left = node3;
        node5.right = node7;
        node3.left = node2;
        node3.right = node4;
        node2.left = node1;
        node7.left = node6;
        node7.right = node8;
        /**
         *                 5
         *                / \
         *               3   7
         *              / \ / \
         *             2  4 6  8
         *            /
         *           1
         */

        Boolean b = isBST2(node5);
        System.out.println(b);

    }

    public static boolean isBST(TreeNode head) {
        if (head == null) {
            return true;
        }

        boolean isLeftBst = isBST(head.left);
        if (!isLeftBst) {
            return false;
        }
        if (head.val <= preval) {
            return false;
        } else {
            preval = head.val;
        }
        return isBST(head.right);
    }

    public static boolean isBST2(TreeNode head) {
        if (head != null) {
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    if (head.val <= preval) {
                        return false;
                    } else {
                        preval = head.val;
                    }
                    head = head.right;
                }
            }
        }
        return true;
    }


}
