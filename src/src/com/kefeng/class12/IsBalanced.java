package com.kefeng.class12;

/**
 * 判断二叉树是否为平衡二叉树
 */
public class IsBalanced {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static boolean isBalanced(Node head) {
        return process(head).isBalanced;
    }

    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean i, int h) {
            isBalanced = i;
            height = h;
        }
    }

    public static Info process(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = true;
        if (!leftInfo.isBalanced) {
            isBalanced = false;
        }
        if (!rightInfo.isBalanced) {
            isBalanced = false;
        }
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }
}
