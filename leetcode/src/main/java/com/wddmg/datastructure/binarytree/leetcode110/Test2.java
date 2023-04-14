package com.wddmg.datastructure.binarytree.leetcode110;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/07 15:22 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(3), new TreeNode(9), new TreeNode(20), null, null, new TreeNode(15), new TreeNode(7)};
        TreeUtil.createTree(root);
//        print(root[0]);
        boolean res = isBalanced(root[0]);
        System.out.println(res);

    }

    public static boolean isBalanced(TreeNode root) {
        return height(root) >= 0;
    }

    public static int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = height(root.left);
        int right = height(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        } else {
            return Math.max(left, right) + 1;
        }
    }
}
