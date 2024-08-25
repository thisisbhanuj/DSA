package main.ds.tree.selfbalancing.advanced;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Useful in scenarios where maintaining historical versions of the tree is important, such as undo/redo operations in software.
 */
public class PersistentAVLTree {
    private AVLNode currentRoot;
    private final Stack<AVLNode> versions;
    private final Stack<AVLNode> undoStack;
    private final Stack<AVLNode> redoStack;
    private final Map<AVLNode, AVLNode> cloneCache;

    public PersistentAVLTree() {
        this.currentRoot = null;
        this.versions = new Stack<>();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.cloneCache = new HashMap<>();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(cloneTree(currentRoot));
            currentRoot = undoStack.pop();
            versions.push(cloneTree(currentRoot));
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(cloneTree(currentRoot));
            currentRoot = redoStack.pop();
            versions.push(cloneTree(currentRoot));
        }
    }

    // Get height directly from the node (cached value)
    public int getHeight(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        }
    }

    public int getBalanceFactor(AVLNode node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
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

    public AVLNode rotateLeft(AVLNode unbalancedNode) {
        AVLNode newRoot = unbalancedNode.right;
        AVLNode movedSubTree = newRoot.left;

        newRoot.left = unbalancedNode;
        unbalancedNode.right = movedSubTree;

        updateHeight(unbalancedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    public AVLNode rotateRight(AVLNode unbalancedNode) {
        AVLNode newRoot = unbalancedNode.left;
        AVLNode movedSubTree = newRoot.right;

        newRoot.right = unbalancedNode;
        unbalancedNode.left = movedSubTree;

        updateHeight(unbalancedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    public void insert(int value) {
        undoStack.push(cloneTree(currentRoot));
        redoStack.clear();  // Clear redo stack when new operation is performed

        if (currentRoot == null) {
            currentRoot = new AVLNode(value);
        } else {
            currentRoot = insertNode(currentRoot, value, null);
        }
        versions.push(cloneTree(currentRoot));
    }

    public AVLNode insertNode(AVLNode node, int value, AVLNode parent) {
        if (node == null) {
            AVLNode newNode = new AVLNode(value);
            newNode.parent = parent;
            return newNode;
        }

        if (value < node.value) {
            node.left = insertNode(node.left, value, node);
        } else if (value > node.value) {
            node.right = insertNode(node.right, value, node);
        } else {
            return node;  // Duplicate values are not allowed
        }

        updateHeight(node);
        return balanceNode(node, value);
    }

    private AVLNode cloneTree(AVLNode node) {
        if (node == null) {
            return null;
        }
// While caching was initially intended to reduce space complexity,
// it introduced some issues that had to be commented out.
// Properly implementing caching could still be beneficial,
// but it requires careful handling to avoid corrupting tree states.
//        if (cloneCache.containsKey(node)) {
//            return cloneCache.get(node);
//        }

        AVLNode newNode = new AVLNode(node.value);
        newNode.height = node.height;
        newNode.left = cloneTree(node.left);
        newNode.right = cloneTree(node.right);

        if (newNode.left != null) newNode.left.parent = newNode;
        if (newNode.right != null) newNode.right.parent = newNode;

        cloneCache.put(node, newNode);
        return newNode;
    }

    public void delete(int value) {
        undoStack.push(cloneTree(currentRoot));
        redoStack.clear();  // Clear redo stack when new operation is performed

        currentRoot = deleteNode(currentRoot, value);
        versions.push(cloneTree(currentRoot));
    }

    public AVLNode deleteNode(AVLNode node, int value) {
        if (node == null) {
            return node;  // Node not found
        }

        if (value < node.value) {
            node.left = deleteNode(node.left, value);
        } else if (value > node.value) {
            node.right = deleteNode(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                AVLNode temp = node.left != null ? node.left : node.right;

                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                AVLNode temp = getMinValueNode(node.right);
                node.value = temp.value;
                node.right = deleteNode(node.right, temp.value);
            }
        }

        if (node == null) {
            return node;
        }

        updateHeight(node);
        return balanceNode(node, value);
    }

    private AVLNode getMinValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void inorderTraversal() {
        inorderTraversalRec(currentRoot);
        System.out.println();
    }

    public void inorderTraversalRec(AVLNode node) {
        if (node != null) {
            inorderTraversalRec(node.left);
            System.out.print(node.value + " ");
            inorderTraversalRec(node.right);
        }
    }

    public AVLNode getVersion(int index) {
        if (index < 0 || index >= versions.size()) {
            throw new IndexOutOfBoundsException("Invalid version index");
        }
        return versions.get(index);
    }

    public AVLNode getRoot() {
        return currentRoot;
    }
}
