package com.wddmg.datastructure.linkedlist.leetcode141;

import com.wddmg.datastructure.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 方法二：快慢指针，这玩意不会存在在环中快指针跳过慢指针的情况，而且第一圈肯定相遇
 * 时间：100；空间：78.95
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/12 15:13 duym Exp $
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

        boolean res = hasCycle(head);
        System.out.println(res);
    }

    public static boolean hasCycle(ListNode head) {
        if(head == null){
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;
        while(fast.next != null && fast.next.next != null){
            if(fast == slow){
                return true;
            }
            fast =fast.next.next;
            slow = slow.next;
        }
        return false;
    }
}
