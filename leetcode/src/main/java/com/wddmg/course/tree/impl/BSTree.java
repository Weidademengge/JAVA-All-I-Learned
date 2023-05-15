package com.wddmg.course.tree.impl;

import com.wddmg.course.tree.Tree;

/**
 * @author duym
 * @version $ Id: AVLTree, v 0.1 2023/05/08 20:10 duym Exp $
 */
public class BSTree<K extends Comparable<K>,V> implements Tree<K,V> {

    private BSTNode root;

    public BSTNode getRoot() {
        return root;
    }

    @Override
    public void put(K key,V value) {
        if (root == null) {
            root = new BSTNode<>(key, value == null ? (V) key : value);
            return;
        }
        int com = 0;
        BSTNode<K, V> cur = root;
        BSTNode parent = null;
        while (cur != null) {
            parent = cur;
            com = key.compareTo(cur.key);
            if (com == 0) {
                cur.setValue(value);
                return;
            } else if (com < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        BSTNode node = new BSTNode<>(key, value == null ? (V) key : value);
        if(com > 0){
            parent.right = node;
        }else{
            parent.left = node;
        }
        node.parent = parent;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    public static class BSTNode<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;
        private BSTNode parent;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public BSTNode getLeft() {
            return left;
        }

        public void setLeft(BSTNode left) {
            this.left = left;
        }

        public BSTNode getRight() {
            return right;
        }

        public void setRight(BSTNode right) {
            this.right = right;
        }

        public BSTNode getParent() {
            return parent;
        }

        public void setParent(BSTNode parent) {
            this.parent = parent;
        }
    }

}
