package main.ds.recursion;

import java.util.*;

/* 🚀 Performance Notes
   ---------------------------------------------------------------------------------------------------------------------------
   | Metric               | Before                                    | After                                                |
   | -------------------- | ----------------------------------------- | ---------------------------------------------------- |
   | Safety check         | O(N) per position (scan rows & diagonals) | O(1) (boolean lookup)                                |
   | Time Complexity      | ~O(N!)                                    | same asymptotically, but much faster constant factor |
   | Space Complexity     | O(N²) board + O(3N) auxiliary arrays      | negligible overhead                                  |
   ---------------------------------------------------------------------------------------------------------------------------
 */
public class NQueensOptimized {

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> results = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');

        /*
            READ: D:\Code\DSA\src\main\ds\recursion\Matrix Diagonal.md
        */
        boolean[] cols = new boolean[n];           // columns
        boolean[] diag1 = new boolean[2 * n - 1];  // (r - c + n - 1)
        boolean[] diag2 = new boolean[2 * n - 1];  // (r + c)

        recursive(0, board, results, n, cols, diag1, diag2);
        return results;
    }

    private static void recursive(
            int row,
            char[][] board,
            List<List<String>> results,
            int n,
            boolean[] cols,
            boolean[] diag1,
            boolean[] diag2
    ) {

        if (row == n) {
            results.add(construct(board));
            return;
        }

        for (int col = 0; col < n; col++) {
            int d1 = row - col + n - 1;
            int d2 = row + col;

            if (cols[col] || diag1[d1] || diag2[d2]) continue; // unsafe

            // 1️⃣ choose
            board[row][col] = 'Q';
            cols[col] = diag1[d1] = diag2[d2] = true;

            // 2️⃣ explore next state
            recursive(row + 1, board, results, n, cols, diag1, diag2);

            // 3️⃣ undo (backtrack)
            board[row][col] = '.';
            cols[col] = diag1[d1] = diag2[d2] = false;
        }
    }

    private static List<String> construct(char[][] board) {
        List<String> snapshot = new ArrayList<>();
        for (char[] row : board) snapshot.add(new String(row));
        return snapshot;
    }

    public static void main(String[] args) {
        List<List<String>> solutions = solveNQueens(8);
        System.out.println("Total solutions: " + solutions.size());
        for (List<String> board : solutions) {
            for (String row : board) System.out.println(row);
            System.out.println();
        }
    }
}
