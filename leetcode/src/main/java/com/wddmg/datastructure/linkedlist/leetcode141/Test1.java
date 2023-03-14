package com.wddmg.datastructure.linkedlist.leetcode141;

import com.wddmg.datastructure.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 方法一: 在set中去找有没有添加过的
 * 时间 12.84，空间：98.59
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/12 15:06 duym Exp $
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

        boolean res = hasCycle(head);
        System.out.println(res);
    }

    public static boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while(head != null){
            if(set.contains(head)){
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }
}
