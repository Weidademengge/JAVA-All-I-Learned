package com.wddmg.datastructure.linkedlist.leetcode21;

import com.wddmg.datastructure.linkedlist.ListNode;

/**
 * 方法一：人类的正常写法，哪个小指哪个，由于不知道第一个是哪个，加一个虚拟头结点，一般都设为-1，虚拟头节点的下一个就是答案
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/12 14:00 duym Exp $
 */
public class Test1 {
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
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        while(list1 != null && list2 != null){
            if(list1.val <= list2.val){
                cur.next = list1;
                list1 = list1.next;
            }else{
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 == null?list2:list1;
        return dummy.next;
    }
}
