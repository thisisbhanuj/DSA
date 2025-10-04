package main.ds.leetcode.custom.twopointers;

import java.util.Arrays;

public class SumTargetSortedArray {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        int target = 13;
        int[] result = twoSum(array, target);
        System.out.println(Arrays.toString(result));
    }

    /**
     * Finds two numbers in a sorted array whose sum equals the target value.
     *
     * <p>
     * Problem intuition:
     * Given a sorted array of integers and a target sum, we need to find the indices
     * of two elements that add up exactly to the target.
     *
     * <pre>
     *     array[left] + array[right] ? target
     * </pre>
     *
     * - Start with two pointers:
     *   left = 0 (start of array), right = n - 1 (end of array).
     * - Because the array is sorted, the sum can be adjusted predictably by moving
     *   either pointer:
     *   <ul>
     *     <li>If the sum is too large (> target), decrement right to reduce it.</li>
     *     <li>If the sum is too small (< target), increment left to increase it.</li>
     *     <li>If the sum equals target, return the pair of indices.</li>
     *   </ul>
     *
     * <p>
     * Why Two Pointers?
     * <ul>
     *     <li>A brute-force solution would require checking every possible pair, O(n^2).</li>
     *     <li>Because the array is sorted, we can decide deterministically which pointer to move
     *         after each comparison — avoiding unnecessary checks.</li>
     *     <li>This makes the algorithm O(n), as each pointer moves at most n steps.</li>
     * </ul>
     *
     * <p>
     * Limitations:
     * <ul>
     *     <li>This implementation returns only the first valid pair found.</li>
     *     <li>If multiple pairs exist, only one is returned.</li>
     *     <li>Requires the array to be sorted. If unsorted, you must sort first (O(n log n))
     *         or use a HashMap approach (O(n) time, O(n) space).</li>
     * </ul>
     *
     * <p>
     * Complexity:
     * <ul>
     *     <li>Time Complexity: O(n) — Each step moves left or right closer together.</li>
     *     <li>Space Complexity: O(1) — Constant extra space used.</li>
     * </ul>
     *
     * @param array a sorted integer array
     * @param target the target sum value
     * @return indices of the two elements that sum to target, or an empty array if none found
     */
    private static int[] twoSum(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int sum = array[left] + array[right];

            if (sum == target) {
                return new int[] {left, right};
            }

            if (sum > target) {
                right--; // reduce sum
            } else {
                left++;  // increase sum
            }
        }

        return new int[]{}; // no pair found
    }
}
