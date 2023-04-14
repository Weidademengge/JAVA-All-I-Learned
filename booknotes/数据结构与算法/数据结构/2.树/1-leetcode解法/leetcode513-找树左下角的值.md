# 题目

![[Pasted image 20230407220522.png]]

# 解法1

递归，不涉及中节点的操作，无所谓先中后序
时间：100%，空间：62.7%；
```java
package com.wddmg.datastructure.binarytree.leetcode513;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import com.wddmg.datastructure.binarytree.base.TreeUtil;  
  
/**  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/07 21:19 duym Exp $  
 */public class Test1 {  
    static int max;  
    static int res;  
  
    public static void main(String[] args) {  
        TreeNode[] root = {new TreeNode(1), new TreeNode(2), new TreeNode(3), new TreeNode(4),  
                null, new TreeNode(5), new TreeNode(6),null,null, new TreeNode(7)};  
        TreeUtil.createTree(root);  
        int res = findBottomLeftValue(root[0]);  
        System.out.println(res);  
    }  
  
    public static int findBottomLeftValue(TreeNode root) {  
        dfs(root,1);  
        return res;  
    }  
  
    private static void dfs(TreeNode root, int height) {  
        if (root == null) {  
            return;  
        }  
        if (height > max) {  
            max = height;  
            res = root.val;  
        }  
        dfs(root.left, height + 1);  
        dfs(root.right, height + 1);  
    }  
}
```

# 解法2

BFS，写到这了，正常狗都能写出BFS。
时间：61.15%，空间：73.15%；
```java
public static int findBottomLeftValue(TreeNode root) {  
    if (root == null) {  
        return 0;  
    }  
    int res = 0;  
    Queue<TreeNode> queue = new LinkedList<>();  
    queue.add(root);  
    while (!queue.isEmpty()) {  
        int size = queue.size();  
        res = queue.peek().val;  
        while (size-- > 0) {  
            TreeNode cur = queue.poll();  
            if(cur.left != null){  
                queue.add(cur.left);  
            }  
            if(cur.right != null){  
                queue.add(cur.right);  
            }  
        }  
    }  
    return res;  
}
```

# 解法3

还是BFS，单独拿出来是因为，一开始写的时候，不知道怎么判断是否有左右，看到一个答案，反着写就好了，写起来方便多了。

时间：61.14%，空间：24.35%
```java
public static int findBottomLeftValue(TreeNode root) {  
    if (root == null) {  
        return 0;  
    }  
    int res = root.val;  
    Queue<TreeNode> queue = new LinkedList<>();  
    queue.add(root);  
    while (!queue.isEmpty()) {  
        TreeNode cur = queue.poll();  
        if(cur.right != null){  
            queue.add(cur.right);  
            res = cur.right.val;  
        }  
        if(cur.left != null){  
            queue.add(cur.left);  
            res = cur.left.val;  
        }  
    }  
    return res;  
}
```

