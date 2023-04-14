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
public class Test2 {

    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(1),new TreeNode(2),new TreeNode(3),null,new TreeNode(5)};
        TreeUtil.createTree(root);
        List<String> res = binaryTreePaths(root[0]);
        System.out.println(res);
    }


    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if(root == null){
            return paths;
        }

        Queue<TreeNode> nodeQueue =  new LinkedList<>();
        Queue<String> pathQueue = new LinkedList<>();

        nodeQueue.add(root);
        pathQueue.add(Integer.toString(root.val));
        while(!nodeQueue.isEmpty()){
            TreeNode cur = nodeQueue.poll();
            String path = pathQueue.poll();

            if(cur.left == null && cur.right == null){
                paths.add(path);
            }else{
                if(cur.left != null){
                    nodeQueue.add(cur.left);
                    StringBuffer sb = new StringBuffer(path);
                    pathQueue.add(sb.append("->").append(cur.left.val).toString());
                }
                if(cur.right != null){
                    nodeQueue.add(cur.right);
                    StringBuffer sb = new StringBuffer(path);
                    pathQueue.add(sb.append("->").append(cur.right.val).toString());
                }
            }
        }
        return paths;
    }

}
