package com.wddmg.datastructure.linkedlist.leetcode234;

import com.wddmg.datastructure.ListNode;

import java.util.Stack;

/**
 * 方法四：又是递归，这个不想想了，太累了，脑子全死在理解地柜上了
 * 时间：17.95，空间：11.13
 * @author duym
 * @version $ Id: Test4, v 0.1 2023/03/13 22:47 duym Exp $
 */
public class Test4 {
    private ListNode frontPointer;
    public static void main(String[] args) {

        // 示例1
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(2);
        ListNode node4 = new ListNode(1);
        head.next = node2;
        node2.next =node3;
        node3.next = node4;

        // 示例2
//        ListNode head = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        head.next = node2;

        Test4 t = new Test4();
        boolean res = t.isPalindrome(head);
        System.out.println(res);
    }


    public  boolean isPalindrome(ListNode head) {
        frontPointer = head;
        return recursivelyCheck(head);
    }

    private  boolean recursivelyCheck(ListNode currentNode) {
        if (currentNode != null) {
            if (!recursivelyCheck(currentNode.next)) {
                return false;
            }
            if (currentNode.val != frontPointer.val) {
                return false;
            }
            frontPointer = frontPointer.next;
        }
        return true;
    }

}
