package com.kefeng.class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 求二叉树的最大宽度
 */
public class TreeMaxWidth {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 层次遍历二叉树寻找最大宽度
     *
     * @param head
     * @return
     */
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        /**使用哈希表存储每个节点所在的层数*/
        Map<Node, Integer> map = new HashMap<>();
        map.put(head, 1);
        int max = 0;
        int curLevel = 1;
        int curLevelNodes = 0;
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            int curNodeLevel = map.get(poll);
            if (poll.left != null) {
                map.put(poll.left, curNodeLevel + 1);
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                map.put(poll.right, curNodeLevel + 1);
                queue.offer(poll.right);
            }
            /**如果当前节点在当前层则让当前层的节点数自增，否则当前层转移到下一层，当前层节点数的初值变为1*/
            if (curNodeLevel == curLevel) {
                curLevelNodes++;
            } else {
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
        }
        max = Math.max(max, curLevelNodes);
        return max;
    }

    /**
     * 使用几个常量计算二叉树的最大宽度
     *
     * @param head
     * @return
     */
    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        int max = 0;
        int curLevelNodes = 0;
        /**当前层的最后一个节点*/
        Node curEnd = head;
        /**当前层的下一层的最后一个节点*/
        Node nextEnd = null;
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            if (poll.left != null) {
                queue.offer(poll.left);
                nextEnd = poll.left;
            }
            if (poll.right != null) {
                queue.offer(poll.right);
                nextEnd = poll.right;
            }
            /**从队列中弹出一个节点后，让当前层的节点数自增*/
            curLevelNodes++;
            /**如果当前节点和当前层的最后一个节点相同，表示当前层已经到最后一个节点，需要到下一层计算*/
            if (poll == curEnd) {
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 0;
                curEnd = nextEnd;
            }
        }
        return max;
    }

    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("error");
            }
        }
        System.out.println("finished");

//        Node head = new Node(1);
//        Node left = new Node(2);
//        head.left = left;
//        Node right = new Node(3);
//        head.right = right;
//        left.left = new Node(4);
//        Node leftRight = new Node(5);
//        left.right = leftRight;
//        right.right = new Node(6);
//        leftRight.left = new Node(7);
//        System.out.println(maxWidthUseMap(head));
//        System.out.println(maxWidthNoMap(head));

    }

}
