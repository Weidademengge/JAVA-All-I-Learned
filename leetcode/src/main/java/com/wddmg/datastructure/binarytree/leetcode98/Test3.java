package com.wddmg.datastructure.binarytree.leetcode98;

import com.wddmg.datastructure.binarytree.base.TreeNode;

/**
 * 树递归套路
 * 时间：100%，空间：32.77%
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/03 19:31 duym Exp $
 */
public class Test3 {
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

        Boolean b = isValidBST(node5);
        System.out.println(b);
    }

    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static boolean isValidBST(TreeNode node,long lower,long upper) {
        if(node == null){
            return true;
        }
        if(node.val <= lower || node.val >= upper){
            return false;
        }
        return isValidBST(node.left,lower,node.val) && isValidBST(node.right,node.val,upper);
    }
}
