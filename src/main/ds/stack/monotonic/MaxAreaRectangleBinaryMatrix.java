package main.ds.stack.monotonic;

import java.util.Stack;

/**
 * Computes the largest rectangle area filled with 1s in a binary matrix.
 *
 * <p>Intuition:
 * Each row of the binary matrix can be treated as the base of a histogram.
 * For every row, we build a histogram of consecutive 1s in each column
 * (extending from top to the current row). Then, for each histogram,
 * we compute the largest rectangular area using a Monotonic Stack.
 *
 * <p>This approach effectively transforms the 2D problem into multiple
 * instances of the 1D "Largest Rectangle in Histogram" problem.
 *
 * <p>Time Complexity: O(rows * cols)
 * - For each row, we traverse all columns once to update heights O(cols)
 * - For each histogram, we compute largest rectangle in O(cols)
 * - Total = O(rows * cols)
 *
 * <p>Space Complexity: O(cols)
 * - Extra space for height array and stack.
 */
public class MaxAreaRectangleBinaryMatrix {

    /**
     * Finds the maximal rectangle containing only 1s in a binary matrix.
     *
     * @param matrix Binary matrix (0s and 1s)
     * @return Maximum rectangle area of contiguous 1s
     */
    public int maximalRectangle(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        for (int i = 0; i < rows; i++) {
            // Build histogram for the current row
            for (int j = 0; j < cols; j++) {
                heights[j] = (matrix[i][j] == 0) ? 0 : heights[j] + 1;
            }
            // Compute the largest rectangle area for this histogram
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    /**
     * Computes the largest rectangle area in a histogram.
     *
     * <p>Intuition:
     * We use a Monotonic Increasing Stack that keeps track of indices of bars
     * in ascending order of their heights. Whenever we encounter a bar
     * shorter than the stack's top, we pop from the stack and compute the
     * area using the popped height as the smallest bar in the rectangle.
     *
     * <p>Time Complexity: O(n)
     * - Each bar is pushed and popped at most once.
     *
     * <p>Space Complexity: O(n)
     * - Stack stores indices of bars.
     *
     * @param heights Array representing histogram heights
     * @return Largest rectangular area possible
     */
    private int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;

        // Traverse through all bars (include sentinel bar at end)
        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            // Pop higher bars and calculate area
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

    /**
     * Driver method for quick testing.
     */
    public static void main(String[] args) {
        MaxAreaRectangleBinaryMatrix solver = new MaxAreaRectangleBinaryMatrix();

        int[][] matrix = {
                {1, 0, 1, 0, 0},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 0, 1, 0}
        };

        int maxRectangleArea = solver.maximalRectangle(matrix);
        System.out.println("Maximum Rectangle Area: " + maxRectangleArea);
    }
}
