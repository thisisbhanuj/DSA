package main.ds.tree.intervals.avl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AVLIntervalTree tree = new AVLIntervalTree();

        // Insert intervals
        tree.insert(15, 20);
        tree.insert(10, 30);
        tree.insert(17, 19);
        tree.insert(5, 20);
        tree.insert(12, 15);
        tree.insert(30, 40);

        // Inorder traversal
        System.out.println("Inorder traversal:");
        tree.inorderTraversal();

        // Query overlapping intervals
        List<AVLIntervalNode> result = tree.queryOverlappingIntervals(14, 16);
        System.out.println("Overlapping intervals with [14, 16]:");
        for (AVLIntervalNode node : result) {
            System.out.println("[" + node.start + ", " + node.end + "]");
        }

        // Delete an interval
        tree.delete(10, 30);
        System.out.println("Inorder traversal after deletion:");
        tree.inorderTraversal();
    }
}

