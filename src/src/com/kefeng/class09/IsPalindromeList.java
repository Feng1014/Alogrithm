package com.kefeng.class09;

import java.util.Stack;

/**
 * 判断链表是否回文链
 */
public class IsPalindromeList {

    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 使用stack容器保存链表中的每个元素
     * 如果出栈元素值和遍历链表结果相同，则链为回文链
     */
    public static boolean isPalindrome1(Node root) {
        Node head = root;
        Stack<Node> nodes = new Stack<>();
        while (head != null) {
            nodes.push(head);
            head = head.next;
        }
        while (root != null) {
            if (root.value != nodes.pop().value) {
                return false;
            }
            root = root.next;
        }
        return true;
    }

    public static boolean isPalindrome(Node root) {
        Node slow = root, fast = root;
        Node pre = null, cur = root;
        while (fast != null && fast.next != null) {
            cur = slow;
            slow = slow.next;
            fast = fast.next.next;
            cur.next = pre;
            pre = cur;
        }
        /**对于奇数个元素的链表，快指针到达链尾但没有指向空，则表明慢指针指向了中间元素，需要将慢指针再向后移一位。[1,2,3,2,1]fast指向1，则slow指向3，此时需要将slow再向后移一位
         * 对于偶数个元素的链表，快指针到达链尾并指向空，慢指针指向两个中间节点的靠后节点。[1,2,2,1]fast指向空后，slow指向第二个2*/
        if (fast != null) {
            slow = slow.next;
        }
        while (pre != null && slow != null) {
            if (pre.value != slow.value) {
                return false;
            }
            pre = pre.next;
            slow = slow.next;
        }
        return true;
    }

    public static void printLinkedList(Node root) {
        while (root != null) {
            System.out.print(root.value + " ");
            root = root.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.println(isPalindrome(head));
        System.out.println(isPalindrome1(head));
    }

}
