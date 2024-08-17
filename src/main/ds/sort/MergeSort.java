package main.ds.sort;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] arrayToBeMerged = {5, 1, 0, 8, 3, 2, 10, 9, 6, 4};
        System.out.println("Original Array: " + Arrays.toString(arrayToBeMerged));

        MergeSort sorter = new MergeSort();
        sorter.divideWorkAndMerge(arrayToBeMerged, 0, arrayToBeMerged.length - 1);

        System.out.println("Sorted Array: " + Arrays.toString(arrayToBeMerged));
    }

    private void divideWorkAndMerge(int[] arrayToBeMerged, int low, int high) {
        if (low < high) {  // Base case: when low >= high, recursion stops
            int mid = low + (high - low) / 2;

            // Recursively divide the array
            divideWorkAndMerge(arrayToBeMerged, low, mid);
            divideWorkAndMerge(arrayToBeMerged, mid + 1, high);

            // Merge the divided arrays
            merge(arrayToBeMerged, low, mid, high);
        }
    }

    private void merge(int[] array, int low, int mid, int high) {
        // Copy the subarrays
        int[] leftArray = Arrays.copyOfRange(array, low, mid + 1);
        int[] rightArray = Arrays.copyOfRange(array, mid + 1, high + 1);

        int i = 0, j = 0;
        int k = low;

        // Merge the two subarrays back into the original array
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy any remaining elements of leftArray
        while (i < leftArray.length) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // Copy any remaining elements of rightArray
        while (j < rightArray.length) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
