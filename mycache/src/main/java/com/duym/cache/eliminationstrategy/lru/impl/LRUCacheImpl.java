package com.duym.cache.eliminationstrategy.lru.impl;

import com.duym.cache.eliminationstrategy.Node;
import com.duym.cache.eliminationstrategy.lru.LRUCache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author duym
 * @version $ Id: LRUCache, v 0.1 2023/04/26 19:22 duym Exp $
 */
public class LRUCacheImpl<K,V>  implements LRUCache<K,V> {


    private Map<K, Node> map = new ConcurrentHashMap<>();

    private int capacity;

    private int size;


    private Node head;

    private Node tail;

    public LRUCacheImpl(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.pre = head;
    }

    @Override
    public void put(K key, V value) {
        Node node = new Node(key,value);
        if(!map.containsKey(key)){
            map.put(key,node);
            addToHead(node);
            size++;
            if(size > capacity){
                Node tailPre = removeTail();
                map.remove(tailPre);
                size--;
            }
        }else{
            node = map.get(key);
            node.value = value;
            moveToHead(node);
        }
    }

    private Node removeTail() {
        Node tailPre = tail.pre;
        removeNode(tailPre);
        return tailPre;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(Node node) {
        node.next = head.next;
        node.pre = head;
        head.next.pre = node;
        head.next = node;
    }


    private void removeNode(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    @Override
    public V get(K key) throws Exception {
        Node node = map.get(key);
        if(node == null){
            throw new Exception("没有，萨比");
        }else{
            moveToHead(node);
        }
        return (V) node.value;
    }

    @Override
    public void remove(K key) throws Exception {
        Node node = map.get(key);
        if(node == null){
            throw new Exception("没有，萨比");
        }else{
            removeNode(node);
        }
        map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public void clear() {
        map.clear();
        head.next = null;
        tail.next = null;
    }

    @Override
    public String toString() {
        Node cur = head.next;
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        while(cur != null && cur != tail){
            sb.append(cur.key).append("=").append(cur.value);
            sb.append(",");
            cur = cur.next;
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }
}
