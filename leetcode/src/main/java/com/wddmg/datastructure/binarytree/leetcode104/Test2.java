package com.wddmg.datastructure.binarytree.leetcode104;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * BFS，把添加到集合的操作改为层数++
 * 时间：20.43;空间：98.52%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/03 22:30 duym Exp $
 */
public class Test2 {
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

        int res = maxDepth(node3);

        System.out.println(res);
    }

    public static int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        int res = 0;
        Deque<TreeNode> queue = new LinkedList<>();
        TreeNode cur = root;
        queue.add(cur);
        while(!queue.isEmpty()){
            int curLevelSize = queue.size();
            for (int i = 0; i < curLevelSize; i++) {
                cur = queue.pop();
                if(cur.left != null){
                    queue.add(cur.left);
                }
                if(cur.right != null){
                    queue.add(cur.right);
                }
            }
            res++;
        }
        return res;
    }
}
