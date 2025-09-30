# Duplicate Detection in Arrays – Memory & Runtime Trade-offs

This project demonstrates three approaches to detect duplicates in integer arrays and analyzes their **memory usage**, **runtime performance**, and **practical constraints**.

---

## **1. BitSet Approach**

```java
BitSet bitSet = new BitSet(max + 1);
for (int num : nums) {
    if (bitSet.get(num)) return true;
    bitSet.set(num);
}
```

### **How it works**

* BitSet stores **1 bit per integer**, effectively creating a **compact boolean array**.
* `set(index)` → marks a number as seen.
* `get(index)` → checks if a number is already present.

### **Pros**

* Ultra memory-efficient for **dense, bounded, non-negative ranges**.
* Extremely fast O(1) lookup and insertion.

### **Cons**

* Cannot store **negative numbers** directly.
* Preallocates memory for the **entire range**: `max - min + 1`.
* Fails for **huge or sparse ranges**, may cause **Memory Limit Exceeded**.

### **Best Use Case**

* Arrays with integers `[0, 10^6]` or other small, dense ranges.

---

## **2. HashMap Approach**

```java
Map<Integer, Boolean> seen = new HashMap<>();
for (int num : nums) {
    if (seen.containsKey(num)) return true;
    seen.put(num, true);
}
```

### **How it works**

* Stores each number as a key with associated value (e.g., Boolean).
* Provides O(1) average lookup and insertion.

### **Pros**

* Handles **negative numbers**.
* Works for **huge or sparse ranges**.
* Allows storing **extra information** per element (like count or index).

### **Cons**

* Uses **more memory** per entry (key + value objects + HashMap overhead).
* Slightly slower than HashSet for simple existence checks.

### **Best Use Case**

* When additional information per element is needed, or input ranges are huge/unbounded.

---

## **3. HashSet Approach**

```java
Set<Integer> seen = new HashSet<>();
for (int num : nums) {
    if (seen.contains(num)) return true;
    seen.add(num);
}
```

### **How it works**

* Internally backed by a HashMap.
* Only stores **unique elements**, no extra values needed.
* Lookup and insertion: O(1) average.

### **Pros**

* Handles **negative numbers** naturally.
* Efficient memory usage for sparse arrays (stores only actual numbers).
* Fastest and simplest solution for general-purpose duplicate detection.

### **Cons**

* Slight object overhead per number compared to BitSet in dense, bounded cases.

### **Best Use Case**

* Most practical scenario: **sparse, unbounded, or negative ranges**.
* Recommended for interview submissions unless the range is small and dense.

---

## **Observations from LeetCode Submissions**

| Approach | Memory Usage | Runtime Percentile | Notes                                                             |
| -------- | ------------ | ------------------ | ----------------------------------------------------------------- |
| BitSet   | 63 MB        | 20%                | Memory overhead due to range shifting; slower on sparse inputs    |
| HashMap  | 59 MB        | 47%                | Works for negatives, but stores extra values; moderate memory     |
| HashSet  | 57 MB        | 94%                | Best memory/runtime for sparse or mixed positive/negative numbers |

> **Lesson:** Fancy memory-optimized structures like BitSet may not always beat a simple HashSet in practice. Always consider **input constraints** (range, density, negatives) before choosing a data structure.

---

## **Interview Tip**

* Explain all three approaches:

    1. BitSet → dense and small ranges
    2. HashSet → general-purpose, sparse/negative ranges
    3. HashMap → if extra information per number is needed

* Discuss **memory vs time trade-offs** and why you chose the best approach based on input constraints.
---
