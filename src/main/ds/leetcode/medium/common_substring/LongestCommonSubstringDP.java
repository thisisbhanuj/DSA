package main.ds.leetcode.medium.common_substring;

/*
    This dynamic programming approach is feasible for a small number of strings and moderate lengths
    but becomes impractical for a large number of strings or very long strings due to its high time and
    space complexity. For larger datasets or more strings, approaches like suffix trees or binary search
    with hashing might be more practical.
 */
public class LongestCommonSubstringDP {

    public static String longestCommonSubstring(String[] strings) {
        if (strings.length < 3) {
            throw new IllegalArgumentException("At least 3 strings are required");
        }

        String s1 = strings[0];
        String s2 = strings[1];
        String s3 = strings[2];

        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();

        int[][][] dp = new int[len1 + 1][len2 + 1][len3 + 1];
        int maxLength = 0;
        int endIndexS1 = 0;

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                for (int k = 1; k <= len3; k++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1) && s2.charAt(j - 1) == s3.charAt(k - 1)) {
                        dp[i][j][k] = dp[i - 1][j - 1][k - 1] + 1;
                        if (dp[i][j][k] > maxLength) {
                            maxLength = dp[i][j][k];
                            endIndexS1 = i - 1;
                        }
                    } else {
                        dp[i][j][k] = 0;
                    }
                }
            }
        }

        // Extract the longest common substring
        return maxLength == 0 ? "" : s1.substring(endIndexS1 - maxLength + 1, endIndexS1 + 1);
    }

    public static void main(String[] args) {
        String[] strings = {"abcdefgh", "cdefghijkl", "efghijklmn"};
        System.out.println("Longest Common Substring: " + longestCommonSubstring(strings));
    }
}
