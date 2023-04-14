# 题目

![[Pasted image 20230407173142.png]]

# 解法1

递归：后序，但收集的不是叶子节点，因为无法判断叶子几点是否是左叶子，所以提前判断
时间：100%，空间：30.94%
```java
package com.wddmg.datastructure.binarytree.leetcode404;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import com.wddmg.datastructure.binarytree.base.TreeUtil;  
  
/**  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/07 17:38 duym Exp $  
 */public class Test1 {  
    public static void main(String[] args) {  
        TreeNode[] root = {new TreeNode(3),new TreeNode(9),new TreeNode(20),null,null,new TreeNode(15),new TreeNode(7)};  
        TreeUtil.createTree(root);  
        int res = sumOfLeftLeaves(root[0]);  
        System.out.println(res);  
    }  
  
    public static int sumOfLeftLeaves(TreeNode root) {  
        if (root == null || (root.left == null && root.right == null)) {  
            return 0;  
        }  
        int leftSum = sumOfLeftLeaves(root.left);  
        if(root.left != null && root.left.left == null && root.left.right == null){  
            leftSum = root.left.val;  
        }  
        int rightSum = sumOfLeftLeaves(root.right);  
        return leftSum + rightSum;  
    }  
}
```

# 解法2

迭代，BFS
时间：100%，空间42.78%
```java
public static int sumOfLeftLeaves(TreeNode root) {  
    if (root == null) {  
        return 0;  
    }  
    int res = 0;  
    Queue<TreeNode> queue = new LinkedList<>();  
    queue.add(root);  
    while (!queue.isEmpty()) {  
        TreeNode cur = queue.poll();  
        if (cur.left != null) {  
            if (cur.left.left == null && cur.left.right == null) {  
                res += cur.left.val;  
            } else {  
                queue.add(cur.left);  
            }  
        }  
        if (cur.right != null) {  
            if (cur.right.left == null && cur.right.right == null) {  
                continue;  
            } else {  
                queue.add(cur.right);  
            }  
        }  
    }  
    return res;  
}
```

