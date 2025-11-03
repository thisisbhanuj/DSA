package main.ds.recursion;

import main.ds.linkedlist.SingleLinkedListNode;

/*
    RECURSION is a product of { Decision Space + Choices }, as primary aspects.
    Reduction of inputs is secondary and the side-effect of the process, hence
    the focus should be on the primary things. RECURSIVE TREE is created by
    Input - Output process, where ROOT should be initialized first,
    and the Branches are the # of Choices causing reduction of input space.

    Stress-Time Recognition Trick
    *****************************
    When under stress in an interview, immediately ask yourself:

    1. What am I building step-by-step? (state)
    → e.g., partial configuration of board, subset, string, etc.

    2. What are the available moves/choices?
    → e.g., next column, next number, next letter.

    3. When is the configuration complete? (base)
    → e.g., row == N, index == nums.length, etc.

    4. Do I need to backtrack?
    → if you’re exploring multiple branches.

    🧭 Why We “Undo” Things
    ************************
    ❓Why do we undo the choice after recursion?

    Because recursion builds the solution in place (shared memory, same data structure).
    When we return from recursion, we need to restore the previous state before trying the next choice.
    So “undo” isn’t mystical — it’s just state restoration.

    You can visualize it like this:
        state = []
        choose A → state = [A]
        recurse()
        undo A → state = []
        choose B → state = [B]
        recurse()

    If you don’t undo, all choices get mixed — you never cleanly isolate one path of exploration.

    🔁 The Generic Pseudocode Template
    ***********************************
        void recursion(State state) {
            if (isGoal(state)) {
                record(state);
                return;
            }

            for (Choice choice : getChoices(state)) {
                if (isValid(choice, state)) {
                    make(choice, state);     // 1️⃣ Choose
                    recursion(state);        // 2️⃣ Explore
                    undo(choice, state);     // 3️⃣ Un-choose
                }
            }
        }
    ***********************************

    Every recursion / backtracking problem fits into this skeleton.
    Once you articulate that in words, recursion’s code almost writes itself.

    -------------------------------------------------------------------------------
    | Concept              | N-Queens                 | Linked List Reversal      |
    | -------------------- | ------------------------ | ------------------------- |
    | Decision Space       | Current row              | Current node              |
    | Choices per decision | Columns (0..N-1)         | 1 (move to next node)     |
    | Action per choice    | Place a queen            | Reverse pointer           |
    | Next State           | Next row                 | Next node                 |
    | Undo                 | Remove queen (backtrack) | None (irreversible)       |
    | Base case            | row == N                 | current == null           |
    | Output               | One valid board          | New head of reversed list |
    -------------------------------------------------------------------------------
 */
public class ReverseLinkedList {
    /**
        Let’s reframe the problem:
            Decision space: current node (like current row)
            Choice: next pointer direction (currently points forward, will point backward)
            Exploration: move to the next node
            Undo: not applicable — we commit to reversal, no backtracking.

        So instead of for-loop (multiple branches), we have a single branch — a chain recursion.
    **/
    public static SingleLinkedListNode recursiveReverse(SingleLinkedListNode current) {
        // Decision space: current node

        // Base case
        if (current == null || current.next == null) {
            return current;
        }

        // Exploration : Recursively reverse the sublist starting from the next node.
        SingleLinkedListNode reversedListHead = recursiveReverse(current.next);

        // Choice : Essentially below handles the first reversal, recursion will perform subsequently on next nodes
        current.next.next = current; // Adjust the links to reverse the nodes from 2nd node to 1st
        current.next = null; // Adjust the link for 1st to NULL

        return reversedListHead;
    }

    public static void main(String[] args) {
        // Create a sample linked list
        SingleLinkedListNode head = SingleLinkedListNode.sampleLinkedList();

        System.out.println("Original List:");
        SingleLinkedListNode.printList(head);

        head = recursiveReverse(head);

        System.out.println("Reversed List:");
        SingleLinkedListNode.printList(head);
    }
}