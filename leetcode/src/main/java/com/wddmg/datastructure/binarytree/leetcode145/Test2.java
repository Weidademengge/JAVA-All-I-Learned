package com.wddmg.datastructure.binarytree.leetcode145;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.*;

/** 双栈
 * 时间：20.46% 空间：10.93%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/03 15:25 duym Exp $
 */
public class Test2 {
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
        TreeNode cur = root;
        if (cur != null) {
            Stack<TreeNode> s1 = new Stack<>();
            Stack<TreeNode> s2 = new Stack<>();
            s1.push(cur);
            while (!s1.isEmpty()) {
                cur = s1.pop();
                s2.push(cur);
                if (cur.left != null) {
                    s1.push(cur.left);
                }
                if (cur.right != null) {
                    s1.push(cur.right);
                }
            }
            while (!s2.isEmpty()) {
                res.add(s2.pop().val);
            }
        }
        return res;
    }
}
