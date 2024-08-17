package main.ds.linkedlist;

public class RemoveDuplicateFromSortedLinkedList {
    /*
        NOTE :
        Space complexity of the recursive implementation is O(n).
        Iterative solution will make it O(1)
    */
    public static void removeDuplicate (SingleLinkedListNode head) {
        if (head == null) return;
        SingleLinkedListNode current = head;

        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next =  current.next.next;;
                removeDuplicate(current.next);
            }
           current = current.next;
        }
    }

    public static void main(String[] args) {
        SingleLinkedListNode head11 = new SingleLinkedListNode(1);
        SingleLinkedListNode node21 = new SingleLinkedListNode(2);
        SingleLinkedListNode node31 = new SingleLinkedListNode(3);
        SingleLinkedListNode node41 = new SingleLinkedListNode(4);
        SingleLinkedListNode node51 = new SingleLinkedListNode(4);

        head11.next = node21;
        node21.next = node31;
        node31.next = node41;
        node41.next = node51;

        if (head11 != null) {
            removeDuplicate(head11);
            System.out.println("Updated List: ");
            SingleLinkedListNode.printList(head11);
        }
    }
}
