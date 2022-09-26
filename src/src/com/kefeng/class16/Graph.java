package com.kefeng.class16;

import java.util.HashMap;
import java.util.HashSet;


/**
 * 定义图
 * 由节点和边组成
 */
public class Graph {

    public HashMap<Integer, Node> nodes;
    public HashSet<Edge> edges;

    public Graph(){
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
