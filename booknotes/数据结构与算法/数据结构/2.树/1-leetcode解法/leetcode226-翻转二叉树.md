# 题目
![[Pasted image 20230404213538.png]]

# 解法1：

这题应该算是最简单的递归题了，如果递归能做出来，说明递归应该会一些了

原理：

- 不需要返回值;

- 终止条件是节点为null

- 一层的递归逻辑就是左右子节点互换，不用考虑是否为null

时间：100%，空间：68.79%

```java
package com.wddmg.datastructure.binarytree.leetcode226;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
  
import static com.wddmg.datastructure.binarytree.base.RecursiveOrder.inOrder;  
import static com.wddmg.datastructure.binarytree.base.RecursiveOrder.preOrder;  
  
/**  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/04 21:27 duym Exp $  
 */public class Test1 {  
    public static void main(String[] args) {  
        TreeNode node4 = new TreeNode(4);  
        TreeNode node2 = new TreeNode(2);  
        TreeNode node1 = new TreeNode(1);  
        TreeNode node3 = new TreeNode(3);  
        TreeNode node7 = new TreeNode(7);  
        TreeNode node6 = new TreeNode(6);  
        TreeNode node9 = new TreeNode(9);  
  
        node4.left = node2;  
        node4.right = node7;  
        node2.left = node1;  
        node2.right = node3;  
        node7.left = node6;  
        node7.right = node9;  
  
        invertTree(node4);  
  
        inOrder(node4);  
    }  
  
    public static TreeNode invertTree(TreeNode root) {  
        invert(root);  
        return root;  
    }  
  
    public static void invert(TreeNode root){  
        if(root == null){  
            return;  
        }  
        TreeNode temp = root.left;  
        root.left = root.right;  
        root.right = temp;  
        invert(root.left);  
        invert(root.right);  
    }  
}
```

# 解法2：

BFS:每次弹出这个节点，就让左右互换，互换完了把它们加入到队列里。

时间：100%，空间88.39%

```java
package com.wddmg.datastructure.binarytree.leetcode226;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import java.util.Deque;  
import java.util.LinkedList;  
import java.util.Queue;  
  
import static com.wddmg.datastructure.binarytree.base.RecursiveOrder.inOrder;  
  
/**  
 * @author duym  
 * @version $ Id: Test2, v 0.1 2023/04/04 21:43 duym Exp $  
 */public class Test2 {  
    public static void main(String[] args) {  
        TreeNode node4 = new TreeNode(4);  
        TreeNode node2 = new TreeNode(2);  
        TreeNode node1 = new TreeNode(1);  
        TreeNode node3 = new TreeNode(3);  
        TreeNode node7 = new TreeNode(7);  
        TreeNode node6 = new TreeNode(6);  
        TreeNode node9 = new TreeNode(9);  
  
        node4.left = node2;  
        node4.right = node7;  
        node2.left = node1;  
        node2.right = node3;  
        node7.left = node6;  
        node7.right = node9;  
  
        invertTree(node4);  
  
        inOrder(node4);  
    }  
  
    public static TreeNode invertTree(TreeNode root) {  
        if(root == null){  
            return root;  
        }  
        Deque<TreeNode> queue = new LinkedList<>();  
        queue.add(root);  
        while(!queue.isEmpty()){  
            TreeNode cur = queue.pop();  
            TreeNode temp = cur.left;  
            cur.left = cur.right;  
            cur.right = temp;  
            if(cur.left != null){  
                queue.add(cur.left);  
            }  
            if(cur.right != null){  
                queue.add(cur.right);  
            }  
        }  
        return root;  
    }  
}
```

# 解法3：

深度优先，和队列其实是一样的，不写了