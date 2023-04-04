package com.wddmg.datastructure.linkedlist.leetcode19;

import com.wddmg.datastructure.linkedlist.ListNode;

/**
 * 正常人思想，先得到链表长度，长度-倒数=正数，接下来需要找到删除节点的前一个节点，让它的指针指向它的下下一个，就可以了
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/11 16:30 duym Exp $
 */
public class Test1 {

    public static void main(String[] args) {
        //示例1
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        int n = 2;

        //示例2
//        ListNode head = new ListNode(1);
//        int n = 1;

        //示例3
//        ListNode head = new ListNode(1);
//        ListNode node1 = new ListNode(2);
//        head.next = node1;
//        int n = 1;

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
        ListNode fp = head;
        int len = 0;
        while(fp != null){
            fp = fp.next;
            len++;
        }
        if(len - n == 0){
            return head.next;
        }
        fp = head;
        int count = len - n - 1;
        while(count-- >0){
            fp = fp.next;
        }
        fp.next = fp.next.next;
        return head;
    }
}
