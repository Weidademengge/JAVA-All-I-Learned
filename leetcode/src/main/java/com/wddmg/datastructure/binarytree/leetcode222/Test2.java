package com.wddmg.datastructure.binarytree.leetcode222;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/05 22:00 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {

    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int res = 0;
        while(!queue.isEmpty()){
            TreeNode cur = queue.pop();
            if(cur.left != null){
                queue.add(cur.left);
            }
            if(cur.right != null){
                queue.add(cur.right);
            }
            res++;
        }
        return res;
    }
}
