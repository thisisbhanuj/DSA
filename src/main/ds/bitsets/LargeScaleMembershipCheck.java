package main.ds.bitsets;

import java.util.BitSet;

public class LargeScaleMembershipCheck {

    public static void main(String[] args) {
        // Simulate 1 billion user IDs in a small example
        // For demonstration, we'll use a smaller range
        int maxUserId = 1000;
        int[] userIds = {1, 5, 100, 999, 500};

        // Create BitSet with size = maxUserId + 1
        BitSet seen = new BitSet(maxUserId + 1);

        // Mark all existing IDs as seen
        for (int id : userIds) {
            seen.set(id); // O(1)
        }

        // New incoming IDs to check
        int newId1 = 100; // duplicate
        int newId2 = 250; // new

        System.out.println("Is " + newId1 + " already seen? " + isSeen(seen, newId1));
        System.out.println("Is " + newId2 + " already seen? " + isSeen(seen, newId2));
    }

    // Check if a user ID has already been seen
    static boolean isSeen(BitSet seen, int userId) {
        if (seen.get(userId)) {
            return true;  // duplicate
        } else {
            seen.set(userId); // mark as seen
            return false;
        }
    }
}
