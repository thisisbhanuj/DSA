package main.ds.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    ---------------------------------------------------
    | Concept              | N-Queens                 |
    | -------------------- | ------------------------ |
    | Decision Space       | Current row              |
    | Choices per decision | Columns (0..N-1)         |
    | Action per choice    | Place a queen            |
    | Next State           | Next row                 |
    | Undo                 | Remove queen (backtrack) |
    | Base case            | row == N                 |
    | Output               | One valid board          |
    ---------------------------------------------------
 */

/** 🧠 Problem
    Place N queens on an N×N board so that no two queens attack each other.
    Return all possible arrangements (each represented as a list of strings)

     Time Complexity:
     ~ O(N!) because each row allows fewer valid positions.

     Space Complexity:
     O(N²) for board + O(N) recursion stack.
*/
public class NQueens {

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> results = new ArrayList<>();
        char[][] board = new char[n][n];

        // initialize board with '.'
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        backtrack(0, board, results, n);
        return results;
    }

    private static void backtrack(int row, char[][] board, List<List<String>> results, int n) {
        // base case: all queens placed
        if (row == n) {
            results.add(construct(board));
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col, n)) {
                // 1️⃣ choose
                board[row][col] = 'Q';
                // 2️⃣ explore next state
                backtrack(row + 1, board, results, n);
                // 3️⃣ un-choose (backtrack)
                board[row][col] = '.';
            }
        }
    }

    private static boolean isSafe(char[][] board, int row, int col, int n) {
        // check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }

        // check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }

        // check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }

        return true;
    }

    private static List<String> construct(char[][] board) {
        List<String> snapshot = new ArrayList<>();
        for (char[] row : board) {
            snapshot.add(new String(row));
        }
        return snapshot;
    }

    public static void main(String[] args) {
        List<List<String>> solutions = solveNQueens(4);
        for (List<String> board : solutions) {
            for (String row : board) System.out.println(row);
            System.out.println();
        }
    }
}

