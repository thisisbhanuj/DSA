package main.ds.anagrams;

import java.util.*;

public class GroupAnagram {

    static void groupAnagram(String[] words) {
        if (words == null || words.length == 0) return;

        Map<String, List<String>> grouped = new HashMap<>();

        for (String word : words) {
            if (word == null) continue;
            // Normalize: lowercase, remove spaces/punctuations
            word = word.replaceAll("[^\\p{L}]", "").toLowerCase();

            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }

        // Print each anagram group
        for (List<String> group : grouped.values()) {
            System.out.println(group);
        }
    }

    public static void main(String[] args) {
        String[] words = {"listen", "silent", "enlist", "hello", "ohlle", "world"};
        groupAnagram(words);
    }
}