package com.wddmg.datastructure.linkedlist.leetcode148;

import com.wddmg.datastructure.ListNode;

/**
 * 方法二：并归排序，这玩意nlogn的就这么几种
 * 时间：58.75，空间：78.32
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/12 22:42 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(1);
        ListNode node4 = new ListNode(3);
        head.next = node2;
        node2.next = node3;
        node3.next =node4;

        ListNode res = sortList(head);

        while(res != null){
            System.out.print(res.val + "->");
            res = res.next;
        }
        System.out.print("null");

    }
    public static ListNode sortList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode mid = findMid(head);

        ListNode tmp = mid.next;
        mid.next = null;

        ListNode left = sortList(head);
        ListNode right = sortList(tmp);

        return merge(left,right);


    }

    private static ListNode findMid(ListNode head) {
        ListNode fast = head.next;
        ListNode slow = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private static  ListNode merge(ListNode left,ListNode right){
        ListNode res = new ListNode(-1);
        ListNode cur = res;
        while(left != null && right != null){
            if(left.val < right.val){
                cur.next = left;
                left = left.next;
            }else{
                cur.next = right;
                right = right.next;
            }
            cur = cur.next;
        }
        if(left != null){
            cur.next = left;
        }
        if(right != null){
            cur.next = right;
        }
        return res.next;
    }
}
