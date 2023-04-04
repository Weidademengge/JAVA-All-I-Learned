package com.wddmg.datastructure.binarytree.base;

import java.util.LinkedList;

/**
 * 是否是完全二叉树
 * 1.先说一下什么是完全二叉树：要么每层是满的，如果最后一层不满，那它也是从左往右依次满的
 * 2.所以，如果一个节点有右子节点而没有左子节点就肯定不是
 * 3.在2的基础上，如果遍历到了只有左子节点而没有右子节点的节点，这时候要求后面所有的节点都要是叶子结点
 * 4.方法：按照BFS遍历，每次做判断就好
 * @author duym
 * @version $ Id: TestCBT, v 0.1 2023/03/30 21:24 duym Exp $
 */
public class TestCBT {
    public static void main(String[] args) {

    }

    public static boolean isCBT(TreeNode head){
        LinkedList<TreeNode> queue = new LinkedList<>();
        boolean leaf = false;
        TreeNode l = null;
        TreeNode r = null;
        queue.add(head);
        while(!queue.isEmpty()){
            head = queue.poll();
            l = head.left;
            r = head.right;
            if ((l == null && r != null) || (leaf && (l != null || r != null))) {
                return false;
            }
            if(l != null){
                queue.add(l);
            }
            if(r != null){
                queue.add(r);
            }
            if(l != null || r != null){
                leaf = true;
            }
        }
        return true;
    }
}
