package main.ds.tree.intervals.avl;

import java.util.ArrayList;
import java.util.List;

public class AVLIntervalTree {
    private AVLIntervalNode root;

    public AVLIntervalNode getRoot() {
        return root;
    }

    public List<AVLIntervalNode> queryOverlappingIntervals(int start, int end) {
        List<AVLIntervalNode> result = new ArrayList<>();
        queryOverlappingIntervalsRec(root, start, end, result);
        return result;
    }

    private void queryOverlappingIntervalsRec(AVLIntervalNode node, int start, int end, List<AVLIntervalNode> result) {
        if (node == null) {
            return;
        }

        if (node.start <= end && start <= node.end) {
            result.add(node);
        }

        if (node.left != null && node.left.maxEnd >= start) {
            queryOverlappingIntervalsRec(node.left, start, end, result);
        }

        if (node.right != null && node.start <= end) {
            queryOverlappingIntervalsRec(node.right, start, end, result);
        }
    }

    // Get height directly from the node (cached value)
    public int getHeight(AVLIntervalNode node){
        return (node == null) ? 0 : node.height;
    }

    private void updateHeight(AVLIntervalNode node) {
        if (node != null) {
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            node.maxEnd = node.end; // Initial maxEnd is the end of the current node

            if (node.left != null) {
                node.maxEnd = Math.max(node.maxEnd, node.left.maxEnd);
            }
            if (node.right != null) {
                node.maxEnd = Math.max(node.maxEnd, node.right.maxEnd);
            }
        }
    }

    public int getBalanceFactor(AVLIntervalNode node){
        if(node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    public AVLIntervalNode rotateLeft(AVLIntervalNode unbalancedNode){
        AVLIntervalNode newRoot = unbalancedNode.right;
        AVLIntervalNode movedSubTree = newRoot.left;

        newRoot.left = unbalancedNode;
        unbalancedNode.right = movedSubTree;

        // After performing rotations (both left and right), heights are updated in the rotated nodes using the updateHeight method.
        // This ensures that heights are adjusted correctly after structural changes.
        updateHeight(unbalancedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    public AVLIntervalNode rotateRight(AVLIntervalNode unbalancedNode){
        AVLIntervalNode newRoot = unbalancedNode.left;
        AVLIntervalNode movedSubTree = newRoot.right;

        newRoot.right = unbalancedNode;
        unbalancedNode.left = movedSubTree;

        // After performing rotations (both left and right), heights are updated in the rotated nodes using the updateHeight method.
        // This ensures that heights are adjusted correctly after structural changes.
        updateHeight(unbalancedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    public AVLIntervalNode insertNode(AVLIntervalNode node, int start, int end) {
        if (node == null) {
            return new AVLIntervalNode(start, end);
        }

        if (start < node.start) {
            node.left = insertNode(node.left, start, end);
        } else if (start > node.start) {
            node.right = insertNode(node.right, start, end);
        } else {
            // Handle the case of overlapping intervals if needed
            return node;
        }

        updateHeight(node);
        return balanceNode(node, start);
    }

    private AVLIntervalNode balanceNode(AVLIntervalNode node, int start) {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (start < node.left.start) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }

        if (balanceFactor < -1) {
            if (start > node.right.start) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    public AVLIntervalNode deleteNode(AVLIntervalNode node, int start, int end) {
        if (node == null) {
            return node;  // Node not found
        }

        // Perform standard BST deletion
        if (start < node.start) {
            node.left = deleteNode(node.left, start, end);
        } else if (start > node.start) {
            node.right = deleteNode(node.right, start, end);
        } else {
            // Node with only one child or no child
            if (node.left == null || node.right == null) {
                AVLIntervalNode temp = node.left != null ? node.left : node.right;

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
                AVLIntervalNode temp = getMinValueNode(node.right);

                // Copy the inorder successor's interval to this node
                node.start = temp.start;
                node.end = temp.end;

                // Delete the inorder successor
                node.right = deleteNode(node.right, temp.start, temp.end);
            }
        }

        // If the tree had only one node, return
        if (node == null) {
            return node;
        }

        // Update height
        updateHeight(node);

        // Balance the node and return
        return balanceNode(node, start);
    }

    private AVLIntervalNode getMinValueNode(AVLIntervalNode node) {
        AVLIntervalNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void insert(int start, int end) {
        if (root == null) {
            root = new AVLIntervalNode(start, end);
        } else {
            root = insertNode(root, start, end);  // Ensure the root is updated
        }
    }

    public void delete(int start, int end) {
        root = deleteNode(root, start, end);
    }

    public void inorderTraversal() {
        inorderTraversalRec(root);
        System.out.println();
    }

    private void inorderTraversalRec(AVLIntervalNode node) {
        if (node != null) {
            inorderTraversalRec(node.left);
            System.out.print("[" + node.start + ", " + node.end + "] ");
            inorderTraversalRec(node.right);
        }
    }
}
