package main.ds.tree.binary;

public class TreeDiameter {
    static class TreeNode{
        TreeNode left;
        TreeNode right;
        TreeNode(){
            left = new TreeNode();
            right = new TreeNode();
        }
    }
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        calculateHeight(root);
        return diameter;
    }

    private int calculateHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // Recursively calculate height of left and right subtrees
        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);

        // Update the diameter: it could be the longest path through this node
        diameter = Math.max(diameter, leftHeight + rightHeight);

        // Return the height of the current node
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
