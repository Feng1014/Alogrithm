package com.kefeng.class31;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AC自动机。在大文本中匹配铭感字符串。
 */
public class AC2 {

    public static class Node {
        //end表示该节点是否为字符串的结尾，如果是end的值就是该字符串
        public String end;
        //表示end字符串是否使用过
        public boolean endUse;
        //end字符串中最后字符指向的节点
        public Node fail;
        public Node[] nexts;

        public Node() {
            end = null;
            endUse = false;
            fail = null;
            nexts = new Node[26];
        }
    }

    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.end = s;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cfail = null;
            while (!queue.isEmpty()) {
                /**选出父节点*/
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    /**如果父节点有i号子节点，重新对fail赋值*/
                    if (cur.nexts[i] != null) {
                        /**先把父节点i号子节点的fail指向root,如果后期需要更新则更新，如果不需要则不更新*/
                        cur.nexts[i].fail = root;
                        /**从父节点的fail指针上更新父节点所有子节点的fail指针*/
                        cfail = cur.fail;
                        while (cfail != null) {
                            /**如果父节点的fail指向的节点有i号子节点，更新父节点的i号子节点的fail指针*/
                            if (cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                            }
                            /**如果父节点的fail指向的节点没有i号子节点，则再向上寻找父节点fail指针指向节点的fail指针指向的节点有无i号子节点。如果依然没有再向上寻找*/
                            cfail = cfail.fail;
                        }
                        /**将当前父节点的i号子节点填入队列*/
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        public List<String> containWords(String content) {
            List<String> ans = new ArrayList<>();
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            for (int i = 0; i < 26; i++) {
                /**index表示cur的第index子节点*/
                index = str[i] - 'a';
                /**如果cur不存在第index子节点，且cur不是root。则将cur转向cur的fail指针指向的节点*/
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                /**来到cur节点的第index子节点。如果有index子节点指向index子节点，如果没有指向root节点*/
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                /**遍历cur节点fail指针指向的所有节点，看是否出现敏感字符串*/
                follow = cur;
                while (follow != root) {
                    if (follow.endUse) {
                        break;
                    }
                    /**在cur节点的所有fail指针上出现了敏感字符串，则添加到ans中*/
                    if (follow.end != null) {
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        ACAutomation acAutomation= new ACAutomation();
        acAutomation.insert("abs");
        acAutomation.insert("sdfg");
        acAutomation.insert("sdfgj");
        acAutomation.build();
        List<String> words = acAutomation.containWords("ashjgkaksgabsakjsddfgjkdng");
        for (String word: words
             ) {
            System.out.println(word);
        }
    }

}
