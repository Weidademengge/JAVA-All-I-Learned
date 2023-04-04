package com.wddmg.datastructure.binarytree.base;

/**
 * @author duym
 * @version $ Id: MorrisTest, v 0.1 2023/04/02 22:11 duym Exp $
 */
public class MorrisTest {
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
        /**
         *                 1
         *                / \
         *               2   3
         *              / \ / \
         *             4  5 6  7
         */

//        preOrderMorris(node1);
//        inOrderMorris(node1);
        postOrderMorris(node1);
//        System.out.println();
    }

    public static void morris(TreeNode head){
        if(head == null){
            return;
        }
        TreeNode cur = head;
        TreeNode mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // mostRight变成了cur左子树上， 最右的节点
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur =cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }

    public static void preOrderMorris(TreeNode head){
        if(head == null){
            return;
        }
        TreeNode cur = head;
        TreeNode mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // mostRight变成了cur左子树上， 最右的节点
                if(mostRight.right == null){
                    System.out.println(cur.val);
                    mostRight.right = cur;
                    cur =cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }else{
                System.out.println(cur.val);
            }
            cur = cur.right;
        }
    }

    public static void inOrderMorris(TreeNode head){
        if(head == null){
            return;
        }
        TreeNode cur = head;
        TreeNode mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // mostRight变成了cur左子树上， 最右的节点
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur =cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }
            System.out.print(cur.val);
            cur = cur.right;
        }
    }

    public static void postOrderMorris(TreeNode head){
        if(head == null){
            return;
        }
        TreeNode cur = head;
        TreeNode mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // mostRight变成了cur左子树上， 最右的节点
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur =cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        printEdge(head);
        System.out.println();
    }

    // 以X为头的树，逆序打印这棵树的有边界
    public static void printEdge(TreeNode X){
        TreeNode tail = reverseEdge(X);
        TreeNode cur = tail;
        while(cur != null){
            System.out.print(cur.val);
            cur =cur.right;
        }
        reverseEdge(tail);
    }

    public static TreeNode reverseEdge(TreeNode from){
        TreeNode pre = null;
        TreeNode next = null;
        while(from != null){
            next =from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    public static boolean isBSTMorris(TreeNode head){
        if(head == null){
            return true;
        }
        TreeNode cur = head;
        TreeNode mostRight = null;
        int preValue = Integer.MIN_VALUE;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                // mostRight变成了cur左子树上， 最右的节点
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur =cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }
            if(cur.val <= preValue){
                return false;
            }
            preValue = cur.val;
            cur = cur.right;
        }
        return true;
    }

}
