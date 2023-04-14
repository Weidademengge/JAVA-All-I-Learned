# 题目



# 解法1

递归加回溯，递归采用的是先序遍历

```java
package com.wddmg.datastructure.binarytree.leetcode257;  
  
import com.wddmg.datastructure.binarytree.base.TreeNode;  
import com.wddmg.datastructure.binarytree.base.TreeUtil;  
import java.util.ArrayList;  
import java.util.List;  
  
/**  
 * @author duym  
 * @version $ Id: Test, v 0.1 2023/04/07 16:27 duym Exp $  
 */public class Test1 {  
  
    public static void main(String[] args) {  
        TreeNode[] root = {new TreeNode(1),new TreeNode(2),new TreeNode(3),null,new TreeNode(5)};  
        TreeUtil.createTree(root);  
//        TreeUtil.print(root[0]);  
        binaryTreePaths(root[0]);  
    }  
  
  
    public static List<String> binaryTreePaths(TreeNode root) {  
        List<String> paths = new ArrayList<>();  
        constructPaths(root,"",paths);  
        return paths;  
    }  
  
    public static void constructPaths(TreeNode root,String path,List<String> paths){  
        if(root != null){  
            StringBuffer pathSB = new StringBuffer(path);  
            pathSB.append(root.val);  
            if(root.left == null && root.right == null){  
                paths.add(pathSB.toString());  
            }else{  
                pathSB.append("->");  
                constructPaths(root.left,pathSB.toString(),paths);  
                constructPaths(root.right,pathSB.toString(),paths);  
            }  
        }  
    }  
}
```

# 解法2

迭代BFS

```java
public static List<String> binaryTreePaths(TreeNode root) {  
    List<String> paths = new ArrayList<>();  
    if(root == null){  
        return paths;  
    }  
  
    Queue<TreeNode> nodeQueue =  new LinkedList<>();  
    Queue<String> pathQueue = new LinkedList<>();  
  
    nodeQueue.add(root);  
    pathQueue.add(Integer.toString(root.val));  
    while(!nodeQueue.isEmpty()){  
        TreeNode cur = nodeQueue.poll();  
        String path = pathQueue.poll();  
  
        if(cur.left == null && cur.right == null){  
            paths.add(path);  
        }else{  
            if(cur.left != null){  
                nodeQueue.add(cur.left);  
                StringBuffer sb = new StringBuffer(path);  
                pathQueue.add(sb.append("->").append(cur.left.val).toString());  
            }  
            if(cur.right != null){  
                nodeQueue.add(cur.right);  
                StringBuffer sb = new StringBuffer(path);  
                pathQueue.add(sb.append("->").append(cur.right.val).toString());  
            }  
        }  
    }  
    return paths;  
}
```

# 解法3

递归。。。

时间：18.25%，空间：93.34%

```java
public static List<String> binaryTreePaths(TreeNode root) {  
    List<String> paths = new ArrayList<>();  
    if (root == null) {  
        return paths;  
    }  
  
    if (root.left == null && root.right == null) {  
        paths.add(root.val + "");  
        return paths;  
    }  
    for (String path : binaryTreePaths(root.left)) {  
        paths.add(root.val+"->"+path);  
    }  
    for (String path : binaryTreePaths(root.right)) {  
        paths.add(root.val+"->"+path);  
    }  
  
    return paths;  
}
```