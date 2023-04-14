package com.wddmg.datastructure.binarytree.leetcode513;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/07 21:19 duym Exp $
 */
public class Test1 {
    static int max;
    static int res;

    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(1), new TreeNode(2), new TreeNode(3), new TreeNode(4),
                null, new TreeNode(5), new TreeNode(6),null,null, new TreeNode(7)};
        TreeUtil.createTree(root);
        int res = findBottomLeftValue(root[0]);
        System.out.println(res);
    }

    public static int findBottomLeftValue(TreeNode root) {
        dfs(root,1);
        return res;
    }

    private static void dfs(TreeNode root, int height) {
        if (root == null) {
            return;
        }
        if (height > max) {
            max = height;
            res = root.val;
        }
        dfs(root.left, height + 1);
        dfs(root.right, height + 1);
    }
}
