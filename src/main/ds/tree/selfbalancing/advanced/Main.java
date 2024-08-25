package main.ds.tree.selfbalancing.advanced;

public class Main {
    public static void main(String[] args) {
        // Create an instance of PersistentAVLTree
        PersistentAVLTree tree = new PersistentAVLTree();

        // Insert elements into the tree
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);

        // Print the current tree (inorder traversal)
        System.out.println("Current tree (inorder):");
        tree.inorderTraversal();

        // Perform undo operation
        tree.undo();
        System.out.println("Perform undo operation (inorder):");
        tree.inorderTraversal();

        // Perform redo operation
        tree.redo();
        System.out.println("Perform redo operation (inorder):");
        tree.inorderTraversal();

        // Perform more operations
        tree.insert(15);
        tree.insert(25);
        tree.delete(10);

        // Print the updated tree
        System.out.println("Updated tree, 10 deleted as last update (inorder):");
        tree.inorderTraversal();

        // Undo to revert to previous state
        tree.undo();
        System.out.println("Undo to revert to previous state (inorder):");
        tree.inorderTraversal();

        // Redo to apply the last undone change
        tree.redo();
        System.out.println("Redo to apply the last undone change (inorder):");
        tree.inorderTraversal();

        // Access a specific version (for example, version 0)
        AVLNode version0 = tree.getVersion(0);
        System.out.println("Version 0 tree (inorder):");
        tree.inorderTraversalRec(version0);
        System.out.println();
    }
}
//                        O/P:
// *************************************************
// Current tree (inorder):
//10 20 30 40 50
//Perform undo operation (inorder):
//10 20 30 40
//Perform redo operation (inorder):
//10 20 30 40 50
//Updated tree, 10 deleted as last update (inorder):
//15 20 25 30 40 50
//Undo to revert to previous state (inorder):
//10 15 20 25 30 40 50
//Redo to apply the last undone change (inorder):
//15 20 25 30 40 50
//Version 0 tree (inorder):
//10
// *************************************************
