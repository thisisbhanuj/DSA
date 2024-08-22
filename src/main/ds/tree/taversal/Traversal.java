package main.ds.tree.taversal;

import java.util.LinkedList;
import java.util.Queue;

public class Traversal {
    static class BinaryTree {
        public BinaryTree leftChild;
        public BinaryTree rightChild;
        public int value;

        BinaryTree(int val) {
            this.value = val;
            this.leftChild = null;
            this.rightChild = null;
        }
    }

    private BinaryTree add(BinaryTree node, int val) {
        if (node == null) return new BinaryTree(val);

        if (val < node.value) {
            node.leftChild = add(node.leftChild, val);
        } else {
            node.rightChild = add(node.rightChild, val);
        }
        return node;
    }

    private BinaryTree createRoot(int val) {
        return new BinaryTree(val);
    }

    private void addChild(BinaryTree root, int data) {
        add(root, data);
    }

    private void inOrderTraversal(BinaryTree node) {
        if (node == null) return;

        inOrderTraversal(node.leftChild);
        System.out.print(node.value + ",");
        inOrderTraversal(node.rightChild);
    }

    private void preOrderTraversal(BinaryTree node) {
        if (node == null) return;

        System.out.print(node.value + ",");
        preOrderTraversal(node.leftChild);
        preOrderTraversal(node.rightChild);
    }

    private void postOrderTraversal(BinaryTree node) {
        if (node == null) return;

        postOrderTraversal(node.leftChild);
        postOrderTraversal(node.rightChild);
        System.out.print(node.value + ",");
    }

    /**
     * This method ensures that nodes are visited level by level from top to bottom and left to right within each level
     */
    private void breadthFirstTraversal(BinaryTree node){
        if (node == null) {
            return;
        }
        // Queue Mechanism: The queue is key to maintaining the correct order of nodes to be printed.
        Queue<BinaryTree> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            BinaryTree head = queue.poll(); // queue.poll() removes and returns the node at the front of the queue.
            System.out.print(head.value + ",");

            if (head.leftChild != null) queue.offer(head.leftChild); // If the dequeued node has a left child, it's added to the queue.
            if (head.rightChild != null) queue.offer(head.rightChild); // Similarly, if the node has a right child, it's also added to the queue.
        }
    }

    public static void main(String[] args) {
        Traversal traversal = new Traversal();
        BinaryTree root = traversal.createRoot(100);
        traversal.addChild(root, 10);
        traversal.addChild(root, 5);
        traversal.addChild(root, 15);
        traversal.addChild(root, 8);
        traversal.addChild(root, 99);
        traversal.addChild(root, 4);
        traversal.addChild(root, 43);
        traversal.addChild(root, 71);
        traversal.addChild(root, 33);

        System.out.println("In Order : ");
        traversal.inOrderTraversal(root);
        System.out.println();
        System.out.println("Pre Order : ");
        traversal.preOrderTraversal(root);
        System.out.println();
        System.out.println("Post Order : ");
        traversal.postOrderTraversal(root);
        System.out.println();
        System.out.println("Level Order : ");
        traversal.breadthFirstTraversal(root);
    }
}
