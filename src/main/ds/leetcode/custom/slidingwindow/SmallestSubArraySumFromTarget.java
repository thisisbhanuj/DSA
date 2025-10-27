package main.ds.leetcode.custom.slidingwindow;

import java.util.ArrayList;
import java.util.List;

/**
 * SmallestSubArraySumFromTarget
 *
 * <p>
 * This class demonstrates the Sliding Window pattern to solve the problem of finding the
 * smallest contiguous subarray whose sum is greater than or equal to a given target.
 * </p>
 *
 * <h3>Design Idea / Intuition:</h3>
 * <ol>
 *   <li>Use two pointers: <code>left</code> and <code>right</code> to maintain a window of elements.</li>
 *   <li>Expand the window by moving <code>right</code> and adding elements to the current sum.</li>
 *   <li>Whenever the sum >= target, try shrinking the window from <code>left</code> to find the smallest valid subarray.</li>
 *   <li>Track the minimum length while shrinking to ensure we always get the smallest subarray.</li>
 * </ol>
 *
 * <h3>Time Complexity:</h3>
 * <p>
 * Each element is added at most once and removed at most once from the window.
 * Therefore, the total time complexity is O(n), where n is the number of elements in the array.
 * </p>
 *
 * <h3>Space Complexity:</h3>
 * <p>
 * We only use a few variables to track the sum, pointers, and minimum length.
 * Hence, the space complexity is O(1) (constant space).
 * </p>
 *
 * <h3>Use Case / Pattern Recognition:</h3>
 * <p>
 * This approach works for problems where:
 * <ul>
 *   <li>We need a contiguous subarray or substring</li>
 *   <li>We need to find a sum, product, or condition involving the elements</li>
 *   <li>We want to optimize from O(n^2) brute force to O(n)</li>
 * </ul>
 * This is the essence of the Sliding Window pattern.
 * </p>
 */
public class SmallestSubArraySumFromTarget {
    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 1, 6, 7, 8, 4, 5};
        int target = 5;

        int minLength = findSmallestSubArray(weights, target);
        System.out.println("Smallest subarray length with sum ≥ " + target + " is: " + minLength);

        List<Integer> data = findSmallestSubArrayList(weights, target);
        System.out.println("Smallest subarray length with sum ≥ " + target + " is: " + data);
    }

    /**
     * Finds the length of the smallest contiguous subarray whose sum is greater than or equal to target.
     *
     * @param weights the array of positive integers
     * @param target  the target sum to reach or exceed
     * @return the length of the smallest subarray with sum >= target; returns 0 if no such subarray exists
     */
    private static int findSmallestSubArray(int[] weights, int target) {
        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < weights.length; right++) {
            sum += weights[right];

            // Shrink the window while sum >= target
            while (sum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                sum -= weights[left];
                left++;
            }
        }

        // If no subarray found, return 0
        return (minLength == Integer.MAX_VALUE) ? 0 : minLength;
    }

    private static List<Integer> findSmallestSubArrayList(int[] weights, int target) {
        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;
        int startIndex = 0; // To remember the start of the best window

        for (int right = 0; right < weights.length; right++) {
            sum += weights[right];

            // Shrink the window from left while sum >= target
            while (sum >= target) {
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                    startIndex = left; // Update start of smallest subarray
                }
                sum -= weights[left];
                left++;
            }
        }

        // Build the smallest subarray list
        List<Integer> smallestSubarray = new ArrayList<>();
        if (minLength != Integer.MAX_VALUE) {
            for (int i = startIndex; i < startIndex + minLength; i++) {
                smallestSubarray.add(weights[i]);
            }
        }

        return smallestSubarray;
    }
}
