package main.ds.leetcode.custom.slidingwindow;

import java.util.ArrayList;
import java.util.List;

public class AllSubArraysSumFromTarget {
    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 1, 6, 7, 8, 4, 5};
        int target = 5;

        List<List<Integer>> subArrays = findAllSubArrays(weights, target);
        System.out.println("All subarray's with sum ≥ " + target + " are: " + subArrays);
    }

    private static List<List<Integer>> findAllSubArrays(int[] weights, int target) {
        int left = 0;
        int sum = 0;
        List<List<Integer>> result = new ArrayList<>();

        for (int right = 0; right < weights.length; right++) {
            sum += weights[right];

            // Shrink the window and collect all valid subarrays
            while (sum >= target) {
                // Add all subarrays from left to right
                List<Integer> subarray = new ArrayList<>();
                for (int i = left; i <= right; i++) {
                    subarray.add(weights[i]);
                }
                result.add(subarray);

                sum -= weights[left];
                left++;
            }
        }

        return result;
    }
}
