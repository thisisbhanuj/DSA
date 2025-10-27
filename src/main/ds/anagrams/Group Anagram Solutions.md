# 🧠 Group Anagram Solutions --- Design & Analysis

## 1. Problem Statement

Given a list of words, group together all words that are anagrams of
each other.\
Example:\
Input → `["listen", "silent", "enlist", "hello", "ohlle", "world"]`\
Output →
`[["listen", "silent", "enlist"], ["hello", "ohlle"], ["world"]]`

------------------------------------------------------------------------

## 2. Approaches

### **Approach 1: Basic Sort-Based HashMap (Single-threaded)**

#### **Algorithm**

1.  For each word, convert to lowercase and remove non-alphabetic
    characters.
2.  Sort its characters --- the sorted string acts as a *canonical key*.
3.  Insert into a HashMap where key = sorted word, value = list of
    words.

#### **Code Snippet**

``` java
Map<String, List<String>> map = new HashMap<>();
for (String word : words) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    String key = new String(chars);
    map.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
}
```

#### **Complexity**

-   **Time** → O(N × K log K) (K = average word length)\
-   **Space** → O(N × K)

#### **Strengths**

-   Simple and deterministic.
-   Works well for moderate input sizes.

#### **Weaknesses**

-   Sorting each word is costly for long words.
-   Not thread-safe without synchronization.

------------------------------------------------------------------------

### **Approach 2: Concurrent Stream Processing with AtomicInteger**

#### **Algorithm**

1.  Uses Java Streams and `ConcurrentHashMap` for thread-safe grouping.
2.  Each word's key is its sorted character array.
3.  Uses `AtomicInteger` for generating IDs per group safely.

#### **Why AtomicInteger?**

AtomicInteger ensures *lock-free thread safety* for counter increments
--- avoiding race conditions when multiple threads append new groups
concurrently.

#### **Complexity**

-   **Time** → O(N × K log K)
-   **Space** → O(N × K)
-   Additional overhead from stream parallelization and atomic
    operations.

#### **Strengths**

-   Leverages parallelism on multi-core CPUs.
-   Thread-safe by design.
-   Scales well for streaming or concurrent data ingestion.

#### **Weaknesses**

-   Slightly more GC pressure.
-   Overhead from thread context-switching if input size is small.

------------------------------------------------------------------------

### **Approach 3: Caffeine Cache (LRU-based Grouping)**

#### **Algorithm**

1.  Use **Caffeine Cache** for high-performance, memory-efficient group
    lookups.
2.  Each key = sorted word signature.
3.  Cache handles eviction and concurrency internally.

#### **Code Snippet**

``` java
Cache<String, List<String>> cache = Caffeine.newBuilder()
    .maximumSize(1000)
    .build();

for (String word : words) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    String key = new String(chars);

    cache.asMap().computeIfAbsent(key, k -> new ArrayList<>()).add(word);
}
```

#### **Complexity**

-   **Time** → O(N × K log K)
-   **Space** → O(N × K) with bounded LRU cache eviction.

#### **Strengths**

-   Handles large-scale inputs gracefully.
-   Excellent cache eviction & concurrency model.
-   Production-grade for streaming pipelines or API services.

#### **Weaknesses**

-   Requires external dependency (`com.github.ben-manes.caffeine`).
-   Slight complexity in setup/config tuning.

------------------------------------------------------------------------

## 3. Comparative Analysis

  ---------------------------------------------------------------------------------
**Approach**   **Thread-Safe**   **Best For**  **Performance**   **Complexity**
  -------------- ----------------- ------------- ----------------- ----------------
Basic HashMap  ❌ No             Simple        Moderate          Low
offline tasks

Concurrent     ✅ Yes            Parallel      High on           Medium
Stream                           ingestion     multi-core

Caffeine Cache ✅ Yes            Scalable,     Excellent         High
production                      
pipelines
  ---------------------------------------------------------------------------------

------------------------------------------------------------------------

## 4. Recommendations

  -----------------------------------------------------------------------
**Use Case**              **Recommended Approach**
  ------------------------- ---------------------------------------------
Small batch processing    Basic HashMap

Concurrent ingestion /    Concurrent Stream with AtomicInteger
multithreaded streams

Production-scale,         Caffeine Cache
real-time systems
  -----------------------------------------------------------------------

------------------------------------------------------------------------

## 5. Future Extensions

-   Replace sorting with frequency-vector hashing → O(N × K).
-   Use **Bloom filters** for pre-checking duplicates.
-   Integrate with **Kafka Streams** or **Flink** for real-time
    pipelines.

------------------------------------------------------------------------
