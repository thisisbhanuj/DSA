package ds.linkedlist.merging;

import ds.linkedlist.SingleLinkedListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/*
*
* Time Complexity:
* ----------------
* Constructing the min heap:
*   Inserting each node into the min heap takes O(log(k)) time, where k is the number of linked lists.
*   Since there are potentially N nodes across all the linked lists, the total time complexity for constructing the min heap is O(N log(k)).
* Merging the linked lists:
*   Each node is visited and processed once. Since there are N nodes in total, the time complexity for merging the linked lists is O(N).
*
* Therefore, the overall time complexity of the program is O(N log(k)),
* where N is the total number of nodes in all the linked lists and k is the number of linked lists.
*
* Space Complexity:
* -----------------
* The space complexity is determined by the min heap.
* At any given time, the min heap can contain at most k nodes, where k is the number of linked lists.
* Therefore, the space complexity for the min heap is O(k).
* Additional space is required for the dummy node and current node, both of which require O(1) space.
* Thus, the overall space complexity of the program is O(k).
*
*/
public class MergeKSortedLinkedListsUsingMinHeap {
    private static SingleLinkedListNode mergeMultipleLinkedLists(SingleLinkedListNode[] lists){
        PriorityQueue<SingleLinkedListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));

        for(SingleLinkedListNode head : lists){
            if (head != null) minHeap.offer(head);
        }

        SingleLinkedListNode dummy = new SingleLinkedListNode(-1);
        SingleLinkedListNode current = dummy;

        while (!minHeap.isEmpty()) {
            SingleLinkedListNode node = minHeap.poll();
            current.next = node;
            current = current.next;
            if (current.next != null) {
                minHeap.offer(current.next);
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        // Initialize the Linked Lists
        SingleLinkedListNode head1 = SingleLinkedListNode.sampleLinkedList();
        SingleLinkedListNode head2 = new SingleLinkedListNode(16);
        head2.next = new SingleLinkedListNode(17);
        head2.next.next = new SingleLinkedListNode(18);
        head2.next.next.next = new SingleLinkedListNode(19);
        head2.next.next.next.next = new SingleLinkedListNode(20);
        SingleLinkedListNode head3 = new SingleLinkedListNode(8);
        head3.next = new SingleLinkedListNode(18);
        head3.next.next = new SingleLinkedListNode(19);

        // Arrays of the linked lists of type SingleLinkedListNode
        SingleLinkedListNode[] lists = {head1, head2, head3};
        SingleLinkedListNode sortedLL = mergeMultipleLinkedLists(lists);
        SingleLinkedListNode.printList(sortedLL);
    }
}
