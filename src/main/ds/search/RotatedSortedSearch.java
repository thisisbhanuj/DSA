package main.ds.search;

public class RotatedSortedSearch {

    public static int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            // ✅ 1. Direct hit
            if (nums[mid] == target) return mid;

            // ✅ 2. Check which half is sorted
            if (nums[start] <= nums[mid]) {
                // 👉 Left half [start..mid] is sorted

                // Is target inside this sorted range?
                if (target >= nums[start] && target < nums[mid]) {
                    // Yes → discard end half
                    end = mid - 1;
                } else {
                    // No → target must be in pivoted (unsorted) half
                    start = mid + 1;
                }

            } else {
                // 👉 Right half [mid..end] is sorted

                // Is target inside this sorted range?
                if (target > nums[mid] && target <= nums[end]) {
                    // Yes → discard start half
                    start = mid + 1;
                } else {
                    // No → target must be in pivoted (unsorted) half
                    end = mid - 1;
                }
            }
        }

        // ❌ Target not found
        return -1;
    }

    /**
     * Finds the smallest element in a rotated sorted array.
     *
     * @param data Rotated sorted array
     * @return Index of the smallest element
     */
    private static int globalMinimum(int[] data) {
        int start = 0, end = data.length - 1;

        // Binary search locates the smallest element → the “rotation point”.
        while (start < end) {
            int mid = start + (end - start) / 2;

            // Compare mid with the rightmost element
            if (data[mid] > data[end]) {
                // Pivot must be to the right of mid
                start = mid + 1;
            } else {
                // Pivot is at mid or to the left
                end = mid;
            }
        }
        return start;
    }

    /**
     * Finds the pivot (maximum element) in a rotated sorted array.
     *
     * @param data Rotated sorted array
     * @return Index of the pivot element
     */
    private static int globalMaximum(int[] data) {
        int start = 0, end = data.length - 1;

        // Binary search locates the smallest element → the “rotation point”.
        while (start < end) {
            int mid = start + (end - start) / 2;

            // Compare mid with the rightmost element
            if (data[mid] > data[end]) {
                // Pivot must be to the right of mid
                start = mid + 1;
            } else {
                // Pivot is at mid or to the left
                end = mid;
            }
        }
        // Pivot (maximum) is one element left of the smallest element.
        // Ensures circular wrap-around, so we never get a negative index.
        // So now it works even if the array is not rotated.
        return (start - 1 + data.length) % data.length;
    }

    public static void main(String[] args) {
        int[] arr = {7, 8, 9, 3, 4, 5, 6};
        int target = 6;
        int index = search(arr, target);
        System.out.println("Index of " + target + ": " + index);
        System.out.println("Global Minimum : " + arr[globalMinimum(arr)]);
        System.out.println("Global Maximum : " + arr[globalMaximum(arr)]);
    }
}

