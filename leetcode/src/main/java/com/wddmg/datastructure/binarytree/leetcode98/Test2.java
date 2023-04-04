package com.wddmg.datastructure.binarytree.leetcode98;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.Stack;

/**
 * 非递归
 * 时间:19.73%,空间：83.43%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/03 17:52 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node6 = new TreeNode(6);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);

        node5.left = node1;
        node5.right = node4;
        node4.left = node3;
        node4.right =node6;

        TreeNode node0 = new TreeNode(0);


        boolean res = isValidBST(node5);
        System.out.println(res);
    }
    public static boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        long preVal = Long.MIN_VALUE;
        while(!stack.isEmpty() || cur != null){
            if(cur != null){
                stack.push(cur);
                cur = cur.left;
            }else{
                cur = stack.pop();
                if(preVal >= cur.val){
                    return false;
                }else{
                    preVal =cur.val;
                }
                cur = cur.right;
            }
        }
        return true;
    }

}
