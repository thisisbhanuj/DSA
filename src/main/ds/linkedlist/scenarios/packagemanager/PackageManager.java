package main.ds.linkedlist.scenarios.packagemanager;

import java.util.HashSet;
import java.util.Set;

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

        PackageNode slow = start;
        PackageNode fast = start;

        int length = 0;
        int power = 1;

        // Detecting cycle
        while (fast != null && !fast.dependencies.isEmpty()) {
            if (length == power) {
                power *= 2;
                length = 0;
                slow = fast;
            }

            fast = fast.dependencies.iterator().next();
            length++;

            if (slow == fast) {
                break; // Cycle Detected
            }
        }

        // No cycle found
        if (fast == null || fast.dependencies.isEmpty()) {
            return null;
        }

        // Reset slow to start and move fast length steps ahead
        slow = start;
        fast = start;

        for (int i = 0; i < length; i++) {
            fast = fast.dependencies.iterator().next();
        }

        // Detect the start of the cycle
        while (slow != fast) {
            slow = slow.dependencies.iterator().next();
            fast = fast.dependencies.iterator().next();
        }

        return slow.name; // Cycle start node
    }

    public static void main(String[] args) {
        PackageNode packageNodeA = new PackageNode("A");
        PackageNode packageNodeB = new PackageNode("B");
        PackageNode packageNodeC = new PackageNode("C");
        PackageNode packageNodeD = new PackageNode("D");

        packageNodeA.addDependencies(packageNodeB);
        packageNodeB.addDependencies(packageNodeC);
        packageNodeC.addDependencies(packageNodeD);
        packageNodeD.addDependencies(packageNodeB); // Cycle

        PackageManager packageManager = new PackageManager();
        String cycle = packageManager.detectCycle(packageNodeA);
        System.out.println("Cyclic Dependency @ " + cycle);
    }
}

