package main.ds.linkedlist;

public class RemoveAdjacentDuplicateFromSortedLinkedList {
    public static SingleLinkedListNode removeDuplicateRecursive(SingleLinkedListNode node) {
        if (node == null || node.next == null) return node;

        node.next = removeDuplicateRecursive(node.next);

        if (node.val == node.next.val) return node.next;

        return node;
    }

    /*
         Time Complexity:
           Each node is visited once, and each duplicate removal is handled in constant time, so O(N).

         Space Complexity:
           The space complexity would be O(1) because the function only uses a constant amount of extra space (for the current pointer).
     */
    public static void removeDuplicateIterative(SingleLinkedListNode head) {
        if (head == null) return;
        SingleLinkedListNode current = head;

        while (current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
    }

    public static void main(String[] args) {
        SingleLinkedListNode head11 = new SingleLinkedListNode(1);
        SingleLinkedListNode node21 = new SingleLinkedListNode(1);
        SingleLinkedListNode node31 = new SingleLinkedListNode(2);
        SingleLinkedListNode node41 = new SingleLinkedListNode(3);
        SingleLinkedListNode node51 = new SingleLinkedListNode(4);
        SingleLinkedListNode node61 = new SingleLinkedListNode(5);
        SingleLinkedListNode node71 = new SingleLinkedListNode(6);
        SingleLinkedListNode node81 = new SingleLinkedListNode(6);

        head11.next = node21;
        node21.next = node31;
        node31.next = node41;
        node41.next = node51;
        node51.next = node61;
        node61.next = node71;
        node71.next = node81;

        head11 = removeDuplicateRecursive(head11);

        removeDuplicateIterative(head11);

        System.out.println("Updated List: ");
        SingleLinkedListNode.printList(head11);
    }
}
