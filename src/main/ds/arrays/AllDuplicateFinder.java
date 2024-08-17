package main.ds.arrays;

import java.util.ArrayList;
import java.util.List;

public class AllDuplicateFinder {
    public static List<Integer> findAllDuplicates(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];
        List<Integer> duplicates = new ArrayList<>();

        // Move slow and fast pointers until they meet or go beyond array bounds
        do {
            slow = nums[slow];
            if (fast >= nums.length || nums[fast] >= nums.length) {
                return duplicates; // No cycle, return empty list
            }
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Move slow pointer to the start and keep fast pointer at meeting point
        slow = nums[0];
        while (slow != fast) {
            duplicates.add(slow); // Add current element as a duplicate
            slow = nums[slow];
            fast = nums[fast];
        }

        // Add the last duplicate element (slow)
        duplicates.add(slow);
        return duplicates;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 3, 4, 5, 5, 3};
        List<Integer> duplicates = findAllDuplicates(nums);
        System.out.println("Duplicate elements: " + duplicates);
    }
}
