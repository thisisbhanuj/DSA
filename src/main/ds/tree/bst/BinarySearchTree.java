package main.ds.tree.bst;

/**
 * A simple implementation of a Binary Search Tree (BST).
 * This class provides methods to insert, delete, find, and traverse nodes in the tree.
 */
public class BinarySearchTree {

    /**
     * A private inner class representing a node in the binary tree.
     * Each node contains a value and references to its left and right children.
     */
    private static class BinaryTree {
        private int value;
        private BinaryTree left;
        private BinaryTree right;

        /**
         * Constructor to create a new node.
         *
         * @param value The integer value to store in the node.
         */
        public BinaryTree(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    // Root of the Binary Search Tree
    private static BinaryTree root;

    /**
     * Traverses the binary tree in an in-order fashion (left, root, right).
     * This method prints the value of each node in ascending order.
     *
     * @param current The current node being traversed.
     */
    public static void traverseInOrder(BinaryTree current) {
        if (current == null) return;

        // Traverse left subtree
        traverseInOrder(current.left);
        // Visit the root node
        System.out.print(current.value + " ");
        // Traverse right subtree
        traverseInOrder(current.right);
    }

    /**
     * Calculates the maximum height (depth) of the binary tree.
     *
     * @param current The current node being examined.
     * @return The maximum height of the tree, or -1 if the tree is empty.
     */
    public static int maxHeight(BinaryTree current) {
        if (current == null) return -1;
        int leftHeight = maxHeight(current.left);
        int rightHeight = maxHeight(current.right);

        // Height is the maximum of left or right subtree height plus one for the current node
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Finds the node with the minimum value in the binary tree.
     *
     * @param current The current node being examined.
     * @return The node with the minimum value.
     */
    public static BinaryTree minimum(BinaryTree current) {
        // The minimum value is found in the leftmost node
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * Finds the node with the maximum value in the binary tree.
     *
     * @param current The current node being examined.
     * @return The node with the maximum value.
     */
    public static BinaryTree maximum(BinaryTree current) {
        // The maximum value is found in the rightmost node
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    /**
     * Searches for a node with the specified value in the binary tree.
     *
     * @param current The current node being examined.
     * @param data The value to search for.
     * @return The node containing the value, or null if not found.
     */
    public static BinaryTree find(BinaryTree current, int data) {
        while (current != null) {
            if (data == current.value) {
                return current;
            } else if (data < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    /**
     * Deletes a node with the specified value from the binary tree.
     * The deleted node is replaced by its in-order successor if it has two children.
     *
     * @param current The current node being examined.
     * @param data The value of the node to be deleted.
     * @return The modified tree after deletion.
     */
    private static BinaryTree deleteAndReplace(BinaryTree current, int data) {
        if (current == null) return null;

        if (data == current.value) {
            // Case 1: Node has no children
            if (current.left == null && current.right == null) return null;

            // Case 2: Node has only one child
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // Case 3: Node has two children
            BinaryTree successor = minimum(current.right);
            current.value = successor.value;
            current.right = deleteAndReplace(current.right, successor.value);

        } else if (data < current.value) {
            current.left = deleteAndReplace(current.left, data);
        } else {
            current.right = deleteAndReplace(current.right, data);
        }

        return current;
    }

    /**
     * Public method to delete a node with the specified value from the binary tree.
     *
     * @param value The value of the node to be deleted.
     */
    private static void delete(int value) {
        root = deleteAndReplace(root, value);
    }

    /**
     * Inserts a new value into the binary tree at the appropriate position.
     *
     * @param current The current node being examined.
     * @param data The value to insert.
     * @return The updated tree after insertion.
     */
    public static BinaryTree insert(BinaryTree current, int data) {
        if (current == null) {
            return new BinaryTree(data);
        }

        if (data < current.value) {
            current.left = insert(current.left, data);
        } else {
            current.right = insert(current.right, data);
        }

        return current;
    }

    /**
     * Adds a new child node to the binary tree.
     * The method inserts the new value starting from the root node.
     *
     * @param data The value of the new child node.
     * @throws IllegalStateException If the root node has not been created.
     */
    public static void addChild(int data) {
        if (root == null) {
            throw new IllegalStateException("Cannot add child. Root node not created.");
        }

        insert(root, data);
    }

    /**
     * Creates the root node of the binary tree with the specified value.
     *
     * @param value The value of the root node.
     * @throws IllegalStateException If the root node already exists.
     */
    public static void createRoot(int value) {
        if (root != null) {
            throw new IllegalStateException("Root node already exists.");
        }

        root = new BinaryTree(value);
    }

    /**
     * The main method demonstrates the use of the Binary Search Tree by performing
     * various operations like insertion, traversal, searching, and deletion.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        createRoot(100);
        addChild(10);
        addChild(5);
        addChild(15);
        addChild(8);
        addChild(99);
        addChild(4);
        addChild(43);
        addChild(71);
        addChild(33);

        int height = maxHeight(root);
        System.out.println("Max Height : " + height);

        System.out.print("In-order Traversal: ");
        traverseInOrder(root);
        System.out.println();

        BinaryTree found = find(root, 99);
        if (found != null) {
            System.out.println("Found node with value: " + found.value);
        } else {
            System.out.println("Node not found!");
        }

        System.out.println("Maximum value in the tree: " + maximum(root).value);
        System.out.println("Minimum value in the tree: " + minimum(root).value);

        delete(5);
        System.out.print("In-order Traversal after deletion: ");
        traverseInOrder(root);
    }
}
