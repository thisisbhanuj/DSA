package main.offbeat.atlassian.rollinghash;

import java.util.*;

/**
 * RollingChunkDuplicateDetector identifies duplicate content chunks across multiple files
 * using a Rabin-Karp–style rolling hash mechanism.
 *
 * <p>This approach is useful for detecting redundant data between files,
 * similar to how content-defined chunking works in deduplication or backup systems.
 *
 * <p><b>Concept:</b> Treat each file's contents as a string. Split it into overlapping
 * chunks of fixed size {@code CHUNK_SIZE}, compute rolling hashes, and record
 * which files share identical hash+chunk pairs.
 *
 * <p><b>Time Complexity:</b> O(ΣN) — where ΣN is the total number of characters across all files.
 * Each character is processed once per file via a rolling hash update (constant time per character).
 * <br><b>Space Complexity:</b> O(ΣN) — storing all rolling hashes and chunk substrings.
 *
 * <p>⚠️ For large files, consider chunking by sliding windows of 4KB–8KB or using non-overlapping chunks.
 *
 * @author Bhanuj
 */
public class RollingChunkDuplicateDetector {

    /** Base for polynomial rolling hash. */
    private static final long BASE = 257;

    /** Large prime modulus to prevent overflow and reduce collisions. */
    private static final long MOD = 1_000_000_009;

    /** Default chunk size (bytes). Use small for demonstration purposes. */
    private static final int CHUNK_SIZE = 4096;

    /**
     * Demonstrates duplicate detection across file contents.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Map<String, String> files = Map.of(
                "a.txt", "abcdefghij",
                "b.txt", "abcdxyz",
                "c.txt", "abcdefghij"
        );

        RollingChunkDuplicateDetector detector = new RollingChunkDuplicateDetector();
        Map<Long, Map<String, List<String>>> result = detector.findChunkDuplicates(files);

        result.forEach((hash, innerMap) -> {
            System.out.println("HASH: " + hash);
            innerMap.forEach((chunk, paths) -> {
                System.out.println("Chunk: '" + chunk + "' -> Files: " + paths);
            });
        });
    }

    /**
     * Finds duplicate chunks across multiple files using rolling hashes.
     *
     * <p>Each file is processed into overlapping chunks. For each chunk,
     * a rolling hash is computed and mapped to its originating file(s).
     * Collisions are disambiguated by verifying the actual chunk string.
     *
     * @param files mapping of file paths to file contents
     * @return a nested map:
     *         <ul>
     *             <li>Outer key → rolling hash value</li>
     *             <li>Inner key → actual chunk string</li>
     *             <li>Inner value → list of file paths containing that chunk</li>
     *         </ul>
     *
     * <p><b>Time Complexity:</b> O(ΣN) — linear in total file length across all files.<br>
     * <b>Space Complexity:</b> O(ΣN) — for hash and substring mappings.
     */
    public Map<Long, Map<String, List<String>>> findChunkDuplicates(Map<String, String> files) {
        Map<Long, Map<String, List<String>>> chunkMap = new HashMap<>();

        for (Map.Entry<String, String> entry : files.entrySet()) {
            String path = entry.getKey();
            String value = entry.getValue();
            List<Long> rollingHashes = computeRollingHashes(value);

            for (int i = 0; i < rollingHashes.size(); i++) {
                long hash = rollingHashes.get(i);
                String chunk = value.substring(i, i + Math.min(CHUNK_SIZE, value.length() - i));

                chunkMap.computeIfAbsent(hash, k -> new HashMap<>())
                        .computeIfAbsent(chunk, k -> new ArrayList<>())
                        .add(path);
            }
        }

        return chunkMap;
    }

    /**
     * Computes rolling hashes for all overlapping chunks within a string.
     *
     * <p>Implements Rabin–Karp polynomial rolling hash:
     * <pre>
     * hash = (s[0]*BASE^(k-1) + s[1]*BASE^(k-2) + ... + s[k-1]) mod MOD
     * </pre>
     * and efficiently rolls forward by subtracting the outgoing character and
     * adding the new incoming one.
     *
     * @param s input string (file content)
     * @return list of rolling hashes corresponding to each chunk window
     *
     * <p><b>Time Complexity:</b> O(N) — each hash update is O(1).<br>
     * <b>Space Complexity:</b> O(N) — list of hashes for each window.
     */
    private List<Long> computeRollingHashes(String s) {
        List<Long> hashes = new ArrayList<>();
        int n = s.length();
        if (n < 1) return hashes;

        int effectiveChunk = Math.min(CHUNK_SIZE, n);
        long hash = 0;
        long power = 1;

        // Precompute BASE^(chunkSize-1) % MOD
        for (int i = 0; i < effectiveChunk - 1; i++) {
            power = (power * BASE) % MOD;
        }

        // Initial hash
        for (int i = 0; i < effectiveChunk; i++) {
            hash = (hash * BASE + s.charAt(i)) % MOD;
        }
        hashes.add(hash);

        // Rolling window
        for (int i = effectiveChunk; i < n; i++) {
            long outgoing = s.charAt(i - effectiveChunk);
            long incoming = s.charAt(i);

            hash = (hash - (outgoing * power % MOD) + MOD) % MOD; // remove outgoing
            hash = (hash * BASE + incoming) % MOD;                // add incoming
            hashes.add(hash);
        }

        return hashes;
    }
}
