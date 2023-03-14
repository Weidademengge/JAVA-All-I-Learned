package com.wddmg.datastructure.linkedlist.leetcode234;

import com.wddmg.datastructure.ListNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 方法二：用栈，第一次全放里，然后让temp回到头节点，一个一个对比.时间慢的要死
 * 时间：5.86；空间：48.28
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/13 21:18 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {

        // 示例1
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(2);
        ListNode node4 = new ListNode(1);
        head.next = node2;
        node2.next =node3;
        node3.next = node4;

        // 示例2
//        ListNode head = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        head.next = node2;


        boolean res = isPalindrome(head);
        System.out.println(res);
    }

    public static boolean isPalindrome(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        ListNode temp = head;
        while(temp != null){
            stack.add(temp);
            temp = temp.next;
        }
        temp = head;
        int size = stack.size();
        while(size-- > 0){
            if(stack.pop().val == temp.val){
                temp = temp.next;
            }else{
                return false;
            }
        }
        return true;
    }
}
