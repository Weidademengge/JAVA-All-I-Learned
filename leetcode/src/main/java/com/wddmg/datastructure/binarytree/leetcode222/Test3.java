package com.wddmg.datastructure.binarytree.leetcode222;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/05 22:17 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);

        node1.left = node2;
        node2.left = node4;
        node2.right = node5;
        node1.right = node3;
        node3.left = node6;

        int res = countNodes(node1);
        System.out.println(res);
    }

    public static int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode left = root.left, right = root.right;
        int leftHeight = 0, rightHeight = 0;
        while (left != null) {
            left = left.left;
            leftHeight++;
        }
        while (right != null) {
            right = right.right;
            rightHeight++;
        }
        if (leftHeight == rightHeight) {
            return (2 << leftHeight) - 1;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
}
