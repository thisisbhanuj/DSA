package main.ds.stack.monotonic;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Computes the largest rectangular area in a histogram using a
 * monotonic stack approach.
 *
 * <p>Core idea:
 * For each bar, find the nearest smaller bar on the left and right.
 * The bar’s height extends between those indices, defining its maximal rectangle.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
public class HistogramArea {

    /**
     * Finds the index of the next smaller element to the right for each element.
     *
     * @param arr the input heights array
     * @return an array where each position contains the index of the next smaller element to the right;
     *         if none exists, stores {@code arr.length}
     */
    static int[] nextSmallerToRight(int[] arr) {
        int[] right = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();

        // Traverse from right to left
        for (int i = arr.length - 1; i >= 0; i--) {
            // Pop elements higher or equal to current — they can't be "next smaller"
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            // If stack is empty, no smaller element to right → use boundary index
            right[i] = stack.isEmpty() ? arr.length : stack.peek();
            stack.push(i); // Push current index for future comparisons
        }

        return right;
    }

    /**
     * Finds the index of the next smaller element to the left for each element.
     *
     * @param arr the input heights array
     * @return an array where each position contains the index of the next smaller element to the left;
     *         if none exists, stores {@code -1}
     */
    static int[] nextSmallerToLeft(int[] arr) {
        int[] left = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();

        // Traverse from left to right
        for (int i = 0; i < arr.length; i++) {
            // Pop elements higher or equal to current — they can't be "next smaller"
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            // If stack is empty, no smaller element to left → use boundary index
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        return left;
    }

    /**
     * Computes the maximum rectangular area under the histogram.
     *
     * @param heights the heights of histogram bars
     * @return the maximum rectangular area
     */
    static int maxArea(int[] heights) {
        int maxArea = 0;
        int[] right = nextSmallerToRight(heights);
        System.out.println("NSR: " + Arrays.toString(right));
        int[] left = nextSmallerToLeft(heights);
        System.out.println("NSL: " + Arrays.toString(left));

        for (int i = 0; i < heights.length; i++) {
            // Width = distance between next smaller on right and left minus 1
            int width = right[i] - left[i] - 1;
            int area = heights[i] * width;
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println("Max Area: " + maxArea(heights)); // ✅ Expected: 10
    }
}
