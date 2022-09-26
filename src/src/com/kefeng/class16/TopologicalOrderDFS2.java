package com.kefeng.class16;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 拓扑排序
 */
public class TopologicalOrderDFS2 {

    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public static class Record {
        public DirectedGraphNode node;
        public long deep;

        public Record(DirectedGraphNode x, long o) {
            node = x;
            deep = o;
        }
    }

    public static class myComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o1.deep == o2.deep ? 0 : (o1.deep > o2.deep ? -1 : 1);
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph
        ) {
            f(cur, order);
        }
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()
        ) {
            recordArr.add(r);
        }
        recordArr.sort(new myComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record rec : recordArr
        ) {
            ans.add(rec.node);
        }
        return ans;
    }

    /**
     * 对每个节点求点次
     * @param cur
     * @param order
     * @return
     */
    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        long nodes = 0;
        for (DirectedGraphNode neigh : cur.neighbors
        ) {
            nodes += f(neigh, order).deep;
        }
        Record record = new Record(cur, nodes + 1);
        order.put(cur, record);
        return record;
    }
}
