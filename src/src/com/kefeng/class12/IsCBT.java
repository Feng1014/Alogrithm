package com.kefeng.class12;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 判断二叉树是否为完全二叉树
 */
public class IsCBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static boolean isCBT(Node head) {
        if (head == null) {
            return false;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        boolean leaf = false;
        Node l = null;
        Node r = null;
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            l = poll.left;
            r = poll.right;
            if ((leaf && (l != null || r != null)) || (l == null && r != null)) {
                return false;
            }
            if (l != null) {
                queue.offer(l);
            }
            if (r != null) {
                queue.offer(r);
            }
            if (l == null || r == null) {
                leaf = true;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.right = new Node(5);
        System.out.println(isCBT(head));
    }
}
