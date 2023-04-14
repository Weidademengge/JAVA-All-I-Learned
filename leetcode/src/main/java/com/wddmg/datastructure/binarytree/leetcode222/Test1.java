package com.wddmg.datastructure.binarytree.leetcode222;

import com.wddmg.datastructure.binarytree.base.TreeNode;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/05 22:00 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {

    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
}
