package main.ds.linkedlist;

public class RemoveNode {

    public static SingleLinkedListNode removeNode(SingleLinkedListNode start, int value) {
        // Base case: If the list is empty, return null
        if (start == null) return null;

        // This ensures that the entire list is processed before checking whether to remove the current node.
        // Every node’s next pointer is updated with the result of the recursive call,
        // ensuring that any matching nodes further down the list are removed first, and then the current node is checked.
        start.next = removeNode(start.next, value);

        // If the current node's value matches, skip this node
        if (start.val == value) {
            return start.next;
        }

        // Otherwise, return the current node
        return start;
    }

    public static void main(String[] args) {
        SingleLinkedListNode head = SingleLinkedListNode.sampleLinkedList();
        head = removeNode(head, 2);
        SingleLinkedListNode.printList(head);
    }
}

//### Understanding the Recursive Call
//        1. **Recursive Breakdown**:
//        - **Goal**: Remove all nodes with a specific value from a singly linked list.
//
//        2. **Recursive Process**:
//        - The recursion helps process the list one node at a time. It simplifies handling the entire list by breaking it down into smaller subproblems.
//
//### How It Works
//        1. **Recursive Call**:
//        - `start.next = removeNode(start.next, value);`
//        - This call deals with the rest of the list after the current node.
//        - It recursively processes the nodes following the current one and updates the `next` reference of the current node to the result of this recursive call.
//
//        2. **Processing Each Node**:
//        - After processing the rest of the list, the current node checks if its value matches the target value (`if (start.val == value)`):
//        - If it matches, the current node is removed by returning `start.next` (the next node in the list).
//        - If it does not match, the current node remains in the list, and the current node itself is returned.
//
//### Example Walkthrough
//      Let's say the list is `1 -> 2 -> 3 -> 2 -> 4` and we want to remove nodes with the value `2`.
//
//        1. **Initial Call**: `removeNode(head, 2)`
//        - Processes the head node (`1`) and moves to the next node.
//
//        2. **Recursive Call**: `removeNode(2 -> 3 -> 2 -> 4, 2)`
//        - Processes the node with value `2` and moves to `3`.
//
//        3. **Recursive Call**: `removeNode(3 -> 2 -> 4, 2)`
//        - Processes `3` and moves to the next `2`.
//
//        4. **Recursive Call**: `removeNode(2 -> 4, 2)`
//        - Processes the `2`, which matches the value to be removed.
//        - Calls `removeNode(4, 2)` to handle the rest of the list.
//        - `removeNode(4, 2)` processes `4`, which doesn’t match `2`, so it returns `4`.
//
//        5. **Returning Back**:
//        - `removeNode(2 -> 4, 2)` returns `4`, removing the `2` from the list.
//        - `removeNode(3 -> 2 -> 4, 2)` returns `3 -> 4`, removing the `2` from the list.
//        - `removeNode(2 -> 3 -> 2 -> 4, 2)` returns `3 -> 4`, removing the `2` from the list.
//        - `removeNode(head, 2)` finally returns `1 -> 3 -> 4`.
//
//### Key Points
//
//        - **Recursion**: Each recursive call works on a smaller portion of the list.
//        - **Base Case**: When `start` is `null`, it returns `null`, handling the end of the list.
//        - **Update**: After recursion, the current node's `next` is updated based on whether the node should be kept or removed.
//
//      This way, recursion simplifies processing each node and handling the list without needing complex iterative logic.