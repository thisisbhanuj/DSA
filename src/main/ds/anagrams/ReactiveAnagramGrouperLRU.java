package main.ds.anagrams;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * ReactiveAnagramGrouper with LRU eviction.
 *
 * <p>Eviction is based on **access order**: least-recently-used group
 * is removed automatically when MAX_GROUPS is exceeded.</p>
 *
 * <h2>Thread Safety</h2>
 * Uses synchronized blocks around LRU structure, while metrics and word ingestion
 * remain asynchronous.
 */
public class ReactiveAnagramGrouperLRU {

    private static final int MAX_GROUPS = 5;

    // === LRU structure: synchronized LinkedHashMap ===
    private static final Map<String, List<String>> lruGroups = Collections.synchronizedMap(
            new LinkedHashMap<>(16, 0.75f, true) { // accessOrder = true
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, List<String>> eldest) {
                    boolean remove = size() > MAX_GROUPS;
                    if (remove) {
                        System.out.println("[LRU Evicting] " + eldest.getValue());
                    }
                    return remove;
                }
            }
    );

    private static final ExecutorService workerPool = Executors.newFixedThreadPool(4);
    private static final LongAdder totalWords = new LongAdder();
    private static final LongAdder wordsThisSecond = new LongAdder();
    private static final ScheduledExecutorService metricsScheduler = Executors.newScheduledThreadPool(1);

    static {
        metricsScheduler.scheduleAtFixedRate(() -> {
            long rate = wordsThisSecond.sumThenReset();
            System.out.printf("[Metrics] Words/sec = %-5d | Total = %-5d | Groups = %-3d%n",
                    rate, totalWords.sum(), lruGroups.size());
        }, 1, 1, TimeUnit.SECONDS);
    }

    public static void onWord(String word) {
        workerPool.submit(() -> process(word));
    }

    private static void process(String word) {
        if (word == null || word.isBlank()) return;

        String normalized = word.toLowerCase().replaceAll("[^\\p{L}]", "");
        String key = getSignature(normalized);

        synchronized (lruGroups) {
            lruGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }

        totalWords.increment();
        wordsThisSecond.increment();
    }

    private static String getSignature(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c))
                freq[c - 'a']++;
        }
        return Arrays.toString(freq);
    }

    public static void printGroups() {
        synchronized (lruGroups) {
            System.out.println("\n==== LRU Groups ====");
            lruGroups.forEach((k, v) -> System.out.println(v));
            System.out.println("Total words processed: " + totalWords.sum());
        }
    }

    public static void shutdown() {
        workerPool.shutdown();
        metricsScheduler.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        String[] stream = {"listen", "silent", "enlist", "hello", "ohlle", "world", "dlrow", "abc", "cab", "bac"};

        for (String word : stream) {
            onWord(word);
        }

        Thread.sleep(5000); // allow async processing

        printGroups();
        shutdown();
    }
}
