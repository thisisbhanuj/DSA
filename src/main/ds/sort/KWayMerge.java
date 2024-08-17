package main.ds.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

class ArrayEntry {
    int value;
    int arrayIndex;
    int elementIndex;

    public ArrayEntry(int value, int arrayIndex, int elementIndex) {
        this.value = value;
        this.arrayIndex = arrayIndex;
        this.elementIndex = elementIndex;
    }
}

public class KWayMerge {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {6, 7, 8, 9, 10};
        int[] c = {1, 3, 5, 7, 9};

        int[][] arrays = {a, b, c};

        int[] result = mergeKLists(arrays);
        System.out.println("Merged Array: " + Arrays.toString(result));
    }

    /**
     * Merges k sorted arrays into a single sorted array.
     *
     * @param arrays An array of sorted integer arrays to be merged.
     * @return A single sorted array containing all the elements from the input arrays.
     *
     * Time Complexity: O(n log k), where n is the total number of elements across all k arrays.
     *                  - Adding each element to the min-heap costs O(log k).
     *                  - Since there are n elements to add, the overall time complexity is O(n log k).
     *
     * Space Complexity: O(n + k), where n is the total number of elements in the result array
     *                   and k is the number of arrays.
     *                   - O(k) space is required for the min-heap to store elements from k arrays.
     *                   - O(n) space is required to store the final merged array.
     */
    public static int[] mergeKLists(int[][] arrays) {
        PriorityQueue<ArrayEntry> minHeap = new PriorityQueue<>((e1, e2) -> e1.value - e2.value);
        int totalLength = 0;

        // Initialize the heap with the first element from each array
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                minHeap.add(new ArrayEntry(arrays[i][0], i, 0));
                totalLength += arrays[i].length;
            }
        }

        int[] result = new int[totalLength];
        int index = 0;

        // Process the heap until it is empty
        while (!minHeap.isEmpty()) {
            ArrayEntry current = minHeap.poll();
            result[index++] = current.value;

            // If there's another element in the same array, add it to the heap
            if (current.elementIndex + 1 < arrays[current.arrayIndex].length) {
                minHeap.add(new ArrayEntry(arrays[current.arrayIndex][current.elementIndex + 1], current.arrayIndex, current.elementIndex + 1));
            }
        }

        return result;
    }

}
