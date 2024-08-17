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
