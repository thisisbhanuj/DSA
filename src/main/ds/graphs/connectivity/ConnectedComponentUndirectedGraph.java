package ds.graphs.connectivity;

import java.util.*;

class Graph {
    // To store adjacency list for each vertex
    public Map<Integer, ArrayList<Integer>> vertices;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    // Method to add a vertex in the graph
    public void addVertex(int v){
        vertices.putIfAbsent(v, new ArrayList<>());
    }

    // Method to add an edge in the undirected graph
    public void addEdge(int u, int v){
        addVertex(u);
        addVertex(v);

        // Add edge from u to v if not already present
        if (!vertices.get(u).contains(v)) {
            vertices.get(u).add(v);
        }

        // Add edge from v to u if not already present
        if (!vertices.get(v).contains(u)) {
            vertices.get(v).add(u);
        }
    }

    // Depth-First Search traversal
    private void dfs(int vertex, Set<Integer> visited, ArrayList<Integer> component) {
        // Mark vertex as visited
        visited.add(vertex);

        // Add vertex to current component
        component.add(vertex);

        // Recurse on all its neighbouring vertices
        ArrayList<Integer> neighbours = vertices.get(vertex);
        for (int neighbour : neighbours) {
            if (!visited.contains(neighbour)) {
                dfs(neighbour, visited, component);
            }
        }
    }

    // Method to find all connected components
    public void findConnectedComponents(){
        // Every ArrayList.contains() operation has a time complexity of O(n),
        // which could slow things down when the number of vertices increases.
        // Instead of an ArrayList, consider using a HashSet for visited to decrease this to O(1).
        Set<Integer> visited = new HashSet<>();

        // Iterate over all vertices
        for (int vertex : vertices.keySet()) {
            // If vertex is not visited, start DFS and print new component
            if (!visited.contains(vertex)) {
                ArrayList<Integer> component = new ArrayList<>();
                dfs(vertex, visited, component);
                System.out.println("Connected Components : " + component);
            }
        }
    }

    /*The time complexity is O(V+E), where V is the number of vertices and E is the number of edges in the graph.
    This is because every vertex and every edge will be explored in the worst-case scenario.*/
    private void cycle(int vertex, int parent, Set<Integer> visited, Deque<Integer> stack) {
        // Mark the current node as visited and part of recursion stack
        visited.add(vertex);
        stack.add(vertex);

        // Recurse for all the vertices adjacent to this vertex
        for (int neighbour : vertices.get(vertex)) {
            if (!visited.contains(neighbour)) {
                cycle(neighbour, vertex, visited, stack);
            } else if (neighbour != parent) { // if it is in the stack and it's not the parent, then, it's a cycle
                System.out.println("Cycle Detected: " + stack);
            }
        }

        // remove the vertex from the recursion stack
        stack.remove(Integer.valueOf(vertex));
    }

    /*The time complexity is also O(V+E) for the same reason as above.
    Each vertex is visited once, and for each vertex, we perform a DFS to check for a cycle.*/
    public void findSingleCyclicComponent(){
        // Every ArrayList.contains() operation has a time complexity of O(n),
        // which could slow things down when the number of vertices increases.
        // Instead of an ArrayList, consider using a HashSet for visited to decrease this to O(1).
        Set<Integer> visited = new HashSet<>();
        // ArrayDeque is faster than Stack Class for stack operations
        Deque<Integer> stack = new ArrayDeque<>();

        // Iterate over all vertices
        for (int vertex : vertices.keySet()) {
            // If vertex is not visited, start DFS and print new component
            if (!visited.contains(vertex)) {
                cycle(vertex, -1, visited, stack);
            }
        }
    }
}

public class ConnectedComponentUndirectedGraph {

    public static void main(String[] args) {
        // Create a new graph
        Graph graph = new Graph();

        // Add vertices
        graph.addVertex(1);
        graph.addVertex(3);
        graph.addVertex(5);
        graph.addVertex(7);
        graph.addVertex(9);

        // Add edges
        graph.addEdge(3, 1);
        graph.addEdge(5, 7);
        graph.addEdge(7, 9);
        graph.addEdge(9, 5);

        // Find connected components
        graph.findConnectedComponents();
        System.out.println();
        // Find cyclic components
        graph.findSingleCyclicComponent();
    }
}

/* --------------------------------------------------------------------------------------------------------------
* FURTHER OPTIMIZATIONS :

1. **Parallelize your code**:
* If your graph is very large and if your machine has multiple cores,
* you might be able to speed up your computation by using parallel computing libraries (like the ForkJoinPool).
* However, this will make your code more complex and may not be worth the added complexity for smaller graphs or on machines with few cores.

2. **Improve memory usage**:
* Currently, you store the graph as an adjacency list.
* If you have a sparse graph (few edges compared to the number of vertices), this is a good approach.
* However, if you have a dense graph (many edges),
* you might be able to save memory by storing the graph as an adjacency matrix (a 2D array) instead.
* This comes with its own trade-offs (like slower lookups for certain operations),
* so whether this is a good idea depends on your specific use case.

3. **Optimize your data structures**:
* You've already used a HashSet for faster lookups and a Deque for stack operations.
* But you might be able to find more efficient data structures for specific operations,
* depending on the specifics of your use case.
* This would be very specific to the operations you're performing and
* would require profiling your code to see where the bottlenecks are.
*
* Again, these are pretty small improvements and might not be worth the added complexity, depending on your use case.
* Also, always make sure to profile your code before and after making these kinds of changes,
* to ensure that they're actually improving your code's performance and not just making it more complex.
* --------------------------------------------------------------------------------------------------------------
*/