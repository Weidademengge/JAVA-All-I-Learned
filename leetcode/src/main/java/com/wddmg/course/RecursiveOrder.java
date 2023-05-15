package com.wddmg.course;

import com.wddmg.datastructure.binarytree.base.TreeNode;
import com.wddmg.datastructure.binarytree.base.TreeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * @author duym
 * @version $ Id: RecursiveOrder, v 0.1 2023/05/03 13:15 duym Exp $
 */
public class RecursiveOrder {
//    static List<Integer> list = new ArrayList<>();
    public static void main(String[] args) {
        TreeNode[] root = {new TreeNode(1),null,new TreeNode(2),new TreeNode(3)};
        TreeUtil.createTree(root);
        TreeUtil.print(root[0]);
        List<Integer> list = preorderTraversal(root[0]);
        System.out.println(list);
    }

//    public static List<Integer> preorderTraversal(TreeNode root) {
//        if(root == null){
//            return list;
//        }
//
//        preorderTraversal(root.left);
//        preorderTraversal(root.right);
//        list.add(root.val);
//        return list;
//    }
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        preorder(root,list);
        return list;
    }
    public static void preorder(TreeNode root,List<Integer> list) {
        if(root == null){
            return;
        }
        preorder(root.left,list);
        preorder(root.right,list);
        list.add(root.val);
    }


}
