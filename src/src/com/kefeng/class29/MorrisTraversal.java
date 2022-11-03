package com.kefeng.class29;

/**
 * morris遍历，不需要额外的空间时间复杂度o(n)，空间复杂度o(1)
 */
public class MorrisTraversal {

    public static class Node {
        public int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    /**
     * morris遍历
     *
     * @param node
     */
    public static void morris(Node node) {
        Node cur = node;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }

    /**
     * morris前序遍历
     *
     * @param node
     */
    public static void morrisPre(Node node) {
        if (node == null) {
            return;
        }
        Node cur = node;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    System.out.print(cur.data + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                System.out.print(cur.data + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * morris中序遍历
     *
     * @param node
     */
    public static void morrisIn(Node node) {
        if (node == null) {
            return;
        }
        Node cur = node;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            /**morris遍历中只出现一次的节点直接打印。出现两次的节点在第二次出现的时候遍历。第二次出现依然会向后走，走出if语句。来到这句打印*/
            System.out.print(cur.data + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * morris后序遍历
     * @param node
     */
    public static void morrisPos(Node node) {
        if (node == null) {
            return;
        }
        Node cur = node;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    /**针对第二次到达的节点，将这些节点的左子树的最右节点序列逆序。*/
                    printTree(cur.left);
                }
            }
            cur = cur.right;
        }
        /**将根节点的最右节点序列逆序*/
        printTree(node);
        System.out.println();
    }

    public static void printTree(Node node) {
        Node tail = reverseTree(node);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.data + " ");
            cur = cur.right;
        }
        reverseTree(tail);
    }

    public static Node reverseTree(Node node) {
        Node pre = null;
        Node next = null;
        while (node != null) {
            next = node.right;
            node.right = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        morrisPre(head);
        morrisIn(head);
        morrisPos(head);
    }

}
