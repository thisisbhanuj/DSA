package main.ds.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/*
   Problem:
   ********
   A text editor receives a stream of operations:
    +c → append char c
    -  → delete last char
    u  → undo last operation
    r  → redo last undo operation

   | Operation   | Undo Stack                             | Redo Stack                           |
   | ----------- | -------------------------------------- | ------------------------------------ |
   | `+c` or `-` | Push operation                         | Clear redo(only when branching)      |
   | `undo`      | Pop from undo → inverse → push to redo |                                      |
   | `redo`      | Pop from redo → reapply → push to undo |                                      |
*/
public class EditorOperations {
    private static class Operation {
        char type;
        char value;
        Operation(char type, char value) {
            this.type = type;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        String[] ops = {"+a", "+b", "+c", "-", "u", "+d", "u", "r", "r"};
        System.out.println(finalExpression(ops)); // Expected: "abcd"
    }

    public static String finalExpression(String[] ops) {
        StringBuilder sb = new StringBuilder();
        // Undo simply replays the inverse of the last action and moves that action to a redo stack.
        Deque<Operation> undoStack = new ArrayDeque<>();
        Deque<Operation> redoStack = new ArrayDeque<>();

        for (String op : ops) {
            if (op.charAt(0) == '+') {
                char c = op.charAt(1);
                sb.append(c);
                undoStack.push(new Operation('+', c));
                redoStack.clear(); // new edit → clear redo (branch)
            } else if (op.equals("-")) {
                if (!sb.isEmpty()) {
                    char removed = sb.charAt(sb.length() - 1);
                    sb.deleteCharAt(sb.length() - 1);
                    undoStack.push(new Operation('-', removed));
                    redoStack.clear(); // new edit → clear redo
                }
            } else if (op.equals("u")) { // undo
                if (!undoStack.isEmpty()) {
                    Operation last = undoStack.pop();
                    if (last.type == '+') {
                        sb.deleteCharAt(sb.length() - 1);
                        redoStack.push(new Operation('+', last.value));
                    } else if (last.type == '-') {
                        sb.append(last.value);
                        redoStack.push(new Operation('-', last.value));
                    }
                }
            } else if (op.equals("r")) { // redo
                if (!redoStack.isEmpty()) {
                    Operation redoOp = redoStack.pop();
                    if (redoOp.type == '+') {
                        sb.append(redoOp.value);
                        undoStack.push(new Operation('+', redoOp.value));
                    } else if (redoOp.type == '-') {
                        sb.deleteCharAt(sb.length() - 1);
                        undoStack.push(new Operation('-', redoOp.value));
                    }
                }
            }
        }
        return sb.toString();
    }
}
