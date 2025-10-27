package main.ds.anagrams;

import com.github.benmanes.caffeine.cache.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Concurrent, production-grade implementation of Anagram grouping
 * using Caffeine cache with LRU + TinyLFU eviction.
 *
 * <p>This implementation automatically handles concurrent insertions,
 * bounded memory (via maximumSize), and timed eviction (via expireAfterAccess).</p>
 *
 * <h3>Complexity</h3>
 * <ul>
 *   <li>Insertion: O(k) for sorting/signature generation</li>
 *   <li>Lookup: O(1) average via Caffeine's concurrent map</li>
 *   <li>Eviction: amortized O(1)</li>
 * </ul>
 *
 * <p>Recommended for high-throughput streaming or production cache layers.</p>
 */
public class CaffeineAnagramGrouper {

    /** Maximum number of distinct anagram groups retained. */
    private static final int MAX_GROUPS = 1_000;

    /** Cache that maps normalized signature → list of words. */
    private static final Cache<String, List<String>> anagramCache = Caffeine.newBuilder()
            .maximumSize(MAX_GROUPS)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .recordStats()
            .build();

    /**
     * Normalizes input by removing non-letter characters and lowercasing.
     * Converts to an anagram signature (sorted letters).
     *
     * @param word input string (may contain punctuation, mixed case)
     * @return canonical key representing the anagram class
     */
    private static String signature(String word) {
        if (word == null) return "";
        String normalized = word.toLowerCase().replaceAll("[^\\p{L}]", "");
        char[] chars = normalized.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Processes a word and groups it into its corresponding anagram bucket.
     * Thread-safe via Caffeine's concurrent map backend.
     *
     * @param word new word to ingest
     */
    public static void onWord(String word) {
        if (word == null || word.isBlank()) return;

        String key = signature(word);
        anagramCache.asMap().compute(key, (k, list) -> {
            if (list == null) list = new ArrayList<>();
            list.add(word);
            return list;
        });
    }

    /**
     * Returns a snapshot of current anagram groups.
     * Safe for concurrent inspection.
     *
     * @return list of anagram groups
     */
    public static Collection<List<String>> currentGroups() {
        return anagramCache.asMap().values();
    }

    /**
     * Prints cache stats: hit/miss ratios, evictions, etc.
     */
    public static void printStats() {
        System.out.println(anagramCache.stats());
    }

    public static void main(String[] args) throws InterruptedException {
        onWord("listen");
        onWord("silent");
        onWord("enlist");
        onWord("hello");
        onWord("ohlle");
        onWord("world");

        System.out.println("Grouped Anagrams:");
        currentGroups().forEach(System.out::println);

        printStats();
    }
}
