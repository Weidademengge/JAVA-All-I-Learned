package com.wddmg.datastructure.linkedlist.leetcode234;

import com.wddmg.datastructure.linkedlist.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法一：把所有节点放到list中，双指针判断，效果还挺好
 * 时间：53.4%，空间：87.65
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/03/13 21:03 duym Exp $
 */
public class Test1 {
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
        List<ListNode> list = new ArrayList<>();
        ListNode temp = head;
        while(temp != null){
            list.add(temp);
            temp =temp.next;
        }
        int left = 0;
        int right = list.size() - 1;
        while(left < right){
            if(list.get(left).val == list.get(right).val){
                left++;
                right--;
            }else{
                return false;
            }
        }
        return true;
    }
}
