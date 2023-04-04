package com.wddmg.datastructure.binarytree.leetcode144;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 前序遍历，利用栈，左神喜欢用栈
 * 时间：100%，空间：36.81%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/02 21:42 duym Exp $
 */
public class Test2 {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);

        node1.right = node2;
        node2.left = node3;

        List<Integer> res = preorderTraversal(node1);
        System.out.println(res);
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            res.add(root.val);
            if (root.right != null) {
                stack.push(root.right);
            }
            if (root.left != null) {
                stack.push(root.left);
            }
        }
        return res;
    }
}
