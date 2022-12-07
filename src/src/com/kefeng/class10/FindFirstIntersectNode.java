package com.kefeng.class10;

import com.kefeng.class09.CopyListWithRandom;

/**
 * 两个链表，寻找第一个相交节点
 */
public class FindFirstIntersectNode {

    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 找出两条链表中的第一个相交节点
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        /**寻找第一条链的入环节点*/
        Node loop1 = getLoopNode(head1);
        /**寻找第二条链的入环节点*/
        Node loop2 = getLoopNode(head2);
        /**如果两条链都无环，则使用无环寻找相交节点的方法*/
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        /**如果两条链都有环，则使用有环寻找相交节点的方法*/
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        /**如果一条链有环，一条链无环，则两条链不可能相交，返回空值*/
        return null;
    }

    /**
     * 如果链表存在环，则找到第一个入环节点。如果不存在环，则返回空值。
     *
     * @param head
     * @return
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head.next, fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 如果两个链表都无环，找出第一个相交的节点。如果没有相交的节点则返回空值
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1, cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = head1, cur2 = head2;
        if (loop1 == loop2) {
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    public static void printLinkedList(Node head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(5);
        Node node = new Node(3);
        head.next.next = node;
        Node node1 = new Node(4);
        node.next = node1;
        node.next.next = node;
//        printLinkedList(head);
        Node loopNode = getLoopNode(head);
        System.out.println(loopNode.value);

        Node head1 = new Node(2);
        head1.next = new Node(7);
        Node node2 = new Node(9);
        head1.next.next = node2;
        node2.next = node;
        node2.next.next = node1;
//        printLinkedList(head1);
        Node loopNode1 = getLoopNode(head1);
        System.out.println(loopNode1.value);
//        head1.next = node;
//        head1.next.next = node1;
//        Node noLoop = noLoop(head, head1);
//        System.out.println(noLoop.value);

        Node intersectNode = getIntersectNode(head, head1);
        System.out.println(intersectNode.value);
    }

}
