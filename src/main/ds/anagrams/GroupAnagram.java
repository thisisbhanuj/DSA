package main.ds.anagrams;

import java.util.*;

/**
 * Utility class to group words that are anagrams of each other.
 * <p>
 * Two words are anagrams if they contain the same characters
 * with the same frequency, ignoring case and punctuation.
 * <p>
 * Example:
 * <pre>
 * Input: ["listen", "silent", "enlist", "hello", "ohlle", "world"]
 * Output:
 *  [listen, silent, enlist]
 *  [hello, ohlle]
 *  [world]
 * </pre>
 *
 * Supported Approaches:
 * 1. Sorting-based signature.
 * 2. Frequency-count-based signature.
 *
 * Both methods group words into lists based on identical canonical keys.
 *
 * Author: Bhanuj
 */
public class GroupAnagram {

    /**
     * Groups words into anagrams by sorting each word’s characters.
     * <p>
     * Steps:
     * - Normalize the word: lowercase + remove non-letter chars.
     * - Sort characters to derive the canonical key.
     * - Aggregate words with the same sorted key.
     *
     * @param words array of input words to be grouped
     *
     * Time Complexity: O(N * M log M)
     * - N = number of words
     * - M = average length of a word
     * (Sorting each word dominates)
     *
     * Space Complexity: O(N * M)
     * - Storing grouped words + intermediate keys
     */
    static void groupAnagramUsingSort(String[] words) {
        if (words == null || words.length == 0) return;

        Map<String, List<String>> grouped = new HashMap<>();

        for (String word : words) {
            if (word == null) continue;

            // Normalize: remove punctuation and lowercase
            word = word.replaceAll("[^\\p{L}]", "").toLowerCase();

            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }

        System.out.println("=== Group Anagram Using Sort ===");
        grouped.values().forEach(System.out::println);
        // grouped.forEach((k, v) -> { System.out.println(k); System.out.println(v); });
    }

    /**
     * Groups words into anagrams using frequency-count signature.
     * <p>
     * Steps:
     * - Normalize: lowercase + remove punctuation.
     * - Create a frequency vector (26-length for a-z).
     * - Convert frequency array to a string key.
     * - Group words sharing identical frequency distribution.
     *
     * @param words array of input words to be grouped
     *
     * Time Complexity: O(N * M)
     * - N = number of words
     * - M = average length of a word
     * (Frequency counting is linear per word)
     *
     * Space Complexity: O(N * M)
     * - HashMap for grouping + frequency key representation
     *
     * Notes:
     * - Works only for English letters a–z.
     * - Use a Map<Character, Integer> for Unicode-safe version.
     */
    static void groupAnagramUsingFrequencyCount(String[] words) {
        if (words == null || words.length == 0) return;

        Map<String, List<String>> grouped = new HashMap<>();

        for (String word : words) {
            if (word == null) continue;

            word = word.replaceAll("[^\\p{L}]", "").toLowerCase();

            int[] frequency = new int[26];
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (c >= 'a' && c <= 'z')
                    frequency[c - 'a']++;
            }
            String key = Arrays.toString(frequency);

            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }

        System.out.println("=== Group Anagram Using Frequency Count ===");
        grouped.values().forEach(System.out::println);
        // grouped.forEach((k, v) -> { System.out.println(k); System.out.println(v); });
    }

    public static void main(String[] args) {
        String[] words = {"listen", "silent", "enlist", "hello", "ohlle", "world"};
        groupAnagramUsingSort(words);
        groupAnagramUsingFrequencyCount(words);
    }
}
