package com.kefeng.class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 对二叉树执行序列化和反序列
 */
public class SerializeAndReconstructTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 对二叉树执行序列化，使用队列存储序列化的结果
     *
     * @param head
     * @return
     */
    public static Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pre(head, ans);
        return ans;
    }

    public static void pre(Node head, Queue<String> ans) {
        if (head == null) {
            return;
        }
        ans.add(String.valueOf(head.value));
        pre(head.left, ans);
        pre(head.right, ans);
    }

    /**
     * 根据前序序列化得到结果对二叉树执行反序列化，得到原来的二叉树
     *
     * @param preList
     * @return
     */
    public static Node buildTreeByPreQueue(Queue<String> preList) {
        if (preList == null || preList.size() == 0) {
            return null;
        }
        return preBuild(preList);
    }

    public static Node preBuild(Queue<String> preList) {
        String poll = preList.poll();
        if (poll == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(poll));
        head.left = preBuild(preList);
        head.right = preBuild(preList);
        return head;
    }

    /**
     * 对二叉树使用层序序列化，使用Queue存储层序后的结果
     *
     * @param head
     * @return
     */
    public static Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        if (head == null) {
            ans.offer(null);
        } else {
            ans.offer(String.valueOf(head.value));
            Queue<Node> queue = new LinkedList<>();
            queue.offer(head);
            while (!queue.isEmpty()) {
                Node poll = queue.poll();
                if (poll.left != null) {
                    ans.offer(String.valueOf(poll.left.value));
                    queue.offer(poll.left);
                } else {
                    ans.offer(null);
                }
                if (poll.right != null) {
                    ans.offer(String.valueOf(poll.right.value));
                    queue.offer(poll.right);
                } else {
                    ans.offer(null);
                }
            }
        }
        return ans;
    }

    /**
     * 将层序序列化得到的结果反序列化为二叉树
     *
     * @param levelList
     * @return
     */
    public static Node buildTreeByLevelQueue(Queue<String> levelList) {
        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        Node head = generateNode(levelList.poll());
        Queue<Node> queue = new LinkedList<>();
        if (head != null) {
            queue.add(head);
        }
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            poll.left = generateNode(levelList.poll());
            poll.right = generateNode(levelList.poll());
            if (poll.left != null) {
                queue.add(poll.left);
            }
            if (poll.right != null) {
                queue.add(poll.right);
            }
        }
        return head;
    }

    public static Node generateNode(String str) {
        if (str == null) {
            return null;
        }
        return new Node(Integer.valueOf(str));
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
//        Queue<String> queue = preSerial(head);
        Queue<String> queue = levelSerial(head);
        for (String str : queue
        ) {
            System.out.print(Integer.valueOf(str) + " ");
        }
    }
}
