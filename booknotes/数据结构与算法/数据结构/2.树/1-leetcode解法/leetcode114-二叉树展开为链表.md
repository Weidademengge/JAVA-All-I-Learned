
# 题目
![[Pasted image 20230404161342.png]]

# 解法1：

最简单的方法就是前序遍历然后把所有的节点放到集合里，集合从头到尾把节点的右指针指向下一个。本来不想写的，算了还是写吧。

时间：21.71%
空间：61.89%

```java
package com.wddmg.datastructure.binarytree.leetcode114;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import java.util.ArrayList;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/04 14:58 duym Exp $  
 */public class Test1 {  
    public static void main(String[] args) {  
  
    }  
  
    public void flatten(TreeNode root) {  
        List<TreeNode> list = new ArrayList<>();  
        preOrder(root, list);  
        for (int i = 0; i < list.size() - 1; i++) {  
            list.get(i).right = list.get(i + 1);  
            list.get(i).left = null;  
        }  
    }  
  
    public static void preOrder(TreeNode root, List<TreeNode> list) {  
        if (root == null) {  
            return;  
        }  
  
        list.add(root);  
        preOrder(root.left, list);  
        preOrder(root.right, list);  
    }  
}
```

# 解法2：

迭代的方法，我弹出来的和我栈的最上层肯定是先序，这时候直接连就行，效果不太好总觉得是因为peek方法时间太长。
时间：21.71%，空间：9.99%。
```java
package com.wddmg.datastructure.binarytree.leetcode114;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Stack;  
  
/**  
 * 时间：21.71%,空间：9.99%  
 * @author duym  
 * @version $ Id: Test1, v 0.1 2023/04/04 14:58 duym Exp $  
 */public class Test2 {  
    public static void main(String[] args) {  
        TreeNode node1 = new TreeNode(1);  
        TreeNode node2 = new TreeNode(2);  
        TreeNode node3 = new TreeNode(3);  
        TreeNode node4 = new TreeNode(4);  
        TreeNode node5 = new TreeNode(5);  
        TreeNode node6 = new TreeNode(6);  
  
        node1.left = node2;  
        node1.right = node5;  
        node2.left = node3;  
        node2.right = node4;  
        node5.right = node6;  
  
        flatten(node1);  
  
        while(node1.right!= null){  
            System.out.println(node1.val);  
            node1 =node1.right;  
        }  
    }  
  
    public static void flatten(TreeNode root) {  
        if(root == null){  
            return;  
        }  
        Stack<TreeNode> stack = new Stack<>();  
        stack.push(root);  
        while(!stack.isEmpty()){  
            TreeNode cur = stack.pop();  
  
            if(cur.right != null){  
                stack.push(cur.right);  
            }  
            if(cur.left != null){  
                stack.push(cur.left);  
            }  
            cur.left = null;  
            if(stack.isEmpty()){  
                cur.right = null;  
            }else{  
                cur.right = stack.peek();  
            }  
        }  
    }  
}
```

# 解法3

寻找前驱节点，前驱节点这个太有意思了，写到这发现morris、左旋右旋都需要找前驱节点

原理：找到前驱节点，让前驱节点指向当前节点的右节点，当前节点的右指针指向左指针指向的下一个节点，断了左指针就行了

时间：100%，空间：60%

```java
package com.wddmg.datastructure.binarytree.leetcode114;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import java.util.Stack;  
  
/**  
 * 寻找前驱节点的方法  
 *  
 * @author duym  
 * @version $ Id: Test3, v 0.1 2023/04/04 17:28 duym Exp $  
 */public class Test3 {  
    public static void main(String[] args) {  
        TreeNode node1 = new TreeNode(1);  
        TreeNode node2 = new TreeNode(2);  
        TreeNode node3 = new TreeNode(3);  
        TreeNode node4 = new TreeNode(4);  
        TreeNode node5 = new TreeNode(5);  
        TreeNode node6 = new TreeNode(6);  
  
        node1.left = node2;  
        node1.right = node5;  
        node2.left = node3;  
        node2.right = node4;  
        node5.right = node6;  
  
        flatten(node1);  
  
        while (node1.right != null) {  
            System.out.println(node1.val);  
            node1 = node1.right;  
        }  
    }  
  
    public static void flatten(TreeNode root) {  
        TreeNode cur = root;  
        while (cur != null) {  
            if (cur.left != null) {  
                TreeNode predecessor = cur.left;  
                while (predecessor.right != null) {  
                    predecessor = predecessor.right;  
                }  
                predecessor.right = cur.right;  
                cur.right = cur.left;  
                cur.left = null;  
            }  
            cur =cur.right;  
        }  
    }  
}
```