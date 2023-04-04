package com.wddmg.datastructure.binarytree.leetcode145;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * morris
 * 时间：100%，空间：44.94%
 * @author duym
 * @version $ Id: Test4, v 0.1 2023/04/03 16:17 duym Exp $
 */
public class Test4 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        node1.right = node2;
        node2.left = node3;

        List<Integer> res = postorderTraversal(node1);
        System.out.println(res);
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        TreeNode predecessor = null;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                predecessor = cur.left;
                while (predecessor.right != null && predecessor.right != cur) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    predecessor.right = null;
                    printEdge(cur.left,res);
                }
            }
            cur = cur.right;
        }
        printEdge(root,res);
        return res;
    }

    public static List<Integer> printEdge(TreeNode X,List<Integer> res) {
        TreeNode tail = reverseEdge(X);
        TreeNode cur = tail;
        while (cur != null) {
            res.add(cur.val);
            cur = cur.right;
        }
        return res;
    }

    public static TreeNode reverseEdge(TreeNode from) {
        TreeNode pre = null;
        TreeNode next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }
}
