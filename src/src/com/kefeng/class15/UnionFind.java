package com.kefeng.class15;


import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 定义并查集，
 * 并查集的两种方法，判断是否同一个集合isSameSet，合并两个集合union
 */
public class UnionFind {

    public static class Node<V> {

        V value;

        public Node(V v) {
            value = v;
        }
    }

    public static class unionFind<V> {

        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        public unionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values
            ) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findFather(Node<V> cur){
            Stack<Node<V>> stack = new Stack<>();
            while (parents.get(cur) != cur){
                stack.push(cur);
                cur = parents.get(cur);
            }
            while (!stack.isEmpty()){
                parents.put(stack.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a)) ==findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            Node<V> ahead = findFather(nodes.get(a));
            Node<V> bhead = findFather(nodes.get(b));
            if (ahead != bhead){
                int aSetSize = sizeMap.get(ahead);
                int bSetSize = sizeMap.get(bhead);
                Node<V> big = aSetSize > bSetSize ? ahead : bhead;
                Node<V> small = big == ahead ? bhead : ahead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }
    }
}
