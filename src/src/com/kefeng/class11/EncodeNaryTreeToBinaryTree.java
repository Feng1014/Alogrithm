package com.kefeng.class11;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 多叉树转为二叉树
 */
public class EncodeNaryTreeToBinaryTree {

    /**
     * 多叉树的定义
     */
    public static class Node {
        public int value;
        public List<Node> children;

        public Node() {

        }

        public Node(int v) {
            value = v;
        }

        public Node(int v, List<Node> c) {
            value = v;
            children = c;
        }
    }

    /**
     * 二叉树的定义
     */
    public static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            value = v;
        }
    }

    /**
     * 多叉树转为二叉树
     *
     * @param head
     * @return
     */
    public static TreeNode encode(Node head) {
        if (head == null) {
            return null;
        }
        /**多叉树的头节点也是二叉树的头节点*/
        TreeNode node = new TreeNode(head.value);
        /**二叉树每个节点的左子树是多叉树上该节点的所有兄弟节点*/
        node.left = en(head.children);
        return node;
    }

    public static TreeNode en(List<Node> children) {
        TreeNode head = null;
        TreeNode cur = null;
        /**遍历多叉树中某个节点的所有孩子节点*/
        for (Node child : children
        ) {
            TreeNode tNode = new TreeNode(child.value);
            /**如果没有头节点，则遍历孩子的过程中遇到的第一个节点就是头节点。否则就让遍历到的孩子节点依次链在当前节点的右节点上*/
            if (head == null) {
                head = tNode;
            } else {
                cur.right = tNode;
            }
            /**当前节点来到遍历的孩子节点*/
            cur = tNode;
            /**当前节点的左子树即是遍历到孩子的所有孩子*/
            cur.left = en(child.children);
        }
        return head;
    }

    /**
     * 二叉树转为多叉树
     *
     * @param head
     * @return
     */
    public static Node decode(TreeNode head) {
        if (head == null) {
            return null;
        }
        /**多叉树的头节点即是二叉树的头节点，多叉树的孩子节点即是二叉树的左子树*/
        return new Node(head.value, de(head.left));
    }

    public static List<Node> de(TreeNode head) {
        List<Node> children = new ArrayList<>();
        /**二叉树中每个节点的左子树的右边界是多叉树中该节点的所有孩子节点*/
        while (head != null) {
            /**从二叉树的左子树中得到多叉树的节点*/
            Node node = new Node(head.value, de(head.left));
            /**将多叉树的节点添加到孩子链中*/
            children.add(node);
            /**二叉树的节点向右移动*/
            head = head.right;
        }
        return children;
    }

}
