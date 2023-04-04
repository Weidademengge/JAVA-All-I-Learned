package com.wddmg.datastructure.linkedlist.leetcode142;

import com.wddmg.datastructure.linkedlist.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 方法一：最简单的方法，141的set版，比赛这么写实在太爽了
 * 时间：17.27 空间：12.11
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/12 20:10 duym Exp $
 */
public class Test1 {
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
        Set<ListNode> set = new HashSet<>();
        while(head != null){
            if(set.contains(head)){
                return head;
            }
            set.add(head);
            head = head.next;
        }
        return null;
    }
}
