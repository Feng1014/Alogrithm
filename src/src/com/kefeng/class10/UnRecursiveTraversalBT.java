package com.kefeng.class10;

import java.util.Stack;

/**
 * 非递归遍历二叉树
 */
public class UnRecursiveTraversalBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 前序遍历
     *
     * @param root
     */
    public static void pre(Node root) {
        if (root != null) {
            /**使用栈存储遍历到的每个节点*/
            Stack<Node> nodes = new Stack<>();
            /**头节点先入栈*/
            nodes.push(root);
            while (!nodes.isEmpty()) {
                /**头节点入栈后，接着出栈，并输出头节点的值*/
                Node pop = nodes.pop();
                System.out.print(pop.value + " ");
                /**接着让头节点的右节点入栈*/
                if (pop.right != null) {
                    nodes.push(pop.right);
                }
                /**右节点入栈后再让左节点入栈。这样可以保证左节点先弹出，右节点后弹出，实现先头，再左，再右的先序遍历*/
                if (pop.left != null) {
                    nodes.push(pop.left);
                }
            }
        }
        System.out.println();
    }

    /**
     * 中序遍历
     *
     * @param root
     */
    public static void in(Node root) {
        if (root != null) {
            /**使用栈存储遍历到的每个节点*/
            Stack<Node> nodes = new Stack<>();
            while (!nodes.isEmpty() || root != null) {
                /**中序首先判断该节点是否存在，接着让该节点以及该节点的左子树依次入栈。*/
                if (root != null) {
                    nodes.push(root);
                    root = root.left;
                } else {
                    /**如果没有左子树可以入栈了，则弹出栈顶元素，并对栈顶元素的右子树执行入栈操作*/
                    Node pop = nodes.pop();
                    System.out.print(pop.value + " ");
                    root = pop.right;
                }
            }
        }
        System.out.println();
    }

    /**
     * 后序遍历
     *
     * @param root
     */
    public static void post(Node root) {
        if (root != null) {
            /**后序遍历需要两个栈。如果nodes栈的出栈顺序是【头，右，左】，那么postNode栈的入栈顺序是【头，右，左】，则postNode栈的出栈顺序是【左，右，头】，完成后续遍历*/
            Stack<Node> nodes = new Stack<>();
            Stack<Node> postNode = new Stack<>();
            nodes.push(root);
            while (!nodes.isEmpty()) {
                Node pop = nodes.pop();
                postNode.push(pop);
                if (pop.left != null) {
                    nodes.push(pop.left);
                }
                if (pop.right != null) {
                    nodes.push(pop.right);
                }
            }
            while (!postNode.isEmpty()) {
                System.out.print(postNode.pop().value + " ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        pre(root);
        in(root);
        post(root);
    }
}
