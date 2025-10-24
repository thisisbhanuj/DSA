package main.ds.stack.monotonic;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

/**
 * <h2>Largest Rectangle in Histogram — Monotonic Stack Techniques</h2>
 *
 * <p>This class provides two variants of the histogram area algorithm:
 * <ul>
 *   <li><b>v1:</b> Explicit computation using precomputed
 *       <i>Next Smaller to Left (NSL)</i> and <i>Next Smaller to Right (NSR)</i>.</li>
 *   <li><b>v2:</b> A canonical, single-pass monotonic stack solution
 *       that computes areas dynamically without auxiliary arrays.</li>
 * </ul>
 *
 * <p>The problem: given an array of bar heights, compute the largest rectangular area
 * that fits entirely under the histogram.
 *
 * <p>Both algorithms run in {@code O(n)} time and use {@code O(n)} space,
 * but differ in conceptual and structural elegance.
 */
public class HistogramArea {

    /**
     * Computes the index of the next smaller element to the right (NSR)
     * for each bar in the histogram.
     *
     * <p>For every index {@code i}, finds the nearest index {@code j > i}
     * where {@code heights[j] < heights[i]}. If no such element exists,
     * assigns {@code heights.length}.
     *
     * <p>This is useful for problems that need explicit range boundaries
     * (e.g. histogram area, stock span, rain water trapping).
     *
     * @param arr the array of bar heights
     * @return an array where {@code right[i]} = index of next smaller bar on the right,
     *         or {@code arr.length} if none exists
     */
    static int[] nextSmallerToRight(int[] arr) {
        int[] right = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? arr.length : stack.peek();
            stack.push(i);
        }
        return right;
    }

    /**
     * Computes the index of the next smaller element to the left (NSL)
     * for each bar in the histogram.
     *
     * <p>For every index {@code i}, finds the nearest index {@code j < i}
     * where {@code heights[j] < heights[i]}. If no such element exists,
     * assigns {@code -1}.
     *
     * @param arr the array of bar heights
     * @return an array where {@code left[i]} = index of next smaller bar on the left,
     *         or {@code -1} if none exists
     */
    static int[] nextSmallerToLeft(int[] arr) {
        int[] left = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return left;
    }

    /**
     * <h3>Version 1 — Explicit NSL/NSR Approach</h3>
     *
     * <p>For each bar:
     * <ul>
     *   <li>Finds the next smaller bar to its left (NSL) and right (NSR).</li>
     *   <li>Uses these indices to determine the width of the rectangle
     *       in which the current bar is the limiting height.</li>
     * </ul>
     *
     * <p>This version emphasizes conceptual clarity and is useful for learning
     * or extending to related problems that require explicit boundary data.
     *
     * <p>Time Complexity: O(n) — each element processed once per stack.
     * <br>Space Complexity: O(n) — two auxiliary arrays for NSL and NSR.
     *
     * @param heights the heights of histogram bars
     * @return the maximum rectangular area under the histogram
     */
    static int histogramArea_v1(int[] heights) {
        int maxArea = 0;
        int[] right = nextSmallerToRight(heights);
        System.out.println("NSR: " + Arrays.toString(right));
        int[] left = nextSmallerToLeft(heights);
        System.out.println("NSL: " + Arrays.toString(left));

        for (int i = 0; i < heights.length; i++) {
            int width = right[i] - left[i] - 1;
            int area = heights[i] * width;
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;
    }

    /**
     * <h3>Version 2 — Canonical Single-Pass Monotonic Stack</h3>
     *
     * <p>This is the clean, production-grade variant. It maintains an
     * <b>increasing monotonic stack</b> of indices as it traverses the histogram.
     * When a smaller bar appears, it:
     * <ul>
     *   <li>Pops all taller bars — each pop finalizes that bar’s maximal area.</li>
     *   <li>Computes the area using its height and the span between
     *       the nearest smaller bars on both sides.</li>
     * </ul>
     *
     * <p>The algorithm effectively performs a “sweep line” through the histogram:
     * each bar is pushed and popped exactly once. A sentinel 0-height bar at the end
     * ensures all remaining bars are resolved cleanly.
     *
     * <p>Advantages:
     * <ul>
     *   <li>Single pass, no auxiliary boundary arrays.</li>
     *   <li>Each index pushed/popped once — true O(n) runtime.</li>
     *   <li>Concise and cache-friendly; ideal for production environments.</li>
     * </ul>
     *
     * @param heights the heights of histogram bars
     * @return the maximum rectangular area under the histogram
     */
    private static int histogramArea_v2(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int rightBoundary = i;
                int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
                int width = rightBoundary - leftBoundary - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println("Max Area using v1: " + histogramArea_v1(heights)); // ✅ Expected: 10
        System.out.println("Max Area using v2: " + histogramArea_v2(heights)); // ✅ Expected: 10
    }
}
