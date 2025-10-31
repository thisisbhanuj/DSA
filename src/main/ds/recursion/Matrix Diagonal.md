**Geometry of diagonals** on a matrix (or chessboard) - it’s a neat little mathematical mapping trick.

---

### 💡 Problem setup

We have a ( n \times n ) grid.
Each cell is identified by coordinates ( (r, c) ), where ( r, c \in [0, n-1] ).

You want to uniquely identify each **diagonal** — the sets of cells that lie on the same diagonal — with a single number.

---

### 1️⃣ Left Diagonals (↙ / “\” shaped)

These are diagonals where **row – column is constant**.

Example (for 4×4):

```
(0,0)
(1,1)
(2,2)
(3,3)
```

→ all have `r - c = 0`

Another diagonal:

```
(1,0), (2,1), (3,2)
```

→ all have `r - c = 1`

So, the key invariant is:
[
r - c = \text{constant}
]
That’s what defines one "" diagonal.

But `r - c` can be **negative**:

* top-right diagonals have `r - c < 0`
* bottom-left diagonals have `r - c > 0`

Range of `r - c`:
[
-(n-1) \leq r - c \leq n-1
]

That’s **2n - 1 possible diagonals**.

We can’t use negative indices in arrays, so we **shift** it by adding ( n - 1 ):

[
\text{index} = (r - c) + (n - 1)
]

Hence:
[
\boxed{\text{diag1}[r - c + n - 1]}
]

---

### 2️⃣ Right Diagonals (↘ / “/” shaped)

These are diagonals where **row + column is constant**.

Example (4×4):

```
(0,3), (1,2), (2,1), (3,0)
```

→ all have `r + c = 3`

So the invariant:
[
r + c = \text{constant}
]

Range of `r + c`:
[
0 \leq r + c \leq 2n - 2
]
(again, 2n − 1 diagonals total)

We can directly use that as an array index:
[
\boxed{\text{diag2}[r + c]}
]

---

### 🧠 Intuition

* Each diagonal is a **level set** (a set of points having the same value) of a linear equation:

    * "" → ( r - c = k )
    * "/" → ( r + c = k )
* The offset ( n-1 ) is just a normalization to map all possible values to nonnegative indices.

---

### 🔢 Example for n = 4

| Cell (r,c) | r-c | r-c+(n-1) | r+c |
| ---------- | --- | --------- | --- |
| (0,0)      | 0   | 3         | 0   |
| (0,3)      | -3  | 0         | 3   |
| (3,0)      | 3   | 6         | 3   |
| (2,1)      | 1   | 4         | 3   |

So:

* diag1[3] → main diagonal (r−c=0)
* diag2[3] → the “/” diagonal crossing top-right to bottom-left.

---