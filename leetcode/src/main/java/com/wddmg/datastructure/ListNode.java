package com.wddmg.datastructure;

/**
 * 供链表使用的正常node，若是其他链表中有其他node类型，在其包内部定义
 * @author duym
 * @version $ Id: ListNode, v 0.1 2023/03/11 16:37 duym Exp $
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode(int val) {
        this.val = val;
    }
}
