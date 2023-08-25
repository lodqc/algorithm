package com.fq.algorithm

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

fun main(args: Array<String>) {
    val graph =
        GraphUtils.createGraph(arrayOf(intArrayOf(0, 1, 5), intArrayOf(1, 2, 3), intArrayOf(0, 2, 7)))
    print(graph)
}