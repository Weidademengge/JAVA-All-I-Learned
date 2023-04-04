package com.wddmg.datastructure.binarytree.leetcode144;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * morris遍历
 * 时间：100,空间：15.62
 * @author duym
 * @version $ Id: Test4, v 0.1 2023/04/03 14:58 duym Exp $
 */
public class Test4 {
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
        if(root == null){
            return res;
        }
        TreeNode predecessor = null;
        TreeNode cur = root;
        while(cur != null){
            if(cur.left != null){
                predecessor = cur.left;
                while(predecessor.right != null && predecessor.right != cur){
                    predecessor = predecessor.right;
                }
                if(predecessor.right == null){
                    predecessor.right = cur;
                    res.add(cur.val);
                    cur = cur.left;
                    continue;
                }else{
                    predecessor.right = null;
                }
            }else{
                res.add(cur.val);
            }
            cur = cur.right;
        }
        return res;
    }
}
