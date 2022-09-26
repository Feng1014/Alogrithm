package com.kefeng.class16;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * https://www.lintcode.com/problem/topological-sorting
 * 题目要求自定义节点，求图的拓扑排序
 */
public class TopologicalOrderDFS1 {

    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public static class record {
        public DirectedGraphNode node;
        public int deep;

        public record(DirectedGraphNode x, int o) {
            node = x;
            deep = o;
        }
    }

    public static class myComparator implements Comparator<record> {

        @Override
        public int compare(record o1, record o2) {
            return o2.deep - o1.deep;
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph
        ) {
            f(cur, order);
        }
        ArrayList<record> recordArr = new ArrayList<>();
        for (record r : order.values()
        ) {
            recordArr.add(r);
        }
        recordArr.sort(new myComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (record r : recordArr
        ) {
            ans.add(r.node);
        }
        return ans;
    }

    /**
     * 求当前节点的最大深度
     * @param cur
     * @param order
     * @return
     */
    public static record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        int follows = 0;
        for (DirectedGraphNode neighbor : cur.neighbors
        ) {
            follows = Math.max(follows, f(neighbor, order).deep);
        }
        record recor = new record(cur, follows + 1);
        order.put(cur, recor);
        return recor;
    }
}
