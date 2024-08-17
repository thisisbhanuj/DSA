package main.ds.graphs.impl;

import java.util.*;


// Graph class to manage the graph data structure
public class Graph {

    // Node class to represent a single node in the graph
    public class GraphNode {
        ArrayList<GraphNode> neighbours;
        public int ID;

        public GraphNode (int id) {
            this.neighbours = new ArrayList<>();
            this.ID = id;
        }

        public void addNeighbours(ArrayList<GraphNode> neighbours) {
            if (neighbours.size() == 0) {
                System.out.println("No neighbours present, hence nothing to add.");
                return;
            }
            this.neighbours.addAll(neighbours);
        }

        public ArrayList<GraphNode> getNeighbours() {
            return neighbours;
        }
    }

    /* TreeMap implementation of the adjacency list ensures that
    the neighbors of each node are stored in a sorted order based on their node ids. */
    private TreeMap<Integer, GraphNode> nodes;

    public Graph(){
        this.nodes = new TreeMap<>();
    }

    public void addGraphNode(int id) {
        nodes.putIfAbsent(id, new GraphNode(id)); // Will not override the existing value
    }

    public void addEdge(int source, int target){
        GraphNode sourceNode = nodes.get(source), targetNode = nodes.get(target);
        ArrayList<GraphNode> list = new ArrayList<>();
        if (sourceNode != null && targetNode != null) {
            list.add(targetNode);
            sourceNode.addNeighbours(list);
        }
    }

    public void removeGraphNode(int nodeId) {
        GraphNode nodeToBeRemoved = nodes.get(nodeId);
        if (nodeToBeRemoved != null) {
            for (GraphNode node: nodes.values()) { // Remove the node from the neighbors of other nodes
                node.neighbours.remove(nodeId);
            }
            nodes.remove(nodeToBeRemoved);// Remove the node from the TreeMap
        }
    }

    public void removeEdge(int node_a, int node_b){
        GraphNode sourceNode = nodes.get(node_a), targetNode = nodes.get(node_b);
        if (sourceNode != null && targetNode != null) {
            sourceNode.neighbours.remove(targetNode); // Remove the targetNode from the neighbors of sourceNode
        }
    }

    public Collection<GraphNode> getNodes(){
        return nodes.values();
    }

    // DFS traversal starting from a given node
    public void dfsTraversal(GraphNode node, Set<Integer> visited) {
        if (node == null || visited.contains(node.ID)) return;

        System.out.println(node.ID + " ");

        visited.add(node.ID);

        for (GraphNode neighbour: node.neighbours) {
            dfsTraversal(neighbour, visited);
        }

    }

    /*
    Iterative Depth-First Traversal is an alternative approach to DFS and
    can be useful in scenarios where recursive function calls are not preferred or
    when dealing with very deep graphs where recursion might lead to a stack overflow.

    The key idea behind IDFT is to use a stack to keep track of nodes that need to be visited.
    It continues this process until there are no more unvisited nodes in the current branch.
    When this happens, the algorithm backtracks by popping nodes from the stack and
    continues the process for the remaining nodes.
    */
    public void iterativeDepthFirstTraversal(GraphNode start) {
        if (start == null) return;

        Deque<GraphNode> stack = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        stack.push(start);
        visited.add(start.ID);

        while (!stack.isEmpty()) {
            GraphNode current = stack.pop(); // Pop a node from the stack.
            System.out.print(current.ID + " "); //Process the node (e.g., print its value).

            for (GraphNode neighbor : current.neighbours) {
                if (!visited.contains(neighbor.ID)) {
                    stack.push(neighbor); // Push the unvisited neighbors onto the stack.
                    visited.add(neighbor.ID); // Get its unvisited neighbors and mark them as visited.
                }
            }
        }
    }

    /*It works well ONLY for Connected Graphs*/
    public void bfsTraversal(GraphNode node, Set<Integer> visited) {
        Queue<GraphNode> queue = new LinkedList<>();
        queue.offer(node);
        visited.add(node.ID);

        while (!queue.isEmpty()) {
            GraphNode graphNode = queue.poll();
            System.out.println(graphNode.ID + " ");

            for (GraphNode neighbor : graphNode.neighbours) {
                if (!visited.contains(neighbor.ID)) {
                    visited.add(neighbor.ID);
                    queue.add(neighbor);
                }
            }
        }
    }

    // Helper method for DFS traversal to compute reachable nodes from a given start node.
    private void dfsReachableNodes(GraphNode start, Set<Integer> visited, Set<Integer> reachableNodes) {
        visited.add(start.ID);
        reachableNodes.add(start.ID);

        for (GraphNode neighbour : start.neighbours) {
            if (!visited.contains(neighbour.ID)) {
                dfsReachableNodes(neighbour, visited, reachableNodes);
            }
        }
    }

    // Compute the transitive closure of the graph using DFS.
    public Graph computeTransitiveClosure() {
        Graph transitiveClosure = new Graph();

        for (GraphNode node : nodes.values()) {
            Set<Integer> visited = new HashSet<>();
            Set<Integer> reachableNodes = new HashSet<>();
            dfsReachableNodes(node, visited, reachableNodes);

            for (int reachableNodeID : reachableNodes) {
                transitiveClosure.addGraphNode(reachableNodeID);
                transitiveClosure.addEdge(node.ID, reachableNodeID);
            }
        }

        return transitiveClosure;
    }

    /*
    * A disconnected graph consists of multiple connected components,
    * each representing a separate subgraph that is not reachable from the other components.
    * To identify the nodes that belong to disconnected components,
    * we can perform a graph traversal starting from each unvisited node and
    * keep track of all visited nodes during the traversal.
    * The nodes that have not been visited during traversals are part of disconnected components.
    */
    public Set<Integer> findNodesInDisconnectedComponents(Graph graph) {
        Set<Integer> allNodes = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> disconnectedNodes = new HashSet<>();

        // Add all nodes to the set of all nodes
        for (GraphNode node : graph.getNodes()) {
            allNodes.add(node.ID);
        }

        // Perform DFS traversal starting from each unvisited node
        for (GraphNode node : graph.getNodes()) {
            if (!visited.contains(node.ID)) {
                Set<Integer> reachableNodes = new HashSet<>();
                dfsReachableNodes(node, visited, reachableNodes);

                // Remove reachable nodes from allNodes set
                allNodes.removeAll(reachableNodes);
            }
        }

        // The remaining nodes in allNodes set are part of disconnected components
        disconnectedNodes.addAll(allNodes);
        return disconnectedNodes;
    }

    public void bfsTraversalDisconnectedGraph(Graph graph) {
        Set<Integer> visited = new HashSet<>();
        for (GraphNode node : graph.getNodes()) {
            if (!visited.contains(node.ID)) {
                bfsTraversal(node, visited);
            }
        }
    }
}
