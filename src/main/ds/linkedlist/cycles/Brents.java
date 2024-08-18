package main.ds.linkedlist.cycles;

import main.ds.linkedlist.SingleLinkedListNode;

/*
    Brent's algorithm is another efficient cycle detection algorithm that improves on
    Floyd's Tortoise and Hare algorithm by using a more advanced approach to detect cycles.
 */
public class Brents {
    // Brent's Cycle Detection Algorithm :
    // The brentCycleDetection method uses Brent's algorithm principles, where
    // the fast pointer moves in increasing steps (power), and the slow pointer
    // moves one step at a time. If they meet, a cycle is detected.
    private Integer detectCycleEntry(SingleLinkedListNode head) {
        if (head == null) return null;

        SingleLinkedListNode slow = head;
        SingleLinkedListNode fast = head;
        int power = 1;
        int length = 0;

        // Detect if a cycle exists
        while (fast != null && fast.next != null) {
            if (length == power) {
                power *= 2;
                length = 0;
                slow = fast;
            }

            slow = slow.next;
            fast = fast.next.next;
            length++;

            if (slow == fast) {
                break;
            }
        }

        // No cycle found
        if (fast == null || fast.next == null) {
            return null;
        }

        // Find the cycle start
        slow = head;
        fast = head;
        // After detecting a cycle, length represents the distance from the start of the cycle
        // to the meeting point where slow and fast meet. To find the start of the cycle, you
        // need to align fast with slow so they are both length nodes apart from the cycle's start.
        for (int i = 0; i < length; i++) {
            fast = fast.next;
        }

        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow.val; // Start of the cycle
    }

    public static void main(String[] args) {
        SingleLinkedListNode head = new SingleLinkedListNode(1);
        SingleLinkedListNode node2 = new SingleLinkedListNode(2);
        SingleLinkedListNode node3 = new SingleLinkedListNode(3);
        SingleLinkedListNode node4 = new SingleLinkedListNode(4);
        SingleLinkedListNode node5 = new SingleLinkedListNode(5);
        SingleLinkedListNode node6 = new SingleLinkedListNode(6);
        SingleLinkedListNode node7 = new SingleLinkedListNode(7);
        SingleLinkedListNode node8 = new SingleLinkedListNode(8);
        SingleLinkedListNode node9 = new SingleLinkedListNode(9);
        SingleLinkedListNode node10 = new SingleLinkedListNode(10);
        SingleLinkedListNode node11 = new SingleLinkedListNode(11);
        SingleLinkedListNode node12 = new SingleLinkedListNode(12);
        SingleLinkedListNode node13 = new SingleLinkedListNode(13);

        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;
        node8.next = node9;
        node9.next = node10;
        node10.next = node11;
        node11.next = node12;
        node12.next = node13;
        node13.next = node10; // Cyclical

        Integer cycleStart = new Brents().detectCycleEntry(head);
        System.out.println("Cycle Start : " + (cycleStart != null ? cycleStart : "No cycle"));
    }
}