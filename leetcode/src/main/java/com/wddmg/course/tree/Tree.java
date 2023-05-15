package com.wddmg.course.tree;

import com.wddmg.datastructure.binarytree.base.TreeNode;

public interface Tree<K extends Comparable<K>,V> {

    void put(K key,V value);

    V remove(K key);
}
