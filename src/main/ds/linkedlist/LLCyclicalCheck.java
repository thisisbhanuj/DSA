package main.ds.linkedlist;
/*
    NOTE : We can use HashSet to keep track of the values traversed,
           but it is going to make space complexity as O(n)
*/
public class LLCyclicalCheck {
    /*
    * Floyd's cycle-finding algorithm.
    * Time complexity is O(n) and the space complexity is O(1)
    */
    private static boolean isCyclical(SingleLinkedListNode current) {
        boolean status = false;
        SingleLinkedListNode slow = current, fast = current;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                status = true;
                return status;
            }
        }

        return status;
    }

    /*
    * To find the node from where a cycle begins in a linked list,
    * you can use Floyd's cycle-finding algorithm,
    * also known as the "tortoise and hare" algorithm.
    */
    public SingleLinkedListNode detectCycleEntry(SingleLinkedListNode head) {
        // Check if the linked list is empty or has only one node
        if (head == null || head.next == null) {
            return null;
        }

        // Initialize slow and fast pointers
        SingleLinkedListNode slow = head;
        SingleLinkedListNode fast = head;

        // Move the slow and fast pointers until they meet
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;  // Cycle detected
            }
        }

        // If there is no cycle, return null
        if (fast == null || fast.next == null) {
            return null;
        }

        // Move the slow pointer back to the head and move both pointers at the same pace
        // The point where they meet again is the starting node of the cycle
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        SingleLinkedListNode head = new SingleLinkedListNode(1);
        SingleLinkedListNode node2 = new SingleLinkedListNode(2);
        SingleLinkedListNode node3 = new SingleLinkedListNode(3);
        SingleLinkedListNode node4 = new SingleLinkedListNode(4);
        SingleLinkedListNode node5 = new SingleLinkedListNode(5);
        SingleLinkedListNode node6 = new SingleLinkedListNode(2);
        SingleLinkedListNode node7 = new SingleLinkedListNode(3);
        SingleLinkedListNode node8 = new SingleLinkedListNode(4);
        SingleLinkedListNode node9 = new SingleLinkedListNode(5);
        SingleLinkedListNode node10= new SingleLinkedListNode(2);
        SingleLinkedListNode node11= new SingleLinkedListNode(3);
        SingleLinkedListNode node12= new SingleLinkedListNode(4);
        SingleLinkedListNode node13= new SingleLinkedListNode(5);

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

        boolean found =  isCyclical(head);

        System.out.println("Is LL Cyclical : " + found);
    }
}
