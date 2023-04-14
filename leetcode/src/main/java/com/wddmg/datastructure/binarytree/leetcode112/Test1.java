package com.wddmg.datastructure.binarytree.leetcode112;

import com.wddmg.datastructure.binarytree.base.TreeNode;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/07 22:30 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {

    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null){
            return false;
        }
        if(root.left == null && root.right == null){
            return root.val == targetSum;
        }
        if(root.left != null){
            return hasPathSum(root.left,targetSum - root.left.val);
        }
        if(root.right != null){
            return hasPathSum(root.right,targetSum - root.right.val);
        }
        return false;
    }

}
