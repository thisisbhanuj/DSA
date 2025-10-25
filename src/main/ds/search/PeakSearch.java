package main.ds.search;

/**
 * PeakSearch demonstrates how to find a peak element in an array using binary search.
 * A peak element is defined as an element that is greater than its neighbors.
 * This implementation guarantees O(log n) time complexity.
 *
 * Example:
 * Input:  [7, 8, 9, 3, 4, 5, 1]
 * Output: Index 2 (value 9 is a peak)
 */
public class PeakSearch {
    public static void main(String[] args) {
        int[] arr = {7, 8, 9, 3, 4, 5, 1};

        // Call peak() to find index of first peak element
        int index = peak(arr);
        System.out.println("First Peak @ index: " + index + ", value: " + arr[index]);
    }

    // ✅ Key Points for Revision
    // Binary search logic: slope comparison guides the search direction.
    // Peak guarantee: there is always at least one peak in any array (boundary or internal).
    // Time complexity: O(log n), space O(1).
    // Avoiding pitfalls: no mid-1 or mid+1 out-of-bounds errors.
    // Usage: works even if the array is “rotated” or partially unsorted; only the slope matters.
    /**
     * Finds a peak element in the array using binary search.
     *
     * @param data The input array of integers
     * @return Index of a peak element (element greater than its neighbors)
     */
    private static int peak(int[] data) {
        int start = 0;
        int end = data.length - 1;

        // Continue until search space reduces to a single element
        while (start < end) {
            int mid = start + (end - start) / 2;

            // Slope analysis:
            // - If mid < mid+1, we are on an ascending slope → peak must be on the right
            // - If mid > mid+1, we are on a descending slope → peak is at mid or on the left
            if (data[mid] < data[mid + 1]) {
                start = mid + 1; // move to right half
            } else {
                end = mid; // move to left half, including mid
            }
        }

        // When start == end, search space is one element → it is a peak
        return start;
    }
}
