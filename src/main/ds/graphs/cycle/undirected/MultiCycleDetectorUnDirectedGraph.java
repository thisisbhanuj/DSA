package main.ds.graphs.cycle.undirected;

import java.util.*;

public class MultiCycleDetectorUnDirectedGraph {
    private final Map<Integer, ArrayList<Integer>> adjacencyList;
    private final ArrayList< ArrayList<Integer> > cycles;
    static final int UNVISITED = 0;
    static final int EXPLORING = 1;
    static final int COMPLETED = 2;

    MultiCycleDetectorUnDirectedGraph(int vertices) {
        this.adjacencyList = new HashMap<>();
        this.cycles = new ArrayList<>();
    }

    // Function to add an edge to the graph
     private void addEdge(int sourceNode, int destNode) {
         adjacencyList.putIfAbsent(sourceNode, new ArrayList<>());
         adjacencyList.putIfAbsent(destNode, new ArrayList<>());

         adjacencyList.get(sourceNode).add(destNode);
         adjacencyList.get(destNode).add(sourceNode);
    }

    private void dfsDetectCycle(int currentNode, int parent, Map<Integer, Integer> visited, Map<Integer, Integer> dfsParent) {
        if (visited.get(currentNode) == COMPLETED) return;
        // Mark current node as being visited
        visited.put(currentNode, EXPLORING);
        dfsParent.put(currentNode, parent);

        // Visit all the adjacent nodes
        for (int adjacentNode : adjacencyList.get(currentNode)) {
            if (visited.get(adjacentNode) == EXPLORING && parent != adjacentNode) {
                // adjacent node is in the recursion stack, it's a back edge thus a cycle
                addCycle(currentNode, adjacentNode, dfsParent);
            } else if (visited.get(adjacentNode) == UNVISITED) {
                dfsDetectCycle(adjacentNode, currentNode, visited, dfsParent);
            }
        }
        // After visiting all the adjacent nodes of the current node, mark it as completely visited
        visited.put(currentNode, COMPLETED);
    }

    private void addCycle(int currentNode, int startNode, Map<Integer, Integer> dfsParent) {
        ArrayList<Integer> cycle = new ArrayList<>();
        cycle.add(startNode);
        int node = currentNode;
        while (node != startNode) {
            cycle.add(node);
            node = dfsParent.get(node);
        }

        cycles.add(cycle);
    }

    private void printCycle() {
        for(ArrayList<Integer> cycle : cycles) {
            System.out.println(cycle);
        }
    }

    public static void main(String[] args) {
        int numVertices = 13;

        MultiCycleDetectorUnDirectedGraph graph = new MultiCycleDetectorUnDirectedGraph(numVertices);

        graph.addEdge(1,2);
        graph.addEdge(2,3);
        graph.addEdge(3,4);
        graph.addEdge(4,6);
        graph.addEdge(4,7);
        graph.addEdge(5,6);
        graph.addEdge(3,5);
        graph.addEdge(7,8);
        graph.addEdge(6,10);
        graph.addEdge(5,9);
        graph.addEdge(10,9);
        graph.addEdge(10,11);
        graph.addEdge(11,12);
        graph.addEdge(11,13);
        graph.addEdge(12,13);

        /*The dfsParent array is used to keep track of the parent node for each node during the (DFS).
          It helps us avoid considering the immediate parent of a node as part of a cycle*/
        Map<Integer, Integer> dfsParent = new HashMap<>(numVertices);
        Map<Integer, Integer> visited = new HashMap<>(numVertices);
        for (int i = numVertices; i > 0; i--) {
            visited.put(i, UNVISITED);
            dfsParent.put(i, -1);
        }

        for (int vertex = 1; vertex <= numVertices; vertex++) {
            if (visited.get(vertex) == UNVISITED) {
                graph.dfsDetectCycle(vertex, dfsParent.get(vertex), visited, dfsParent);
            }
        }

        graph.printCycle();
    }
}
