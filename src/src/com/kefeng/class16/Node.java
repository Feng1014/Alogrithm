package com.kefeng.class16;

import java.util.ArrayList;


/**
 * 定义图的节点
 */
public class Node {

    /**点的值*/
    public int value;
    /**点的入度*/
    public int in;
    /**点的出度*/
    public int out;
    /**从该点出发到达的点集合*/
    public ArrayList<Node> nexts;
    /**从该点出发相连的边*/
    public ArrayList<Edge> edges;

    public Node(int value){
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
