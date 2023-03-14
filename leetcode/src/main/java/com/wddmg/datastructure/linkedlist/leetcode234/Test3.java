package com.wddmg.datastructure.linkedlist.leetcode234;

import com.wddmg.datastructure.ListNode;

import java.awt.*;
import java.util.Stack;

/**
 * 方法三：快慢指针，把后半段反转过来，然后一个一个对比，恶心的是对比之前得把后半段反转过来，然后还要反转回去
 * 时间：63.17；空间：51.53
 * @author duym
 * @version $ Id: Test3, v 0.1 2023/03/13 21:25 duym Exp $
 */
public class Test3 {
    public static void main(String[] args) {

        // 示例1
//        ListNode head = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(2);
//        ListNode node4 = new ListNode(1);
//        head.next = node2;
//        node2.next =node3;
//        node3.next = node4;

        // 示例2
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        head.next = node2;


        boolean res = isPalindrome(head);
        System.out.println(res);
    }

    public static boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null){
            return true;
        }

        //通过快慢指针找到中间位置12321的3,123321的第一个3
        ListNode rightHead = findRightHead(head);
        ListNode rightEnd = reverse(rightHead);

        ListNode leftTemp = head;
        ListNode rightTemp = rightEnd;
        while(rightTemp != null){
            if(leftTemp.val == rightTemp.val){
                leftTemp = leftTemp.next;
                rightTemp = rightTemp.next;
            }else{
                return false;
            }
        }

        //还原一下，动人家东西还不还原，这里我也不知道左边走的是不是最后，如果是最后就连一下，不是就和左边下一个连
        rightHead = reverse(rightEnd);
        if(leftTemp.next == null){
            leftTemp.next = rightHead;
        }else{
            leftTemp.next.next = rightHead;
        }
        return true;
    }

    private static ListNode reverse(ListNode rightHead) {
        ListNode cur = rightHead,pre = null;
        while(cur != null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    private static ListNode findRightHead(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow.next;
    }
}
