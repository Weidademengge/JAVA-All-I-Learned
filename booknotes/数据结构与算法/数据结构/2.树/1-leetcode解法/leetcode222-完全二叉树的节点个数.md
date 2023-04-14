# 题目：

![[Pasted image 20230405220428.png]]

# 解法1：

先用个最简单的方法，递归前序，测试也不用写了

时间：100%，空间70.69%

```java
package com.wddmg.datastructure.binarytree.leetcode222;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
  
/**  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/05 22:00 duym Exp $  
 */public class Test1 {  
    public static void main(String[] args) {  
  
    }  
  
    public int countNodes(TreeNode root) {  
        if (root == null) {  
            return 0;  
        }  
        return countNodes(root.left) + countNodes(root.right) + 1;  
    }  
}
```

# 解法2：

写个BFS，正常应该写个递归后续，这个最恶心

时间：8.88%，空间：21.65%

```java
public int countNodes(TreeNode root) {  
    if (root == null) {  
        return 0;  
    }  
    Deque<TreeNode> queue = new LinkedList<>();  
    queue.add(root);  
    int res = 0;  
    while(!queue.isEmpty()){  
        TreeNode cur = queue.pop();  
        if(cur.left != null){  
            queue.add(cur.left);  
        }  
        if(cur.right != null){  
            queue.add(cur.right);  
        }  
        res++;  
    }  
    return res;  
}
```

# 解法3：

一个完全二叉树它的左右子树一定会有满二叉树，而满二叉树的数量为2>>n -1个结点，其中n为层数，所以我们不用全部遍历，可以把满二叉树的部分结点跳过。

我们怎么判断是不是满二叉树。这里的前提条件是，当前给的肯定是完全二叉树，而完全二叉树的子树也是完全二叉树，所以只要完全二叉树的最左和最右节点的层数相同，它一定是个满二叉树

时间：100%，空间：55.27%

```java
public static int countNodes(TreeNode root) {  
    if (root == null) {  
        return 0;  
    }  
    TreeNode left = root.left, right = root.right;  
    int leftHeight = 0, rightHeight = 0;  
    while (left != null) {  
        left = left.left;  
        leftHeight++;  
    }  
    while (right != null) {  
        right = right.right;  
        rightHeight++;  
    }  
    if (leftHeight == rightHeight) {  
        return (2 << leftHeight) - 1;  
    }  
    return countNodes(root.left) + countNodes(root.right) + 1;  
}
```