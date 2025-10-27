package main.ds.anagrams;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ReactiveAnagramGrouper provides a thread-safe, reactive-style mechanism
 * for grouping streaming words into anagram clusters in real time.
 *
 * <p>Words are asynchronously processed and placed into concurrent groups
 * based on their normalized character signature. Designed to simulate
 * ingestion from live data streams such as Kafka topics, sockets, or file tails.</p>
 *
 * <h2>Key Features</h2>
 * <ul>
 *   <li>Concurrent processing using a fixed thread pool</li>
 *   <li>Lock-free grouping with ConcurrentHashMap and CopyOnWriteArrayList</li>
 *   <li>Configurable eviction of old groups to prevent memory growth</li>
 *   <li>Handles noisy inputs (punctuation, case differences)</li>
 * </ul>
 *
 * <h2>Complexity</h2>
 * <ul>
 *   <li><b>Time Complexity:</b>
 *       <ul>
 *         <li>Per word normalization: O(L)</li>
 *         <li>Signature generation: O(26) ≈ O(1)</li>
 *         <li>Insertion into group: O(1) amortized</li>
 *       </ul>
 *       Total per word ≈ O(L)
 *   </li>
 *   <li><b>Space Complexity:</b>
 *       <ul>
 *         <li>O(G * W_avg * L_avg), where G = number of groups</li>
 *         <li>Each word stored once in memory</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <h2>Thread Safety</h2>
 * All operations are thread-safe. Groups are maintained in
 * {@link ConcurrentHashMap} with {@link CopyOnWriteArrayList} for safe concurrent writes.
 *
 * <h2>Scalability</h2>
 * Thread pool size and MAX_GROUPS define throughput and memory control.
 * Ideal for small-to-medium ingestion rates; large-scale setups can offload evicted groups to persistent stores.
 *
 * <h2>Example Usage</h2>
 * <pre>
 *   String[] stream = {"listen", "silent", "hello", "enlist", "ohlle", "world", "dlrow"};
 *   for (String word : stream) {
 *       ReactiveAnagramGrouper.onWord(word);
 *   }
 *   Thread.sleep(1000);
 *   ReactiveAnagramGrouper.printGroups();
 *   ReactiveAnagramGrouper.shutdown();
 * </pre>
 *
 */
public class ReactiveAnagramGrouper {
    private static final int MAX_GROUPS = 1000;
    private static final ConcurrentHashMap<String, CopyOnWriteArrayList<String>> groups = new ConcurrentHashMap<>();
    private static final AtomicInteger totalWords = new AtomicInteger(0);
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * Submits a word to the thread pool for asynchronous processing.
     *
     * @param word the incoming word to be grouped
     */
    public static void onWord(String word) {
        executor.submit(() -> process(word));
    }

    /**
     * Normalizes and classifies the word into its respective anagram group.
     * <p>
     * Removes punctuation, converts to lowercase, computes a frequency signature,
     * and places it into the corresponding group.
     * </p>
     *
     * @param word the word to process
     */
    private static void process(String word) {
        if (word == null || word.isBlank()) return;

        String normalized = word.toLowerCase().replaceAll("[^\\p{L}]", "");
        String key = getSignature(normalized);

        groups.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).add(word);
        totalWords.incrementAndGet();

        if (groups.size() > MAX_GROUPS) {
            evictOneGroup();
        }
    }

    /**
     * Generates a deterministic frequency-based signature for a given word.
     * Example: "listen" and "silent" → same signature.
     *
     * @param s input word
     * @return canonical frequency key
     */
    private static String getSignature(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c))
                freq[c - 'a']++;
        }
        return Arrays.toString(freq);
    }

    /**
     * Evicts the oldest group when MAX_GROUPS threshold is exceeded.
     * Simple heuristic (non-LRU).
     */
    private static void evictOneGroup() {
        String oldest = groups.keys().nextElement();
        System.out.println("[Evicting] " + groups.get(oldest));
        groups.remove(oldest);
    }

    /**
     * Prints all current anagram groups and total processed count.
     */
    public static void printGroups() {
        groups.forEach((k, v) -> System.out.println(v));
        System.out.println("Total words processed: " + totalWords.get());
    }

    /**
     * Gracefully shuts down the executor service.
     */
    public static void shutdown() {
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        String[] stream = {"listen", "silent", "hello", "enlist", "ohlle", "world", "dlrow"};

        for (String word : stream) {
            onWord(word);
        }

        Thread.sleep(1000); // Allow async processing

        System.out.println("\nFinal Groups:");
        printGroups();

        shutdown();
    }
}
