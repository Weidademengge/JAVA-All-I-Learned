package com.wddmg.course.tree.impl;

import com.wddmg.course.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author duym
 * @version $ Id: RBTree, v 0.1 2023/05/08 20:10 duym Exp $
 */
public class RBTree<K extends Comparable<K>, V> implements Tree<K, V> {

    private static final boolean RED = false;

    private static final boolean BLACK = true;

    private RBNode root;

    public RBNode getRoot() {
        return root;
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
    private void leftRotate(RBNode p) {
        if (p != null) {
            RBNode pr = p.right;
            p.right = pr.left;
            if (pr.left != null) {
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
        }
    }

    /**
     *          p                     pl
     *         / \                   / \
     *       pl  pr                ll  p
     *      /  \                      / \
     *     ll  lr                    lr pr
     *
     * @param p
     */
    private void rightRotate(RBNode p) {
        if (p != null) {
            RBNode pl = p.left;
            p.left = pl.right;
            if (pl.right != null) {
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
            pl.right = p;
            p.parent = pl;
        }
    }


    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new RBNode<K, V>(key, value == null ? (V) key : value, null);
            return;
        }
        int com = 0;
        RBNode<K, V> cur = this.root;
        RBNode<K, V> parent = null;
        while (cur != null) {
            parent = cur;
            com = key.compareTo(cur.key);
            if (com < 0) {
                cur = cur.left;
            } else if (com > 0) {
                cur = cur.right;
            } else {
                cur.setValue(value);
                return;
            }
        }
        RBNode<K, V> node = new RBNode<>(key, (V) key, null);
        node.parent = parent;

        com = key.compareTo(parent.key);
        if (com < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        fixAfterPut(node);

    }

    private void fixAfterPut(RBNode<K, V> node) {
        node.color = RED;
        while (node != null && node != root && node.parent.color == RED) {
//            1.node的父节点是爷爷的左孩子
            if (parentOf(node) == leftChild(parentOf(parentOf(node)))) {
                // 叔叔节点
                RBNode<K, V> uncle = rightChild(parentOf(parentOf(node)));
                if (colorOf(uncle)) {
                    setColor(parentOf(node), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {
                    if (node == rightChild(parentOf(node))) {
                        node = parentOf(node);
                        leftRotate(node);
                    }
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    rightRotate(parentOf(parentOf(node)));
                }
            } else {
                RBNode<K, V> uncle = leftChild(parentOf(parentOf(node)));

                if (colorOf(uncle) == RED) {
                    setColor(parentOf(node), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {
                    if (node == leftChild(parentOf(node))) {
                        node = parentOf(node);
                        rightRotate(node);
                    }
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    leftRotate(parentOf(parentOf(node)));
                }
            }
        }
        root.color = BLACK;
    }

    // 前驱
    private RBNode<K,V> predecessor(RBNode<K,V> node){
        if(node == null){
            return null;
        }else if(node.left != null){
            RBNode<K,V> predecessor = node.left;
            while(predecessor.right != null){
                predecessor = predecessor.right;
            }
            return predecessor;
        }else{
            // 不会执行
            RBNode<K,V> predecessor = node.parent;
            RBNode<K,V> cur = node;
            while(predecessor != null && predecessor.right == cur){
                cur = predecessor;
                predecessor = predecessor.parent;
            }
            return predecessor;
        }
    }

    // 后继
    private RBNode<K,V> successor(RBNode<K,V> node){
        if(node == null){
            return null;
        }else if(node.right != null){
            RBNode<K,V> successor = node.right;
            while(successor.left != null){
                successor = successor.left;
            }
            return successor;
        }else{
            // 不会执行
            RBNode<K,V> successor = node.parent;
            RBNode<K,V> cur = node;
            while(successor != null && successor.left == cur){
                cur = successor;
                successor = successor.parent;
            }
            return successor;
        }
    }

    private boolean colorOf(RBNode<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private RBNode<K, V> parentOf(RBNode<K, V> node) {
        return node != null ? node.parent : null;
    }

    private RBNode<K, V> leftChild(RBNode<K, V> node) {
        return node != null ? node.left : null;
    }

    private RBNode<K, V> rightChild(RBNode<K, V> node) {
        return node != null ? node.left : null;
    }

    private void setColor(RBNode<K, V> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    @Override
    public V remove(K key) {

        RBNode<K,V> node = getNode(key);
        if(node == null){
            return null;
        }
        V oldValue = node.value;
        deleteNode(node);
        return oldValue;
    }

    private void deleteNode(RBNode<K,V> node) {
        if(node.left != null && node.right != null){
            RBNode<K,V> successor = successor(node);
            node.key = successor.key;
            node.value = successor.value;
            node = successor;
        }
        RBNode<K,V> replacement = node.left != null? node.left:node.right;
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
            if(node.color == BLACK){
                fixAfterDeletion(node);
            }
        } else if (node.parent == null) {
            root = null;
        } else{
            if(node.color == BLACK){
                fixAfterDeletion(node);
            }else{
                if(node == node.parent.left){
                    node.parent.left = null;
                }else{
                    node.parent.right = null;
                }
                node.parent = null;
            }

        }
    }

    private void fixAfterDeletion(RBNode<K,V> node) {
        if(node != root && node.color == BLACK){
            // node是左儿子
            if(node == leftChild(parentOf(node))){
                RBNode<K, V> bro = rightChild(parentOf(node));

                if(colorOf(bro) == RED){
                    setColor(bro, BLACK);
                    setColor(parentOf(node), RED);
                    leftRotate(parentOf(node));
                    bro = rightChild(parentOf(node));
                }
            }
        }
        //一定是红->黑，删除红色，替换的节点的颜色改为黑色
        setColor(node,BLACK);
    }

    private RBNode<K,V>  getNode(K key) {
        RBNode<K,V>  node = this.root;
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

    public static class RBNode<K extends Comparable<K>, V> {
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private K key;
        private V value;

        public RBNode() {

        }

        public RBNode(K k, V v, RBNode parent) {
            this.parent = parent;
            this.color = RED;
            this.key = k;
            this.value = v;
        }

        public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K k, V v) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = k;
            this.value = v;
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
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
    }


}
