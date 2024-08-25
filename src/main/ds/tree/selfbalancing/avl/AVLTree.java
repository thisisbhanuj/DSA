package main.ds.tree.selfbalancing.avl;

// AVLTree class to manage the AVL tree operations
public class AVLTree {
    private AVLNode root;

    public int getHeight(AVLNode node){
        if (node == null) return 0;
        return node.height;
    }

    public int getBalanceFactor(AVLNode node){
        if(node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    public AVLNode rotateLeft(AVLNode unbalancedNode){
        AVLNode newRoot = unbalancedNode.right;
        AVLNode movedSubTree = newRoot.left;

        newRoot.left = unbalancedNode;
        unbalancedNode.right = movedSubTree;

        unbalancedNode.height = Math.max(getHeight(unbalancedNode.left), getHeight(unbalancedNode.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        return newRoot;
    }

    public AVLNode rotateRight(AVLNode unbalancedNode){
        AVLNode newRoot = unbalancedNode.left;
        AVLNode movedSubTree = newRoot.right;

        newRoot.right = unbalancedNode;
        unbalancedNode.left = movedSubTree;

        unbalancedNode.height = Math.max(getHeight(unbalancedNode.left), getHeight(unbalancedNode.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;

        return newRoot;
    }

    public AVLNode insertNode(AVLNode node, int value){
        if (node == null) {
            return new AVLNode(value);  // New node creation
        }

        if (value < node.value) {
            node.left = insertNode(node.left, value);
        } else if (value > node.value) {
            node.right = insertNode(node.right, value);
        } else {
            // Duplicate values are not allowed
            return node;
        }

        // Update height of this ancestor node
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        // Balance the node and return
        return balanceNode(node, value);
    }

    private AVLNode balanceNode(AVLNode node, int value) {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (value < node.left.value) {
                return rotateRight(node);  // Left-Left case
            } else if (value > node.left.value) {
                node.left = rotateLeft(node.left);  // Left-Right case
                return rotateRight(node);
            }
        }

        if (balanceFactor < -1) {
            if (value > node.right.value) {
                return rotateLeft(node);  // Right-Right case
            } else if (value < node.right.value) {
                node.right = rotateRight(node.right);  // Right-Left case
                return rotateLeft(node);
            }
        }

        return node;  // Node is balanced
    }

    public void insert(int value) {
        if (root == null) {
            root = new AVLNode(value);
        } else {
            root = insertNode(root, value);  // Ensure the root is updated
        }
    }

    public void inorderTraversal() {
        inorderTraversalRec(root);
        System.out.println();
    }

    private void inorderTraversalRec(AVLNode node) {
        if (node != null) {
            inorderTraversalRec(node.left);
            System.out.print(node.value + " ");
            inorderTraversalRec(node.right);
        }
    }
}
