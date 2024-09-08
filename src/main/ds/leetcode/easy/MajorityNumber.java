package main.ds.leetcode.easy;

import java.util.HashMap;

public class MajorityNumber {

    class Solution {
        public int majorityElement(int[] nums) {
            HashMap<Integer, Integer> map = new HashMap<>();

            for (int index = 0; index < nums.length; index++) {
                int count = map.getOrDefault(nums[index], 0) + 1;  // Increment count
                map.put(nums[index], count);

                if (count > nums.length / 2) {
                    return nums[index];  // Return the majority element
                }
            }

            return 0;  // This return is just for syntax; we assume majority always exists.
        }

        /**
         *  The Boyer-Moore Voting Algorithm assumes that the majority element always exists in the array.
         */
        public int majorityElementOptimized(int[] nums) {
            int candidate = 0;  // Placeholder for majority element candidate
            int count = 0;      // Vote count

            for (int num : nums) {
                if (count == 0) {
                    candidate = num;  // New candidate
                }
                count += (num == candidate) ? 1 : -1;  // Increment or decrement count
            }

            return candidate;  // Return the majority element
        }

        /**
         * In case where the majority element might not exist, we would need to verify the
         * candidate by counting how many times it actually appears in the array after finding the candidate.
         */
        public int majorityElementMaybe(int[] nums) {
            int candidate = 0;
            int count = 0;

            // Step 1: Find the candidate using Boyer-Moore Voting Algorithm
            for (int num : nums) {
                if (count == 0) {
                    candidate = num;
                }
                count += (num == candidate) ? 1 : -1;
            }

            // Step 2: Verify if the candidate is truly the majority element
            count = 0;
            for (int num : nums) {
                if (num == candidate) {
                    count++;
                }
            }

            // Check if candidate appears more than n / 2 times
            if (count > nums.length / 2) {
                return candidate;
            } else {
                throw new IllegalArgumentException("No majority element found"); // In case no majority element exists
            }
        }


    }

}
