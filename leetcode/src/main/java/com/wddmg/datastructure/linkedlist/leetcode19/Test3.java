package com.wddmg.datastructure.linkedlist.leetcode19;

import com.wddmg.datastructure.ListNode;

/**
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/03/12 12:54 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {
//示例1
//        ListNode head = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
//        head.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        int n = 2;

        //示例2
//        ListNode head = new ListNode(1);
//        int n = 1;

        //示例3
        ListNode head = new ListNode(1);
        ListNode node1 = new ListNode(2);
        head.next = node1;
        int n = 1;

        //示例4
//        ListNode head = new ListNode(1);
//        ListNode node1 = new ListNode(2);
//        head.next = node1;
//        int n = 2;

        ListNode newHead = removeNthFromEnd(head,n);
        while(newHead != null){
            System.out.print(newHead.val + "->");
            newHead = newHead.next;
        }
        System.out.print("null");
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        while(n-- > 0){
            fast = fast.next;
        }
        if(fast == null){
            return head.next;
        }
        while(fast.next == null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next =slow.next.next;
        return head;
    }
}
