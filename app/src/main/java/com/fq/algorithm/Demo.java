package com.fq.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

class EdgeComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge edge, Edge t1) {
        return 0;
    }
}

class NodeRecord {
    public Node node;
    public int distance;
}

class NodeHeap {

    public NodeHeap(int size) {

    }

    public void addorUpdateOrIgnore(Node head, int i) {

    }

    public boolean isEmpty() {
        return false;
    }

    public NodeRecord pop() {
        return null;
    }
}

public class Demo {
    //从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addorUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.getEdges()) {
                nodeHeap.addorUpdateOrIgnore(edge.getTo(), edge.getWeight() + distance);
            }
            result.put(cur, distance);
        }

        return result;
    }
}