package com.wddmg.datastructure.binarytree.leetcode111;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/05 20:03 duym Exp $
 */
public class Test3 {

    public static void main(String[] args) {
        // 示例1
//        TreeNode node3 = new TreeNode(3);
//        TreeNode node9 = new TreeNode(9);
//        TreeNode node20 = new TreeNode(20);
//        TreeNode node15 = new TreeNode(15);
//        TreeNode node7 = new TreeNode(7);
//
//        node3.left = node9;
//        node3.right = node20;
//        node20.left = node15;
//        node20.right = node7;

        // 示例2
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);

        node2.right = node3;
        node3.right = node4;
        node4.right = node5;
        node5.right = node6;
        int res = minDepth(node2);
        System.out.println(res);
    }

    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        queue.add(root);
        map.put(root, 1);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.pop();
            int curLevel = map.get(cur);
            if (cur.left == null && cur.right == null) {
                return map.get(cur);
            }
            if (cur.left != null) {
                map.put(cur.left, curLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                map.put(cur.right, curLevel + 1);
                queue.add(cur.right);
            }
        }
        return 0;
    }
}
