package com.wddmg.datastructure.binarytree.leetcode114;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 时间：21.71%,空间：9.99%
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/04 14:58 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);

        node1.left = node2;
        node1.right = node5;
        node2.left = node3;
        node2.right = node4;
        node5.right = node6;

        flatten(node1);

        while(node1.right!= null){
            System.out.println(node1.val);
            node1 =node1.right;
        }
    }

    public static void flatten(TreeNode root) {
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode cur = stack.pop();

            if(cur.right != null){
                stack.push(cur.right);
            }
            if(cur.left != null){
                stack.push(cur.left);
            }
            cur.left = null;
            if(stack.isEmpty()){
                cur.right = null;
            }else{
                cur.right = stack.peek();
            }
        }
    }
}
