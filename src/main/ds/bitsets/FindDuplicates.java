package main.ds.bitsets;

import java.util.*;

public class FindDuplicates {
        public static void main(String[] args){
            int[] array = {1, 2, 3, 4, 5, 6, 7, 1, 8};
            boolean result = findDuplicates(array);
            System.out.println("Is Duplicate found? : " + result);
        }

      static boolean findDuplicates(int[] array){
            OptionalInt optMax = Arrays.stream(array).max();
            if (optMax.isEmpty()) return false;

            BitSet bitSet = new BitSet(optMax.getAsInt() + 1);

            for (int value: array){
                if (bitSet.get(value)) return  true;
                bitSet.set(value);
            }

            return false;
      }

    public boolean hybridSolution(int[] nums) {
        if (nums.length <= 1) return false;

        // Find min and max
        int min = Arrays.stream(nums).min().getAsInt();
        int max = Arrays.stream(nums).max().getAsInt();

        // Decide approach: BitSet if range is small enough
        long range = (long) max - (long) min + 1;

        if (range <= 1_000_000_0L) { // adjust threshold as needed
            // Use BitSet for dense, bounded range
            BitSet bitSet = new BitSet((int) range);
            for (int num : nums) {
                int shifted = num - min;
                if (bitSet.get(shifted)) return true;
                bitSet.set(shifted);
            }
            return false;
        } else {
            // Use HashSet for huge/sparse ranges
            Set<Integer> seen = new HashSet<>();
            for (int num : nums) {
                if (seen.contains(num)) return true;
                seen.add(num);
            }
            return false;
        }
    }
}

/*
 * ===================== BITSET EXPLAINED =====================
 *
 * What is a BitSet?
 * -----------------
 * - Think of BitSet as an ultra-compact array of bits (0 or 1).
 * - At index i, the bit can be ON (1) or OFF (0).
 * - Unlike int[] (4 bytes per int), BitSet uses just 1 bit per flag.
 *
 * Key operations:
 * - bitSet.set(5) → marks index 5 as seen.
 * - bitSet.get(5) → checks if index 5 was already marked.
 *
 * Why calculate the max?
 * ---------------------
 * - BitSet internally allocates bits up to the largest index you touch.
 * - If numbers go up to N, BitSet needs N+1 slots (0…N).
 *
 * Example:
 * nums = [1,3,7]
 * Initial bits: 0 0 0 0 0 0 0 0
 * set(1) → 0 1 0 0 0 0 0 0
 * set(3) → 0 1 0 1 0 0 0 0
 * set(7) → 0 1 0 1 0 0 0 1
 * Trying set(3) again → get(3) = true → duplicate found
 *
 * Is it like an array?
 * -------------------
 * - Yes, but ultra-compact.
 * - boolean[] uses 1 byte per flag (8× bigger), BitSet packs 8 flags per byte.
 * - Essentially: an array of booleans compressed into bits.
 *
 * Practical Use Cases:
 * -------------------
 * 1. Duplicate detection in bounded ranges
 * 2. Missing number problems
 * 3. Prime sieves (Sieve of Eratosthenes)
 * 4. Large-scale membership checks (1B+ IDs)
 * 5. Subset / bitmask DP
 * 6. Graph adjacency compact representation
 *
 * Interview Tip:
 * - Use BitSet when memory is tight and the value range is bounded.
 * - Shows optimization mindset and knowledge of low-level data structures.
 * =============================================================
 */
