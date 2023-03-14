package com.wddmg.datastructure.linkedlist.leetcode160;

import com.wddmg.datastructure.ListNode;

import java.util.List;

/**
 * 两个都走一遍，然后再次相等的就是公共的节点，设a是HeadA从头到公共节点的长度，b是公共长度，c是HeadB从头到公共节点的长度，a+b+c = b+c+a
 * 时间：97.88，空间：56.2
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/13 17:22 duym Exp $
 */
public class Test1 {
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
        ListNode tempA = headA;
        ListNode tempB = headB;
        while(tempA != tempB){
//            tempA = (tempA == null)?headB:tempA.next;
//            tempB = (tempB == null)?headA:tempB.next;
            if(tempA == null){
                tempA = headB;
            }else{
                tempA = tempA.next;
            }
            if(tempB == null){
                tempB = headA;
            }else{
                tempB = tempB.next;
            }
        }
        return tempA;
    }
}
