package com.kefeng.class16;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Dijkstra算法
 * 有向无环图中求某点到其他节点的最小距离
 */
public class Dijkstra {

    /**
     * dijkstra算法从任意节点开始，
     *
     * @param from
     * @return
     */
    public static HashMap<Node, Integer> dijkstra(Node from) {
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        /**初始节点的距离为0，即自己到自己的距离为0*/
        distanceMap.put(from, 0);
        HashSet<Node> selectedNodes = new HashSet<>();
        /**选出中继节点，源点到目标点经过中继节点*/
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            int distance = distanceMap.get(minNode);
            /**遍历中继点相连的每条边*/
            for (Edge edge : minNode.edges
            ) {
                Node toNode = edge.to;
                /**如果每条边的终点不在哈希表中，那么从源点经过中继点到目标点的距离为：源点到中继点的距离+中继点和目标点边的距离*/
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    /**如果每条边的终点不在哈希表中，那么就使用哈希表中已有的源点到终点的值和源点到中继点的距离+中继点和目标点边的距离比大小，小值更新到哈希表中*/
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            /**中继点放入set中*/
            selectedNodes.add(minNode);
            /**重新找中继点*/
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()
        ) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!selectedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }


    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    /**
     * 使用加强堆，替代依次遍历寻找已排序的节点
     */
    public static class NodeHeap {
        /**
         * 实际的堆结构
         */
        private Node[] nodes;
        /**
         * key表示node，value表示堆中的位置
         */
        private HashMap<Node, Integer> headIndexMap;
        /**
         * key表示node，value表示从源点出发到当前节点目前的最小距离
         */
        private HashMap<Node, Integer> distanceMap;
        /**
         * 堆上有多少个点
         */
        private int size;

        public NodeHeap(int size) {
            nodes = new Node[size];
            headIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * 如果源节点到node节点的距离为distance，判断是否需要更新
         */
        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(distanceMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                distanceMap.put(node, distance);
                headIndexMap.put(node, size);
                insertHeapify(size++);
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            headIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void insertHeapify(int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left]) ? left + 1 : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) break;
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private boolean isEntered(Node node) {
            return headIndexMap.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && headIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            headIndexMap.put(nodes[index1], index2);
            headIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

    /**
     * 使用加强堆替换遍历任何元素
     * @param head
     * @param size
     * @return
     */
    public static HashMap<Node, Integer> dijkstra1(Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges
            ) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

    public static void main(String[] args) {
//        String a = "hello";
//        String b = "hello";
//        String c = new String("hello");
//        System.out.println(a==b);
//        System.out.println(a==c);
//        System.out.println(a.equals(c));
        Integer a = 2;
        Integer b = 2;
        Integer c = new Integer(2);
        System.out.println(a == c);
        System.out.println(a.equals(c));
    }
}
