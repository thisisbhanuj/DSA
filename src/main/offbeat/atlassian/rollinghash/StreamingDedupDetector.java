package main.offbeat.atlassian.rollinghash;

import java.io.*;
import java.util.*;
// ------------------------------------------------------------------------------------------
// 🧩 Question:
//      “How would you detect duplicate files efficiently?”
// ------------------------------------------------------------------------------------------
//🎯 Answer (Battle-Mode Style)
//  I’d use a rolling-hash–based approach inspired by Rabin-Karp.
//  - Each file is processed as a byte stream. Instead of hashing the entire file, I slide a fixed-size window (say 4 KB) and compute a rolling hash per chunk. The key advantage is that each new hash can be updated in O(1) time — subtract the outgoing byte, add the incoming one — so the overall runtime is linear, O(N).
//  - I store these chunk hashes in a map from hash → chunk → file paths. That lets me detect shared chunks across files without reading everything into memory.
//  - To prevent false positives, I verify actual bytes when two hashes match — either a direct string compare or a secondary strong hash like SHA-256.
//  - Space usage scales with the number of unique chunks, roughly O(N / K).
//  - The same logic applies to streaming data — as bytes arrive, the rolling window continuously updates hashes.
//    It’s exactly how Dropbox or Git deduplication layers operate.
// ------------------------------------------------------------------------------------------
/**
 * StreamingDedupDetector
 * ----------------------
 * Detects duplicate and partially overlapping files based on content hashes.
 *
 * This implementation uses a **rolling hash** (Rabin-Karp style) to process files
 * as a continuous byte stream without loading entire files into memory.
 *
 * Features:
 *  - Memory-efficient (streaming 4KB at a time)
 *  - Detects full-file duplicates (identical content)
 *  - Detects partial overlaps (shared 4KB+ sequences)
 *  - Collision-safe via (hash + chunk-content) validation
 *
 * Example use-case: large-scale deduplication or sync tools (rsync, ZFS, etc.)
 *
 * Time Complexity:
 *  - O(N) per file, where N = file size in bytes
 *    Each byte contributes O(1) work to rolling hash.
 *
 * Space Complexity:
 *  - O(U) where U = number of unique chunks across all files
 *    Each unique chunk entry stores: hash + chunk string + file references
 *
 * Note: For binary files, replace `new String(raw)` with Base64 encoding.
 */
public class StreamingDedupDetector {

    private static final long BASE = 257;          // Base multiplier for rolling hash
    private static final long MOD = 1_000_000_009; // Large prime modulus to reduce collisions
    private static final int CHUNK_SIZE = 4096;    // Chunk size (4KB typical for file dedup)

    // Hash → Map<chunkContent, List<fileNames>>
    private final Map<Long, Map<String, List<String>>> globalChunkMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Map<String, File> files = Map.of(
                "a.txt", new File("a.txt"),
                "b.txt", new File("b.txt"),
                "c.txt", new File("c.txt")
        );

