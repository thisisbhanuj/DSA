package ds.graphs.cycle;

import ds.graphs.impl.Graph;

import java.util.*;

/*
    Detect cycles in both directed and undirected graphs, including disconnected graphs.

    This code uses the DFS approach, but it does not use a recursion stack.
    Instead, it only maintains a 'visited' set. A cycle is detected if a visited node is encountered.
    However, this method will only find a back edge to an ancestor in the DFS traversal,
    not a cycle with all its nodes. In other words, it identifies a node in the cycle but
    doesn't provide the full cycle path. Furthermore, once it finds a cycle,
    it stops and prints the visited nodes, which are not necessarily the nodes in the cycle.
*/

public class SingleCycleDetector {
    private static Set<Set<Integer>> cycles = new HashSet<>();

    // Undirected Graph Impl
    private static boolean detectCycleUndirectedGraph(Graph.GraphNode node, Set<Integer> visited, int origin) {
        visited.add(node.ID);
        for (Graph.GraphNode adjacentNode : node.getNeighbours()) {
            if ((visited.contains(adjacentNode.ID) && adjacentNode.ID != origin) ||
                    (!visited.contains(adjacentNode.ID) && detectCycleUndirectedGraph(adjacentNode, visited, node.ID))) {
                return true;
            }
        }

        return false;
    }

    private static boolean detectCycleUndirectedGraph(Graph graph, Set<Integer> visited) {
        for (Graph.GraphNode node : graph.getNodes()) {
            if (!visited.contains(node.ID) && detectCycleUndirectedGraph(node, visited, -1)) {
                System.out.print("UnDirected Graph Cycle Exist for : " + visited );
                return true;
            }
        }

        return false;
    }

    /* Directed Graph Impl*/

    private static boolean detectCycleDirectedGraph(Graph.GraphNode node, Set<Integer> visited) {
        if (visited.contains(node.ID)) {
            return true;
        }
        visited.add(node.ID);
        for (Graph.GraphNode adjacentNode : node.getNeighbours()) {
            if (detectCycleDirectedGraph(adjacentNode, visited)) {
                return true;
            }
        }

        return false;
    }

    private static void detectCycleDirectedGraph(Graph graph, Set<Integer> visited) {
        for (Graph.GraphNode node : graph.getNodes()) { // handles disconnected graphs
            if (detectCycleDirectedGraph(node, visited)) {
                System.out.println("Directed Graph Cycle Exist for : " + visited );
                return;
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addGraphNode(1);
        graph.addGraphNode(2);
        graph.addGraphNode(3);
        graph.addGraphNode(4);
        graph.addGraphNode(5);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);// Causes Cyclic nature
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 4);// Causes Cyclic nature

        detectCycleDirectedGraph(graph, new HashSet<>());
    }
}
