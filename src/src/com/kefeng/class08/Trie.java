package com.kefeng.class08;

/**
 * 前缀树得到数组实现
 */
public class Trie {

    /**
     * 前缀树的节点类
     */
    public static class Node {
        /**
         * 该节点有多少个字符串经过
         */
        public int pass;
        /**
         * 以该节点为末尾的字符串有多少个
         */
        public int end;
        /**
         * 和该节点相连的节点
         */
        public Node[] nexts;

        public Node() {
            pass = 0;
            end = 0;
            nexts = new Node[26];
        }
    }

    private Node root;

    public Trie() {
        root = new Node();
    }

    /**
     * 向前缀树中新增元素
     *
     * @param word
     */
    public void insert(String word) {
        if (word == null) {
            return;
        }
        char[] str = word.toCharArray();
        /**前缀树的根节点赋给node*/
        Node node = root;
        node.pass++;
        int path = 0;
        /**依次遍历word字符串，构造前缀树*/
        for (int i = 0; i < str.length; i++) {
            /**node节点的第path个分支节点*/
            path = str[i] - 'a';
            /**如果node的第path个分支节点不存在，则创建出来*/
            if (node.nexts[path] == null) {
                node.nexts[path] = new Node();
            }
            /**node下移到第path个分支节点*/
            node = node.nexts[path];
            node.pass++;
        }
        /**到word字符串末尾，表示word字符串在前缀树上出现，让end自增*/
        node.end++;
    }

    /**
     * 查询前缀树中出现了多少个word字符串
     *
     * @param word
     * @return
     */
    public int search(String word) {
        if (word == null) {
            return 0;
        }
        char[] str = word.toCharArray();
        Node node = root;
        int index = 0;
        for (int i = 0; i < str.length; i++) {
            index = str[i] - 'a';
            if (node.nexts[index] == null) {
                return 0;
            }
            node = node.nexts[index];
        }
        /**返回end表示前缀树中出现了多少个word字符串*/
        return node.end;
    }

    /**
     * 查找前缀树中有多少个以pre为前缀的字符串
     *
     * @param pre
     * @return
     */
    public int searchPrefix(String pre) {
        if (pre == null) {
            return 0;
        }
        char[] str = pre.toCharArray();
        Node node = root;
        int index = 0;
        for (int i = 0; i < str.length; i++) {
            index = str[i] - 'a';
            if (node.nexts[index] == null) {
                return 0;
            }
            node = node.nexts[index];
        }
        /**返回pass表示前缀树中出现了多少个以pre为前缀的字符串*/
        return node.pass;
    }

    /**
     * 删除前缀树中某个字符串。
     * 从头开始对每个节点的pass减1，减到最后一个节点，对end减一即可。
     *
     * @param word
     */
    public void delete(String word) {
        /**需要先判断该字符串是否存在*/
        if (search(word) != 0) {
            char[] str = word.toCharArray();
            Node node = root;
            node.pass--;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                /**node的下一个节点的pass减1后如果等于0，则表示该字符串需要全部删除，让下一个节点置空即可*/
                if (--node.nexts[index].pass == 0) {
                    node.nexts[index] = null;
                    return;
                }
                node = node.nexts[index];
            }
            node.end--;
        }
    }

}