        StreamingDedupDetector detector = new StreamingDedupDetector();
        detector.detect(files);
    }

    /**
     * Main deduplication pipeline.
     * 1. Stream each file and compute rolling hashes.
     * 2. Store chunk fingerprints and detect overlaps.
     * 3. Print full-file duplicates and partial overlaps.
     */
    public void detect(Map<String, File> files) throws IOException {
        Map<String, List<Long>> fileHashes = new HashMap<>();

        for (Map.Entry<String, File> entry : files.entrySet()) {
            String fileName = entry.getKey();
            File file = entry.getValue();

            System.out.println("\nProcessing file: " + fileName);
            List<Long> hashes = streamAndHash(fileName, file);
            fileHashes.put(fileName, hashes);
        }

        System.out.println("\n=== FULL FILE DUPLICATES ===");
        detectFullFileDuplicates(fileHashes);

        System.out.println("\n=== PARTIAL OVERLAPS ===");
        detectPartialOverlaps();
    }

    /**
     * Streams a file and computes rolling hashes for every 4KB window.
     *
     * @param fileName - logical name of the file (for tracking)
     * @param file - file to process
     * @return list of rolling hash values for the file
     * @throws IOException - if file cannot be read
     *
     * Time Complexity: O(N)
     * Space Complexity: O(CHUNK_SIZE)
     */
    private List<Long> streamAndHash(String fileName, File file) throws IOException {
        List<Long> hashes = new ArrayList<>();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {

            byte[] buffer = new byte[CHUNK_SIZE];
            int bytesRead;
            LinkedList<Byte> window = new LinkedList<>();

            long hash = 0;
            long power = 1;

            // Precompute BASE^(CHUNK_SIZE-1)
            for (int i = 0; i < CHUNK_SIZE - 1; i++) {
                power = (power * BASE) % MOD;
            }

            // Stream through file byte-by-byte
            while ((bytesRead = bis.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    byte b = buffer[i];

                    if (window.size() < CHUNK_SIZE) {
                        // Filling the initial window
                        window.add(b);
                        hash = (hash * BASE + (b & 0xFF)) % MOD;
                        if (window.size() == CHUNK_SIZE)
                            recordChunk(fileName, new ArrayList<>(window), hash);
                    } else {
                        // Rolling window: remove oldest byte, add new byte
                        byte outgoing = window.removeFirst();
                        window.add(b);

                        hash = (hash - ((outgoing & 0xFF) * power % MOD) + MOD) % MOD;
                        hash = (hash * BASE + (b & 0xFF)) % MOD;

                        recordChunk(fileName, new ArrayList<>(window), hash);
                    }
                    hashes.add(hash);
                }
            }
        }
        return hashes;
    }

    /**
     * Records the chunk hash and associates it with the file.
     * Also handles collision safety by storing both hash and raw content.
     *
     * @param fileName - file containing the chunk
     * @param windowBytes - list of bytes in the current chunk window
     * @param hash - rolling hash for the chunk
     */
    private void recordChunk(String fileName, List<Byte> windowBytes, long hash) {
        byte[] raw = new byte[windowBytes.size()];
        for (int i = 0; i < windowBytes.size(); i++) {
            raw[i] = windowBytes.get(i);
        }

        // For binary-safe implementation, use Base64 here
        String chunkKey = new String(raw);

        globalChunkMap
                .computeIfAbsent(hash, k -> new HashMap<>())
                .computeIfAbsent(chunkKey, k -> new ArrayList<>())
                .add(fileName);
    }

    /**
     * Detects full-file duplicates by comparing sequence of hashes per file.
     *
     * @param fileHashes - map of file → list of rolling hashes
     *
     * Time Complexity: O(F * N)
     * Space Complexity: O(F * N)
     *   where F = number of files, N = chunks per file
     */
    private void detectFullFileDuplicates(Map<String, List<Long>> fileHashes) {
        Map<String, List<String>> seqMap = new HashMap<>();

        for (Map.Entry<String, List<Long>> entry : fileHashes.entrySet()) {
            String seqKey = entry.getValue().toString();
            seqMap.computeIfAbsent(seqKey, k -> new ArrayList<>()).add(entry.getKey());
        }

        seqMap.values().stream()
                .filter(list -> list.size() > 1)
                .forEach(list -> System.out.println("Duplicate files: " + list));
    }

    /**
     * Detects partial overlaps — i.e., any two files that share a chunk.
     *
     * Time Complexity: O(U + R)
     *   U = number of unique chunks, R = number of repeated chunks
     *
     * Space Complexity: O(U)
     */
    private void detectPartialOverlaps() {
        Map<String, Set<String>> overlaps = new HashMap<>();

        for (Map<String, List<String>> innerMap : globalChunkMap.values()) {
            for (List<String> fileList : innerMap.values()) {
                if (fileList.size() > 1) {
                    for (String f1 : fileList) {
                        overlaps.computeIfAbsent(f1, k -> new HashSet<>()).addAll(fileList);
                        overlaps.get(f1).remove(f1); // Remove self
                    }
                }
            }
        }

        if (overlaps.isEmpty()) {
            System.out.println("No partial overlaps found.");
        } else {
            overlaps.forEach((file, set) ->
                    System.out.println(file + " overlaps with " + set));
        }
    }
}
