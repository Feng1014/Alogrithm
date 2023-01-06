package com.kefeng.class12;

/**
 * 求二叉树中两个节点之间的最大距离
 */
public class MaxDistance {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static int maxDistance(Node head) {
        return process(head).maxDistance;
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }
    }

    public static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;
        int maxDistance = Math.max(Math.max(p1, p2), p3);
        return new Info(maxDistance, height);
    }

}
