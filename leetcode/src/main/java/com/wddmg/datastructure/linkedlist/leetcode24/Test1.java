package com.wddmg.datastructure.linkedlist.leetcode24;

import com.wddmg.datastructure.ListNode;

/**
 * 方法一：找两个换一下，把cur放到换完的结尾
 * 时间：100%，空间：63.2%
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/12 14:33 duym Exp $
 */
public class Test1 {
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
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode cur = dummy;


        while(cur.next != null && cur.next.next != null){
            ListNode l1 = cur.next;
            ListNode l2 = cur.next.next;
            l1.next = l2.next;
            l2.next = l1;
            cur.next = l2;
            cur = l1;
        }
        return dummy.next;
    }
}
