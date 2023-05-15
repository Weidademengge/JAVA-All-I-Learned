package com.wddmg.course;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.*;

import static com.wddmg.datastructure.binarytree.base.TreeUtil.print;

/**
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/05/03 16:54 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        /*
         *                 1
         *                / \
         *               2   3
         *              / \ / \
         *             4  5 6  7
         */

        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;

        print(node1);

//        recursiveOrder(node1);
//        preOrder(node1);
//        inOrder(node1);
        BFS2(node1);
    }

    public static void recursiveOrder(TreeNode head) {
        if(head == null){
            return;
        }
//        System.out.print(head.val);
        recursiveOrder(head.left);
        System.out.print(head.val);
        recursiveOrder(head.right);
//        System.out.print(head.val);

    }

    public static void preOrder(TreeNode head){
        if(head == null){
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.add(head);
        while(!stack.isEmpty()){
            TreeNode cur = stack.pop();
            if(cur.right != null){
                stack.add(cur.right);
            }
            if(cur.left != null){
                stack.add(cur.left);
            }
        }

    }

//    public static void inOrder2(TreeNode root){
//        if(root == null){
//            return;
//        }
//        Stack<TreeNode> stack = new Stack<>();
//        TreeNode cur = root;
//        while(!stack.isEmpty() || cur != null){
//            if(cur != null){
//                stack.add(cur);
//                cur = cur.left;
//            }else{
//                cur = stack.pop();
//                System.out.print(cur.val);
//                cur = cur.right;
//            }
//        }
//    }

    public static void inOrder(TreeNode root){
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        stack.add(cur);
        while(!stack.isEmpty()){
            if(cur.left != null){
                stack.add(cur.left);
                cur = cur.left;
            }else{
                cur = stack.pop();
                System.out.print(cur.val);
                if(cur.right != null){
                    cur = cur.right;
                    stack.add(cur);
                }
            }
        }
    }

    public static void postOrder(TreeNode root){
        if(root == null){
            return;
        }
        List<Integer> list = new ArrayList<>();
        Deque<TreeNode> queue = new LinkedList<>();
        queue.addFirst(root);
        while(!queue.isEmpty()){
            TreeNode cur = queue.pop();
            list.add(cur.val);
            if(cur.left != null){
                queue.addFirst(cur.left);
            }
            if(cur.right != null){
                queue.addFirst(cur.right);
            }
        }
        Collections.reverse(list);
        list.forEach(e->System.out.print(e));
    }

    public  static void BFS(TreeNode root){
        if(root == null){
            return;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        queue.addFirst(root);
        List<Integer> list = new ArrayList<>();
        while(!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.pop();
                list.add(cur.val);
                if(cur.left != null){
                    queue.add(cur.left);
                }
                if(cur.right != null){
                    queue.add(cur.right);
                }
            }
        }
        list.forEach(e-> System.out.print(e));
    }

    public static void BFS2(TreeNode root){
        if(root == null){
            return;
        }
        List<List<Integer>> res = new ArrayList<>();
        BFSAAA(root,1,res);
        System.out.println(res);
    }

    public static void BFSAAA(TreeNode node,int level,List<List<Integer>> res){
        if(node == null){
            return;
        }
        if(level > res.size()){
            res.add(new ArrayList<>());
        }
        res.get(level - 1).add(node.val);
        BFSAAA(node.left,level+1,res);
        BFSAAA(node.right,level+1,res);
    }

}
