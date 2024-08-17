package main.ds.graphs.cycle.union;

import java.util.Arrays;

/*
The time complexity for this approach is O(E⋅log ∗ V), log* denotes the iterated logarithm, which grows very slowly.
*/
public class DisjointSetUndirectedGraph {
    int V, E;    // V -> vertices, E -> edges
    Edge[] edge; // collection of all edges

    class Edge {
        int src, dest;
    }

    // Creates a graph with V vertices and E edges
    DisjointSetUndirectedGraph(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; i++) {
            edge[i] = new Edge();
        }
    }

    // A utility function to find the subset of an element i
    int find(int[] parent, int i) {
        if (parent[i] == -1)
            return i;
        return find(parent, parent[i]);
    }

    // A utility function to do union of two subsets
    void Union(int[] parent, int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);
        parent[xset] = yset;
    }

    // The main function to check whether a given graph contains a cycle or not
    boolean isCycle(DisjointSetUndirectedGraph graph) {
        // Allocate memory for creating V subsets
        int[] parent = new int[graph.V];
        // Initialize all subsets as single element sets
        Arrays.fill(parent, -1);

        // Iterate through all edges of graph, find subset of both
        // vertices of every edge, if both subsets are same, then
        // there is cycle in graph.
        for (int i = 0; i < graph.E; ++i) {
            int x = graph.find(parent, graph.edge[i].src);
            int y = graph.find(parent, graph.edge[i].dest);

            if (x == y)
                return true;

            graph.Union(parent, x, y);
        }
        return false;
    }

    public static void main(String[] args) {
        int V = 3, E = 3;
        DisjointSetUndirectedGraph graph = new DisjointSetUndirectedGraph(V, E);

        // add edge 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;

        // add edge 1-2
        graph.edge[1].src = 1;
        graph.edge[1].dest = 2;

        // add edge 0-2
        graph.edge[2].src = 0;
        graph.edge[2].dest = 2;

        if (graph.isCycle(graph))
            System.out.println("Graph contains cycle");
        else
            System.out.println("Graph doesn't contain cycle");
    }

}
