package com.kefeng.class16;


import java.util.*;

/**
 * 拓扑排序
 */
public class TopologySort {

    public static List<Node> topologySorted(Graph graph) {

        HashMap<Node, Integer> map = new HashMap<>();
        Queue<Node> zeroIn = new LinkedList<>();
        for (Node node : graph.nodes.values()
        ) {
            map.put(node, node.in);
            if (node.in == 0) {
                zeroIn.add(node);
            }
        }
        List<Node> ans = new ArrayList<>();
        while (!zeroIn.isEmpty()) {
            Node cur = zeroIn.poll();
            ans.add(cur);
            for (Node node : cur.nexts
            ) {
                map.put(node, map.get(node) - 1);
                if (map.get(node) == 0) {
                    ans.add(node);
                }
            }
        }
        return ans;
    }
}
