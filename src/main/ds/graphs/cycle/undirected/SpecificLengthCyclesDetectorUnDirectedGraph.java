package main.ds.graphs.cycle.undirected;

import java.util.*;

/*The time complexity of this cycle detection algorithm is O((V+E)*(V^k)), where:

- V is the number of vertices in the graph.
- E is the number of edges in the graph.
- k is the length of the cycle you're searching for.

Here's why:

1. DFS algorithm once for each vertex, so the "V" term in the complexity comes from the loop over all vertices in `countCycles` method.

2. Each run of DFS is O(V+E) in the worst case, the algorithm must explore every vertex and edge in the worst-case scenario.

3. The k-th power of V comes from the depth of your DFS recursion.
We decrement the `backtrack` variable until it reaches 0, we explore as deep as the length of the cycle you're searching for.
Since each layer of the recursion can involve iterating over all vertices, this contributes a factor of V^k.

However, remember that complexity analysis gives you an upper bound in the worst-case scenario.
In practice, algorithm's performance will often be much better than this worst-case scenario,
particularly if graph is sparse (i.e., E << V^2) or if you're searching for relatively short cycles.
*/
public class SpecificLengthCyclesDetectorUnDirectedGraph {

    static int count = 0;
    static int V = 5;
    static ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> cycles = new ArrayList<>();

    static void DFS(int start, int vertex, int backtrack, boolean[] visited, List<Integer> currentCycle) {
        visited[vertex] = true;
        currentCycle.add(vertex);

        if (backtrack == 0) {
            visited[vertex] = false;

            if (adj.get(vertex).contains(start)) {
                count++;
                cycles.add(new ArrayList<>(currentCycle));
            }

            currentCycle.remove(currentCycle.size() - 1);

            return;
        }

        for (int neighbour : adj.get(vertex)) {
            if (!visited[neighbour]) {
                DFS(start, neighbour, backtrack-1, visited, currentCycle);
            }
        }

        visited[vertex] = false;

        currentCycle.remove(currentCycle.size() - 1);
    }

    static int countCycles(int cycleLength) {
        boolean[] visited = new boolean[V];

        for (int start = 0; start < V - (cycleLength - 1); start++) {
            DFS(start, start, cycleLength-1, visited, new ArrayList<>());
            visited[start] = true;
        }

        return count / 2;
    }

    public static void main(String[] args) {
        for(int i = 0; i < V; i++)
            adj.add(new ArrayList<>());

        adj.get(0).add(1);
        adj.get(0).add(3);
        adj.get(0).add(2);
        adj.get(1).add(0);
        adj.get(1).add(2);
        adj.get(1).add(3);
        adj.get(1).add(4);
        adj.get(2).add(0);
        adj.get(2).add(1);
        adj.get(2).add(4);
        adj.get(3).add(0);
        adj.get(3).add(1);
        adj.get(3).add(4);
        adj.get(4).add(1);
        adj.get(4).add(3);
        adj.get(4).add(2);

        int n = 3;

        System.out.println("Total cycles of length "+ n + " are "+ countCycles(n));

        // print all the cycles
        for(List<Integer> cycle: cycles) {
            System.out.println(cycle);
        }
    }
}