package main.ds.graphs.bipartite;

import java.util.*;

public class Bipartite {
    private static final int RED = 1;
    private static final int BLUE = 2;

    private final Map<Integer, ArrayList<Integer>> adjacencyList;
    private final Map<Integer, Integer> nodeColor;

    Bipartite() {
        this.adjacencyList = new HashMap<>();
        this.nodeColor = new HashMap<>();
    }

    private void addEdge(int src, int des) {
        adjacencyList.putIfAbsent(src, new ArrayList<>());
        adjacencyList.putIfAbsent(des, new ArrayList<>());
        adjacencyList.get(src).add(des);
        adjacencyList.get(des).add(src);
    }

    private boolean bipartiteBFS(int startVertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        nodeColor.put(startVertex, RED);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            int alternateColor = (nodeColor.get(vertex) == RED) ? BLUE : RED;

            for (int adjacentNode : adjacencyList.get(vertex)) {
                if (!nodeColor.containsKey(adjacentNode)) {
                    nodeColor.put(adjacentNode, alternateColor);
                    queue.add(adjacentNode);
                } else if (nodeColor.get(adjacentNode) != alternateColor) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean bipartiteDFS(int vertex, int color) {
        nodeColor.put(vertex, color);
        //In a bipartite graph, all nodes can be colored in such a way that no two adjacent nodes share the same color.
        int alternateColor = (color == RED) ? BLUE : RED;

        for (int adjacentNode : adjacencyList.get(vertex)) {
            // return false as soon as a conflict is found.
            if (!nodeColor.containsKey(adjacentNode)) {
                if (!bipartiteDFS(adjacentNode, alternateColor)) {
                    return false;
                }
            } else if (nodeColor.get(adjacentNode) != alternateColor) {
                return false;
            }
        }
        return true;
    }

    private void checkBipartiteUsingDFS() {
        for (int vertex : adjacencyList.keySet()) { // iterate over all vertices and handle disconnected graphs.
            if (!nodeColor.containsKey(vertex) && !bipartiteDFS(vertex, RED)) {
                System.out.println("DFS : Graph is not Bipartite");
                return;
            }
        }
        System.out.println("DFS : Graph is Bipartite");
    }

    private void checkBipartiteUsingBFS() {
        for (int vertex : adjacencyList.keySet()) { // iterate over all vertices and handle disconnected graphs.
            if (!nodeColor.containsKey(vertex) && !bipartiteBFS(vertex)) {
                System.out.println("BFS : Graph is not Bipartite");
                return;
            }
        }
        System.out.println("BFS : Graph is Bipartite");
    }

    public static void main(String[] args) {
        Bipartite bipartite = new Bipartite();

        bipartite.addEdge(1,2);
        bipartite.addEdge(2,3);
        bipartite.addEdge(3,4);
        bipartite.addEdge(4,6);
        bipartite.addEdge(4,7);
        bipartite.addEdge(5,6);
        bipartite.addEdge(3,5);
        bipartite.addEdge(7,8);
        bipartite.addEdge(6,10);
        bipartite.addEdge(5,9);
        bipartite.addEdge(10,9);
        bipartite.addEdge(10,11);
        bipartite.addEdge(11,12);
        bipartite.addEdge(11,13);
        bipartite.addEdge(12,13);

        bipartite.checkBipartiteUsingDFS();
        bipartite.nodeColor.clear(); // Resetting the color map.
        bipartite.checkBipartiteUsingBFS();
    }
}
