package main.ds.tree.intervals.avl;

public class AVLIntervalNode {
    int start; // Start of the interval
    int end;   // End of the interval
    int maxEnd; // Maximum end value in the subtree
    AVLIntervalNode left, right;
    int height;

    public AVLIntervalNode(int start, int end) {
        this.start = start;
        this.end = end;
        this.maxEnd = end;
        this.height = 1; // Height for AVL
        this.left = this.right = null;
    }
}

