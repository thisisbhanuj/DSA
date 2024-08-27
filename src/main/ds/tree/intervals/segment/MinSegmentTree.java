package main.ds.tree.intervals.segment;

/**
 * A segment tree for efficiently finding the minimum value in a range and updating elements.
 *
 * This segment tree implementation efficiently supports range queries and updates,
 * with both operations taking O(log n) time, where n is the number of elements in
 * the original array. The key idea is to use the segment tree to maintain minimum
 * values over ranges and quickly update them when the underlying data changes.
 */
class MinSegmentTree {
    private final int[] segmentTreeArray;
    private final int originalArrayLength;

    public MinSegmentTree(int[] inputArray) {
        originalArrayLength = inputArray.length;
        segmentTreeArray = new int[2 * originalArrayLength];
        buildSegmentTree(inputArray);
    }

    private void buildSegmentTree(int[] inputArray) {
        System.arraycopy(inputArray, 0, segmentTreeArray, originalArrayLength, originalArrayLength);
        for (int i = originalArrayLength - 1; i > 0; i--) {
            segmentTreeArray[i] = Math.min(segmentTreeArray[2 * i], segmentTreeArray[2 * i + 1]);
        }
    }

    public void updateElement(int index, int newValue) {
        index += originalArrayLength;
        segmentTreeArray[index] = newValue;
        while (index > 1) {
            index /= 2;
            segmentTreeArray[index] = Math.min(segmentTreeArray[2 * index], segmentTreeArray[2 * index + 1]);
        }
    }

    // Finds the minimum value in a given range [from, to] in the original array
    public int queryRangeMin(int from, int to) {
        from += originalArrayLength;
        to += originalArrayLength;
        int min = Integer.MAX_VALUE;
        while (from <= to) {
            if ((from % 2) == 1) {
                min = Math.min(min, segmentTreeArray[from]);
                from++;
            }
            if ((to % 2) == 0) {
                min = Math.min(min, segmentTreeArray[to]);
                to--;
            }
            from /= 2;
            to /= 2;
        }
        return min;
    }
}

