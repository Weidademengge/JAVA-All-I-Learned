package com.wddmg.datastructure.binarytree.leetcode110;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

import static com.wddmg.datastructure.binarytree.base.TreeUtil.print;


/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/06 19:11 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(3),new TreeNode(9),new TreeNode(20),null,null,new TreeNode(15),new TreeNode(7)};
        TreeUtil.createTree(root);
        print(root[0]);
        boolean res = isBalanced(root[0]);
        System.out.println(res);

    }

    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        if (Math.abs(leftHeight - rightHeight) > 1 || !isBalanced(root.left) || !isBalanced(root.right)) {
            return false;
        }
        return true;
    }

    public static int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(height(root.left), height(root.right)) + 1;
    }
}
