package main.ds.tree.intervals.segment.lazy.ecommerce;

/*
    The DiscountSegmentTree class initializes the segment tree and lazy arrays.
    It includes methods for building the tree from an initial array of prices,
    applying discounts, and querying the final price.

    The segment tree is used to store the minimum price in a range of categories.
*/
class DiscountSegmentTree {
    private final int[] segmentTree;
    private final int[] lazyTree;
    private final int size;

    public DiscountSegmentTree(int size) {
        // Initializes the size of the segment tree, which is based on the number of categories or prices you want to manage.
        this.size = size;
        segmentTree = new int[4 * size];
        lazyTree = new int[4 * size];
    }

    // Build the segment tree with initial values (e.g., no discounts)
    public void buildTree(int[] prices, int start, int end, int node) {
        // Checks if the current segment (or range) is a leaf node (i.e., it corresponds to a single category).
        // If so, it assigns the price of that category to the corresponding node in the segmentTree.
        if (start == end) {
            segmentTree[node] = prices[start];
            return;
        }
        int mid = (start + end) / 2;
        buildTree(prices, start, mid, 2 * node + 1);
        buildTree(prices, mid + 1, end, 2 * node + 2);
        // After building both children, the current node value is set to the minimum of its two children.
        // This way, the segment tree maintains information about the minimum value in each segment.
        segmentTree[node] = Math.min(segmentTree[2 * node + 1], segmentTree[2 * node + 2]);
    }

    // Apply any pending updates before querying or updating a node
    private void propagatePendingUpdates(int node, int start, int end) {
        // Checks if there are any pending updates for the current node.
        if (lazyTree[node] != 0) {
            segmentTree[node] += lazyTree[node]; // Applies the pending update to the current node in the segment tree.
            if (start != end) { // If the current node is not a leaf node, propagate the pending update to its children by adding the update value to their corresponding positions in the lazyTree.
                lazyTree[2 * node + 1] += lazyTree[node];
                lazyTree[2 * node + 2] += lazyTree[node];
            }
            lazyTree[node] = 0; // Clear the pending update for the current node, as it has been applied.
        }
    }

    // Apply a discount to a range of categories using lazy propagation
    public void applyDiscount(int start, int end, int leftRange, int rightRange, int discountValue, int node) {
        propagatePendingUpdates(node, start, end); // Before doing anything, ensure all pending updates for the current node are applied.

        if (start > rightRange || end < leftRange) {
            return;
        }

        if (start >= leftRange && end <= rightRange) {
            segmentTree[node] += discountValue; // Apply the discount directly to the current node.
            if (start != end) { // If the current node is not a leaf, defer the update to its children by adding the discount value to their positions in the lazyTree.
                lazyTree[2 * node + 1] += discountValue;
                lazyTree[2 * node + 2] += discountValue;
            }
            return;
        }

        int mid = (start + end) / 2;
        applyDiscount(start, mid, leftRange, rightRange, discountValue, 2 * node + 1);
        applyDiscount(mid + 1, end, leftRange, rightRange, discountValue, 2 * node + 2);
        // After updating both children, update the current node to reflect the minimum value of its children.
        segmentTree[node] = Math.min(segmentTree[2 * node + 1], segmentTree[2 * node + 2]);
    }

    // Retrieve the final price for a category, considering all applied discounts
    public int getFinalPrice(int start, int end, int categoryIndex, int node) {
        propagatePendingUpdates(node, start, end); // Ensure all pending updates for the current node are applied before querying.

        if (start > categoryIndex || end < categoryIndex) {
            return Integer.MAX_VALUE;
        }

        // If the current segment is completely within the query range, return the value stored in the current node.
        if (start >= categoryIndex && end <= categoryIndex) {
            // The segment tree is structured such that each node represents a range (or segment) of categories.
            // When you've narrowed down the range (start == end), it means you're at the exact segment (or leaf node) corresponding to the categoryIndex.
            // Therefore, you should return the value stored at that node in the tree, which is segmentTree[node].
            return segmentTree[node];
        }

        int mid = (start + end) / 2;
        int leftPrice = getFinalPrice(start, mid, categoryIndex, 2 * node + 1);
        int rightPrice = getFinalPrice(mid + 1, end, categoryIndex, 2 * node + 2);
        return Math.min(leftPrice, rightPrice);
    }

    public void applyDiscountToRange(int leftRange, int rightRange, int discountValue) {
        applyDiscount(0, size - 1, leftRange, rightRange, discountValue, 0);
    }

    public int queryFinalPrice(int categoryIndex) {
        return getFinalPrice(0, size - 1, categoryIndex, 0);
    }
}

// Example Usage
class ECommerceDiscountManager {
    public static void main(String[] args) {
        int[] initialPrices = {100, 200, 300, 400, 500};  // Initial prices for 5 categories
        DiscountSegmentTree discountTree = new DiscountSegmentTree(initialPrices.length);

        discountTree.buildTree(initialPrices, 0, initialPrices.length - 1, 0);

        // Apply a 20% discount to categories 1 to 3
        discountTree.applyDiscountToRange(1, 3, -20);

        // Apply an additional 10% discount to categories 2 to 4
        discountTree.applyDiscountToRange(2, 4, -10);

        // Get the final price for category 2
        int finalPrice = discountTree.queryFinalPrice(2);
        System.out.println("Final price for category 2: " + finalPrice);

        // Get the final price for category 4
        finalPrice = discountTree.queryFinalPrice(4);
        System.out.println("Final price for category 4: " + finalPrice);
    }
}
