# 题目

![[Pasted image 20230404221939.png]]



# 解法1：

递归：

- 返回值是int，参数的参数是TreeNode（传进去一个节点，返回一个深度）

- 如果当前节点为null，返回0

- 后序遍历，先算左树深度，再算右树深度，返回的是左树和右树更高的再加1，就是当前节点深度

时间：100% 空间71.52% 

```java
package com.wddmg.datastructure.binarytree.leetcode104;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
  
/**  
 * 递归：左树和右树最大深度
 * 时间：100% 空间71.52%  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/03 22:17 duym Exp $  
 */public class Test1 {  
  
    public static void main(String[] args) {  
        TreeNode node3 = new TreeNode(3);  
        TreeNode node9 = new TreeNode(9);  
        TreeNode node20 = new TreeNode(20);  
        TreeNode node15= new TreeNode(15);  
        TreeNode node7= new TreeNode(7);  
  
        node3.left = node9;  
        node3.right = node20;  
        node20.left = node15;  
        node20.right = node7;  
  
        int res = maxDepth(node3);  
  
        System.out.println(res);  
    }  
  
    public static int maxDepth(TreeNode root) {  
        if(root == null){  
            return 0;  
        }else{  
            int leftTree = maxDepth(root.left);  
            int rightTree = maxDepth(root.right);  
            return Math.max(leftTree,rightTree) + 1;  
        }  
  
    }  
  
}
```


# 解法2：

迭代：BFS，每次遍历当前层数记录一下，最后返回的就是最大深度

时间：20.43;空间：98.52%  

```java
package com.wddmg.datastructure.binarytree.leetcode104;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import java.util.Deque;  
import java.util.LinkedList;  
  
/**  
 * BFS，把添加到集合的操作改为层数++  
 * 时间：20.43;空间：98.52%  
 * @author duym  
 * @version $ Id: Test2, v 0.1 2023/04/03 22:30 duym Exp $  
 */public class Test2 {  
    public static void main(String[] args) {  
        TreeNode node3 = new TreeNode(3);  
        TreeNode node9 = new TreeNode(9);  
        TreeNode node20 = new TreeNode(20);  
        TreeNode node15= new TreeNode(15);  
        TreeNode node7= new TreeNode(7);  
        node3.left = node9;  
        node3.right = node20;  
        node20.left = node15;  
        node20.right = node7;  
  
        int res = maxDepth(node3);  
  
        System.out.println(res);  
    }  
  
    public static int maxDepth(TreeNode root) {  
        if(root == null){  
            return 0;  
        }  
        int res = 0;  
        Deque<TreeNode> queue = new LinkedList<>();  
        TreeNode cur = root;  
        queue.add(cur);  
        while(!queue.isEmpty()){  
            int curLevelSize = queue.size();  
            for (int i = 0; i < curLevelSize; i++) {  
                cur = queue.pop();  
                if(cur.left != null){  
                    queue.add(cur.left);  
                }  
                if(cur.right != null){  
                    queue.add(cur.right);  
                }  
            }  
            res++;  
        }  
        return res;  
    }  
}
```