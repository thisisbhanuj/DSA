package main.ds.strings;

/**
 * RollingHash implements a simple Rabin-Karp style rolling hash algorithm for substring search.
 *
 * <p>
 * Key idea: maintain a hash of a fixed-length sliding window, update efficiently when window moves:
 * <ul>
 *   <li>Drop outgoing character contribution</li>
 *   <li>Shift window (multiply by BASE)</li>
 *   <li>Add incoming character contribution</li>
 *   <li>Modulo operation to avoid overflow</li>
 * </ul>
 * </p>
 *
 * <p>
 * Heuristics for BASE and MOD:
 * <ul>
 *   <li>BASE: small prime near charset size (31 for lowercase, 131 for ASCII)</li>
 *   <li>MOD: large prime ≤ 1e9+7 to reduce collisions and avoid overflow</li>
 * </ul>
 * </p>
 *
 * <p>
 * Time Complexity:
 * <ul>
 *   <li>Precomputation of initial hashes: O(m), where m = pattern length</li>
 *   <li>Sliding window: O(n - m), where n = text length</li>
 *   <li>Substring comparison on hash match (worst case): O(m)</li>
 *   <li>Total worst-case: O((n - m + 1) * m) if every window has hash collision; average case ~ O(n + m)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Space Complexity:
 * <ul>
 *   <li>O(1) extra space for hash values and power calculation</li>
 *   <li>O(m) if storing or comparing substrings explicitly</li>
 * </ul>
 * </p>
 */
public class RollingHash {
    // -----------------------------------------------------------------------------------
    //    PRIME BASE spreads characters apart in value space, reducing accidental collisions.
    //
    //    ⚙️ Choosing BASE
    //
    //    Heuristics:
    //    - Pick a small prime or pseudo-prime near character set size (26, 52, 128, 256).
    //    - Most implementations use 31 or 53 for lowercase/uppercase alphabets.
    //    - If you include all ASCII → 131 or 257 works well.
    //    - Larger base = fewer collisions but higher overflow risk before mod.
    //
    //    So:
    //    BASE = 31;   // for lowercase letters (common choice)
    //    BASE = 131;  // for general ASCII
    //
    //    Reasoning: 31 is prime, cheap to multiply, spreads hashes well for English text.
    // -----------------------------------------------------------------------------------
    private static final int BASE = 31;
    // -----------------------------------------------------------------------------------
    //    MOD keeps it in long range.
    //
    //    ⚙️ Choosing MOD
    //
    //    We need:
    //    - A large prime to minimize collisions and wrap safely in long.
    //      ≤ 1e9+7 (1_000_000_007) fits in 64-bit and is well-tested.
    //    - Alternate safe primes: 1_000_000_009, 998_244_353, or near 2^61 - 1 for 64-bit ops.
    // -----------------------------------------------------------------------------------
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        String text = "ababc";
        String pattern = "ab";

        int n = text.length();
        int m = pattern.length();

        long patternHash = 0, windowHash = 0, power = 1;

        // compute BASE^(m-1)
        for (int i = 0; i < m - 1; i++) power = (power * BASE) % MOD;

        // compute initial hashes
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + (text.charAt(i) - 'a' + 1)) % MOD; // pattern hash using text for demo
            windowHash = (windowHash * BASE + (text.charAt(i) - 'a' + 1)) % MOD;
        }

        System.out.println("Initial patternHash = " + patternHash);
        System.out.println("Initial windowHash = " + windowHash);

        for (int i = 0; i <= n - m; i++) {
            final String substring = text.substring(i, i + m);
            System.out.println("\nWindow [" + i + "," + (i + m - 1) + "] = '" + substring + "'");
            System.out.println("Current windowHash = " + windowHash);

            if (windowHash == patternHash) {
                System.out.println("Hash match! Checking substring equality...");
                if (substring.equals(pattern)) {
                    System.out.println("Substring matches at index " + i);
                } else {
                    System.out.println("False positive due to hash collision.");
                }
            }

            // slide window
            if (i < n - m) {
                long outgoing = (text.charAt(i) - 'a' + 1) * power % MOD;
                windowHash = (windowHash - outgoing + MOD) % MOD;
                windowHash = (windowHash * BASE + (text.charAt(i + m) - 'a' + 1)) % MOD;
                System.out.println("After sliding: windowHash = " + windowHash);
            }
        }
    }
}
