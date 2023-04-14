package com.wddmg.datastructure.binarytree.leetcode257;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/04/07 16:27 duym Exp $
 */
public class Test1 {

    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(1),new TreeNode(2),new TreeNode(3),null,new TreeNode(5)};
        TreeUtil.createTree(root);
        TreeUtil.print(root[0]);
        binaryTreePaths(root[0]);
    }


    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        constructPaths(root,"",paths);
        return paths;
    }

    public static void constructPaths(TreeNode root,String path,List<String> paths){
        if(root != null){
            StringBuffer pathSB = new StringBuffer(path);
            pathSB.append(root.val);
            if(root.left == null && root.right == null){
                paths.add(pathSB.toString());
            }else{
                pathSB.append("->");
                constructPaths(root.left,pathSB.toString(),paths);
                constructPaths(root.right,pathSB.toString(),paths);
            }
        }
    }
}
