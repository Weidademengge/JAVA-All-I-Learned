package com.wddmg.datastructure.binarytree.leetcode513;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/07 21:19 duym Exp $
 */
public class Test2 {

    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(1), new TreeNode(2), new TreeNode(3), new TreeNode(4),
                null, new TreeNode(5), new TreeNode(6), null, null, new TreeNode(7)};
        TreeUtil.createTree(root);
        int res = findBottomLeftValue(root[0]);
        System.out.println(res);
    }

    public static int findBottomLeftValue(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int res = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            res = queue.peek().val;
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if(cur.left != null){
                    queue.add(cur.left);
                }
                if(cur.right != null){
                    queue.add(cur.right);
                }
            }
        }
        return res;
    }


}
