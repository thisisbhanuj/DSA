package main.ds.leetcode.easy;

class SingleNumber {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;  // XOR with each number
        }
        return result;
    }
}
/**
 * Sure! Let's break down the expression `0 ^ 4 ^ 1 ^ 2 ^ 1 ^ 2 = 4` step by step using the properties of XOR.
 *
 * ### Key Properties of XOR:
 * 1. **Identity**: \( x \oplus 0 = x \) (XOR of a number with 0 is the number itself).
 * 2. **Self-cancellation**: \( x \oplus x = 0 \) (XOR of a number with itself is 0).
 * 3. **Commutativity and Associativity**: The order of operations doesn't matter, meaning we can rearrange the terms freely.
 *
 * ### Step-by-Step XOR:
 *
 * #### 1. Start with `result = 0`
 * - `result = 0`
 *
 * #### 2. XOR with 4:
 * - \( 0 \oplus 4 = 4 \) (because \( x \oplus 0 = x \)).
 * - `result = 4`
 *
 * #### 3. XOR with 1:
 * - \( 4 \oplus 1 = 5 \) (binary representation of 4 is `100`, 1 is `001`, XOR them gives `101` which is 5).
 * - `result = 5`
 *
 * #### 4. XOR with 2:
 * - \( 5 \oplus 2 = 7 \) (binary representation of 5 is `101`, 2 is `010`, XOR them gives `111` which is 7).
 * - `result = 7`
 *
 * #### 5. XOR with 1:
 * - \( 7 \oplus 1 = 6 \) (binary representation of 7 is `111`, 1 is `001`, XOR them gives `110` which is 6).
 * - `result = 6`
 *
 * #### 6. XOR with 2:
 * - \( 6 \oplus 2 = 4 \) (binary representation of 6 is `110`, 2 is `010`, XOR them gives `100` which is 4).
 * - `result = 4`
 *
 * ### Final Result:
 * - After XORing all the numbers, we are left with `4`, which is the unique number that appears only once in the array.
 *
 * ### Why This Works:
 * - All numbers that appear twice cancel out because \( x \oplus x = 0 \).
 * - The only number that doesn't get canceled out is the one that appears once, leaving it as the final result.
 *
 * For the example `nums = [4, 1, 2, 1, 2]`, we cancel out the duplicates (`1 ^ 1 = 0`, `2 ^ 2 = 0`), and the remaining number is 4, which is the answer.
 */