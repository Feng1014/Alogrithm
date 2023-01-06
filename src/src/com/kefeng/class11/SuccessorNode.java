package com.kefeng.class11;

/**
 * 求二叉树的后继节点（中序遍历的下一个元素）
 */
public class SuccessorNode {

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int v) {
            value = v;
        }
    }

    public static Node getSuccessorNode(Node head) {
        if (head == null) {
            return head;
        }
        /**当前节点有右子树，则在右子树上寻找最左节点。当前节点没有右子树，则一直向父节点寻找，直到父节点为空或者当前节点是父节点的左节点，返回父节点*/
        if (head.right != null) {
            return getMostLeft(head.right);
        } else {
            Node parent = head.parent;
            while (parent != null && parent.right == head) {
                head = parent;
                parent = head.parent;
            }
            return parent;
        }
    }

    public static Node getMostLeft(Node head) {
        if (head == null) {
            return head;
        }
        while (head.left != null) {
            head = head.left;
        }
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        Node left = new Node(2);
        head.left = left;
        left.parent = head;
        Node right = new Node(3);
        head.right = right;
        right.parent = head;
        Node leftLeft = new Node(4);
        left.left = leftLeft;
        leftLeft.parent = left;
        Node leftRight = new Node(5);
        left.right = leftRight;
        leftRight.parent = left;
        Node rightRight = new Node(6);
        rightRight.parent = right;
        right.right = rightRight;
        Node successorNode = getSuccessorNode(right);
        System.out.println(successorNode.value);
    }
}
