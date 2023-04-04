package com.wddmg.datastructure.binarytree.leetcode94;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 中序遍历：这个题就是我们在base包中的中序遍历，没什么好说的
 * 一定要写会递归和非递归的两种方法
 * 时间：100% 空间：8.96%
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/02 20:58 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);

        node1.right = node2;
        node2.left = node3;

        List<Integer> res = inorderTraversal(node1);
        System.out.println(res);
    }


    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inOrder(root,res);
        return res;
    }

    private static void inOrder(TreeNode root,List<Integer> res){
        if(root == null) {
            return;
        }
        inOrder(root.left,res);
        res.add(root.val);
        inOrder(root.right,res);

    }
}
