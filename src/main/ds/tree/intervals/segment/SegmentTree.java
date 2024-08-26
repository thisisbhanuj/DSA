package main.ds.tree.intervals.segment;

/**
 * A Segment Tree is a data structure that allows for efficient querying and updating of intervals or ranges.
 * It's particularly useful for scenarios like range sum, range minimum/maximum, and other associative operations over an array.
 *
 * The Segment Tree is built in a way that each node represents a segment (or range) of the array.
 * The root of the tree represents the entire array, and each leaf node represents a single element of the array.
 *
 * The Segment Tree doesn't rely on complex balancing techniques like self-balancing trees do.
 * Instead, it simply uses a binary tree model to organize and query data more efficiently than a brute-force approach would allow.
 * In essence, the Segment Tree uses the binary tree structure as a tool rather than a defining feature,
 * making it both simple and powerful for its intended use cases.
 */
class SegmentTree {
    private final int[] segmentTreeArray;  // Array to store the segment tree
    private final int originalArrayLength; // Length of the original input array

    // Constructor to build the tree from the input array
    public SegmentTree(int[] inputArray) {
        originalArrayLength = inputArray.length;
        // The segment tree is stored in a flat array rather than a traditional tree structure.
        // This array needs to accommodate all the leaf nodes (original array elements) and the internal nodes (segment sums).
        // To efficiently manage this, we allocate an array of size 2 * n, where:
        //      - The first n positions ([0] to [n-1]) are used for internal nodes.
        //      - The second n positions ([n] to [2n-1]) are used for the leaf nodes (i.e., the original array).
        segmentTreeArray = new int[2 * originalArrayLength];
        buildSegmentTree(inputArray);
    }

    /**
     * Building the Segment Tree:
     * Leaf Nodes: The segment tree stores the original array elements in the second half of segmentTreeArray (from index originalArrayLength to 2 * originalArrayLength - 1).
     * Internal Nodes: The internal nodes store the sum of their child nodes. For example, the node at index i stores the sum of nodes at indices 2 * i and 2 * i + 1.
     *
     */
    private void buildSegmentTree(int[] inputArray) {
        // Copy the input array into the leaf nodes of the segment tree
        if (originalArrayLength >= 0)
            System.arraycopy(inputArray, 0, segmentTreeArray, originalArrayLength, originalArrayLength);
        // Build the segment tree by calculating the parent nodes
        for (int i = originalArrayLength - 1; i > 0; i--) {
            segmentTreeArray[i] = segmentTreeArray[2 * i] + segmentTreeArray[2 * i + 1];
        }
    }

    /**
     * To update an element, we change its corresponding leaf node and propagate the update up the tree.
     * This ensures that all relevant parent nodes reflect the change, maintaining the correct sum for future queries.
     */
    public void updateElement(int index, int newValue) {
        // Update the leaf node corresponding to the index
        index += originalArrayLength;
        segmentTreeArray[index] = newValue;

        // Propagate the change up the tree
        while (index > 1) {
            index /= 2;
            segmentTreeArray[index] = segmentTreeArray[2 * index] + segmentTreeArray[2 * index + 1];
        }
    }

    /**
     * Range Sum Query:
     * We traverse from the leaf nodes (representing the query range) towards the root, combining the sums of relevant segments.
     * If left is a right child, it is included in the sum, and we move to its sibling’s parent.
     * Similarly, if right is a left child, it is included in the sum, and we move to its sibling’s parent.
     */
    public int queryRangeSum(int left, int right) {
        left += originalArrayLength;  // Adjust to the segment tree index
        right += originalArrayLength; // Adjust to the segment tree index

        int sum = 0;
        while (left <= right) {
            // If left is a right child, include its value and move to the next segment
            if ((left % 2) == 1) {
                sum += segmentTreeArray[left];
                left++;
            }
            // If right is a left child, include its value and move to the previous segment
            if ((right % 2) == 0) {
                sum += segmentTreeArray[right];
                right--;
            }
            // Move up to the parents
            left /= 2;
            right /= 2;
        }
        return sum;
    }

    // Main method to test the Segment Tree
    public static void main(String[] args) {
        int[] inputArray = {1, 3, 5, 7, 9, 11};
        SegmentTree segmentTree = new SegmentTree(inputArray);

        System.out.println(segmentTree.queryRangeSum(1, 3)); // Output: 15 (3 + 5 + 7)

        segmentTree.updateElement(1, 10);
        System.out.println(segmentTree.queryRangeSum(1, 3)); // Output: 22 (10 + 5 + 7)
    }
}
