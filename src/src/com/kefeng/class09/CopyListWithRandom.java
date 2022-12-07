package com.kefeng.class09;

import java.util.HashMap;
import java.util.Map;

/**
 * 复制链表
 * https://leetcode.cn/problems/copy-list-with-random-pointer/
 */
public class CopyListWithRandom {

    public static class Node {
        public int val;
        public Node next;
        public Node random;

        public Node(int v) {
            val = v;
        }
    }

    public static Node copyRandomList(Node root) {
        Map<Node, Node> map = new HashMap<Node, Node>();
        Node cur = root;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = root;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(root);
    }

    public static Node copyRandomList1(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;
        /**[1,2,3]的链表变成[1,1',2,2',3,3']*/
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }
        cur = head;
        Node copy = null;
        /**将[1,1',2,2',3,3']链表的random指针整理正确*/
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            /**假设1的random指针指向3，则1'的random指针指向3'。3‘通过3的next指针即可找到。*/
            copy.random = cur.random != null ? cur.random.next : null;
            cur = next;
        }
        cur = head;
        Node res = head.next;
        /**将[1,1',2,2',3,3']链表中新老链表的next指针断开，形成[1,2,3]和[1',2',3']*/
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            /**1的next指针指向2*/
            cur.next = next;
            /**1'的next指针2’。2‘通过2的next指针找到*/
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }

}
