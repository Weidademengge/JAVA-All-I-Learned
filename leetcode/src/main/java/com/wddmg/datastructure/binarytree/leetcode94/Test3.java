package com.wddmg.datastructure.binarytree.leetcode94;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * morris遍历
 * 时间：100%，空间63.46%
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/04/03 14:29 duym Exp $
 */
public class Test3 {
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
        if(root == null){
            return res;
        }
        // 前驱节点，也是左子树的最右节点
        TreeNode predecessor = null;
        // 不改变树结构，这个题虽然可以改，但以后尽量不改
        TreeNode cur = root;
        while(cur != null){
            predecessor = cur.left;
            if(predecessor != null){
                // 找到前驱节点
                while(predecessor.right != null && predecessor.right != cur){
                    predecessor = predecessor.right;
                }
                // 第一次来
                if(predecessor.right == null){
                    predecessor.right = cur;
                    cur = cur.left;
                    continue;
                }
                // 第二次来
                else{
                    predecessor.right = null;
                }
            }
            res.add(cur.val);
            cur =cur.right;
        }


        return res;
    }
}
