package com.duym.cache.eliminationstrategy;

/**
 * @author duym
 * @version $ Id: Node, v 0.1 2023/04/26 19:24 duym Exp $
 */
public class Node<K,V> {
    public K key;
    public V value;
    public Node pre;
    public Node next;

    public Node() {
    }

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Node(K key, V value, Node pre, Node next) {
        this.key = key;
        this.value = value;
        this.pre = pre;
        this.next = next;
    }
}
