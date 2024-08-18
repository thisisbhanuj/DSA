package main.ds.linkedlist;

public class RemoveAdjacentDuplicateFromSortedLinkedList {
     /*
          Time Complexity:
            With the recursive call, the time complexity can be closer to 𝑂(𝑁^2).

          Space Complexity:
            The recursion adds to the call stack.
            In the worst case, where there are many adjacent duplicates,
            the depth of recursion can reach 𝑂(𝑁). This makes the space complexity O(N).
      */
    public static void removeDuplicate(SingleLinkedListNode head) {
        if (head == null) return;
        SingleLinkedListNode current = head;

        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next =  current.next.next;
                // This is problematic because after the recursive call, the outer loop (while) continues processing the same node as it was before the recursive call.
                // This recursive call could potentially process the entire remaining portion of the list again, creating overlapping work.
                removeDuplicate(current.next);
            }
           current = current.next;
        }
    }

    /*
         Time Complexity:
           Each node is visited once, and each duplicate removal is handled in constant time, so O(N).

         Space Complexity:
           The space complexity would be O(1) because the function only uses a constant amount of extra space (for the current pointer).
     */
    public static void removeDuplicateOptimized(SingleLinkedListNode head) {
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
        SingleLinkedListNode node21 = new SingleLinkedListNode(2);
        SingleLinkedListNode node31 = new SingleLinkedListNode(3);
        SingleLinkedListNode node41 = new SingleLinkedListNode(4);
        SingleLinkedListNode node51 = new SingleLinkedListNode(4);

        head11.next = node21;
        node21.next = node31;
        node31.next = node41;
        node41.next = node51;

        removeDuplicateOptimized(head11);
        System.out.println("Updated List: ");
        SingleLinkedListNode.printList(head11);
    }
}
