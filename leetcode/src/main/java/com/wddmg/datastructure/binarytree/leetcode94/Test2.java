package com.wddmg.datastructure.binarytree.leetcode94;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 中序遍历：非递归，在基础里了，这个确实难写，难写的原因我感觉是因为在栈中，需要放入弹出的过程不是连续的
 * 时间：100%，空间62.58%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/02 21:16 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);

        node1.right = node2;
        node2.left = node3;

        List<Integer> res = inorderTraversal(node1);
        System.out.println(res);
    }
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty() || root != null){
            if(root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                res.add(root.val);
                root = root.right;
            }

        }
        return res;
    }
}
