package main.ds.graphs.sorting;

import java.util.*;

public class TopologicalSortingUsingDFS {
    private final Map<Integer, ArrayList<Integer>> adjacencyList;
    private final Deque<Integer> topologicalOrder;
    private final Set<Integer> visited;

    TopologicalSortingUsingDFS() {
        this.adjacencyList = new HashMap<>();
        this.topologicalOrder = new ArrayDeque<>();
        this.visited = new HashSet<>();
    }

    private void addEdge(int src, int dest) {
        adjacencyList.putIfAbsent(src, new ArrayList<>());
        adjacencyList.get(src).add(dest);
    }

    private void dfs(int node) {
        visited.add(node);

        if (adjacencyList.containsKey(node)) {
            for (int adjacentNode : adjacencyList.get(node)) {
                if (!visited.contains(adjacentNode)) {
                    dfs(adjacentNode);
                }
            }
        }

        topologicalOrder.push(node);
    }

    private void topologicalSort() {
        for (int node : adjacencyList.keySet()) {
            if (!visited.contains(node)) {
                dfs(node);
            }
        }

        while (!topologicalOrder.isEmpty()) {
            System.out.print(topologicalOrder.pop() + " ");
        }
    }

    public static void main(String[] args) {
        TopologicalSortingUsingDFS graph = new TopologicalSortingUsingDFS();

        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        System.out.println("Topological Sort:");
        graph.topologicalSort();
    }
}
