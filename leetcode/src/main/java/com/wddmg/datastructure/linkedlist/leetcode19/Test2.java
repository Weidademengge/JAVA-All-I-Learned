package com.wddmg.datastructure.linkedlist.leetcode19;

import com.wddmg.datastructure.ListNode;

/**
 * 方法二：leetcode官方给出的方法是放到栈中，然后栈弹出就可以了，不用正向数第几个，这里就不展开
 * 如果是用栈，还不如用数组，按照道理来说，应该会更慢并且占用空间更大，但最后得到的结果确实好的
 * 时间：100%  空间：95.7%
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/12 12:40 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
//示例1
//        ListNode head = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
//        head.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        int n = 2;

        //示例2
//        ListNode head = new ListNode(1);
//        int n = 1;

        //示例3
        ListNode head = new ListNode(1);
        ListNode node1 = new ListNode(2);
        head.next = node1;
        int n = 1;

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
        ListNode cur = head;
        int len = 0;
        while(cur != null){
            cur =cur.next;
            len++;
        }
        ListNode[] arr = new ListNode[len];
        cur = head;
        for(int i = 0;i<len;i++){
            arr[i] = cur;
            cur = cur.next;
        }
        if(len - n == 0){
            return head.next;
        }
        if(n == 1){
            arr[len - n - 1].next = null;
            return head;
        }
        arr[len - n - 1].next = arr[len - n +1];
        return head;

    }



}
