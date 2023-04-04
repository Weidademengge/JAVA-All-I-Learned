package com.wddmg.datastructure.binarytree.base;

/**
 * 是否是平衡二叉树
 * 1.先说什么是平衡二叉树：左子树的高度和右子树的高度差小于等于1；左右子树都是平衡二叉树
 * 2.这个可以引出二叉树的递归套路
 * 3.套路：罗列条件，要从左树拿到什么，再从右树拿到什么
 * @author duym
 * @version $ Id: isBalancedTree, v 0.1 2023/03/30 22:09 duym Exp $
 */
public class isBalancedTree {

    public static void main(String[] args) {

    }

    public static boolean isBalanced(TreeNode head){
        return process(head).isBalanced;
    };

    public static class ReturnType{
        public boolean isBalanced;
        public int height;

        public ReturnType(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static ReturnType process(TreeNode x){
        if(x == null){
            return new ReturnType(true,0);
        }

        ReturnType leftData = process(x.left);
        ReturnType rightData = process(x.right);

        int height = Math.max(leftData.height, rightData.height) + 1;
        boolean isBalanced = leftData.isBalanced &&
                rightData.isBalanced &&
                Math.abs(leftData.height - rightData.height) < 2;

        return new ReturnType(isBalanced,height);

    }
}
