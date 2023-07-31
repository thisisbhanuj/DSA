package ds.graphs.cycle.directed;

import java.util.*;

/*For detecting cycles in a directed graph. Time Complexity is O(V+E)*/

/*
It uses an additional recursion stack to keep track of nodes in the current traversal path.
If a node is encountered that is already in the recursion stack, then a cycle has been detected.
It maintains a 'pathStack' to record the current traversal path, which allows it to print the detected cycle.
This code is capable of detecting ALL cycles in the graph, not just one.
*/
public class MultiCycleDetectorDirectedGraph {
    // Number of vertices
    private final int V;
    // Adjacency list representation of the graph
    private final Map<Integer, List<Integer>> adjacencyList;

    // Constructor
    public MultiCycleDetectorDirectedGraph(int V) {
        // Setting the number of vertices
        this.V = V;
        // Initializing the adjacency list
        adjacencyList = new HashMap<>(V);

        // Creating a LinkedList for each vertex
        for (int i = 0; i < V; i++)
            adjacencyList.put(i, new LinkedList<>());
    }

    // Function to add an edge to the graph
    private void addEdge(int source, int dest) {
        adjacencyList.get(source).add(dest);
    }

    // Recursive function for detecting a cycle using depth-first traversal
    private boolean depthFirstSearch(int node, boolean[] visited, boolean[] tracker, Deque<Integer> cycleStack) {
        // If the current node is already in the recursion stack, we have a cycle
        if (tracker[node]) {
            cycleStack.push(node);
            return true;
        }

        //  If a node has been visited and it's not in the recursion stack,
        //  that means we've finished exploring that part of the graph and it doesn't contain a cycle.
        if (visited[node])
            return false;

        // Marking the current node as visited and adding it to the recursion stack
        visited[node] = true;
        tracker[node] = true;
        cycleStack.push(node);

        // Checking all adjacent vertices
        List<Integer> adjacenctNodes = adjacencyList.get(node);
        for (Integer adjacenctNode: adjacenctNodes)
            // If any of the adjacent vertices form a cycle, return true
            if ( depthFirstSearch(adjacenctNode, visited, tracker, cycleStack))
                return true;

        // If no adjacent vertices form a cycle, remove the current node from the recursion stack and return false
        cycleStack.pop();
        tracker[node] = false;
        return false;
    }

    // Function to check if the graph contains a cycle
    private void findCycles() {
        // Initializing the visited array, recursion stack and path stack
        boolean[] visited = new boolean[V];
        boolean[] tracker = new boolean[V];
        Deque<Integer> cycleStack = new ArrayDeque<>();

        // Checking all vertices for a cycle
        for (int i = 0; i < V; i++)
            // If a cycle is found, print the cycle and return true
            if (depthFirstSearch(i, visited, tracker, cycleStack)) {
                printCycle(cycleStack);
                // Reset the trackers for new cycles
                cycleStack.clear();
                Arrays.fill(tracker, false);
            }
    }

    // Function to print a cycle
    private void printCycle(Deque<Integer> cycleStack) {
        // Converting the Deque to a List and reversing it to get the correct order
        List<Integer> cycle = new ArrayList<>(cycleStack);
        // Collections.reverse(cycle);
        // Printing the cycle
        System.out.println("Cycle: " + cycle);
    }

    public static void main(String[] args) {
        // Creating a new graph with 6 vertices
        MultiCycleDetectorDirectedGraph graph = new MultiCycleDetectorDirectedGraph(6);
        // Adding edges to the graph
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);  // Cycle 1: 0-1-2-0
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);  // Cycle 2: 3-4-5-3
        graph.addEdge(4, 0);

        graph.findCycles();
    }
}
