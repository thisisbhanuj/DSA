package main.ds.leetcode.custom.twopointers;

import java.util.*;

public class KSumPointers {
    public static void main(String[] args) {
        int[] nums = {1, 0, -1, 0, -2, 2};
        int target = 0;

        // Example: 4-Sum
        List<List<Integer>> result = kSum(nums, target, 4);
        System.out.println(result);
    }

    /**
     * Generic k-Sum solver (works for 2Sum, 3Sum, 4Sum, ..., kSum).
     *
     * @param nums   input array
     * @param target target sum
     * @param k      number of elements in combination
     * @return list of unique combinations
     *
     * Time Complexity:
     *   - O(n^(k-1)) in worst case.
     *   - Example: 2-Sum O(n), 3-Sum O(n^2), 4-Sum O(n^3), etc.
     * Space Complexity: O(k) for recursion depth + O(result) for storing answers.
     *
     * Key Idea:
     *   - Sort array to use two pointers and avoid duplicates.
     *   - Recursively reduce k until it becomes 2 (Two Sum base case).
     */
    public static List<List<Integer>> kSum(int[] nums, int target, int k) {
        Arrays.sort(nums);
        return kSumHelper(nums, target, k, 0);
    }

    private static List<List<Integer>> kSumHelper(int[] nums, int target, int k, int start) {
        List<List<Integer>> res = new ArrayList<>();

        // Base Case: 2Sum with Two Pointers
        if (k == 2) {
            int left = start, right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    res.add(Arrays.asList(nums[left], nums[right]));
                    left++;
                    right--;

                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
            return res;
        }

        // Recursive Case: Reduce k
        for (int i = start; i < nums.length - k + 1; i++) {
            // Skip duplicates
            if (i > start && nums[i] == nums[i - 1]) continue;

            // Recursive call for (k-1)-Sum
            List<List<Integer>> temp = kSumHelper(nums, target - nums[i], k - 1, i + 1);

            // Append current number to all results
            for (List<Integer> t : temp) {
                List<Integer> newList = new ArrayList<>();
                newList.add(nums[i]);
                newList.addAll(t);
                res.add(newList);
            }
        }
        return res;
    }
}
