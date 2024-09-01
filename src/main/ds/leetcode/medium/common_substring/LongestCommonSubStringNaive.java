package main.ds.leetcode.medium.common_substring;

/**
 * @Time-Complexity : O(m x n x min(m, n))
 */
public class LongestCommonSubStringNaive {
    // Function to find the length of the longest common substring
    static int maxCommStr(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int globalMax = 0;

        // Consider every pair of index and find the length
        // of the longest common substring beginning with
        // every pair. Finally return max of all maximums.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int counter = 0; // To locally increment both strings and compare
                while ((i + counter) < m && (j + counter) < n
                        && s1.charAt(i + counter) == s2.charAt(j + counter)) {
                    counter++;
                }
                globalMax = Math.max(globalMax, counter);
            }
        }
        return globalMax;
    }

    public static void main(String[] args) {
        String s1 = "geeksforgeeks";
        String s2 = "practicewritegeekscourses";
        System.out.println(maxCommStr(s1, s2));
    }
}

