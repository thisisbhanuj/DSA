package main.ds.graphs.impl;

import java.util.HashSet;
import java.util.Set;

public class GraphUsingAdjacencyList {
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addGraphNode(1);
        graph.addGraphNode(2);
        graph.addGraphNode(3);
        graph.addGraphNode(4);
        graph.addGraphNode(5);

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(4, 1);

        System.out.println("Graph Adjacency List : ");
        for (Graph.GraphNode node: graph.getNodes()) {
            System.out.print(node.ID + " -> ");
            for (Graph.GraphNode neighbours: node.neighbours) {
                System.out.print(" " + neighbours.ID);
            }
            System.out.println();
        }
        System.out.println();

        Graph.GraphNode start = graph.getNodes().stream().findFirst().orElse(null);
        Set visited = new HashSet();
        System.out.println("DFS Traversal");
        graph.dfsTraversal(start, visited);
        System.out.println();
        System.out.println("BFS Traversal");
        visited = new HashSet();
        graph.bfsTraversal(start, visited);
        System.out.println();

        // Compute the transitive closure
        Graph transitiveClosure = graph.computeTransitiveClosure();

        // Print the transitive closure graph
        System.out.println("Transitive Closure:");
        for (Graph.GraphNode node : transitiveClosure.getNodes()) {
            for (Graph.GraphNode neighbour : node.neighbours) {
                System.out.println(node.ID + " -> " + neighbour.ID);
            }
            System.out.println("------");
        }
    }
}
