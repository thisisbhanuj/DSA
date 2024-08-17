package main.ds.linkedlist.merging;

import ds.linkedlist.SingleLinkedListNode;

public class MergeSortedLinkedListByRecursion {
    /*
    * The time complexity is O(m + n)
    * The maximum depth of the recursive call stack depends on the length of the shorter list.
    * Therefore, the space complexity is O(min(m, n))
    */
    public static SingleLinkedListNode mergeByRecursion (SingleLinkedListNode head1, SingleLinkedListNode head2) {
        if (head1 == null && head2 != null) return head2;
        if (head2 == null && head1 != null) return head1;

        if (head1.val <= head2.val) {
            head1.next = mergeByRecursion(head1.next, head2);
            return head1;
        }
        if (head2.val <= head1.val) {
            head2.next = mergeByRecursion(head2.next, head1);
            return head2;
        }

        return null;
    }

    public static void main(String[] args) {
        // Create sample linked list
        SingleLinkedListNode head11 = new SingleLinkedListNode(1);
        SingleLinkedListNode node21 = new SingleLinkedListNode(8);
        SingleLinkedListNode node31 = new SingleLinkedListNode(26);
        SingleLinkedListNode node41 = new SingleLinkedListNode(40);
        SingleLinkedListNode node51 = new SingleLinkedListNode(300);

        SingleLinkedListNode head22 = new SingleLinkedListNode(5);
        SingleLinkedListNode node22 = new SingleLinkedListNode(10);
        SingleLinkedListNode node32 = new SingleLinkedListNode(26);
        SingleLinkedListNode node42 = new SingleLinkedListNode(44);
        SingleLinkedListNode node52 = new SingleLinkedListNode(91);

        head11.next = node21;
        node21.next = node31;
        node31.next = node41;
        node41.next = node51;

        head22.next = node22;
        node22.next = node32;
        node32.next = node42;
        node42.next = node52;

        if (head11 != null && head22 != null) {
            SingleLinkedListNode head = mergeByRecursion(head11, head22);

            System.out.println("Merged List : ");
            SingleLinkedListNode.printList(head);
        }
    }
}
