package com.wddmg.datastructure.binarytree.leetcode404;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/07 17:38 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(3),new TreeNode(9),new TreeNode(20),null,null,new TreeNode(15),new TreeNode(7)};
        TreeUtil.createTree(root);
        int res = sumOfLeftLeaves(root[0]);
        System.out.println(res);
    }

    public static int sumOfLeftLeaves(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }
        int leftSum = sumOfLeftLeaves(root.left);
        if(root.left != null && root.left.left == null && root.left.right == null){
            leftSum = root.left.val;
        }
        int rightSum = sumOfLeftLeaves(root.right);
        return leftSum + rightSum;
    }
}
