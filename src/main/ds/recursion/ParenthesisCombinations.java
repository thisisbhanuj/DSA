package main.ds.recursion;

import java.util.ArrayList;
import java.util.List;

/*
    RECURSION is a product of { Decision Space + Choices }, as primary aspects.
    Reduction of inputs is secondary and the side effect of the process, hence
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
public class ParenthesisCombinations {
    public static void main(String[] args) {
        List<String> result = new ArrayList<>();
        // Decision Space
        int open = 0, close = 0;
        StringBuilder current = new StringBuilder("");
        List<String> combination = findCombinations(current, open, close, result, 3);
        combination.forEach(System.out::println);
    }

    private static List<String> findCombinations(StringBuilder current, int open, int close, List<String> result, int n) {
        if(open == close && open == n){
            result.add(String.valueOf(current));
        }

        // Choice 1: Include Open
        if(open < n) {
            current.append('(');
            // open++ -- Mutating open/close is like corrupting your parent node’s state before exploring other children.
            // Each node (call frame) must not modify its own decision-space variables in a way that affects sibling branches.
            result = findCombinations(current, open + 1, close, result, n);
            // Undo : Do I need to backtrack? Now let’s try other possibilities
            current.deleteCharAt(current.length() - 1);
        }

        // Choice 2: Include Close
        if(close < open){
            current.append(')');
            // close++ -- Mutating open/close is like corrupting your parent node’s state before exploring other children.
            // Each node (call frame) must not modify its own decision-space variables in a way that affects sibling branches.
            result = findCombinations(current, open, close + 1, result, n);
            // Undo: Do I need to backtrack? Now let’s try other possibilities
            current.deleteCharAt(current.length() - 1);
        }

        return result;
    }
}
