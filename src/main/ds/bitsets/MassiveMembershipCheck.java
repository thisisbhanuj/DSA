package main.ds.bitsets;

/*
 * ===============================================================
 * Problem: Large-Scale Membership Check with BitSet Segmentation
 * ===============================================================
 *
 * Description:
 * -----------------
 * Given a massive range of user IDs (potentially billions or trillions),
 * efficiently check whether a new incoming ID has already been seen.
 *
 * Constraints:
 * - User IDs are long integers.
 * - Total ID space is extremely large; a single BitSet would be too big.
 *
 * Approach:
 * -----------------
 * 1. Segment the ID space into manageable chunks.
 *    - Each chunk contains a fixed number of IDs (e.g., 1 billion IDs per chunk).
 *    - Each chunk is represented by a BitSet of size CHUNK_SIZE.
 * 2. Map an incoming ID to:
 *    - The corresponding chunk index: userId / CHUNK_SIZE
 *    - The position within that chunk: userId % CHUNK_SIZE
 * 3. Use a HashMap<Integer, BitSet> to store only the chunks that have at least one ID.
 *    - Lazy allocation: create a BitSet only when the first ID in that chunk appears.
 * 4. Lookup or mark an ID using get() and set() operations on the BitSet.
 *
 * Benefits:
 * -----------------
 * - Memory-efficient: only allocate BitSets for chunks that have IDs.
 * - O(1) lookup and insertion per ID.
 * - Scalable to billions/trillions of IDs.
 *
 * Interview Tip:
 * -----------------
 * - Shows understanding of memory-efficient data structures and trade-offs.
 * - Can extend to Bloom Filters for probabilistic membership checks on even larger scales.
 * - Useful in real-world scenarios like ad tech, fraud detection and analytics pipelines.
 *
 * BitSet → perfect for bounded, dense, non-negative ranges (like [0, 1_000_000]).
 * HashSet → safe for unbounded ranges or negative numbers.
 * Always check input constraints before deciding memory-optimized data structure.
 */

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class MassiveMembershipCheck {

    // Number of IDs per chunk (1 billion)
    static final int CHUNK_SIZE = 1_000_000_000;

    // Map to hold chunk index -> BitSet
    Map<Integer, BitSet> bitSets = new HashMap<>();

    /**
     * Mark a user ID as seen.
     * @param userId The ID to mark
     */
    public void markSeen(long userId) {
        int chunk = (int)(userId / CHUNK_SIZE);
        int index = (int)(userId % CHUNK_SIZE);

        // Lazily allocate BitSet for the chunk
        bitSets.computeIfAbsent(chunk, k -> new BitSet(CHUNK_SIZE))
                .set(index);
    }

    /**
     * Check whether a user ID has already been seen.
     * @param userId The ID to check
     * @return true if seen, false otherwise
     */
    public boolean isSeen(long userId) {
        int chunk = (int)(userId / CHUNK_SIZE);
        int index = (int)(userId % CHUNK_SIZE);

        BitSet bs = bitSets.get(chunk);
        return bs != null && bs.get(index);
    }

    /**
     * Main
     */
    public static void main(String[] args) {
        MassiveMembershipCheck checker = new MassiveMembershipCheck();

        // Simulated user IDs
        long[] userIds = {5L, 1_500_000_000L, 3_000_000_000L};

        // Mark IDs as seen
        for (long id : userIds) checker.markSeen(id);

        // Test new IDs
        System.out.println("Is 5L seen? " + checker.isSeen(5L));                // true
        System.out.println("Is 1_000_000_000L seen? " + checker.isSeen(1_000_000_000L)); // false
        System.out.println("Is 1_500_000_000L seen? " + checker.isSeen(1_500_000_000L)); // true
    }
}
