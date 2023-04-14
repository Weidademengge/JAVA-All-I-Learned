package com.wddmg.datastructure.binarytree.leetcode111;

import com.wddmg.datastructure.binarytree.base.TreeNode;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/05 20:03 duym Exp $
 */
public class Test1 {

    public static void main(String[] args) {
        TreeNode node3 = new TreeNode(3);
        TreeNode node9 = new TreeNode(9);
        TreeNode node20 = new TreeNode(20);
        TreeNode node15 = new TreeNode(15);
        TreeNode node7 = new TreeNode(7);

        node3.left = node9;
        node3.right = node20;
        node20.left = node15;
        node20.right = node7;

        int res = minDepth(node3);
        System.out.println(res);
    }

    public static int minDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right != null){
            return 1 + minDepth(root.right);
        } else if (root.left != null && root.right == null) {
            return 1 + minDepth(root.left);
        }else{
            return 1 + Math.min(minDepth(root.left),minDepth(root.right));
        }
    }
}
