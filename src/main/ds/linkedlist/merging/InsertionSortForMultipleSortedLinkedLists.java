package main.ds.linkedlist.merging;

import main.ds.linkedlist.SingleLinkedListNode;

public class InsertionSortForMultipleSortedLinkedLists {

    // Merge multiple sorted linked lists into one
    public static SingleLinkedListNode mergeMultipleLists(SingleLinkedListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        SingleLinkedListNode mergedList = null;
        for (SingleLinkedListNode list : lists) {
            mergedList = twoWayMerging(mergedList, list);
        }

        return mergedList;
    }

    // Merge two sorted linked lists into one
    private static SingleLinkedListNode twoWayMerging(SingleLinkedListNode head1, SingleLinkedListNode head2) {
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

    // Insertion Sort for a single linked list
    public static SingleLinkedListNode insertionSortList(SingleLinkedListNode head) {
        if (head == null || head.next == null) return head;

        SingleLinkedListNode sorted = null; // Sorted part of the list

        // Traverse the original list and insert each element into the sorted part
        while (head != null) {
            SingleLinkedListNode current = head;
            head = head.next;

            // Insert current node into the sorted part
            if (sorted == null || sorted.val >= current.val) {
                current.next = sorted;
                sorted = current;
            } else {
                SingleLinkedListNode temp = sorted;
                while (temp.next != null && temp.next.val < current.val) {
                    temp = temp.next;
                }
                current.next = temp.next;
                temp.next = current;
            }
        }

        return sorted;
    }

    // Method to merge multiple sorted linked lists and then sort the merged list
    public static SingleLinkedListNode mergeAndSortLists(SingleLinkedListNode[] lists) {
        SingleLinkedListNode mergedList = mergeMultipleLists(lists);
        return insertionSortList(mergedList);
    }

    public static void main(String[] args) {
        // Example usage
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

        SingleLinkedListNode head33 = new SingleLinkedListNode(3);
        SingleLinkedListNode node23 = new SingleLinkedListNode(6);
        SingleLinkedListNode node33 = new SingleLinkedListNode(7);

        head33.next = node23;
        node23.next = node33;

        SingleLinkedListNode[] lists = {head11, head22, head33};
        SingleLinkedListNode sortedMergedList = mergeAndSortLists(lists);

        System.out.println("Sorted Merged List: ");
        SingleLinkedListNode.printList(sortedMergedList);
    }
}

