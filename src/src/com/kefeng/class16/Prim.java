package com.kefeng.class16;


import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 求最小生成树，使用prim算法
 * 从点开始，找边，再找点
 */
public class Prim {

    public static class edgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> prim(Graph graph) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new edgeComparator());
        HashSet<Node> nodeSet = new HashSet<>();
        Set<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()
        ) {
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Edge edge : node.edges
                ) {
                    priorityQueue.add(edge);
                }
                while (!priorityQueue.isEmpty()) {
                    Edge edg = priorityQueue.poll();
                    Node edgeTo = edg.to;
                    if (!nodeSet.contains(edgeTo)) {
                        nodeSet.add(edgeTo);
                        result.add(edg);
                        for (Edge ed : edgeTo.edges
                        ) {
                            priorityQueue.add(ed);
                        }
                    }
                }
            }
        }
        return result;
    }
}
