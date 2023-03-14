package com.wddmg.datastructure.linkedlist.leetcode148;

import com.wddmg.datastructure.ListNode;

import java.util.Arrays;
import java.util.List;

/**
 * 方法一：冒泡排序，直接超时了,哈哈哈
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/12 22:05 duym Exp $
 */
public class Test1 {
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
        if(head == null){
            return head;
        }
        ListNode cur = head;
        int len = 0;
        while(cur != null){
            len++;
            cur = cur.next;
        }
        ListNode[] arr = new ListNode[len];
        cur = head;
        for(int i = 0;i < len;i++){
            arr[i] = cur;
            cur = cur.next;
            arr[i].next = null;
        }
        for(int i = 0;i < len;i++){
            for(int j = 0;j < len - i - 1;j++){
                if(arr[j].val > arr[j + 1].val){
                    ListNode temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for(int i = 0;i < len - 1;i++){
            arr[i].next = arr[i + 1];
        }
        return arr[0];
    }
}
