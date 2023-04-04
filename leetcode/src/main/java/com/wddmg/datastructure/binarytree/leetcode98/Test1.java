package com.wddmg.datastructure.binarytree.leetcode98;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import sun.reflect.generics.tree.Tree;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/03 17:30 duym Exp $
 */
public class Test1 {
    private static long preVal = Long.MIN_VALUE;
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


        boolean res = isValidBST(node0);
        System.out.println(res);
    }

    public static boolean isValidBST(TreeNode root) {
        if(root == null){
            return true;
        }

        boolean isLeft = isValidBST(root.left);
        if(!isLeft){
            return false;
        }
        if(preVal < root.val){
            preVal = root.val;
        }else{
            return false;
        }
        return isValidBST(root.right);
    }
}
