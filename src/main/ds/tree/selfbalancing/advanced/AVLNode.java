package main.ds.tree.selfbalancing.advanced;

import java.util.Objects;

public class AVLNode {
    int value;
    int height;
    AVLNode left;
    AVLNode right;
    AVLNode parent;

    // Constructor
    public AVLNode(int value) {
        this.value = value;
        this.height = 1;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    // Override equals method to compare nodes correctly
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AVLNode that = (AVLNode) obj;
        return value == that.value &&
                height == that.height &&
                Objects.equals(parent, that.parent);
    }

    // Override hashCode method to generate consistent hash codes
    @Override
    public int hashCode() {
        return Objects.hash(value, height, parent);
    }
}
