package main.ds.tree.selfbalancing.avl;

public class AVLNode {
    int value;       // The value stored in the node
    int height;      // Height of the node in the tree
    AVLNode left;    // Left child node
    AVLNode right;   // Right child node

    AVLNode(int value) {
        this.value = value;
        this.height = 1;  // New node is initially added at the leaf
    }
}

