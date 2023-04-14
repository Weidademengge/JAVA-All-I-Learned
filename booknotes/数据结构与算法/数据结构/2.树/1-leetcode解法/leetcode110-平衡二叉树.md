# 题目

![[Pasted image 20230407150728.png]]


# 解法1

迭代：其实是迭代两次，一个是迭代求出左右子树的高度，另一个就是迭代每个子树是不是平衡二叉树

时间：46.71%，空间：88.1%

```java
package com.wddmg.datastructure.binarytree.leetcode110;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import static com.wddmg.datastructure.binarytree.base.TreeNode.createTree;  
import static com.wddmg.datastructure.binarytree.base.TreeNode.print;  
  
/**  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/06 19:11 duym Exp $  
 */public class Test1 {  
    public static void main(String[] args) {  
        TreeNode[] root = {new TreeNode(3),new TreeNode(9),new TreeNode(20),null,null,new TreeNode(15),new TreeNode(7)};  
        createTree(root);  
//        print(root[0]);  
        boolean res = isBalanced(root[0]);  
        System.out.println(res);  
  
    }  
  
    public static boolean isBalanced(TreeNode root) {  
        if (root == null) {  
            return true;  
        }  
        int leftHeight = height(root.left);  
        int rightHeight = height(root.right);  
        if (Math.abs(leftHeight - rightHeight) > 1 || !isBalanced(root.left) || !isBalanced(root.right)) {  
            return false;  
        }  
        return true;  
    }  
  
    public static int height(TreeNode root) {  
        if (root == null) {  
            return 0;  
        }  
        return Math.max(height(root.left), height(root.right)) + 1;  
  
    }  
}
```

# 解法2

递归：自低而上，后续遍历，遍历一遍所有节点即可。解法1中的遍历还要自上而下再遍历一遍是否左右子树都是平衡树。

```java
public static boolean isBalanced(TreeNode root) {  
    return height(root) >= 0;  
}  
  
public static int height(TreeNode root) {  
    if (root == null) {  
        return 0;  
    }  
    int left = height(root.left);  
    int right = height(root.right);  
    if (left == -1 || right == -1 || Math.abs(left - right) > 1) {  
        return -1;  
    } else {  
        return Math.max(left, right) + 1;  
    }  
}
```