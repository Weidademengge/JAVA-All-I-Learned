package com.wddmg.datastructure.binarytree.leetcode144;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.*;

/**
 * 前序遍历，利用队列
 * 时间：100%，空间：41.95%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/02 21:42 duym Exp $
 */
public class Test3 {

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
        Deque<TreeNode> queue = new LinkedList<>();
        TreeNode temp = root;
        while (!queue.isEmpty() || temp != null) {
            while(temp != null){
                res.add(temp.val);
                queue.push(temp);
                temp =temp.left;
            }
            temp = queue.pop();
            temp = temp.right;
        }
        return res;
    }
}
