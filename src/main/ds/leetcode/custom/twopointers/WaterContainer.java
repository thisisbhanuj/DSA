package main.ds.leetcode.custom.twopointers;
/**
 * Finds the maximum area of water that can be contained between two vertical lines.
 *
 * <p>
 * Problem intuition:
 * Each element in the input array represents the height of a vertical line drawn on the x-axis.
 * The container is formed by choosing two lines at indices L and R, and the area of water held
 * between them is determined by:
 *
 * <pre>
 *     area = (R - L) * min(height[L], height[R])
 * </pre>
 *
 * - The width is simply the distance between the indices (R - L).
 * - The height is constrained by the shorter line, since water will spill over the smaller side.
 *
 * <p>
 * Algorithm (Two Pointers Approach):
 * <ol>
 *     <li>Start with the widest container: L at the beginning (0), R at the end (n-1).</li>
 *     <li>At each step, compute the area between L and R and update the maximum found so far.</li>
 *     <li>Move the pointer at the shorter line inward:
 *         <ul>
 *             <li>If height[L] < height[R], increment L.</li>
 *             <li>Else, decrement R.</li>
 *         </ul>
 *         This is because the width always decreases as the pointers move inward,
 *         so the only hope of finding a larger area is by increasing the limiting height,
 *         which can only happen by moving the smaller side.
 *     </li>
 *     <li>Repeat until L and R meet.</li>
 * </ol>
 *
 * <p>
 * Why Two Pointers?
 * <ul>
 *     <li>A brute-force solution would check every pair of lines, which takes O(n^2) time.</li>
 *     <li>The two-pointer approach leverages the monotonic relationship:
 *         - Width shrinks with every move.
 *         - Moving the taller side cannot help, since area is limited by the smaller line.
 *     </li>
 *     <li>This greedy move ensures that no possible max area is missed.</li>
 * </ul>
 *
 * <p>
 * Complexity:
 * <ul>
 *     <li>Time Complexity: O(n) — Each pointer moves at most n steps.</li>
 *     <li>Space Complexity: O(1) — Constant extra space used.</li>
 * </ul>
 *
 */
public class WaterContainer {
    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int area = findMaxContainer(height);
        System.out.println(area); // Expected: 49
    }

    /**
     *
     * @param height array of non-negative integers representing line heights
     * @return the maximum area of water that can be contained
    */
    private static int findMaxContainer(int[] height) {
        int start = 0;
        int end = height.length - 1;
        int area = 0;

        while(start < end){
            int areaTemp = (end - start) * Math.min(height[start], height[end]);
            area = Math.max(areaTemp, area);

            if(height[start] < height[end]) start++;
            else end--;
        }

        return  area;
    }
}
