package com.wddmg.datastructure.binarytree.leetcode226;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import static com.wddmg.datastructure.binarytree.base.RecursiveOrder.inOrder;
import static com.wddmg.datastructure.binarytree.base.RecursiveOrder.preOrder;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/04 21:27 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        TreeNode node4 = new TreeNode(4);
        TreeNode node2 = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node3 = new TreeNode(3);
        TreeNode node7 = new TreeNode(7);
        TreeNode node6 = new TreeNode(6);
        TreeNode node9 = new TreeNode(9);

        node4.left = node2;
        node4.right = node7;
        node2.left = node1;
        node2.right = node3;
        node7.left = node6;
        node7.right = node9;

        invertTree(node4);

        inOrder(node4);
    }

    public static TreeNode invertTree(TreeNode root) {
        invert(root);
        return root;
    }

    public static void invert(TreeNode root){
        if(root == null){
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invert(root.left);
        invert(root.right);
    }
}
