package main.ds.tree.intervals.segment.lazy;

/**
 * For scenarios involving range updates (e.g., adding a value to a range of elements), a simple Segment Tree might not be efficient.
 * Lazy Propagation helps manage such updates efficiently.
 * #
 * Implementation:
 * Add a lazy array to keep track of pending updates.
 * Update operations propagate lazily, applying changes only when necessary (e.g., during queries or further updates).
 */
class LazySegmentTree {
    private final int[] segmentTreeArray;
    private final int[] lazy;
    private final int originalArrayLength;

    public LazySegmentTree(int[] inputArray) {
        originalArrayLength = inputArray.length;
        segmentTreeArray = new int[2 * originalArrayLength];
        lazy = new int[2 * originalArrayLength];
        buildSegmentTree(inputArray);
    }

    private void buildSegmentTree(int[] inputArray) {
        System.arraycopy(inputArray, 0, segmentTreeArray, originalArrayLength, originalArrayLength);
        for (int i = originalArrayLength - 1; i > 0; i--) {
            segmentTreeArray[i] = segmentTreeArray[2 * i] + segmentTreeArray[2 * i + 1];
        }
    }

    private void apply(int index, int value, int length) {
        segmentTreeArray[index] += value * length;
        if (index < originalArrayLength) lazy[index] += value;
    }

    private void push(int index, int length) {
        if (lazy[index] != 0) {
            apply(2 * index, lazy[index], length / 2);
            apply(2 * index + 1, lazy[index], length / 2);
            lazy[index] = 0;
        }
    }

    public void updateRange(int left, int right, int value) {
        left += originalArrayLength;
        right += originalArrayLength;
        int length = 1;
        while (left <= right) {
            if ((left % 2) == 1) {
                apply(left, value, length);
                left++;
            }
            if ((right % 2) == 0) {
                apply(right, value, length);
                right--;
            }
            left /= 2;
            right /= 2;
            length *= 2;
        }
    }

    public int queryRangeSum(int from, int to) {
        from += originalArrayLength;
        to += originalArrayLength;
        int sum = 0;
        while (from <= to) {
            if ((from % 2) == 1) {
                sum += segmentTreeArray[from];
                from++;
            }
            if ((to % 2) == 0) {
                sum += segmentTreeArray[to];
                to--;
            }
            from /= 2;
            to /= 2;
        }
        return sum;
    }
}
