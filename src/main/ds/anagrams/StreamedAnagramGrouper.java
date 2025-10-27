package main.ds.anagrams;

import java.util.*;

// 🎯 Problem framing : You’re processing a stream (Kafka topic, socket, stdin, etc.) where words arrive one by one, possibly unbounded.
// Goal: Group words that are anagrams, without storing everything in memory if possible.
public class StreamedAnagramGrouper {
    private static final int MAX_GROUPS = 1000; // memory guard
    private static final Map<String, List<String>> groups = new HashMap<>();

    public static void processWord(String word) {
        if (word == null || word.isBlank()) return;

        String normalized = word.toLowerCase().replaceAll("[^\\p{L}]", "");
        String key = getSignature(normalized);

        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);

        if (groups.size() > MAX_GROUPS) {
            evictLeastRecentlyUsed();
        }
    }

    private static String getSignature(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c))
                freq[c - 'a']++;
        }
        return Arrays.toString(freq);
    }

    private static void evictLeastRecentlyUsed() {
        // naive eviction for demo — LRU cache would be ideal
        Iterator<String> it = groups.keySet().iterator();
        if (it.hasNext()) {
            String oldest = it.next();
            System.out.println("Evicting group: " + groups.get(oldest));
            it.remove();
        }
    }

    public static void printGroups() {
        groups.forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v);
        });
    }

    public static void main(String[] args) {
        String[] stream = {"listen", "silent", "hello", "enlist", "ohlle", "world", "dlrow"};
        for (String word : stream) {
            processWord(word);
        }

        System.out.println("\nFinal Groups:");
        printGroups();
    }
}
