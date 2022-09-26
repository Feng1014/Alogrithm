package com.kefeng.class16;


/**
 * 将二维数组转为自定义的图结构
 */
public class GraphGenerator {

    public static Graph generator(int[][] mat) {
        Graph graph = new Graph();
        for (int i = 0; i < mat.length; i++) {
            int weight = mat[i][0];
            int from = mat[i][1];
            int to = mat[i][1];
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge newEdge = new Edge(weight, fromNode, toNode);
            fromNode.out++;
            fromNode.nexts.add(toNode);
            fromNode.edges.add(newEdge);
            toNode.in++;
            graph.edges.add(newEdge);
        }
        return graph;
    }
}
