package com.wddmg.datastructure.linkedlist.leetcode24;

import com.wddmg.datastructure.linkedlist.ListNode;

/**
 * 方法二：递归，
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/12 14:53 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        //示例1
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);


        ListNode res = swapPairs(head);

        while(res != null){
            System.out.print(res.val + "->");
            res = res.next;
        }
        System.out.print("null");
    }

    public static ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return head;
    }
}
