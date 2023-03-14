package com.wddmg.datastructure.linkedlist.leetcode146;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duym
 * @version $ Id: LRUCache, v 0.1 2023/03/12 20:39 duym Exp $
 */
public class LRUCache {

    public static void main(String[] args) {
        // 示例1
//        LRUCache lruCache = new LRUCache(2);
//        lruCache.put(1,1);
//        lruCache.put(2,2);
//        lruCache.get(1);
//        lruCache.put(3,3);
//        lruCache.get(2);
//        lruCache.put(4,4);
//        lruCache.get(1);
//        lruCache.get(3);
//        lruCache.get(4);

        // 示例2
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(2,1);
        lruCache.put(2,2);
        lruCache.get(2);
        lruCache.put(1,1);
        lruCache.put(4,1);
        lruCache.get(2);
    }

    private Map<Integer,DLinkedNode> cache = new HashMap<>();
    private int capacity;

    private int size;

    private DLinkedNode head;

    private DLinkedNode tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key){
        DLinkedNode node = cache.get(key);
        if(node == null){
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key,int value){
        DLinkedNode node = new DLinkedNode(key,value,null,null);
        //如果hashmap中没有要添加的key，value
        if(!cache.containsKey(key)){
            cache.put(key,node);
            addToHead(node);
            size++;
            if(size > capacity){
                DLinkedNode tailpre = removeTail();
                cache.remove(tailpre.key);
                size--;
            }
        }
        //如果hashmap中已经存在key了，把这个node放到头，node中的value改了，这里还要把链表中的存在的node删点
        else{
            node = cache.get(key);
            node.value = value;
            moveToHead(node);
        }
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        DLinkedNode tailpre = tail.pre;
        removeNode(tailpre);
        return tailpre;
    }

    private void removeNode(DLinkedNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void addToHead(DLinkedNode node) {
        node.next = head.next;
        node.pre = head;
        head.next.pre = node;
        head.next = node;
    }

    class DLinkedNode{
        int key;
        int value;
        DLinkedNode pre;
        DLinkedNode next;

        public DLinkedNode() {
        }

        public DLinkedNode(int key, int value, DLinkedNode pre, DLinkedNode next) {
            this.key = key;
            this.value = value;
            this.pre = pre;
            this.next = next;
        }
    }
}
