package com.kefeng.class29;

/**
 * 求二叉树以root节点起始的最小深度
 * https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
 */
public class MinDepth {

    public static class TreeNode {
        public int data;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int d) {
            data = d;
        }
    }

    /**
     * 递归求解二叉树的最小深度
     * @param root
     * @return
     */
    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root);
    }

    public static int process(TreeNode root) {
        if (root.left == null && root.right == null) {
            return 1;
        }
        int leftH = Integer.MAX_VALUE;
        if (root.left != null) {
            leftH = process(root.left);
        }
        int rightH = Integer.MAX_VALUE;
        if (root.right != null) {
            rightH = process(root.right);
        }
        return 1 + Math.min(leftH, rightH);
    }

    /**
     * morris遍历求解二叉树的最小深度
     * @param root
     * @return
     */
    public static int minDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode cur = root;
        TreeNode mostRight = null;
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                /**定义当前节点左子树上所有右节点的个数*/
                int rightBoardSize = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    rightBoardSize++;
                    mostRight = mostRight.right;
                }
                /**第一次到达具有左子树的节点*/
                if (mostRight.right == null) {
                    curLevel++;
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    /**第二次到达具有左子树的节点*/
                    if (mostRight.left == null) {
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    curLevel -= rightBoardSize;
                    mostRight.right = null;
                }
            } else {
                /**只有一次到达当前节点*/
                curLevel++;
            }
            cur = cur.right;
        }
        /**根节点右子树的最右节点需要单独讨论*/
        int finalSize = 1;
        cur = root;
        while (cur.right != null) {
            finalSize++;
            cur = cur.right;
        }
        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, finalSize);
        }
        return minHeight;
    }

}
