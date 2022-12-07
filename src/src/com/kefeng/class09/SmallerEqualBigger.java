package com.kefeng.class09;

/**
 * 给定一个链表，给定一个元素。将链表中小于该元素的值放到左边，等于该元素的值放到中间，大于该元素的值放到右边
 */
public class SmallerEqualBigger {

    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 将链表分成小于pivot，等于pivot，大于pivot三部分
     *
     * @param root
     * @param pivot
     * @return
     */
    public static Node listPartition(Node root, int pivot) {
        Node sH = null, sT = null;
        Node eH = null, eT = null;
        Node bH = null, bT = null;
        Node next = null;
        while (root != null) {
            next = root.next;
            root.next = null;
            if (root.value < pivot) {
                if (sH == null) {
                    sH = root;
                    sT = root;
                } else {
                    sT.next = root;
                    sT = root;
                }
            } else if (root.value == pivot) {
                if (eH == null) {
                    eH = root;
                    eT = root;
                } else {
                    eT.next = root;
                    eT = root;
                }
            } else {
                if (bH == null) {
                    bH = root;
                    bT = root;
                } else {
                    bT.next = root;
                    bT = root;
                }
            }
            root = next;
        }
        if (sT != null) {
            sT.next = eH;
            eT = (eT != null) ? eT : sT;
        }
        if (eT != null) {
            eT.next = bH;
        }
        return sH != null ? sH : (eH != null ? eH : bH);
    }

    public static void printLinkedList(Node root) {
        while (root != null) {
            System.out.print(root.value + " ");
            root = root.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node root = new Node(7);
        root.next = new Node(9);
        root.next.next = new Node(1);
        root.next.next.next = new Node(8);
        root.next.next.next.next = new Node(5);
        root.next.next.next.next.next = new Node(2);
        root.next.next.next.next.next.next = new Node(5);
        printLinkedList(root);
        Node node = listPartition(root, 5);
        printLinkedList(node);
    }

}
