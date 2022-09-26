package com.kefeng.class13;


import java.util.ArrayList;
import java.util.List;


/**
 * 一颗多叉树，计算最大快乐值。
 * 多叉树的结构是（快乐值，子节点以list形式存储）
 * 计算规则：上级节点选中，下级所有节点都无需选中，隔一层再选择节点，由此选出符合条件的节点，计算出一颗多叉树总的快乐值
 */
public class MaxHappy {

    public static class TreeNode {

        public int happy;
        public List<TreeNode> nexts;

        public TreeNode(int happy) {
            this.happy = happy;
            this.nexts = new ArrayList<>();
        }
    }

    public static int maxHappy(TreeNode head) {
        Info allInfo = process(head);
        return Math.max(allInfo.no, allInfo.yes);
    }

    /**
     * 对选中与否构造对象
     */
    public static class Info {

        public int no;
        public int yes;

        public Info(int n, int y) {
            no = n;
            yes = y;
        }
    }

    public static Info process(TreeNode x) {
        if (x == null) {
            return new Info(0, 0);
        }
        int no = 0;
        int yes = x.happy;
        for (TreeNode next : x.nexts
        ) {
            Info nextInfo = process(next);
            no += Math.max(nextInfo.no, nextInfo.yes);
            yes += nextInfo.no;
        }

        return new Info(no, yes);
    }

    public static TreeNode generateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        TreeNode boss = new TreeNode((int) (Math.random() * (maxHappy + 1)));
        generateNext(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    public static void generateNext(TreeNode node, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextSizes = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextSizes; i++) {
            TreeNode next = new TreeNode((int) (Math.random() * (maxHappy + 1)));
            node.nexts.add(next);
            generateNext(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxNexts = 9;
        int maxHappy1 = 100;
        int testSizes = 100000;
        for (int i = 0; i < testSizes; i++) {
            TreeNode head = generateBoss(maxLevel, maxNexts, maxHappy1);
            int happy = maxHappy(head);
            System.out.println(happy);
        }
    }
}

