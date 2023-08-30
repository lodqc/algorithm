package com.fq.algorithm

import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Queue

class Graph {
    var nodes: HashMap<Int, Node> = HashMap()
    var edges: HashSet<Edge> = HashSet()
}

class Node(var value: Int) {
    var `in` = 0
    var out = 0
    var nexts: ArrayList<Node> = ArrayList()
    var edges: ArrayList<Edge> = ArrayList()

}

class Edge(var weight: Int, var from: Node, var to: Node)

object GraphUtils {
    fun createGraph(matrix: Array<IntArray>): Graph {
        val graph = Graph()
        for (i in matrix.indices) {
            val from = matrix[i][0]
            val to = matrix[i][1]
            val weight = matrix[i][2]
            if (!graph.nodes.containsKey(from)) {
                graph.nodes[from] = Node(from)
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes[to] = Node(to)
            }
            val fromNode = graph.nodes[from]!!
            val toNode = graph.nodes[to]!!
            val edge = Edge(weight, fromNode, toNode)
            fromNode.nexts.add(toNode)
            fromNode.out++
            toNode.`in`++
            fromNode.edges.add(edge)
            graph.edges.add(edge)
        }
        return graph
    }
}

fun sortedTopology(graph: Graph): List<Node> {
    //key:某一个node
    //value:剩余的入度
    val inMap = java.util.HashMap<Node, Int>()
    //入度为0的点，才能进这个队列
    val zeroInQueue: Queue<Node> = LinkedList()
    for (node in graph.nodes.values) {
        inMap[node] = node.`in`
        if (node.`in` == 0) {
            zeroInQueue.add(node)
        }
    }
    //拓扑排序的结果，依次加入resu1t
    val result: MutableList<Node> = java.util.ArrayList()
    while (!zeroInQueue.isEmpty()) {
        val cur = zeroInQueue.poll()!!
        result.add(cur)
        for (next in cur.nexts) {
            inMap[next] = inMap[next]!! - 1
            if (inMap[next] == 0) {
                zeroInQueue.add(next)
            }
        }
    }
    return result
}

fun primMST(graph: Graph): Set<Edge>{
    //解锁的边进入小根堆
    val priorityQueue = PriorityQueue(EdgeComparator())
    val set = java.util.HashSet<Node>()
    val result: MutableSet<Edge> = java.util.HashSet() //依次挑选的的边在result里
    for (node in graph.nodes.values) { //随便挑了一个点
        //node是开始点
        if (!set.contains(node)) {
            set.add(node)
            //由一个点，解锁所有相连的边
            priorityQueue.addAll(node.edges)
            while (!priorityQueue.isEmpty()) {
                val edge = priorityQueue.poll()!! //弹出解锁的边中，最小的边
                val toNode = edge.to //可能的一个新的点
                if (!set.contains(toNode)) { //不含有的时候，就是新的点
                    set.add(toNode)
                    result.add(edge)
                    for (nextEdge in toNode.edges) {
                        priorityQueue.add(nextEdge)
                        //break;
                    }
                }
            }
        }
    }
    return result
}

fun dijkstra1(head: Node): java.util.HashMap<Node, Int> {
    //从head出发到所有点的最小距离
    //key:从head出发到达key
    //value：从head出发到达key的最小距离
    //如果在表中，没有T的记录，含义是从head出发到T这个点的距离为正无穷
    val distanceMap = java.util.HashMap<Node, Int>()
    distanceMap[head] = 0
    //已经求过距离的节点，存在selectedNodes中，以后再也不碰
    val selectedNodes = java.util.HashSet<Node>()
    var minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes)
    while (minNode != null) {
        val distance = distanceMap[minNode]!!
        for (edge in minNode.edges) {
            val toNode = edge.to
            if (!distanceMap.containsKey(toNode)) {
                distanceMap[toNode] = distance + edge.weight
            }
            distanceMap[edge.to] = Math.min(
                distanceMap[toNode]!!,
                distance + edge.weight
            )
        }
        selectedNodes.add(minNode)
        minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes)
    }
    return distanceMap
}

fun getMinDistanceAndUnselectedNode(
    distanceMap: java.util.HashMap<Node, Int>,
    touchedNodes: java.util.HashSet<Node>
): Node? {
    var minNode: Node? = null
    var minDistance = Int.MAX_VALUE
    for ((node, distance) in distanceMap) {
        if (!touchedNodes.contains(node) && distance < minDistance) {
            minNode = node
            minDistance = distance
        }
    }
    return minNode
}

//从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
fun dijkstra2(head: Node?, size: Int): java.util.HashMap<Node, Int> {
    val nodeHeap = NodeHeap(size)
    nodeHeap.addorUpdateOrIgnore(head, 0)
    val result = java.util.HashMap<Node, Int>()
    while (!nodeHeap.isEmpty) {
        val record = nodeHeap.pop()
        val cur = record.node
        val distance = record.distance
        for (edge in cur.edges) {
            nodeHeap.addorUpdateOrIgnore(edge.to, edge.weight + distance)
        }
        result[cur] = distance
    }
    return result
}

fun main(args: Array<String>) {
    val graph =
        GraphUtils.createGraph(arrayOf(intArrayOf(0, 1, 5), intArrayOf(1, 2, 3), intArrayOf(0, 2, 7)))
    print(graph)
}