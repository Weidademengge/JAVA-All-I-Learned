package com.wddmg.datastructure.binarytree.leetcode114;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/04 14:58 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {

    }

    public void flatten(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        preOrder(root, list);
        for (int i = 0; i < list.size() - 1; i++) {
            list.get(i).right = list.get(i + 1);
            list.get(i).left = null;
        }
    }

    public static void preOrder(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        list.add(root);
        preOrder(root.left, list);
        preOrder(root.right, list);
    }
}
