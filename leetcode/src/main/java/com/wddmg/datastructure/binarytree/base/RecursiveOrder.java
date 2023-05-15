package com.wddmg.datastructure.binarytree.base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.wddmg.datastructure.binarytree.base.TreeUtil.print;

/**
 * 基础一：递归序,每次调用方法都要打印，结果为124442555213666377731
 *
 * @author duym
 * @version $ Id: Base, v 0.1 2023/03/27 21:32 duym Exp $
 */
public class RecursiveOrder {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;

        print(node1);


//        System.out.println("递归序：");
//        recursiveOrder(node1);
//        System.out.println();
//        System.out.println("先序：");
        preOrder(node1);
//        System.out.println();
//        System.out.println("中序：");
//        inOrder(node1);
//        System.out.println();
//        System.out.println("后序：");
//        postOrder(node1);
//
//
//        System.out.println();
//        System.out.println("非递归先序：");
//        preOrderUnRecur(node1);
//        System.out.println();
//        System.out.println("非递归中序：");
//        inOrderUnRecur(node1);
//        System.out.println();
//        System.out.println("非递归后续：");
//        postOrderUnRecur(node1);
//        System.out.println();
//        System.out.println("================");
//        System.out.println("bfs:");
//        bfs(node1);

    }

    /**
     * 递归序,每次调用方法都要打印，结果为124442555213666377731
     */
    public static void recursiveOrder(TreeNode head) {
        if (head == null) {
            return;
        }
        System.out.print(head.val);
        recursiveOrder(head.left);
        System.out.print(head.val);
        recursiveOrder(head.right);
        System.out.print(head.val);

    }

    /**
     * 先序遍历，先打印头节点，再打印左数节点，再打印右树节点，结果1245367
     */
    public static void preOrder(TreeNode head) {
        if (head == null) {
            return;
        }
        System.out.print(head.val);
        preOrder(head.left);
        preOrder(head.right);
    }

    /**
     * 中序，左头右 结果4251637，这个最简单，直接全部落下就是中序
     */
    public static void inOrder(TreeNode head) {
        if (head == null) {
            return;
        }

        inOrder(head.left);
        System.out.print(head.val);
        inOrder(head.right);
    }

    /**
     * 后序遍历，左右头 4526731
     */
    public static void postOrder(TreeNode head) {
        if (head == null) {
            return;
        }

        postOrder(head.left);
        postOrder(head.right);
        System.out.print(head.val);
    }

    /**
     * 非递归的方法，实现先序遍历，需要准备一个栈，原理是先放右再放左，弹出并打印它自己，依次类推
     */
    public static void preOrderUnRecur(TreeNode head) {

        if (head != null) {
            Stack<TreeNode> stack = new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.val);
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
    }

    /**
     * 非递归中序遍历：一直加左树，加到null，
     * 每次弹出都看当前节点有没有右树，有右树就进栈
     *
     * @param head
     */
    public static void inOrderUnRecur(TreeNode head) {
        if (head != null) {
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.val);
                    head = head.right;
                }
            }
        }
    }


    /**
     * 非递归后续：准备两个栈，栈1和栈2，
     * 1.把当前节点放到栈1，弹出放到栈2,
     * 2.先左再右
     * 原理是放入栈2的顺序是头右左，弹出就变成左右头
     */
    public static void postOrderUnRecur(TreeNode head) {
        if (head != null) {
            Stack<TreeNode> s1 = new Stack<>();
            Stack<TreeNode> s2 = new Stack<>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop();
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().val);
            }
        }
    }

    /**
     * BFS：广度优先，准备个队列，放进去头结点，弹出后，先放左再放右，按照这个顺序
     * DFS：深度优先，先序遍历就是深度优先
     * 这个求哪层数节点的最大值，其实是有个bug
     *
     * @param head
     */
    public static Integer bfs(TreeNode head) {
        if (head == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(head);
        HashMap<TreeNode, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        // 当前层
        int curLevel = 1;
        // 当前节点
        int curLevelNodes = 0;
        int max = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);
            if (curNodeLevel == curLevel) {
                curLevelNodes++;
            } else {
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
//            System.out.print(cur.val);
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
        }
        return max;
    }
}
