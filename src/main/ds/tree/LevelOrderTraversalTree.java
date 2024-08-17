package main.ds.tree;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversalTree {
    private static class TreeNode {
        private TreeNode left;
        private TreeNode right;
        private int data;

        TreeNode(int data){
            this.left = null;
            this.right = null;
            this.data = data;
        }
    }

    private static void printlevelOrder(TreeNode node){
        if (node == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            TreeNode head = queue.poll();
            System.out.println(head.data);

            if (head.left != null) queue.offer(head.left);
            if (head.right != null) queue.offer(head.right);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);

        printlevelOrder(root);
    }
}
