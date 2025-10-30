package main.offbeat.atlassian.rollinghash;
import java.io.*;
import java.util.*;

/**
 * StreamingFileDuplicateDetector identifies duplicate files or file chunks
 * by computing rolling hashes directly from file streams.
 *
 * <p>This class avoids loading full files into memory, making it suitable for
 * large-scale deduplication (e.g., backups, artifact repositories, or distributed storage).
 * It uses a Rabin–Karp–style rolling hash to fingerprint 4KB chunks of data.
 *
 * <p><b>Concept:</b> Reads each file sequentially, computes rolling hash values for
 * overlapping 4KB windows, and then compares files based on their hash sequences.
 *
 * <p><b>Time Complexity:</b> O(ΣN) — linear in total bytes read across all files.<br>
 * <b>Space Complexity:</b> O(ΣN / CHUNK_SIZE) — proportional to the number of rolling hashes retained.
 *
 * <p>⚙️ Optimized for stream processing — ideal when files cannot fit entirely in memory.
 *
 * @author Weiyi Zhi
 */
public class StreamingFileDuplicateDetector {

    /** Base for polynomial rolling hash. */
    private static final long BASE = 257;

    /** Large prime modulus for hash stabilization. */
    private static final long MOD = 1_000_000_009;

    /** Default chunk size (bytes). */
    private static final int CHUNK_SIZE = 4096;

    /**
     * Demonstrates streaming-based duplicate detection for local files.
     *
     * @param args unused
     * @throws IOException if any file I/O operation fails
     */
    public static void main(String[] args) throws IOException {
        Map<String, File> files = Map.of(
                "a.txt", new File("a.txt"),
                "b.txt", new File("b.txt"),
                "c.txt", new File("c.txt")
        );

        StreamingFileDuplicateDetector detector = new StreamingFileDuplicateDetector();
        detector.detectDuplicates(files);
    }

    /**
     * Detects duplicate files based on their rolling hash sequences.
     *
     * <p>Each file is read as a stream and chunked into overlapping windows.
     * A rolling hash is computed for each chunk, creating a unique sequence signature per file.
     * Files with identical hash sequences are considered full duplicates.
     *
     * @param files mapping of file paths to actual {@link File} objects
     * @throws IOException if reading any file fails
     *
     * <p><b>Time Complexity:</b> O(ΣN) — single pass over all file bytes.<br>
     * <b>Space Complexity:</b> O(ΣN / CHUNK_SIZE) — stores one hash per chunk.
     */
    public void detectDuplicates(Map<String, File> files) throws IOException {
        Map<String, List<Long>> fileChunkHashes = new HashMap<>();

        for (Map.Entry<String, File> entry : files.entrySet()) {
            String path = entry.getKey();
            File file = entry.getValue();

            List<Long> rollingHashes = computeRollingHashes(file);
            fileChunkHashes.put(path, rollingHashes);
        }

        // Detect full-file duplicates by comparing hash sequences
        Map<String, List<String>> fullFileMap = new HashMap<>();
        for (Map.Entry<String, List<Long>> entry : fileChunkHashes.entrySet()) {
            String path = entry.getKey();
            List<Long> hashSeq = entry.getValue();
            String key = hashSeq.toString();
            fullFileMap.computeIfAbsent(key, k -> new ArrayList<>()).add(path);
        }

        System.out.println("Full-file duplicates:");
        fullFileMap.values().stream()
                .filter(paths -> paths.size() > 1)
                .forEach(System.out::println);
    }

    /**
     * Computes rolling hash values for all overlapping 4KB windows within a file stream.
     *
     * <p>This method reads the file incrementally using a {@link BufferedInputStream}
     * to avoid memory bloat. Each byte is processed in O(1) time to update the
     * rolling hash. The hash window slides forward one byte at a time, ensuring
     * that identical content regions produce identical hash sequences.
     *
     * <p>Mathematically:
     * <pre>
     * hash = (s[0]*BASE^(k-1) + s[1]*BASE^(k-2) + ... + s[k-1]) mod MOD
     * </pre>
     * and is updated as:
     * <pre>
     * hash' = ((hash - outgoing*BASE^(k-1))*BASE + incoming) mod MOD
     * </pre>
     *
     * @param file the {@link File} to process
     * @return list of rolling hash values representing overlapping chunks
     * @throws IOException if file reading fails
     *
     * <p><b>Time Complexity:</b> O(N) — one rolling update per byte read.<br>
     * <b>Space Complexity:</b> O(N / CHUNK_SIZE) — list of chunk hashes + a small fixed sliding window.
     */
    private List<Long> computeRollingHashes(File file) throws IOException {
        List<Long> hashes = new ArrayList<>();
        byte[] buffer = new byte[CHUNK_SIZE];
        long power = 1;
        long hash = 0;

        // Precompute BASE^(CHUNK_SIZE-1)
        for (int i = 0; i < CHUNK_SIZE - 1; i++) {
            power = (power * BASE) % MOD;
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            int bytesRead;
            LinkedList<Byte> window = new LinkedList<>();

            while ((bytesRead = bis.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    byte b = buffer[i];

                    if (window.size() < CHUNK_SIZE) {
                        // Fill initial window
                        window.add(b);
                        hash = (hash * BASE + (b & 0xFF)) % MOD;
                        if (window.size() == CHUNK_SIZE) hashes.add(hash);
                    } else {
                        // Rolling hash update
                        byte outgoing = window.removeFirst();
                        window.add(b);

                        hash = (hash - ((outgoing & 0xFF) * power % MOD) + MOD) % MOD;
                        hash = (hash * BASE + (b & 0xFF)) % MOD;

                        hashes.add(hash);
                    }
                }
            }
        }

        return hashes;
    }
}
