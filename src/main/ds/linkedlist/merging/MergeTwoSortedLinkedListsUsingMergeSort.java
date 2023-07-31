package ds.linkedlist.merging;

import ds.linkedlist.SingleLinkedListNode;

/* Time complexity is O(n log(n)).
 * This is because the merge sort algorithm takes O(n log(n)) time to sort the merged linked list.
 *
 * Space complexity is O(n), where n is the number of nodes in the merged linked list.
 * This is because the function creates a new linked list to store the merged linked list.
*/
public class MergeTwoSortedLinkedListsUsingMergeSort {

    private static SingleLinkedListNode merge(SingleLinkedListNode head1, SingleLinkedListNode head2) {
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

    public static SingleLinkedListNode mergeSortedList(SingleLinkedListNode head1, SingleLinkedListNode head2) {
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
            SingleLinkedListNode mergedList = mergeSortedList(head11, head22);

            System.out.println("Sorted Merged List: ");
            SingleLinkedListNode.printList(mergedList);
        }
    }
}
