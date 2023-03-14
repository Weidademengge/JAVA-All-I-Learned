package com.wddmg.datastructure.linkedlist.leetcode142;

import com.wddmg.datastructure.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 方法二：数学上的玄学问题，有个评论说a+2b+c = 2a+2b,所以a = c，但是没说明n圈的问题
 * 时间：100 空间：83.74
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/12 20:19 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(1);
        head.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2;

        head = detectCycle(head);
        System.out.println(head.val);
    }

    public static ListNode detectCycle(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null){
            if(fast.next == null || fast.next.next == null){
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                break;
            }
        }
        fast = head;
        while(fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }
}
