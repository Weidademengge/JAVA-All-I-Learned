package com.wddmg.datastructure.binarytree.base;

import javax.xml.transform.Templates;
import java.util.*;

/**
 * @author duym
 * @version $ Id: BinaryTreeNode, v 0.1 2023/03/27 21:34 duym Exp $
 */
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public TreeNode(int val) {
        this.val = val;
    }

}
