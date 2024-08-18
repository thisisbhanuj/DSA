package main.ds.linkedlist.scenarios.packagemanager;

import java.util.*;

/*
    Brent's algorithm is used here to detect cyclic dependencies in a package dependency graph.
 */
public class PackageManager {
    // Represents each package with its dependencies.
    static class PackageNode {
        String name;
        Set<PackageNode> dependencies;

        PackageNode(String pName) {
            name = pName;
            dependencies = new HashSet<>();
        }

        void addDependencies(PackageNode dependency) {
            dependencies.add(dependency);
        }
    }

    private String detectCycle(PackageNode start) {
        if (start == null) return null;

        // The visited set is used to track visited nodes to detect when a cycle is encountered.
        Set<PackageNode> visited = new HashSet<>();
        /* Depth-First Search (DFS) with a Stack */
        // The parents map tracks the parent of each node, allowing us to backtrack to find the start of the cycle.
        Map<PackageNode, PackageNode> parents = new HashMap<>();
        // The stack is used for depth-first traversal of the dependencies.
        Deque<PackageNode> stack = new ArrayDeque<>();
        /* Depth-First Search (DFS) with a Stack */

        stack.push(start);
        parents.put(start, null);

        while (!stack.isEmpty()) {
            PackageNode current = stack.pop();

            if (visited.contains(current)) {
                return findCycleStart(current, parents);
            }

            visited.add(current);

            for (PackageNode dependency : current.dependencies) {
                if (!visited.contains(dependency)) {
                    parents.put(dependency, current);
                    stack.push(dependency);
                } else if (stack.contains(dependency)) {
                    // Cycle detected
                    return findCycleStart(current, parents);
                }
            }
        }
        return null; // No cycle found
    }

    private String findCycleStart(PackageNode node, Map<PackageNode, PackageNode> parents) {
        Set<PackageNode> cycleNodes = new HashSet<>();
        PackageNode temp = node;

        // Traverse to find the cycle
        while (temp != null) {
            cycleNodes.add(temp);
            temp = parents.get(temp);
        }

        // Find intersection of cycle nodes
        temp = node;
        while (!cycleNodes.contains(parents.get(temp))) {
            temp = parents.get(temp);
        }

        return temp != null ? temp.name : null;
    }

    public static void main(String[] args) {
        PackageNode packageNodeA = new PackageNode("A");
        PackageNode packageNodeB = new PackageNode("B");
        PackageNode packageNodeC = new PackageNode("C");
        PackageNode packageNodeD = new PackageNode("D");
        PackageNode packageNodeE = new PackageNode("E");

        packageNodeA.addDependencies(packageNodeB);
        packageNodeB.addDependencies(packageNodeC);
        packageNodeC.addDependencies(packageNodeD);
        packageNodeD.addDependencies(packageNodeB); // Cycle
        packageNodeC.addDependencies(packageNodeE); // Another branch

        PackageManager packageManager = new PackageManager();
        String cycle = packageManager.detectCycle(packageNodeA);
        System.out.println("Cyclic Dependency @ " + (cycle != null ? cycle : "Nothing Found"));
    }
}
