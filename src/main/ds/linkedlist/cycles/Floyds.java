package main.ds.linkedlist.cycles;

import main.ds.linkedlist.SingleLinkedListNode;

/*
    NOTE : We can use HashSet to keep track of the values traversed,
           but it is going to make space complexity as O(n)
*/
public class Floyds {
    /*
    * Floyd's cycle-finding algorithm.
    * Time complexity is O(n) and the space complexity is O(1)
    */
    private static boolean isCyclical(SingleLinkedListNode current) {
        boolean status = false;
        SingleLinkedListNode slow = current, fast = current;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                status = true;
                return status;
            }
        }

        return status;
    }

    /*
    * To find the node from where a cycle begins in a linked list,
    * you can use Floyd's cycle-finding algorithm,
    * also known as the "tortoise and hare" algorithm.
    */
    private static Integer detectCycleEntry(SingleLinkedListNode head) {
        // Check if the linked list is empty or has only one node
        if (head == null || head.next == null) {
            return null;
        }

        // Initialize slow and fast pointers
        SingleLinkedListNode slow = head;
        SingleLinkedListNode fast = head;

        // Move the slow and fast pointers until they meet
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;  // When slow and fast meet inside the cycle, the position where they meet is not necessarily the start of the cycle.
            }
        }

        // If there is no cycle, return null
        if (fast == null || fast.next == null) {
            return null;
        }

        // *****************************************************************************
        // 1. **Cycle Detection**:
        //   - You first detect the presence of a cycle using two pointers: `slow` and `fast`.
        //   - `slow` moves one step at a time, while `fast` moves two steps.
        //   - If there is a cycle, `slow` and `fast` will eventually meet inside the cycle.
        //
        //2. **Understanding the Meeting Point**:
        //   - When `slow` and `fast` meet inside the cycle, the position where they meet is not necessarily the start of the cycle.
        //   - Let's denote:
        //     - `L`: The distance from the head of the list to the start of the cycle.
        //     - `C`: The length of the cycle.
        //     - `x`: The distance from the start of the cycle to the meeting point.
        //
        //   - By the time `slow` and `fast` meet, the `slow` pointer has traveled `L + x` steps, and the `fast` pointer has traveled `2(L + x)` steps.
        //     Because `fast` is moving twice as fast, the extra distance traveled by `fast` equals one complete cycle (i.e., `C`).
        //
        //   Therefore:
        //
        //   2(L + x) = L + x + nC {(where n is the number of cycles completed by fast)}
        //
        //   Simplifying this:
        //   L + x = nC
        //
        //   This equation means that by the time `slow` and `fast` meet, the distance `L + x` is some multiple of the cycle length.
        //
        //3. **Resetting the Slow Pointer**:
        //   - Now, reset the `slow` pointer to the head of the linked list, while keeping `fast` at the meeting point.
        //   - Both pointers (`slow` and `fast`) now move one step at a time.
        //
        //4. **Why They Meet at the Cycle Start**:
        //   - When you move `slow` from the head and `fast` from the meeting point at the same pace, `slow` covers the distance `L` (from the head to the cycle start), and `fast` covers the distance `C - x` (from the meeting point to the cycle start).
        //   - Since `L + x = nC`, after `L` steps, `slow` and `fast` will both be at the start of the cycle.
        //
        //5. **Conclusion**:
        //   - The point where `slow` and `fast` meet again is the start of the cycle.
        //
        //  ### **Example Walkthrough**
        //  Let's go through an example to make this more concrete.
        //
        //- Consider a linked list: `1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> 4` (cycle starts at node `4`).
        //- **Cycle Entry Point** is node `4`.
        //
        //- **Cycle Detection**:
        //  - `slow` and `fast` pointers start at node `1`.
        //  - Eventually, they meet inside the cycle at some node (let's say node `7`).
        //
        //- **Cycle Entry Point Detection**:
        //  - Reset `slow` to the head (`1`), and `fast` remains at `7`.
        //  - Both `slow` and `fast` move one step at a time:
        //    - `slow` moves: `1 -> 2 -> 3 -> 4`
        //    - `fast` moves: `7 -> 8 -> 9 -> 10 -> 4`
        //    - They meet at node `4`, the start of the cycle
        //
        //   IMPORTANT:
        //   *********
        //   slow does not "stay" at 4 after reaching there (from above sequence)
        //   Instead, it keeps moving along the list one step at a time, just like fast.
        //   Both pointers move simultaneously until they converge at the start of the cycle.
        //   Rememeber, L = nC - x
        // *****************************************************************************
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow.val;
    }

    public static void main(String[] args) {
        SingleLinkedListNode head = new SingleLinkedListNode(1);
        SingleLinkedListNode node2 = new SingleLinkedListNode(2);
        SingleLinkedListNode node3 = new SingleLinkedListNode(3);
        SingleLinkedListNode node4 = new SingleLinkedListNode(4);
        SingleLinkedListNode node5 = new SingleLinkedListNode(5);

        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node3; // Creates a cycle starting at node3

        boolean found =  isCyclical(head);
        System.out.println("Is LL Cyclical : " + found);

        if (found) {
            int cycleStart = detectCycleEntry(head);
            System.out.println("Cycle starts at node with value: " + cycleStart);
        }
    }
}
