package com.wddmg.datastructure.binarytree.leetcode257;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/04/07 16:27 duym Exp $
 */
public class Test3 {

    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(1), new TreeNode(2), new TreeNode(3), null, new TreeNode(5)};
        TreeUtil.createTree(root);
        List<String> res = binaryTreePaths(root[0]);
        System.out.println(res);
    }


    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null) {
            return paths;
        }

        if (root.left == null && root.right == null) {
            paths.add(root.val + "");
            return paths;
        }
        for (String path : binaryTreePaths(root.left)) {
            paths.add(root.val+"->"+path);
        }
        for (String path : binaryTreePaths(root.right)) {
            paths.add(root.val+"->"+path);
        }

        return paths;
    }

}
