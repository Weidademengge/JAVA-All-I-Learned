package com.wddmg.datastructure.binarytree.leetcode114;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.Stack;

/**
 * 寻找前驱节点的方法
 *
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/04 17:28 duym Exp $
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
        node1.right = node5;
        node2.left = node3;
        node2.right = node4;
        node5.right = node6;

        flatten(node1);

        while (node1.right != null) {
            System.out.println(node1.val);
            node1 = node1.right;
        }
    }

    public static void flatten(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode predecessor = cur.left;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                predecessor.right = cur.right;
                cur.right = cur.left;
                cur.left = null;
            }
            cur =cur.right;
        }
    }
}
