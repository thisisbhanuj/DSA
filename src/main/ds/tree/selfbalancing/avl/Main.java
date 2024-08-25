package main.ds.tree.selfbalancing.avl;

public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(50);

        System.out.println("Inorder traversal of the constructed AVL tree:");
        tree.inorderTraversal();  // Output will be sorted
    }
}
