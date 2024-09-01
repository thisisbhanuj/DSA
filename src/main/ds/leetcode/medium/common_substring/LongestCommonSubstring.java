package main.ds.leetcode.medium.common_substring;

import java.util.HashSet;

/**
 * It efficiently finds the longest common substring across multiple strings by leveraging binary search and set-based substring checks.
 *
 * @Time-Complexity : O(N * M * log(M)), where N is the number of strings and M is the length of the longest string.
 * @Space-Complexity : O(N * M) for storing substrings.
 */
public class LongestCommonSubstring {

    // Binary search to find the maximum length of common substring
    public static String lcf(String[] source) {
        int low = 0;
        int high = minimumPossiblePrefix(source);
        String result = "";

        while (low <= high) {
            int mid = low + (high - low) / 2;

            String output = longestCommonPrefix(source, mid);
            if (output != null) {
                result = output;
                low = mid + 1;  // Try for a longer substring
            } else {
                high = mid - 1;  // Try for a shorter substring
            }
        }

        return result;
    }

    // Function to find the common substring of a specific length
    static String longestCommonPrefix(String[] source, int subStringLength) {
        if (source.length == 0 || subStringLength == 0) return null;

        String firstString = source[0];
        HashSet<String> commonSubstrings = new HashSet<>();

        // Collect all substrings of the given length from the first string
        for (int index = 0; index <= firstString.length() - subStringLength; index++) {
            commonSubstrings.add(firstString.substring(index, index + subStringLength));
        }

        // Iterate over the rest of the strings
        for (int pos = 1; pos < source.length; pos++) {
            String data = source[pos];
            HashSet<String> currentSet = new HashSet<>();

            for (int index = 0; index <= data.length() - subStringLength; index++) {
                String subString = data.substring(index, index + subStringLength);
                if (commonSubstrings.contains(subString)) {
                    currentSet.add(subString);
                }
            }

            // Update the set of common substrings to include only those found in this string
            commonSubstrings = currentSet;

            // If at any point there are no common substrings of this length, return null
            if (commonSubstrings.isEmpty()) {
                return null;
            }
        }

        // Return any of the common substrings
        return commonSubstrings.iterator().next();
    }

    // Function to find the minimum length of strings in the array
    static int minimumPossiblePrefix(String[] source) {
        if (source.length == 0) return 0;

        int lowest = source[0].length();
        for (String eachString : source) {
            int lengthOfString = eachString.length();
            if (lengthOfString < lowest) {
                lowest = lengthOfString;
            }
        }
        return lowest;
    }

    public static void main(String[] args) {
        String[] strings = {"abcdef", "zzzabcdefijh", "abc"};
        String response = lcf(strings);

        System.out.println(response); // Expected output: "abc"
    }
}
