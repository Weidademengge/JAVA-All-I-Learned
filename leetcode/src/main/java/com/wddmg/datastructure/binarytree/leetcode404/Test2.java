package com.wddmg.datastructure.binarytree.leetcode404;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/07 17:38 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(3), new TreeNode(9), new TreeNode(20), null, null, new TreeNode(15), new TreeNode(7)};
        TreeUtil.createTree(root);
        int res = sumOfLeftLeaves(root[0]);
        System.out.println(res);
    }

    public static int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int res = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left != null) {
                if (cur.left.left == null && cur.left.right == null) {
                    res += cur.left.val;
                } else {
                    queue.add(cur.left);
                }
            }
            if (cur.right != null) {
                if (cur.right.left == null && cur.right.right == null) {
                    continue;
                } else {
                    queue.add(cur.right);
                }
            }
        }
        return res;
    }
}
