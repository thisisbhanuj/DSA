package main.ds.tree.intervals.segment;

/**
 * A segment tree implementation that handles range queries with multiple attributes.
 * This implementation allows for queries like range sum and range maximum.
 */
class MultiAttributeSegmentTree {

    /**
     * A custom node class to store multiple attributes (sum and max) for each segment tree node.
     */
    private static class Node {
        int sum;  // Sum of the elements in the range represented by this node
        int max;  // Maximum value in the range represented by this node

        /**
         * Constructor to initialize the Node with sum and max values.
         *
         * @param sum the sum of elements in the node's range
         * @param max the maximum element in the node's range
         */
        Node(int sum, int max) {
            this.sum = sum;
            this.max = max;
        }
    }

    private final Node[] segmentTreeArray; // Array representing the segment tree
    private final int originalArrayLength; // Length of the original input array

    /**
     * Constructs a MultiAttributeSegmentTree based on the provided input array.
     *
     * @param inputArray the array of integers to build the segment tree from
     */
    public MultiAttributeSegmentTree(int[] inputArray) {
        originalArrayLength = inputArray.length;
        segmentTreeArray = new Node[2 * originalArrayLength];
        buildSegmentTree(inputArray);
    }

    /**
     * Builds the segment tree from the input array.
     * Initializes leaf nodes with the values from the input array and then constructs the internal nodes.
     *
     * @param inputArray the array to build the segment tree from
     */
    private void buildSegmentTree(int[] inputArray) {
        // Initialize the leaf nodes in the segment tree
        for (int i = originalArrayLength; i < 2 * originalArrayLength; i++) {
            segmentTreeArray[i] = new Node(inputArray[i - originalArrayLength], inputArray[i - originalArrayLength]);
        }

        // Build the internal nodes from the leaves up to the root
        for (int i = originalArrayLength - 1; i > 0; i--) {
            Node left = segmentTreeArray[2 * i];
            Node right = segmentTreeArray[2 * i + 1];
            segmentTreeArray[i] = new Node(left.sum + right.sum, Math.max(left.max, right.max));
        }
    }

    /**
     * Updates the value of a specific element in the original array and adjusts the segment tree accordingly.
     *
     * @param index the index of the element to update
     * @param newValue the new value to set at the specified index
     */
    public void updateElement(int index, int newValue) {
        index += originalArrayLength; // Adjust index to the corresponding leaf node
        segmentTreeArray[index] = new Node(newValue, newValue); // Update the leaf node with the new value

        // Update all internal nodes affected by the change
        while (index > 1) {
            index /= 2;
            Node left = segmentTreeArray[2 * index];
            Node right = segmentTreeArray[2 * index + 1];
            segmentTreeArray[index] = new Node(left.sum + right.sum, Math.max(left.max, right.max));
        }
    }

    /**
     * Queries the segment tree for the sum and maximum value in a specific range.
     *
     * @param from the starting index of the range (inclusive)
     * @param to the ending index of the range (inclusive)
     * @return a Node containing the sum and maximum value of the specified range
     */
    public Node queryRange(int from, int to) {
        from += originalArrayLength; // Adjust 'from' to point to the corresponding leaf node
        to += originalArrayLength; // Adjust 'to' to point to the corresponding leaf node
        Node result = new Node(0, Integer.MIN_VALUE); // Initialize the result node

        // Traverse the segment tree to find the sum and maximum in the specified range
        while (from <= to) {
            if ((from % 2) == 1) { // If 'from' is a right child, include its value and move to the next segment
                Node left = segmentTreeArray[from];
                result = new Node(result.sum + left.sum, Math.max(result.max, left.max));
                from++;
            }
            if ((to % 2) == 0) { // If 'to' is a left child, include its value and move to the previous segment
                Node right = segmentTreeArray[to];
                result = new Node(result.sum + right.sum, Math.max(result.max, right.max));
                to--;
            }
            from /= 2; // Move up to the parent node
            to /= 2; // Move up to the parent node
        }
        return result; // Return the result node containing the sum and maximum in the range
    }
}
