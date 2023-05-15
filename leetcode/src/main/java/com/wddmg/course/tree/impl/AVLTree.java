package com.wddmg.course.tree.impl;

import com.wddmg.course.tree.Tree;

/**
 * @author duym
 * @version $ Id: AVLTree, v 0.1 2023/05/08 20:10 duym Exp $
 */
public class AVLTree<K extends Comparable<K>, V> implements Tree<K, V> {

    private AVLNode root;

    public AVLNode getRoot() {
        return root;
    }

    public void setRoot(AVLNode root) {
        this.root = root;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new AVLNode<>(key, value == null ? (V) key : value);
            return;
        }
        int cmp = 0;
        AVLNode<K, V> cur = root;
        AVLNode<K, V> parent = null;
        while (cur != null) {
            parent = cur;
            cmp = key.compareTo(cur.key);
            if (cmp == 0) {
                cur.setValue(value);
                return;
            } else if (cmp < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        AVLNode<K, V> node = new AVLNode<>(key, value == null ? (V) key : value);
        node.parent = parent;
        if (cmp > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        afterAdd(node);
    }

    private boolean isBalanced(AVLNode node){
        return Math.abs(node.balanceFactor()) <= 1;
    }
    protected void afterAdd(AVLNode<K, V> node) {
        if(node == null){
            return;
        }
        while(node.parent != null){
            node = node.parent;
            if(isBalanced(node)){
                node.updateHeight(node);
            }else{
                reBalance(node);
                break;
            }
        }
    }

    private void reBalance(AVLNode<K,V> grand) {
        AVLNode<K,V> parent = grand.tallerChild();
        AVLNode<K,V> node = parent.tallerChild();
        if(grand.left == parent){
            //ll
            if(parent.left == node){
                rotateRight(grand);
            }
            // lr
            else{
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else{
            //rr
            if(parent.right == node){
                rotateLeft(grand);
            }
            // rl
            else{
                rotateRight(parent);
                rotateLeft(grand);
            }
        }

    }
    /**
     *           p                     pr
     *          / \                   / \
     *        pl  pr                p  rr
     *           /  \              / \
     *          rl  rr            pl rl
     *
     * @param p
     */
    private void rotateLeft(AVLNode<K,V> p) {
        if(p == null){
            return;
        }
        AVLNode<K,V> pr = p.right;
        p.right = pr.left;
        if(pr.left != null){
            pr.left.parent = p;
        }
        pr.parent = p.parent;
        if (p.parent == null) {
            root = pr;
        } else if (p.parent.left == p) {
            p.parent.left = pr;
        } else {
            p.parent.right = pr;
        }
        p.parent = pr;
        pr.left = p;

        p.updateHeight(p);
        pr.updateHeight(pr);
    }
    /**
     *          p                     pl
     *         / \                   / \
     *       pl   pr               ll   p
     *      /  \                       / \
     *     ll  lr                     lr pr
     *
     * @param p
     */
    private void rotateRight(AVLNode<K,V> p) {
        if(p == null){
            return;
        }
        AVLNode<K,V> pl = p.left;
        p.left = pl.right;
        if(pl.right != null){
            pl.right.parent = p;
        }
        pl.parent = p.parent;
        if (p.parent == null) {
            root = pl;
        } else if (p.parent.left == p) {
            p.parent.left = pl;
        } else {
            p.parent.right = pl;
        }
        p.parent = pl;
        pl.right = p;
        p.updateHeight(p);
        pl.updateHeight(pl);
    }

    @Override
    public V remove(K key) {
        AVLNode<K,V> node = getNode(key);
        if(node == null){
            return null;
        }
        V oldValue = node.value;
        deleteNode(node);
        return oldValue;
    }

    private void deleteNode(AVLNode<K,V> node) {
        if(node.left != null && node.right != null){
            AVLNode<K,V> predecessor = predecessor(node);
            node.key = predecessor.key;
            node.value = predecessor.value;
            node = predecessor;
        }
        AVLNode<K,V> replacement = node.left != null? node.left:node.right;
        if(replacement != null){
            replacement.parent = node.parent;
            if(node.parent == null){
                root = replacement;
            } else if(node == node.parent.left){
                node.parent.left = replacement;
            } else{
                node.parent.right =replacement;
            }
            node.left = node.right =node.parent = null;
        } else if (node.parent == null) {
            root = null;
        } else{
            if(node == node.parent.left){
                node.parent.left = null;
            }else{
                node.parent.right = null;
            }
            node.parent = null;
        }
        afterRemove(node);
    }

    private void afterRemove(AVLNode<K,V> node) {
        if(node == null){
            return;
        }
        while(node.parent != null){
            node = node.parent;
            if(isBalanced(node)){
                node.updateHeight(node);
            }else{
                reBalance(node);
                break;
            }
        }
    }

    private AVLNode<K,V> predecessor(AVLNode<K,V> node) {
        if(node == null){
            return null;
        }else if(node.left != null){
            AVLNode<K,V> p = node.left;
            while(p.right != null){
                p = p.right;
            }
            return p;
        }else{
            // 不会执行
            AVLNode<K,V> p = node.parent;
            AVLNode<K,V> ch = node;
            while(p != null && ch == p.left){
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private AVLNode<K,V> getNode(K key) {
        AVLNode<K,V> node = this.root;
        while(node != null){
            int cmp = key.compareTo(node.key);
            if(cmp < 0){
                node = node.left;
            }else if(cmp > 0){
                node = node.right;
            }else{
                return node;
            }
        }
        return null;
    }

    public static class AVLNode<K extends Comparable<K>, V> {
        private K key;
        private V value;

        private int height = 1;
        private AVLNode<K,V> left;
        private AVLNode<K,V> right;
        private AVLNode<K,V> parent;

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            return leftHeight - rightHeight;
        }

        public void updateHeight(AVLNode<K,V> node){
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            this.height = 1 + Math.max(leftHeight,rightHeight);
        }

        public AVLNode<K,V> tallerChild(){
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;
            return leftHeight > rightHeight?left:right;
        }

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public AVLNode(K key, V value, AVLNode left, AVLNode right, AVLNode parent) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
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

        public AVLNode getLeft() {
            return left;
        }

        public void setLeft(AVLNode left) {
            this.left = left;
        }

        public AVLNode getRight() {
            return right;
        }

        public void setRight(AVLNode right) {
            this.right = right;
        }

        public AVLNode getParent() {
            return parent;
        }

        public void setParent(AVLNode parent) {
            this.parent = parent;
        }
    }

}
