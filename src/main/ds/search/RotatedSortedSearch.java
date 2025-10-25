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

    public static void main(String[] args) {
        int[] arr = {7, 8, 9, 3, 4, 5, 6};
        int target = 6;
        int index = search(arr, target);
        System.out.println("Index of " + target + ": " + index);
    }
}

