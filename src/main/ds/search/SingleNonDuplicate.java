package main.ds.search;

/**
 * Binary-search solution for finding the single non-duplicate element
 * in a sorted array where every other element appears exactly twice.
 * <hr>
 * <p><b>Invariant:</b>
 * Paired values occupy (even, odd) indices up to the unique element.
 * After that point, alignment shifts and the pairing pattern breaks.
 * <hr>
 * <p><b>Approach:</b>
 * Force the mid-index to be even so (mid, mid+1) is a valid pair boundary.
 * Use the pair’s integrity to decide which half still contains the anomaly.
 * <hr>
 * Time:     O(log n)<br>
 * Space:    O(1)
 * <hr>
 */
public class SingleNonDuplicate {
    public int find(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;

        while (lo < hi) {
            // Midpoint calculation avoids overflow.
            int mid = lo + (hi - lo) / 2;

            /*  Ensure mid is even so (mid, mid+1) forms a proper pair boundary.

                Why use bitwise instead of % 2?
                - Faster (no division, just a single CPU instruction).
                - Standard trick in low-level or perf-critical code.
            */
            if ((mid & 1) == 1) {
                mid--;
            }

            // Before the single element, pairs are correct (nums[even] == nums[even+1]).
            // After the single element, the indexing gets shifted—pairs now start at odd indices.
            // The binary search exploits this invariant.
            if (nums[mid] == nums[mid + 1]) {
                lo = mid + 2;     // Skip this confirmed pair.
            } else {
                hi = mid;         // Break occurs here or earlier.
            }
        }

        // lo == hi → exact position of the unique element.
        return nums[lo];
    }

    public static void main(String[] args) {
        SingleNonDuplicate solver = new SingleNonDuplicate();
        System.out.println(solver.find(new int[]{1,1,2,3,3,4,4})); // prints 2
    }
}
