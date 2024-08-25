package main.ds.tree.selfbalancing.avl;

public class Main {
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);
        avlTree.insert(40);
        avlTree.insert(50);
        avlTree.insert(15);

        System.out.println("Inorder traversal of the constructed AVL tree:");
        avlTree.inorderTraversal();  // Output will be sorted

        avlTree.delete(10);
        System.out.println("Inorder traversal of the constructed AVL tree, after deletion");
        avlTree.inorderTraversal();
    }
}
