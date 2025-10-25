package main.ds.search;

public class RotatedSortedSearch {

    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // ✅ 1. Direct hit
            if (nums[mid] == target) return mid;

            // ✅ 2. Check which half is sorted
            if (nums[left] <= nums[mid]) {
                // 👉 Left half [left..mid] is sorted

                // Is target inside this sorted range?
                if (target >= nums[left] && target < nums[mid]) {
                    // Yes → discard right half
                    right = mid - 1;
                } else {
                    // No → target must be in pivoted (unsorted) half
                    left = mid + 1;
                }

            } else {
                // 👉 Right half [mid..right] is sorted

                // Is target inside this sorted range?
                if (target > nums[mid] && target <= nums[right]) {
                    // Yes → discard left half
                    left = mid + 1;
                } else {
                    // No → target must be in pivoted (unsorted) half
                    right = mid - 1;
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

