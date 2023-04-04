package com.wddmg.datastructure.binarytree.leetcode105;

import com.wddmg.datastructure.binarytree.base.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个题居然是中等，难死我了
 * 时间：50.4%，空间：61.16%
 * @author duym
 * @version $ Id: Test1, v 0.1 2023/04/03 22:51 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[] preorder = {3,9,20,15,7};
        int[] inorder = {9,3,15,20,7};

        TreeNode root = buildTree(preorder, inorder);
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        int preLen = preorder.length;
        int inLen = inorder.length;

        Map<Integer, Integer> map = new HashMap<>(preLen);
        for (int i = 0; i < inLen; i++) {
            map.put(inorder[i], i);
        }
        return buildTree(preorder,0,preLen -1,map,0,inLen -1);
    }

    private static TreeNode buildTree(int[] preorder, int preLeft, int preRight,
                                      Map<Integer, Integer> map, int inLeft, int inRight) {
        if(preLeft > preRight || inLeft > inRight){
            return null;
        }
        int rootVal = preorder[preLeft];
        TreeNode root = new TreeNode(rootVal);
        int pIndex = map.get(rootVal);

        root.left = buildTree(preorder,preLeft+1,pIndex -inLeft + preLeft,map,inLeft,pIndex -1);
        root.right = buildTree(preorder,pIndex-inLeft+preLeft+1,preRight,map,pIndex+1,inRight);
        return root;
    }
}
