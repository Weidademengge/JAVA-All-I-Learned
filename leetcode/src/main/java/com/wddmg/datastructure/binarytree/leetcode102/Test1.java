package com.wddmg.datastructure.binarytree.leetcode102;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的程序遍历 BFS
 * 时间：70.10% 空间；50.69%
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/03 21:58 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        TreeNode node3 = new TreeNode(3);
        TreeNode node9 = new TreeNode(9);
        TreeNode node20 = new TreeNode(20);
        TreeNode node15= new TreeNode(15);
        TreeNode node7= new TreeNode(7);

        node3.left = node9;
        node3.right = node20;
        node20.left = node15;
        node20.right = node7;


        List<List<Integer>> res = levelOrder(node3);
        System.out.println(res);
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            List<Integer> list = new ArrayList<>();
            int curLevelSize = queue.size();
            for (int i = 0; i < curLevelSize; i++) {
                TreeNode cur = queue.pop();
                list.add(cur.val);
                if(cur.left != null){
                    queue.add(cur.left);
                }
                if(cur.right != null){
                    queue.add(cur.right);
                }
            }
            res.add(list);
        }
        return res;
    }
}
