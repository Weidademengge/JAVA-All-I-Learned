package com.wddmg.datastructure.linkedlist.leetcode21;

import com.wddmg.datastructure.linkedlist.ListNode;

/**
 * 方法二：非人类的递归，鲁迅曾经说过，越是想去了解递归，就越不想了解递归
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/12 14:25 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        ListNode head = mergeTwoLists(l1,l2);

        while(head != null){
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.print("null");
    }
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null){
            return list2;
        }else if(list2 == null){
            return list1;
        }else if(list1.val <= list2.val){
            list1.next = mergeTwoLists(list1.next,list2);
            return list1;
        }else{
            list2.next = mergeTwoLists(list2.next,list1);
            return list2;
        }

    }
}
