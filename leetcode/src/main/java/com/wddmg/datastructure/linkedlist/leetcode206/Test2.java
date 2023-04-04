package com.wddmg.datastructure.linkedlist.leetcode206;

import com.wddmg.datastructure.linkedlist.ListNode;

/**
 * 方法二：神奇递归
 * 时间：100%，空间62.47
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/13 22:49 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        ListNode res = reverseList(head);
        while(res != null){
            System.out.print(res.val + "->");
            res = res.next;
        }
        System.out.print("null");
    }

    public static ListNode reverseList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }

        ListNode res = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return res;

    }
}
