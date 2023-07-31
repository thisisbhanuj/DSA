package ds.arrays;

/*
* If you have an array with adjacent duplicate elements,
* you can treat the array as a linked list
* where each element points to the index indicated by its value.
* It helps identify the adjacent duplicate element ONLY,
* as the cycle formed will indicate the presence of a duplicate.
*        It is NOT a valid cycle : {1, 3, 4, 5, 3}
*/
public class AdjacentDuplicateFinder {
    public static int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];

        // Move slow and fast pointers until they meet or go beyond array bounds
        do {
            slow = nums[slow];
            if (fast >= nums.length || nums[fast] >= nums.length) {
                return -1; // No cycle, return -1 or handle it accordingly
            }
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Move slow pointer to the start and keep fast pointer at meeting point
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        // Return the duplicate element
        return slow;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 5, 5}; // Example array with a duplicate element
        int duplicate = findDuplicate(nums);
        System.out.println("Duplicate element: " + duplicate);
    }
}
