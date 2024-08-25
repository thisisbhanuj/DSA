package main.ds.tree.serialization;

import java.util.*;

public class BinaryTreeDeSerializer {

    // Deserialize Pre-Order
    public TreeNode deserialize(String data) {
        if (data.isEmpty()) return null;

        String[] nodes = data.split(",");
        // We convert the String[] array into a Queue to facilitate easy traversal.
        // This is because the tree reconstruction is done in a sequential manner, and
        // a queue allows us to efficiently process each node in the order they appear.
        Queue<String> queue = new LinkedList<>(Arrays.asList(nodes));
        return deserializeHelper(queue);
    }

    private TreeNode deserializeHelper(Queue<String> queue) {
        if (queue.isEmpty()) return null;

        String nodeData = queue.poll();
        if (nodeData == null || nodeData.isEmpty()) return null;

        // Extract the value and the suffix
        int val = Integer.parseInt(nodeData.replaceAll("[a-z]", ""));
        TreeNode node = new TreeNode(val);

        if (nodeData.endsWith("lr")) {
            // Leaf node, no children
            return node;
        } else if (nodeData.endsWith("l")) {
            // Missing left child
            node.right = deserializeHelper(queue);
        } else if (nodeData.endsWith("r")) {
            // Missing right child
            node.left = deserializeHelper(queue);
        } else {
            // Both children are present
            node.left = deserializeHelper(queue);
            node.right = deserializeHelper(queue);
        }

        return node;
    }

    // Level-Order (BFS) Deserialization
    public TreeNode deserializeBFS(String data) {
        if (data.isEmpty()) return null;

        String[] nodes = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
        queue.offer(root);

        for (int i = 1; i < nodes.length; i++) {
            TreeNode parent = queue.poll();
            if (parent == null) continue;

            if (!nodes[i].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(nodes[i]));
                parent.left = left;
                queue.offer(left);
            }

            if (!nodes[++i].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(nodes[i]));
                parent.right = right;
                queue.offer(right);
            }
        }

        return root;
    }

    // Method to display the binary tree in a structured format
    public void display(TreeNode root) {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Number of nodes at the current level

            // Process each node at the current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                if (current != null) {
                    System.out.print(current.val + " ");
                    queue.offer(current.left); // Add left child to the queue
                    queue.offer(current.right); // Add right child to the queue
                } else {
                    System.out.print("null ");
                }
            }
            System.out.println(); // Move to the next level
        }
    }
}
