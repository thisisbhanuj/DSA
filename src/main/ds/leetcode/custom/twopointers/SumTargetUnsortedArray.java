package main.ds.leetcode.custom.twopointers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SumTargetUnsortedArray {
    public static void main(String[] args) {
        int[] array = {7, 2, 11, 15, 1, 8};
        int target = 9;

        int[] result = twoSum(array, target);
        System.out.println(Arrays.toString(result)); // Expected: [1, 4] (2 + 7 = 9)

        int[] difference = difference(array, target);
        System.out.println(Arrays.toString(difference));
    }

    /**
     * Finds two numbers in an unsorted array whose sum equals the target value.
     *
     * <p>
     * Problem intuition:
     * In an unsorted array, the two-pointer technique does not work directly,
     * because moving left/right does not guarantee predictable changes in sum.
     * Instead, we can leverage a HashMap to store previously seen values and
     * quickly check if the complement (target - current) exists.
     *
     * <p>
     * Algorithm (HashMap approach):
     * <ol>
     *     <li>Initialize a HashMap to store numbers and their indices.</li>
     *     <li>Iterate over the array:
     *         <ul>
     *             <li>For each element `x = array[i]`, compute complement = target - x.</li>
     *             <li>If complement is already in the map, return indices {map[complement], i}.</li>
     *             <li>Otherwise, store {x : i} in the map for future lookup.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * <p>
     * Why HashMap?
     * <ul>
     *     <li>HashMap lookup and insert operations are O(1) on average.</li>
     *     <li>Thus, we only need a single pass through the array (O(n)).</li>
     *     <li>This avoids the O(n log n) sorting step required for using two-pointers.</li>
     * </ul>
     *
     * <p>
     * Complexity:
     * <ul>
     *     <li>Time Complexity: O(n) — Each element is processed once.</li>
     *     <li>Space Complexity: O(n) — HashMap stores up to n elements.</li>
     * </ul>
     *
     * <p>
     * Limitations:
     * <ul>
     *     <li>Assumes exactly one valid solution or returns the first pair found.</li>
     *     <li>Will return an empty array if no pair exists.</li>
     * </ul>
     *
     * @param array unsorted array of integers
     * @param target the target sum value
     * @return indices of the two elements that sum to target, or an empty array if none found
     */
    private static int[] twoSum(int[] array, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            int complement = target - array[i];

            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }

            map.put(array[i], i);
        }

        return new int[]{};
    }

    /**
     * Finds two numbers in an array whose difference equals the target value.
     *
     * <p>
     * Problem intuition:
     * We need to identify two elements (a, b) such that:
     * <pre>
     *     a - b = target
     * </pre>
     *
     * Unlike the two-sum problem, difference is directional. That means either:
     * <ul>
     *     <li>a = b + target</li>
     *     <li>b = a - target</li>
     * </ul>
     * So we must check both possibilities when scanning the array.
     *
     * <p>
     * Algorithm (HashMap approach):
     * <ol>
     *     <li>Initialize a HashMap to store numbers and their indices.</li>
     *     <li>Iterate over the array:
     *         <ul>
     *             <li>Let current element be x = array[i].</li>
     *             <li>Check if (x - target) exists in the map:
     *                 <ul><li>If yes, then (x, x - target) is a valid pair.</li></ul>
     *             </li>
     *             <li>Check if (x + target) exists in the map:
     *                 <ul><li>If yes, then (x + target, x) is a valid pair.</li></ul>
     *             </li>
     *             <li>If neither found, store {x : i} in the map.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * <p>
     * Why both cases?
     * <ul>
     *     <li>If the larger number appears first in the array, we need case 1 (x - target).</li>
     *     <li>If the smaller number appears first, we need case 2 (x + target).</li>
     *     <li>Checking both ensures we capture pairs regardless of order.</li>
     * </ul>
     *
     * <p>
     * Complexity:
     * <ul>
     *     <li>Time Complexity: O(n) — Each element is processed once with O(1) map lookups.</li>
     *     <li>Space Complexity: O(n) — HashMap stores up to n elements.</li>
     * </ul>
     *
     * @param array array of integers
     * @param target the target difference value
     * @return indices of the two elements whose difference is target, or an empty array if none found
     */
    private static int[] difference(int[] array, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            int x = array[i];

            // Case 1: x - target already seen
            if (map.containsKey(x - target)) {
                return new int[] { map.get(x - target), i };
            }

            // Case 2: x + target already seen
            if (map.containsKey(x + target)) {
                return new int[] { map.get(x + target), i };
            }

            // Store current number
            map.put(x, i);
        }

        return new int[]{}; // no pair found
    }

}
