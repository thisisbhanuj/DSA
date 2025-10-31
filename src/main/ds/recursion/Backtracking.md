# ♻️ Backtracking vs Simple Recursion

| **Concept** | **Simple Recursion** | **Backtracking** |
| ------------ | -------------------- | ---------------- |
| **Output** | single value (sum, factorial, etc.) | many possible solutions |
| **Goal** | compute result | *enumerate / search / construct* |
| **State** | replaced by parameters | often shared structure (list, board, etc.) |
| **Undo** | not needed | required for clean state |
| **Pruning** | rarely used | central for efficiency |

---

## 🧠 Why It Helps Conceptually

When approaching a recursive problem, identify these:

| **Element** | **Question** | **Example** |
| ------------ | ------------ | ------------ |
| **State** | What are we building? | path, board, subset |
| **Choices** | What can we try next? | next element, next move |
| **Constraint / Prune** | What should we skip early? | invalid position, duplicate |
| **Goal** | When do we stop? | reached target, completed configuration |
| **Undo** | How to restore after trying? | remove last element, reset cell |

Once these are clear, your recursive backtracking function practically **writes itself**.
