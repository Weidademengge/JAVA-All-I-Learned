package com.wddmg.datastructure.binarytree.leetcode104;

import com.wddmg.datastructure.binarytree.base.TreeNode;

/**
 * 递归：左树和右树最大高度
 * 时间：100% 空间71.52%
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/03 22:17 duym Exp $
 */
public class Test1 {

    public static void main(String[] args) {
        TreeNode node3 = new TreeNode(3);
        TreeNode node9 = new TreeNode(9);
        TreeNode node20 = new TreeNode(20);
        TreeNode node15= new TreeNode(15);
        TreeNode node7= new TreeNode(7);

        node3.left = node9;
        node3.right = node20;
        node20.left = node15;
        node20.right = node7;

        int res = maxDepth(node3);

        System.out.println(res);
    }

    public static int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }else{
            int leftTree = maxDepth(root.left);
            int rightTree = maxDepth(root.right);
            return Math.max(leftTree,rightTree) + 1;
        }

    }

}
