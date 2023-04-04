package com.wddmg.datastructure.binarytree.leetcode101;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.LinkedList;

/**
 * 迭代：整个队列，同时加左右，每次都往外弹俩
 * 时间：21.16%，空间：58.69%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/03 21:33 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        // 示例1
        TreeNode node1 = new TreeNode(1);
        TreeNode node21 = new TreeNode(2);
        TreeNode node22 = new TreeNode(2);
        TreeNode node31 = new TreeNode(3);
        TreeNode node32 = new TreeNode(3);
        TreeNode node41 = new TreeNode(4);
        TreeNode node42 = new TreeNode(4);

        node1.left = node21;
        node1.right = node22;
        node21.left = node31;
        node21.right = node41;
        node22.left = node42;
        node22.right = node32;

        boolean res = isSymmetric(node1);
        System.out.println(res);
    }

    public static boolean isSymmetric(TreeNode root) {
        if(root == null || (root.left ==null && root.right == null)){
            return true;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while(!queue.isEmpty()){
            TreeNode left = queue.pop();
            TreeNode right = queue.pop();
            if(left == null && right == null){
                continue;
            }
            if(left == null || right == null){
                return false;
            }
            if(left.val != right.val){
                return false;
            }
            queue.add(left.left);
            queue.add(right.right);
            queue.add(left.right);
            queue.add(right.left);
        }
        return true;
    }
}
