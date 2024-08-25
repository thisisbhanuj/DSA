package main.ds.tree.selfbalancing.avl;

public class Main {
    public static void main(String[] args) {
        // Skewed Insertion Sequences
        AVLTree avlTree = new AVLTree();
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);
        avlTree.insert(40);
        avlTree.insert(50);
        avlTree.insert(15);

        System.out.println("Inorder traversal of the constructed AVL tree:");
        avlTree.inorderTraversal();  // Output will be sorted

        // Deletion & Re-Balancing
        avlTree.delete(10);
        System.out.println("Inorder traversal of the constructed AVL tree, after deletion");
        avlTree.inorderTraversal();

        // Large-Scale Insertions
        // Inserting a large number of nodes sequentially
        // (e.g., inserting elements in ascending or descending order)
        // can create a skewed tree temporarily before rebalancing occurs.
        AVLTree tree = new AVLTree();
        // Inserting elements in ascending order
        for (int i = 1; i <= 1000; i++) {
            tree.insert(i);
        }
        tree.inorderTraversal(); // This should print 1 to 1000 in sorted order

        System.out.println("Height of tree: " + tree.getHeight(tree.getRoot()));
        // Expected output: A value around log2(1000) = ~10
    }
}
