package main.ds.tree.serialization;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Serialization Approach:
 * Leaf Nodes:
 *  For a leaf node, store its value followed by 'lr' (indicating both left and right children are missing).
 * Missing Left Child:
 *  For a node with a missing left child, store the value followed by 'l'.
 * Missing Right Child:
 *  For a node with a missing right child, store the value followed by 'r'.
 * Delimiter:
 *  Use a comma (,) as a delimiter between nodes.
 */
public class BinaryTreeSerializer {
    // Pre-Order Serialization
    public String serializePreOrder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preOrderSerializeHelper(root, sb);
        return sb.toString();
    }

    private void preOrderSerializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }

        sb.append(node.val);

        // Check for the presence of left and right children
        if (node.left == null && node.right == null) {
            sb.append("lr"); // Leaf node
        } else if (node.left == null) {
            sb.append("l"); // Missing left child
        } else if (node.right == null) {
            sb.append("r"); // Missing right child
        }

        sb.append(","); // Add delimiter

        // Recursively serialize left and right children
        preOrderSerializeHelper(node.left, sb);
        preOrderSerializeHelper(node.right, sb);
    }

    // Level-Order (BFS) Serialization
    public String serializeLevelOrder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        levelOrderSerializeHelper(root, sb);
        return sb.toString();
    }

    private void levelOrderSerializeHelper(TreeNode root, StringBuilder sb) {
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
    }
}
