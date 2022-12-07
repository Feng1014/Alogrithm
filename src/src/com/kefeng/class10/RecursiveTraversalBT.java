package com.kefeng.class10;

/**
 * 递归遍历二叉树
 */
public class RecursiveTraversalBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void pre(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.value + " ");
        pre(root.left);
        pre(root.right);
        ;
    }

    public static void in(Node root) {
        if (root == null) {
            return;
        }
        in(root.left);
        System.out.print(root.value + " ");
        in(root.right);
    }

    public static void post(Node root) {
        if (root == null) {
            return;
        }
        post(root.left);
        post(root.right);
        System.out.print(root.value + " ");
    }

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
//        pre(root);
//        in(root);
        post(root);
    }
}
