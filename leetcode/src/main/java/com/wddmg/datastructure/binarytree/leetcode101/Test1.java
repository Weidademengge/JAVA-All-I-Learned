package com.wddmg.datastructure.binarytree.leetcode101;

import com.wddmg.datastructure.binarytree.base.TreeNode;

/**
 * 递归：定义两个指针，递归过程中同时往左往右，一旦有的为空，或者值不相同就返回false
 * 时间：100%，空间：89.83
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/03 19:38 duym Exp $
 */
public class Test1 {

    public static void main(String[] args) {
        // 示例1
        TreeNode node1 = new TreeNode(1);
        TreeNode node21 = new TreeNode(2);
        TreeNode node22 = new TreeNode(2);
        TreeNode node31 = new TreeNode(3);
        TreeNode node32 = new TreeNode(3);
        TreeNode node41 = new TreeNode(4);
        TreeNode node42 = new TreeNode(4);

        node1.left = node21;
        node1.right = node22;
        node21.left = node31;
        node21.right = node41;
        node22.left = node32;
        node22.right = node42;

        boolean res = isSymmetric(node1);
        System.out.println(res);
    }

    public static boolean isSymmetric(TreeNode root) {
        return check(root, root);

    }

    public static boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }
}
