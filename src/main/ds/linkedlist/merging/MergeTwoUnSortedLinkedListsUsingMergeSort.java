package main.ds.linkedlist.merging;

import main.ds.linkedlist.SingleLinkedListNode;

/* Time complexity is O(n log(n)).
 * This is because the merge sort algorithm takes O(n log(n)) time to sort the merged linked list.
 *
 * Space complexity is O(n).
 * Due to the recursion stack used by the merge sort algorithm. Additionally, the linked list is modified in place, so no extra list is created.
*/
public class MergeTwoUnSortedLinkedListsUsingMergeSort {

    private static SingleLinkedListNode merge(SingleLinkedListNode head1, SingleLinkedListNode head2) {
        // Why Use Multiple Pointers :
        /*
         * The use of multiple pointers, specifically the `dummy` and `current` pointers, in the code is a common technique in linked list problems.
         * Here's the logic behind it:
         *
         * ### **1. `dummy` Pointer:**
         * - **Purpose:** The `dummy` node is a placeholder node that simplifies the merging process. It acts as a starting point for the merged linked list.
         * - **Why Use It:**
         *   - Without the dummy node, you would have to handle special cases separately, such as initializing the head of the new linked list and dealing with an empty list.
         *   - By using a dummy node, you can always append new nodes to `current.next` without worrying about whether you're at the beginning of the list or not.
         *   - Once the merging is complete, the actual head of the merged list is `dummy.next`.
         *
         * ### **2. `current` Pointer:**
         * - **Purpose:** The `current` pointer is used to keep track of the last node in the merged list.
         * - **Why Use It:**
         *   - As you merge nodes from the two input lists, `current` points to the last node in the merged list. This allows you to append the next node by setting `current.next`.
         *   - After appending a node, `current` is moved forward to the newly appended node (`current = current.next`), ensuring that it always points to the last node in the merged list.
         *
         * ### **Why Use Multiple Pointers (`dummy` and `current`):**
         * - **Simplifies Edge Cases:** The `dummy` node ensures that the head of the merged list is always easily accessible via `dummy.next`. This avoids the need to treat the first node as a special case.
         * - **Efficient List Building:** The `current` pointer allows you to build the merged list by sequentially adding nodes. It always knows where the end of the merged list is, so you can efficiently append new nodes.
         * - **Cleaner Code:** Using `dummy` and `current` together results in cleaner and more maintainable code, as it reduces the need for conditional checks or special handling for the head node.
         *
         * ### Example:
         * Consider merging two lists `[2, 5, 9]` and `[1, 4, 50]`:
         *
         * 1. **Initial Setup:**
         *    - `dummy = -1 -> null`
         *    - `current = dummy`
         *
         * 2. **First Iteration:**
         *    - Compare `2` and `1`.
         *    - Since `1` is smaller, `current.next` points to `1`.
         *    - Move `current` forward: `current = 1`.
         *
         * 3. **Subsequent Iterations:**
         *    - Continue comparing and merging the lists.
         *    - `dummy` remains as the starting point, while `current` keeps track of the last node added.
         *
         * 4. **Final List:**
         *    - After merging all nodes, the list looks like `-1 -> 1 -> 2 -> 4 -> 5 -> 9 -> 50`.
         *    - Return `dummy.next`, which is the head of the merged list (`1 -> 2 -> 4 -> 5 -> 9 -> 50`).
         *
         * Using `dummy` and `current` together allows for a straightforward and robust implementation of the merge operation.
         */
        SingleLinkedListNode dummy = new SingleLinkedListNode(-1);
        SingleLinkedListNode current = dummy;

        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                current.next = head1;
                head1 = head1.next;
            } else {
                current.next = head2;
                head2 = head2.next;
            }
            current = current.next;
        }

        if (head1 != null) {
            current.next = head1;
        }

        if (head2 != null) {
            current.next = head2;
        }

        return dummy.next;
    }

    private static SingleLinkedListNode mergeSort(SingleLinkedListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        SingleLinkedListNode middle = getMiddle(head);
        SingleLinkedListNode nextOfMiddle = middle.next;
        middle.next = null;

        SingleLinkedListNode left = mergeSort(head);
        SingleLinkedListNode right = mergeSort(nextOfMiddle);

        return merge(left, right);
    }

    private static SingleLinkedListNode getMiddle(SingleLinkedListNode head) {
        if (head == null) {
            return null;
        }

        SingleLinkedListNode slow = head;
        SingleLinkedListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public static SingleLinkedListNode mergeUnSortedList(SingleLinkedListNode head1, SingleLinkedListNode head2) {
        if (head1 == null && head2 == null) return null;
        if (head1 == null) return head2;
        if (head2 == null) return head1;

        SingleLinkedListNode mergedList = merge(head1, head2);
        System.out.println("Merged Lists as : ");
        SingleLinkedListNode.printList(mergedList);
        return mergeSort(mergedList);
    }

    public static void main(String[] args) {
        // Create sample linked lists
        SingleLinkedListNode head11 = new SingleLinkedListNode(9);
        SingleLinkedListNode node21 = new SingleLinkedListNode(2);
        SingleLinkedListNode node31 = new SingleLinkedListNode(5);

        SingleLinkedListNode head22 = new SingleLinkedListNode(1);
        SingleLinkedListNode node22 = new SingleLinkedListNode(4);
        SingleLinkedListNode node32 = new SingleLinkedListNode(50);

        head11.next = node21;
        node21.next = node31;

        head22.next = node22;
        node22.next = node32;

        if (head11 != null && head22 != null) {
            SingleLinkedListNode mergedList = mergeUnSortedList(head11, head22);

            System.out.println("Sorted Merged List: ");
            SingleLinkedListNode.printList(mergedList);
        }
    }
}
