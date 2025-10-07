package main.ds.leetcode.custom.slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {
    public static void main(String[] args) {
        String data = "ADOBEACODEBANCCCCAAAAAAAABACCCC";
        Map<Character, Integer> requiredCount = new HashMap<>();
        requiredCount.put('A', 2);
        requiredCount.put('B', 1);
        requiredCount.put('C', 1);

        String res = findMinimumSubString(data, requiredCount);
        System.out.println("Result: " + res);
    }

    private static String findMinimumSubString(String data, Map<Character, Integer> requiredCount) {
        if (data == null || data.isEmpty() || requiredCount.isEmpty()) return "";

        Map<Character, Integer> windowCount = new HashMap<>();

        int have = 0;                      // how many chars satisfy frequency
        int need = requiredCount.size();   // total distinct chars to satisfy

        int left = 0;
        int minLength = Integer.MAX_VALUE;
        int minStart = 0;                  // track start of best window

        // Expand window with 'right' pointer
        for (int right = 0; right < data.length(); right++) {
            char c = data.charAt(right);

            // Add char to window
            windowCount.put(c, windowCount.getOrDefault(c, 0) + 1);

            // If this char completes one required condition
            if (requiredCount.containsKey(c) &&
                    windowCount.get(c).intValue() == requiredCount.get(c).intValue()) {
                have++;
            }

            // When all required chars are satisfied → try to shrink window
            while (have == need) {
                // Update minimum window if smaller
                int windowSize = right - left + 1;
                if (windowSize < minLength) {
                    minLength = windowSize;
                    minStart = left;
                }

                // Try to remove from left side
                char leftChar = data.charAt(left);
                windowCount.put(leftChar, windowCount.get(leftChar) - 1);

                // If removing broke a valid condition
                if (requiredCount.containsKey(leftChar) &&
                        windowCount.get(leftChar) < requiredCount.get(leftChar)) {
                    have--;
                }

                left++; // contract window
            }
        }

        return (minLength == Integer.MAX_VALUE)
                ? ""
                : data.substring(minStart, minStart + minLength);
    }
}
