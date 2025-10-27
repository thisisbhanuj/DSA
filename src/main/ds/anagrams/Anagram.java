package main.ds.anagrams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Anagram {
    static boolean isAnagramUsingSort(String s, String t) {
        // If the lengths are different, they can't be anagrams
        if ((s == null || t == null)) {
            return false;
        }

        // Whitespace - Punctuation - Spaces & any non-letter characters Handling
        s = s.replaceAll("[^\\p{L}]", "").toLowerCase();
        t = t.replaceAll("[^\\p{L}]", "").toLowerCase();

        if (s.length() != t.length()) return false;

        // Convert both strings to char arrays
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();

        // Sort both char arrays
        Arrays.sort(sArray);
        Arrays.sort(tArray);

        // Compare sorted arrays
        return Arrays.equals(sArray, tArray);
    }

    static boolean isAnagramLinear(String s, String t) {
        if (s == null || t == null) return false;

        // Whitespace - Punctuation - Spaces & any non-letter characters Handling
        s = s.replaceAll("[^\\p{L}]", "").toLowerCase();
        t = t.replaceAll("[^\\p{L}]", "").toLowerCase();

        if (s.length() != t.length()) return false;

        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }

        for (int c : count) {
            if (c != 0) return false;
        }
        return true;
    }

    static boolean isAnagramUnicode(String s, String t) {
        if (s == null || t == null) return false;

        // Whitespace - Punctuation - Spaces & any non-letter characters Handling
        s = s.replaceAll("[^\\p{L}]", "").toLowerCase();
        t = t.replaceAll("[^\\p{L}]", "").toLowerCase();

        if (s.length() != t.length()) return false;

        Map<Character, Integer> charCount = new HashMap<>();

        // Count chars from s
        for (char c : s.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        // Decrement count using t
        for (char c : t.toCharArray()) {
            if (!charCount.containsKey(c)) return false;
            charCount.put(c, charCount.get(c) - 1);
            if (charCount.get(c) == 0) charCount.remove(c);
        }

        // If map is empty, strings are anagrams
        return charCount.isEmpty();
    }

    public static void main(String[] args){
        System.out.println("Is Anagram (Using Sort)   : " + isAnagramUsingSort("bhanuj!", "a!nu, jbh 123"));
        System.out.println("Is Anagram (Using Linear) : " + isAnagramLinear("bha!nuj", "!anu,1 - 4 jbh"));
        System.out.println("Is Anagram (For Unicode)  : " + isAnagramUnicode("café", "1 - ,féca!"));
    }
}
