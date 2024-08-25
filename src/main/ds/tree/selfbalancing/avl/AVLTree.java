package main.ds.tree.selfbalancing.avl;

public class AVLTree {
    private AVLNode root;

    // Get height directly from the node (cached value)
    public int getHeight(AVLNode node){
        return (node == null) ? 0 : node.height;
    }

    // The updateHeight method is a centralized function that ensures height updates are done
    // efficiently and only when necessary. This reduces the chance of redundant recalculations.
    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        }
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

        // After performing rotations (both left and right), heights are updated in the rotated nodes using the updateHeight method.
        // This ensures that heights are adjusted correctly after structural changes.
        updateHeight(unbalancedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    public AVLNode rotateRight(AVLNode unbalancedNode){
        AVLNode newRoot = unbalancedNode.left;
        AVLNode movedSubTree = newRoot.right;

        newRoot.right = unbalancedNode;
        unbalancedNode.left = movedSubTree;

        // After performing rotations (both left and right), heights are updated in the rotated nodes using the updateHeight method.
        // This ensures that heights are adjusted correctly after structural changes.
        updateHeight(unbalancedNode);
        updateHeight(newRoot);

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
        updateHeight(node);

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

    public AVLNode deleteNode(AVLNode node, int value) {
        if (node == null) {
            return node;  // Node not found
        }

        // Perform standard BST deletion
        if (value < node.value) {
            node.left = deleteNode(node.left, value);
        } else if (value > node.value) {
            node.right = deleteNode(node.right, value);
        } else {
            // Node with only one child or no child
            if (node.left == null || node.right == null) {
                AVLNode temp = node.left != null ? node.left : node.right;

                // No child case
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    // One child case
                    node = temp;
                }
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                AVLNode temp = getMinValueNode(node.right);

                // Copy the inorder successor's value to this node
                node.value = temp.value;

                // Delete the inorder successor
                node.right = deleteNode(node.right, temp.value);
            }
        }

        // If the tree had only one node, return
        if (node == null) {
            return node;
        }

        // Update height
        updateHeight(node);

        // Balance the node and return
        return balanceNode(node, value);
    }

    private AVLNode getMinValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void insert(int value) {
        if (root == null) {
            root = new AVLNode(value);
        } else {
            root = insertNode(root, value);  // Ensure the root is updated
        }
    }

    public void delete(int value) {
        root = deleteNode(root, value);
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
