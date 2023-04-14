# 题目
![[Pasted image 20230405202312.png]]


# 解法1：

递归：

传参和返回值：传参肯定是节点，返回当前层数

终止条件还是root== null，返回0

递归逻辑：如果左右有一个空，就返回1+不为空的子树，如果左右都不为空，返回最小层+1

时间：5.49%，空间：23.91%

```java
package com.wddmg.datastructure.binarytree.leetcode111;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  

/**  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/05 20:03 duym Exp $  
 */public class Test1 {  
  
    public static void main(String[] args) {  
        TreeNode node3 = new TreeNode(3);  
        TreeNode node9 = new TreeNode(9);  
        TreeNode node20 = new TreeNode(20);  
        TreeNode node15 = new TreeNode(15);  
        TreeNode node7 = new TreeNode(7);  
  
        node3.left = node9;  
        node3.right = node20;  
        node20.left = node15;  
        node20.right = node7;  
  
        int res = minDepth(node3);  
        System.out.println(res);  
    }  
  
    public static int minDepth(TreeNode root) {  
        if(root == null){  
            return 0;  
        }  
        if(root.left == null && root.right != null){  
            return 1 + minDepth(root.right);  
        } else if (root.left != null && root.right == null) {  
            return 1 + minDepth(root.left);  
        }else{  
            return 1 + Math.min(minDepth(root.left),minDepth(root.right));  
        }  
    }  
}
```

# 解法2：

还是递归，网上这个递归的逻辑太nice了,分了三种情况：

1. 左孩子和有孩子都为空的情况，说明到达了叶子节点，直接返回1即可

2. 如果左孩子和由孩子其中一个为空，那么需要返回比较大的那个孩子的深度  

这里其中一个节点为空，说明m1和m2有一个必然为0，所以可以返回m1 + m2 + 1;

3. 最后一种情况，也就是左右孩子都不为空，返回最小深度+1即可

```java
public static int minDepth(TreeNode root) {  
    if(root == null){  
        return 0;  
    }  
    if (root.left == null && root.right == null) {  
        return 1;  
    }  
    int leftHeight = minDepth(root.left);  
    int rightHeight = minDepth(root.right);  
    if (root.left == null || root.right == null) {  
        return leftHeight + rightHeight + 1;  
    }  
    return Math.min(leftHeight,rightHeight) + 1;  
}
```

# 解法3：

迭代，BFS，每层节点加入队列时，用map记录当前节点的层数，每次循环都获取当前层数。

时间：68.51%，空间：98.99%

```java
public static int minDepth(TreeNode root) {  
    if (root == null) {  
        return 0;  
    }  
    Deque<TreeNode> queue = new LinkedList<>();  
    Map<TreeNode, Integer> map = new HashMap<>();  
    queue.add(root);  
    map.put(root, 1);  
    while (!queue.isEmpty()) {  
        TreeNode cur = queue.pop();  
        int curLevel = map.get(cur);  
        if (cur.left == null && cur.right == null) {  
            return map.get(cur);  
        }  
        if (cur.left != null) {  
            map.put(cur.left, curLevel + 1);  
            queue.add(cur.left);  
        }  
        if (cur.right != null) {  
            map.put(cur.right, curLevel + 1);  
            queue.add(cur.right);  
        }  
    }  
    return 0;  
}
```