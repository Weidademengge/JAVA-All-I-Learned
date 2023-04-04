package com.wddmg.datastructure.linkedlist.leetcode160;

import com.wddmg.datastructure.linkedlist.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 方法二:还是存到set中，狗都会写.不理解为什么增加个set，空间复杂度理论上来说是O（n），结果范围干掉了94
 * 时间：21.37，空间：94.89
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/03/13 17:52 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        //示例1
        ListNode headA = new ListNode(4);
        ListNode nodeA2 = new ListNode(1);
        ListNode node3 = new ListNode(8);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        headA.next = nodeA2;
        nodeA2.next = node3;
        node3.next = node4;
        node4.next = node5;

        ListNode headB = new ListNode(5);
        ListNode nodeB2 = new ListNode(6);
        ListNode nodeB3 = new ListNode(1);
        headB.next = nodeB2;
        nodeB2.next = nodeB3;
        nodeB3.next = node3;

        ListNode res = getIntersectionNode(headA,headB);
        System.out.println(res.val);

    }


    public static  ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == headB){
            return headA;
        }
        ListNode tempA = headA;
        ListNode tempB = headB;
        Set<ListNode> set = new HashSet<>();
        while(tempA != null){
            set.add(tempA);
            tempA = tempA.next;
        }
        while(tempB != null){
            if(set.contains(tempB)){
                return tempB;
            }
            tempB = tempB.next;
        }
        return tempB;
    }
}
