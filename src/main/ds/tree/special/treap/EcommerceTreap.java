package main.ds.tree.special.treap;

import java.util.Random;

/*
Rotations in a Treap maintain a balance similar to how AVL trees or other self-balancing BSTs work.
This balancing ensures that the tree does not degenerate into a linked list and keeps operations efficient. Here's how it works in the context of Treaps:

#### Treap:
- **Rotation**: In a Treap, rotations are performed based on the **priority** of nodes (which mimics heap properties) while still maintaining the **BST property** for keys (relevance scores in our e-commerce example).
- **Balance**: The balancing is inherently probabilistic, as the priority is often chosen randomly. This randomness tends to keep the tree balanced in practice, leading to **`O(log n)`** average-case time complexity for insertion, deletion, and search operations.
- **Worst-Case Scenario**: While random priorities generally keep the tree balanced, there's still a theoretical possibility that all priorities could be skewed, leading to **`O(n)`** operations in the worst case. However, this is rare.

#### AVL Tree:
- **Rotation**: In an AVL tree, rotations are based on the **height** of subtrees to ensure that the tree remains balanced after every insertion or deletion.
- **Balance**: AVL trees strictly maintain balance by ensuring that the height difference between left and right subtrees (balance factor) is at most 1. This guarantees that the tree is balanced, leading to **`O(log n)`** operations in both average and worst-case scenarios.

### Conclusion
Given the left/right rotations to maintain heap properties based on priority, the Treap does indeed remain balanced similarly to an AVL tree in practice.
This means that, in your e-commerce example, you can expect **`O(log n)`** operations for insertions, deletions, and searches both on average and typically in the worst-case.

So, while Treaps can theoretically degrade in rare cases, in practice, with properly chosen (often random) priorities, they function similarly to an AVL tree, offering balanced operations with efficient time complexities.
 */
public class EcommerceTreap {
    static class ProductNode {
        String productName;
        int relevanceScore; // Key: Relevance score based on user preferences
        int popularityScore; // Priority: Popularity score based on user interactions
        ProductNode left, right;

        public ProductNode(String productName, int relevanceScore) {
            this.productName = productName;
            this.relevanceScore = relevanceScore;
            this.popularityScore = new Random().nextInt(100); // Random priority for heap property
            this.left = this.right = null;
        }
    }

    private ProductNode root;

    public EcommerceTreap() {
        this.root = null;
    }

    // Right rotation to maintain the heap property
    private ProductNode rotateRight(ProductNode node) {
        ProductNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        return newRoot;
    }

    // Left rotation to maintain the heap property
    private ProductNode rotateLeft(ProductNode node) {
        ProductNode newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        return newRoot;
    }

    // Insert a new product based on relevance and popularity
    public ProductNode insert(ProductNode root, String productName, int relevanceScore) {
        if (root == null) {
            return new ProductNode(productName, relevanceScore);
        }

        if (relevanceScore < root.relevanceScore) {
            root.left = insert(root.left, productName, relevanceScore);

            if (root.left.popularityScore > root.popularityScore) {
                root = rotateRight(root);
            }
        } else if (relevanceScore > root.relevanceScore) {
            root.right = insert(root.right, productName, relevanceScore);

            if (root.right.popularityScore > root.popularityScore) {
                root = rotateLeft(root);
            }
        }

        return root;
    }

    public void insert(String productName, int relevanceScore) {
        root = insert(root, productName, relevanceScore);
    }

    // Inorder traversal to display sorted products
    public void displaySortedProducts(ProductNode node) {
        if (node != null) {
            displaySortedProducts(node.left);
            System.out.println("Product: " + node.productName +
                    ", Relevance: " + node.relevanceScore +
                    ", Popularity: " + node.popularityScore);
            displaySortedProducts(node.right);
        }
    }

    public void displaySortedProducts() {
        displaySortedProducts(root);
    }

    public static void main(String[] args) {
        EcommerceTreap productTreap = new EcommerceTreap();

        // Insert products with different relevance scores
        productTreap.insert("Running Shoes", 85);
        productTreap.insert("Hiking Boots", 78);
        productTreap.insert("Sneakers", 92);
        productTreap.insert("Sandals", 60);
        productTreap.insert("Formal Shoes", 70);
        productTreap.insert("Flip Flops", 55);

        System.out.println("Sorted products based on relevance and popularity:");
        productTreap.displaySortedProducts();
    }
}
