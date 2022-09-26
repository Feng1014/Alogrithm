package com.kefeng.class16;


import com.kefeng.class15.NumberOfIslands2;

import java.util.*;

/**
 * 最小生成树，使用并查集求解
 */
public class Kruskal {

    public static class UnionFind {

        private HashMap<Node, Node> parents;
        private HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            parents = new HashMap<Node, Node>();
            sizeMap = new HashMap<Node, Integer>();
        }

        public void makeSets(Collection<Node> nodes) {
            parents.clear();
            sizeMap.clear();
            for (Node node : nodes
            ) {
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node findFather(Node cur) {
            Stack<Node> stack = new Stack<>();
            while (cur != parents.get(cur)) {
                stack.push(cur);
                cur = parents.get(cur);
            }
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(Node a, Node b) {
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aHead = findFather(a);
            Node bHead = findFather(b);
            if (aHead != bHead) {
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);
                if (aSize >= bSize) {
                    parents.put(bHead, aHead);
                    sizeMap.put(aHead, aSize + bSize);
                    sizeMap.remove(bHead);
                } else {
                    parents.put(aHead, bHead);
                    sizeMap.put(bHead, bSize + aSize);
                    sizeMap.remove(aHead);
                }
            }
        }
    }

    public static class myComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    /**
     * 最小生成树，并查集，优先队列，返回结果集为set
     *
     * @param graph
     * @return
     */
    public static Set<Edge> kruskal(Graph graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        PriorityQueue<Edge> pq = new PriorityQueue<>(new myComparator());
        for (Edge edge : graph.edges
        ) {
            pq.add(edge);
        }
        Set<Edge> result = new HashSet<>();
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            if (!unionFind.isSameSet(edge.from, edge.to)) {
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }
}
