package main.ds.tree.trie.common.substring;

import java.util.HashSet;
import java.util.Set;

/**
 * It efficiently finds the longest common substring across multiple strings by leveraging binary search and set-based substring checks.
 *
 * @Time-Complexity : O(N * M * log(M)), where N is the number of strings and M is the length of the longest string.
 * @Space-Complexity : O(N * M) for storing substrings.
 */
public class LongestCommonSubstring {
    // Binary search to find the maximum length of common substring
    public static String longestCommonSubstring(String[] strings) {
        int low = 0;
        int high = minLength(strings);
        String result = "";

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String commonSubstring = findCommonSubstringOfLength(strings, mid);

            if (commonSubstring != null) {
                result = commonSubstring;
                low = mid + 1;  // Try for a longer substring
            } else {
                high = mid - 1;  // Try for a shorter substring
            }
        }

        return result;
    }

    // Find the minimum length among the strings
    private static int minLength(String[] strings) {
        int min = Integer.MAX_VALUE;
        for (String s : strings) {
            min = Math.min(min, s.length());
        }
        return min;
    }

    // Find a common substring of exact length
    private static String findCommonSubstringOfLength(String[] strings, int length) {
        if (length == 0) return "";

        Set<String> substrings = new HashSet<>();
        String firstString = strings[0];

        // Collect all substrings of the given length from the first string
        for (int i = 0; i <= firstString.length() - length; i++) {
            substrings.add(firstString.substring(i, i + length));
        }

        // Check if any of these substrings are present in all other strings
        for (int i = 1; i < strings.length; i++) {
            Set<String> currentSubstrings = new HashSet<>();
            String str = strings[i];
            for (int j = 0; j <= str.length() - length; j++) {
                String substring = str.substring(j, j + length);
                if (substrings.contains(substring)) {
                    currentSubstrings.add(substring);
                }
            }
            substrings = currentSubstrings;
            if (substrings.isEmpty()) return null;
        }

        return substrings.isEmpty() ? null : substrings.iterator().next();
    }

    /*
        The binary search with substring hashing approach is efficient for many practical cases,
        but there are other methods worth considering depending on the context and constraints.

        ### 1. Suffix Tree/Array

        Suffix Tree:
        - Build a generalized suffix tree for all strings.
        - Traverse the tree to find the longest substring present in all strings.
        - Time Complexity: `O(N * M)` where `N` is the number of strings and `M` is the total length of all strings combined.
        - Space Complexity: `O(N * M)`.

        Suffix Array:
        - Construct a suffix array for a concatenated string with delimiters.
        - Use a longest common prefix (LCP) array to find the longest common substring.
        - Time Complexity: `O(N * M * log(N * M))`.
        - Space Complexity: `O(N * M)`.

        ### 2. Rabin-Karp Rolling Hash
  
        - Use rolling hash to efficiently compute hash values for substrings of length `L`.
        - Check if a common hash value exists across all strings.
        - Time Complexity: `O(N * M * log M)` depending on hash collision handling.
        - Space Complexity: `O(N * M)`.

        ### 3. Dynamic Programming

        - Construct a dynamic programming table to find the longest common substring.
        - This is less efficient for large numbers of strings due to its high time complexity.
        - Time Complexity: `O(N * M^2)`.
        - Space Complexity: `O(M^2)`.

        ### Efficiency Summary:
        - Binary Search with Hashing: Generally efficient and practical for moderate-sized problems.
        - Suffix Tree/Array: Very efficient for larger datasets but complex to implement.
        - Rolling Hash: Efficient but requires careful handling of hash collisions.
        - Dynamic Programming: Simpler but not suitable for large datasets.

        For most practical purposes and moderate-sized inputs, the binary search with substring hashing method strikes a good balance between simplicity and efficiency.
        For very large datasets, suffix trees or suffix arrays might offer better performance if you can handle their complexity.
     */
    public static void main(String[] args) {
        String[] strings = {"abcdefgh", "cdefghijkl", "efghijklmn"};
        System.out.println("Longest Common Substring: " + longestCommonSubstring(strings));
    }
}
