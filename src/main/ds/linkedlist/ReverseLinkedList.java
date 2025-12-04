package main.ds.linkedlist;

public class ReverseLinkedList {

    /* Space Complexity is more because of internal stack memory use,
    O(n) is same as iterative solution */
    public SingleLinkedListNode recursiveReverse(SingleLinkedListNode current) {
        if (current == null || current.next == null) { // Base case
            return current;
        }

        // Recursively reverse the sublist starting from the next node.
        SingleLinkedListNode reversedListHead = recursiveReverse(current.next);

        // Essentially below handles the first reversal, recursion will perform subsequently on next nodes
        current.next.next = current; // Adjust the links to reverse the nodes from 2nd node to 1st
        current.next = null; // Adjust the link for 1st to NULL

        return reversedListHead;
    }

    /* We will need 3 nodes to reverse */
    public SingleLinkedListNode intuitiveReverse(SingleLinkedListNode head) {
        SingleLinkedListNode prev = null;
        SingleLinkedListNode current = head;

        if (head == null) return null;
        while (current != null) {
            SingleLinkedListNode nextNode = current.next; // Save the next node reference

            current.next = prev; // Reverse the pointer direction
            prev = current; // Move prev one step forward
            current = nextNode; // Move on to the next group.
        }
        // prev will be the new head of the reversed list
        return prev;
    }

    /* Below functionality to cover LINKED LIST REVERSAL IN K-th GROUPS */

    /**
     * Reverses the linked list in fixed-size groups of {@code groupOf} nodes.
     *
     * <p>This pattern appears frequently in real-world systems where data is processed
     * in discrete blocks rather than as a continuous stream. Typical applications include:
     *
     * <ul>
     *   <li><b>Network and cryptographic pipelines</b> — transforming or re-encrypting
     *       packets in fixed-size windows (e.g., 16- or 32-byte blocks).</li>
     *
     *   <li><b>Batch-oriented data processing</b> — reshaping or reversing chunks of
     *       records before forwarding them to a downstream consumer that operates
     *       strictly on block units.</li>
     *
     *   <li><b>Message-queue and middleware systems</b> — reordering messages within
     *       bounded batches to support LIFO-like consumption semantics or specialized
     *       dispatch patterns.</li>
     *
     *   <li><b>Chunk-based file and stream transformations</b> — performing block-level
     *       operations during compression, decompression, or streaming transforms
     *       where each block is treated independently.</li>
     *
     *   <li><b>Low-level and embedded workloads</b> — reversing or rearranging bounded
     *       segments of samples, pixels, or sensor data in DSP pipelines or drivers.</li>
     * </ul>
     *
     * <p>The algorithm performs an in-place reversal of each group, then recursively
     * processes the remaining list, stitching the reversed segments together. This
     * avoids additional memory allocation while maintaining predictable block
     * boundaries—critical in systems that enforce strict batching constraints.
     *
     * @param head    the head of the linked list
     * @param groupOf the size of each reversal group
     * @return the new head of the list after group-wise reversal
     */
    public static SingleLinkedListNode reverseKGroupUsingRecursion(SingleLinkedListNode head, int groupOf) {
        /*
         * The time complexity of the algorithm is O(n/k) because it iterates through the linked list n/k times.
         * For each iteration, it reverses a group of k nodes, which takes O(k) time.
         * Therefore, the total time complexity of the algorithm is O(n/k) + O(k) = O(n/k).
         *
         * The space complexity of the algorithm is O(n/k).
         */
        SingleLinkedListNode current = head;
        SingleLinkedListNode prev = null;
        SingleLinkedListNode next = null;

        int count = 0;
        while (current != null && count < groupOf) {
            next = current.next; // Save the next node reference

            /*Reversal Starts*/
            current.next = prev; // Reverse the pointer direction
            prev = current; // Move prev one step forward
            /*Reversal Ends*/

            current = next; // Move on to the next group.
            count++;
        }

        // Link the reversed group to the next group.
        if (next != null) {
            head.next = reverseKGroupUsingRecursion(next, groupOf);
        }

        return prev;
    }

    /**
     * Reverses the linked list in fixed-size groups of {@code groupOf} nodes.
     *
     * <p>This pattern appears frequently in real-world systems where data is processed
     * in discrete blocks rather than as a continuous stream. Typical applications include:
     *
     * <ul>
     *   <li><b>Network and cryptographic pipelines</b> — transforming or re-encrypting
     *       packets in fixed-size windows (e.g., 16- or 32-byte blocks).</li>
     *
     *   <li><b>Batch-oriented data processing</b> — reshaping or reversing chunks of
     *       records before forwarding them to a downstream consumer that operates
     *       strictly on block units.</li>
     *
     *   <li><b>Message-queue and middleware systems</b> — reordering messages within
     *       bounded batches to support LIFO-like consumption semantics or specialized
     *       dispatch patterns.</li>
     *
     *   <li><b>Chunk-based file and stream transformations</b> — performing block-level
     *       operations during compression, decompression, or streaming transforms
     *       where each block is treated independently.</li>
     *
     *   <li><b>Low-level and embedded workloads</b> — reversing or rearranging bounded
     *       segments of samples, pixels, or sensor data in DSP pipelines or drivers.</li>
     * </ul>
     *
     * <p>The algorithm performs an in-place reversal of each group, then recursively
     * processes the remaining list, stitching the reversed segments together. This
     * avoids additional memory allocation while maintaining predictable block
     * boundaries—critical in systems that enforce strict batching constraints.
     *
     * @param head    the head of the linked list
     * @param k the size of each reversal group
     * @return the new head of the list after group-wise reversal
     */
    public static SingleLinkedListNode reverseLinkedListInKGroups(SingleLinkedListNode head, int k) {
        /*
         * The time complexity of the algorithm is O(n/k) because it iterates through the linked list n/k times.
         * For each iteration, it reverses a group of k nodes, which takes O(k) time.
         * Therefore, the total time complexity of the algorithm is O(n/k) + O(k) = O(n/k).
         *
         * The space complexity of the algorithm is O(1).
         * This is because the algorithm only uses a constant amount of space to store the pointers prev and current.
         */
        if (k <= 1) {
            return head;
        }

        SingleLinkedListNode prev = null;
        SingleLinkedListNode current = head;
        while (current != null) {
            SingleLinkedListNode nextNode = current.next;

            // Reverse the current group.
            for (int i = 0; i < k - 1; i++) {
                current.next = prev;
                prev = current;
                current = nextNode;
            }

            // Link the reversed group to the next group.
            if (nextNode != null) {
                prev.next = nextNode;
            }

            // Move on to the next group.
            current = nextNode;
        }

        return prev;
    }

    public static void main(String[] args) {
        // Create a sample linked list
        SingleLinkedListNode head = SingleLinkedListNode.sampleLinkedList();

        int groupOf = 2; // Number of nodes in each group

        System.out.println("Original List:");
        SingleLinkedListNode.printList(head);

        head = reverseKGroupUsingRecursion(head, groupOf);

        System.out.println("Reversed List:");
        SingleLinkedListNode.printList(head);
    }
}