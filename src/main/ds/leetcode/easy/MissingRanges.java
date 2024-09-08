package main.ds.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

public class MissingRanges {
    static void findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        int prev = lower - 1;  // prev tracks the number just before the current range being checked.

        for (int i = 0; i <= nums.length; i++) {
            // curr represents the current number from the array (or upper + 1 when the loop ends).
            int curr = (i < nums.length) ? nums[i] : upper + 1;

            // The gap between prev + 1 and curr - 1 identifies the missing range.
            if (prev + 1 <= curr - 1) {
                result.add(formatRange(prev + 1, curr - 1));
            }

            prev = curr;
        }

         System.out.println(result);
    }

    static String formatRange(int low, int high) {
        return (low == high) ? String.valueOf(low) : low + "->" + high;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7,9};
        findMissingRanges(nums, 5, 12);
    }
}
