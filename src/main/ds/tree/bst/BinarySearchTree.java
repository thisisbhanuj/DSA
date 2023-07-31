package ds.tree.bst;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {
    private static class BinaryTree {
        private int value;
        private BinaryTree left;
        private BinaryTree right;
        private int depth;

        public BinaryTree(int value, int depth) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.depth = depth;
        }
    }

    private static BinaryTree root;

    /*
    * It can traverse the tree from any given node
    * Time Complexity : O(n)
    * Space Complexity: O(height), it is determined by the maximum height of the function call stack due to recursive calls
    *                   for balanced tree it will be O(log[n])
    * */
    public static BinaryTree traverseInOrder(BinaryTree current){
        if (current != null && current.left != null) {
            current.left = traverseInOrder(current.left);
        }

        System.out.print(current.value + " ");

        if (current != null && current.right != null) {
            current.right = traverseInOrder(current.right);
        }
        return current; // IF we return null then it change the existing tree, only root will remain
    };

    public static int maxHeight(BinaryTree current) {
        // Returning -1 when the node parameter is null is a convention
        // often used in tree-related algorithms to handle the base case of an empty tree.
        if (current == null) return -1;
        int leftHeight = maxHeight(current.left);
        int rightHeight = maxHeight(current.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static BinaryTree minimum(BinaryTree current) {
        if (current.left == null) return current;

        return minimum(current.left);
    }

    public static BinaryTree maximum(BinaryTree current) {
        if (current.right == null) return current;

        return maximum(current.right);
    }

    /*
     * It can traverse the tree from any given node
     * Time Complexity : O(log[n])
     * Space Complexity: O(height), it is determined by the maximum height of the function call stack due to recursive calls
     *                   for balanced tree it will be O(log[n])
     * */
    public static BinaryTree find(BinaryTree current, int data){
        if (current != null && data == current.value) {
            return current;
        }

        if (current != null) {
            if (data < current.value) {
                return find(current.left, data);
            } else {
                return find(current.right, data);
            }
        }

        return null;
    }

    private static BinaryTree deleteAndReplace(BinaryTree current, int data){
        if (current == null) return null;

        if (data == current.value) {
            /* Case 1: Node to be deleted has no child or only one child. */
            if (current.left == null && current.right == null){ // No child
                current = null;
            } else if (current.left == null){ // Has Left child
                current = current.right;
            } else if (current.right == null){ // Has Right child
                current = current.left;
            } else {
                /* Case 2: Node to be deleted has both left and right children. */

                //  We need to find a replacement node that preserves the BST property.
                //  One approach is to find either the successor or predecessor of the node to be deleted.
                //  The successor is the smallest node in the right subtree,
                //  and the predecessor is the largest node in the left subtree.
                //  By replacing the node to be deleted with the successor or predecessor,
                //  we ensure that the BST property is maintained.
                //  After that, we need to delete the successor or predecessor node from its original location.
                BinaryTree successor = minimum(current.right);
                current.value = successor.value;
                current.right = deleteAndReplace(current.right, successor.value);
            }
        } else if (data < current.value) {
            current.left = deleteAndReplace(current.left, data);
        } else {
            current.right = deleteAndReplace(current.right, data);
        }

        return current;
    }

    private static void delete(int value){
        deleteAndReplace(root, value);
    }

    public static BinaryTree insert(BinaryTree current, int data, int depth) {
        if (current == null) {
            current = new BinaryTree(data, depth);
            return current;
        }

        if (data < current.value) {
            current.left = insert(current.left, data, depth + 1);
        } else {
            current.right = insert(current.right, data, depth + 1);
        }

        return current;
    }

    public static void addChild(int data) {
        if (root == null) {
            throw new IllegalStateException("Cannot add child. Root node not created.");
        }

        insert(root, data, 0);
    }

    public static void createRoot(int value) {
        if (root != null) {
            throw new IllegalStateException("Root node already exists.");
        }

        root = new BinaryTree(value, 0);
    }

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

        traverseInOrder(root);
        System.out.println();

        BinaryTree found = find(root, 99);
        if (found != null) {
            System.out.println("Found : " + found);
        } else {
            System.out.println("Not found!");
        }

        System.out.println("MAX - " + maximum(root).value);
        System.out.println("MIN - " + minimum(root).value);

        delete(5);
        traverseInOrder(root);
    }
}
