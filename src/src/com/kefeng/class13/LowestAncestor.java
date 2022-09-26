package com.kefeng.class13;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 寻找二叉树中两个节点的最近公共祖先节点
 */
public class LowestAncestor {

    public static class TreeNode {

        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    /**
     * 寻找一个节点，把找到的所有节点都保存到hashmap中；再找另一个节点，如果在这个过程中有hashmap中的节点出现，则认为这两个节点有公共祖先
     * @param head
     * @param o1
     * @param o2
     * @return
     */
    public static TreeNode lowestAncestor1(TreeNode head, TreeNode o1, TreeNode o2) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        HashSet<TreeNode> o1Set = new HashSet<>();
        TreeNode cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }

    public static void fillParentMap(TreeNode head, HashMap<TreeNode, TreeNode> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    public static TreeNode findAncestor(TreeNode head, TreeNode a, TreeNode b) {
        return process(head, a, b).ans;
    }

    public static class Info {

        public boolean findA;
        public boolean findB;
        public TreeNode ans;

        public Info(boolean fA, boolean fB, TreeNode an) {
            findA = fA;
            findB = fB;
            ans = an;
        }
    }

    /**
     * 递归查找两个节点的最近公共祖先节点
     * 左子树上查找（找到的情况：a是head，b是head下的子节点，则a是最近公共祖先；b是head，a是head下的子节点，则b是最近公共祖先；a和b分布在两侧，则head为最近公共祖先）
     * 右子树上查找和左子树上查找一模一样
     * @param head
     * @param a
     * @param b
     * @return
     */
    public static Info process(TreeNode head, TreeNode a, TreeNode b) {
        if (head == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(head.left, a, b);
        Info rightInfo = process(head.right, a, b);
        boolean findA = (a == head) || leftInfo.findA || rightInfo.findA;
        boolean findB = (b == head) || leftInfo.findB || rightInfo.findB;
        TreeNode ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else {
            if (findA && findB) {
                ans = head;
            }
        }
        return new Info(findA, findB, ans);
    }

    public static TreeNode generateCBT(int maxlevel, int maxvalue) {
        return generate(1, maxlevel, maxvalue);
    }

    public static TreeNode generate(int level, int maxlevel, int maxvalue) {
        if (level > maxlevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxvalue));
        head.left = generate(level + 1, maxlevel, maxvalue);
        head.right = generate(level + 1, maxlevel, maxvalue);
        return head;
    }

    public static TreeNode pickRandomOne(TreeNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        preFillList(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    public static void preFillList(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        preFillList(head.left, arr);
        preFillList(head.right, arr);
    }

    public static void main(String[] args) {
        int maxlevel = 4;
        int maxvalue = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateCBT(maxlevel, maxvalue);
            TreeNode a = pickRandomOne(head);
            TreeNode b = pickRandomOne(head);
            if (findAncestor(head, a, b) != lowestAncestor1(head, a, b)){
                System.out.println("opas==========");
            }
        }
        System.out.println("finish!");
    }
}

