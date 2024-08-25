package main.ds.tree.selfbalancing.avl;

// AVLTree class to manage the AVL tree operations
public class AVLTree {
    private AVLNode root;

    // Get the height of a node
    private int getHeight(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    // Get the balance factor of a node (difference between left and right subtree heights)
    private int getBalanceFactor(AVLNode node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // Right rotation to fix Left-Left imbalance
    private AVLNode rotateRight(AVLNode unbalancedNode) {
        AVLNode newRoot = unbalancedNode.left;
        AVLNode movedSubtree = newRoot.right;

        // Perform rotation
        newRoot.right = unbalancedNode;
        unbalancedNode.left = movedSubtree;

        // Update heights
        unbalancedNode.height = Math.max(getHeight(unbalancedNode.left), getHeight(unbalancedNode.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        // Return new root after rotation
        return newRoot;
    }

    // Left rotation to fix Right-Right imbalance
    private AVLNode rotateLeft(AVLNode unbalancedNode) {
        AVLNode newRoot = unbalancedNode.right;
        AVLNode movedSubtree = newRoot.left;

        // Perform rotation
        newRoot.left = unbalancedNode;
        unbalancedNode.right = movedSubtree;

        // Update heights
        unbalancedNode.height = Math.max(getHeight(unbalancedNode.left), getHeight(unbalancedNode.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        // Return new root after rotation
        return newRoot;
    }

    // Insert a value into the AVL tree and balance the tree
    private AVLNode insertNode(AVLNode node, int value) {
        // Perform normal BST insertion
        if (node == null) return new AVLNode(value);

        if (value < node.value) {
            node.left = insertNode(node.left, value);
        } else if (value > node.value) {
            node.right = insertNode(node.right, value);
        } else {
            // Duplicate values are not allowed
            return node;
        }

        // Update the height of the ancestor node
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // Get the balance factor to check if the node is unbalanced
        int balanceFactor = getBalanceFactor(node);

        // Perform rotations to balance the node if needed
        if (balanceFactor > 1 && value < node.left.value) {
            // Left-Left case
            return rotateRight(node);
        }

        if (balanceFactor < -1 && value > node.right.value) {
            // Right-Right case
            return rotateLeft(node);
        }

        if (balanceFactor > 1 && value > node.left.value) {
            // Left-Right case
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balanceFactor < -1 && value < node.right.value) {
            // Right-Left case
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        // Return the unchanged node pointer
        return node;
    }

    // Public method to insert a value into the AVL tree
    public void insert(int value) {
        root = insertNode(root, value);
    }

    // In-order traversal of the AVL tree (for sorted order output)
    public void inorderTraversal() {
        inorderTraversalRec(root);
        System.out.println();  // Print newline after traversal
    }

    private void inorderTraversalRec(AVLNode node) {
        if (node != null) {
            inorderTraversalRec(node.left);
            System.out.print(node.value + " ");
            inorderTraversalRec(node.right);
        }
    }
}
