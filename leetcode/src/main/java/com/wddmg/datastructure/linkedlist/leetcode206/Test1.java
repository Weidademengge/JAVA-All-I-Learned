package com.wddmg.datastructure.linkedlist.leetcode206;

import com.wddmg.datastructure.ListNode;

import java.util.List;

/**方法一：定义两个节点，pre和cur，然后在循环中定义一个临时节点，temp,让它等cur.next,一会儿指针断了可别跑了个der的，然后就是逆向指就完了
 * 时间：100，空间：16.19
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/13 20:44 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        ListNode res = reverseList(head);
        while(res != null){
            System.out.print(res.val + "->");
            res = res.next;
        }
        System.out.print("null");
    }

    public static ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while(cur != null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;

    }
}
