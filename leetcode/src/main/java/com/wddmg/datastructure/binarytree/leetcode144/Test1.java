package com.wddmg.datastructure.binarytree.leetcode144;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 前序遍历,和base中一样
 * 时间：100%，空间：17.79%
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/02 21:39 duym Exp $
 */
public class Test1 {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);

        node1.right = node2;
        node2.left = node3;

        List<Integer> res = preorderTraversal(node1);
        System.out.println(res);
    }
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        preOrder(root,res);
        return res;
    }
    private static void preOrder(TreeNode root,List<Integer> res){
        if(root == null) {
            return;
        }
        res.add(root.val);
        preOrder(root.left,res);
        preOrder(root.right,res);

    }
}
