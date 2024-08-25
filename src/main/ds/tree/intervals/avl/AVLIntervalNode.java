package main.ds.tree.intervals.avl;

public class AVLIntervalNode {
    int start; // Start of the interval
    int end;   // End of the interval
    AVLIntervalNode left, right;
    int height;
    // *************************************************************************************************************************
    // The `maxEnd` (or `maxNode`) field in an interval tree node helps optimize the querying of overlapping intervals. Here’s why it's important:
    //
    //### Purpose of `maxEnd`
    //   --------------------
    //  1. Efficient Overlap Queries:
    //   - Pruning: When querying for overlapping intervals, `maxEnd` allows you to prune branches of the tree that can't possibly contain overlapping intervals. This is because if the maximum end of a subtree is less than the start of the query interval, the subtree cannot contain any overlapping intervals.
    //   - Reduction in Search Space: Without `maxEnd`, you would need to search all subtrees even if you know that certain parts of the tree cannot contain overlapping intervals. `maxEnd` helps reduce the number of nodes that need to be examined.
    //
    //  2. Maintaining Correctness:
    //   - Interval Containment: `maxEnd` helps maintain the correctness of the tree by ensuring that each node correctly reflects the maximum end value of intervals in its subtree. This is crucial for accurate querying and balancing of the tree.
    //
    //### How `maxEnd` is Used
    //  ----------------------
    //  1. In Query Operations:
    //   - When searching for intervals that overlap with a given interval `[start, end]`, you can use `maxEnd` to decide if a subtree needs to be searched. If `maxEnd` of a node’s left subtree is less than `start`, then the right subtree is searched, and vice versa.
    //
    //  2. In Insertions and Deletions:
    //   - When inserting or deleting intervals, you need to update `maxEnd` to ensure it reflects the maximum end of intervals in its subtree. This helps maintain the efficiency of future queries and the overall integrity of the tree.
    //
    //### Example
    //  Consider a node `N` with an interval `[start, end]` and its subtree has intervals with maximum end `maxEnd`:
    //
    //    - Insertion: When you insert an interval `[newStart, newEnd]`, `maxEnd` helps determine where to insert the interval. After insertion, `maxEnd` values are updated to include `newEnd` if it's greater than the current `maxEnd`.
    //    - Query: To find overlapping intervals with `[queryStart, queryEnd]`, `maxEnd` helps to skip branches of the tree where `maxEnd` is less than `queryStart`, since such branches can't contain any intervals overlapping `[queryStart, queryEnd]`.
    //
    //  Relationship between maxEnd & end
    //  ---------------------------------
    //  maxEnd > end:
    //      Yes, maxEnd can be greater than the end value of the node's own interval.
    //      This happens if there are intervals in the subtree of the node whose end values extend beyond the end of the current node.
    //      The maxEnd value is intended to reflect the maximum end value of any interval in the entire subtree, not just the interval of the current node.
    //
    //  maxEnd ≤ end:
    //      maxEnd will never be less than the end of the current node's interval
    //      because it includes the end of the node's own interval and any additional intervals in its subtree.
    //      If maxEnd were less than end, it would not accurately represent the maximum end of all intervals in the subtree.
    // *************************************************************************************************************************
    int maxEnd;

    public AVLIntervalNode(int start, int end) {
        this.start = start;
        this.end = end;
        this.maxEnd = end;
        this.height = 1; // Height for AVL
        this.left = this.right = null;
    }
}

