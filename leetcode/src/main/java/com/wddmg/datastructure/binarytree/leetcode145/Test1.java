package com.wddmg.datastructure.binarytree.leetcode145;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归：
 * 时间100%，空间：60.96%
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/03 15:16 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);

        node1.right = node2;
        node2.left = node3;

        List<Integer> res = postorderTraversal(node1);
        System.out.println(res);
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        postOrder(root,res);
        return res;
    }

    public static void postOrder(TreeNode root,List<Integer> res){
        if(root == null){
            return;
        }
        postOrder(root.left,res);
        postOrder(root.right,res);
        res.add(root.val);
    }
}
