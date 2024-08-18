package main.ds.linkedlist;

import java.util.HashSet;
import java.util.Set;

public class RemoveAnyDuplicateFromLinkedList{
    public static void main(String[] args) {
        SingleLinkedListNode head = new SingleLinkedListNode(1);
        SingleLinkedListNode head1 = new SingleLinkedListNode(2);
        SingleLinkedListNode head2 = new SingleLinkedListNode(3);
        SingleLinkedListNode head3 = new SingleLinkedListNode(4);
        SingleLinkedListNode head4 = new SingleLinkedListNode(5);
        SingleLinkedListNode head5 = new SingleLinkedListNode(6);
        SingleLinkedListNode head6 = new SingleLinkedListNode(7);
        SingleLinkedListNode head7 = new SingleLinkedListNode(3); // Duplicate

        head.next = head1;
        head1.next = head2;
        head2.next = head3;
        head3.next = head4;
        head4.next = head5;
        head5.next = head6;
        head6.next = head7;
        head7.next = null;

        removeDups(head);

        // Print the list after removing duplicates
        SingleLinkedListNode.printList(head);
    }

    private static void removeDups(SingleLinkedListNode node) {
        if (node == null || node.next == null) {
            return;
        }

        Set<Integer> set = new HashSet<>();
        SingleLinkedListNode previous = null;

        while (node != null) {
            if (set.contains(node.val)) {
                //********************************************************
                // Pointer Adjustment:
                //---------------------
                // By changing previous.next to current.next, you're not removing the current node explicitly (e.g., by deallocating memory),
                // but you're removing it from the logical structure of the list.
                // The current node becomes inaccessible from the list and will eventually be garbage collected (in languages with automatic memory management like Java).
                //
                // No Explicit Removal Required:
                //------------------------------
                // In Java, the memory for the current node will be freed automatically by the garbage collector
                // if there are no more references to it. Once previous.next is set to current.next,
                // the reference to current is gone from the list, making it eligible for garbage collection.
                //********************************************************
                previous.next = node.next;
            } else {
                // Add value to the set and move the previous pointer
                set.add(node.val);
                previous = node;
            }
            // Move to the next node
            node = node.next;
        }
    }
}