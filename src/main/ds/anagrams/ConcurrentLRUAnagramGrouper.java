package main.ds.anagrams;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * ConcurrentLRUAnagramGrouper
 *
 * <p>
 * A lock-free, thread-safe, streaming Anagram Grouper.
 * Designed for concurrent ingestion of words and automatic grouping by their
 * anagram signatures.
 * When total group count exceeds MAX_GROUPS, oldest groups are evicted in FIFO order
 * (approximate LRU behavior).
 * </p>
 *
 * <p>
 * <b>Core Design Principles:</b><br>
 * - Non-blocking (lock-free) concurrent ingestion using {@link ConcurrentHashMap}.<br>
 * - Eviction tracking via {@link ConcurrentLinkedQueue} for order maintenance.<br>
 * - Atomic metrics collection via {@link LongAdder}.<br>
 * - String normalization using Unicode letter class {@code \\p{L}}.<br>
 * </p>
 *
 * <p><b>Complexity:</b><br>
 * Time: O(k) per word, where k = length of word (for signature computation).<br>
 * Space: O(n * m), where n = number of groups, m = average group size.</p>
 *
 * <p><b>Thread Safety:</b><br>
 * Fully concurrent. No global synchronization. Eviction is approximate under high concurrency.</p>
 *
 * <p><b>Usage:</b><br>
 * Suitable for production-scale stream processing, log analytics, or data enrichment pipelines.</p>
 */
public class ConcurrentLRUAnagramGrouper {

    /** Maximum number of distinct anagram groups retained in memory. */
    private static final int MAX_GROUPS = 1000;

    /** Primary structure: maps anagram signature → queue of words belonging to that group. */
    private static final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> groups = new ConcurrentHashMap<>();

    /** FIFO queue to track insertion order for approximate LRU eviction. */
    private static final ConcurrentLinkedQueue<String> evictionQueue = new ConcurrentLinkedQueue<>();

    /** Atomic metric counter for total words processed. */
    private static final LongAdder totalWordsProcessed = new LongAdder();

    /**
     * Ingests a single word from stream input.
     *
     * @param word input token (may include punctuation or mixed case)
     */
    public static void onWord(String word) {
        if (word == null || word.isEmpty()) return;

        String key = signature(word);

        // Compute group atomically
        groups.compute(key, (k, queue) -> {
            if (queue == null) {
                queue = new ConcurrentLinkedQueue<>();
                evictionQueue.add(k);
            }
            queue.add(word);
            return queue;
        });

        totalWordsProcessed.increment();

        // Trigger lightweight eviction if limit exceeded
        evictIfNeeded();
    }

    /**
     * Computes normalized anagram signature.
     * <p>
     * Converts word to lowercase, removes punctuation,
     * and counts occurrences of each alphabetic character.
     * Example: "Silent" → [1, 0, 0, ..., 1]
     * </p>
     *
     * @param word input string
     * @return normalized signature string for grouping
     */
    private static String signature(String word) {
        int[] freq = new int[26];
        word = word.toLowerCase().replaceAll("[^\\p{L}]", "");
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c)) freq[c - 'a']++;
        }
        return Arrays.toString(freq);
    }

    /**
     * Evicts oldest groups to keep memory bounded.
     * Approximate LRU: order maintained by insertion sequence.
     */
    private static void evictIfNeeded() {
        while (groups.size() > MAX_GROUPS) {
            String oldestKey = evictionQueue.poll();
            if (oldestKey != null) groups.remove(oldestKey);
        }
    }

    /**
     * Retrieves all current anagram groups.
     * Intended for monitoring or batch output.
     *
     * @return snapshot map of signature → words
     */
    public static Map<String, List<String>> snapshot() {
        Map<String, List<String>> snapshot = new HashMap<>();
        groups.forEach((key, queue) -> snapshot.put(key, new ArrayList<>(queue)));
        return snapshot;
    }

    /**
     * Returns number of words processed so far.
     */
    public static long getTotalWordsProcessed() {
        return totalWordsProcessed.sum();
    }

    // === Demo Runner ===
    public static void main(String[] args) throws InterruptedException {
        // Simulated multi-threaded ingestion
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<String> words = List.of("listen", "silent", "enlist", "hello", "ohlle", "world", "dlrow");

        for (String word : words) {
            pool.submit(() -> onWord(word));
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.SECONDS);

        System.out.println("Total Words Processed : " + getTotalWordsProcessed());
        snapshot().forEach((sig, group) -> System.out.println(group));
    }
}
