package main.offbeat.dash.lockfree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Represents a bucket in the extendible hash table.
 * Uses lock-free mechanisms for concurrent access.
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 */
class LockFreeBucket<K, V> {
    private final AtomicReferenceArray<Entry<K, V>> entries;
    private final int capacity;
    private final AtomicInteger size;

    public LockFreeBucket(int capacity) {
        this.capacity = capacity;
        this.entries = new AtomicReferenceArray<>(capacity);
        // AtomicInteger: Used for size to allow concurrent updates without locking.
        this.size = new AtomicInteger(0);
    }

    /**
     * Retrieves the value associated with the given key.
     * @param key The key to look up.
     * @return The value associated with the key, or null if not found.
     */
    public V get(K key) {
        int index = key.hashCode() % capacity;
        Entry<K, V> entry = entries.get(index);
        if (entry != null && entry.key.equals(key)) {
            return entry.value;
        }
        return null;
    }

    /**
     * Inserts a key-value pair into the bucket.
     * @param key The key to insert.
     * @param value The value to associate with the key.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean put(K key, V value) {
        int index = key.hashCode() % capacity;
        Entry<K, V> entry = new Entry<>(key, value);
        if (entries.compareAndSet(index, null, entry)) {
            size.incrementAndGet();
            return true;
        }
        return false;
    }

    /**
     * Removes a key-value pair from the bucket.
     * @param key The key to remove.
     * @return true if the removal was successful, false otherwise.
     */
    public boolean remove(K key) {
        int index = key.hashCode() % capacity;
        Entry<K, V> entry = entries.get(index);
        if (entry != null && entry.key.equals(key)) {
            entries.set(index, null);
            size.decrementAndGet();
            return true;
        }
        return false;
    }

    /**
     * Gets the current number of entries in the bucket.
     * @return The size of the bucket.
     */
    public int size() {
        return size.get();
    }

    /**
     * Retrieves all entries in the bucket.
     * @return An array of all entries in the bucket.
     */
    public Entry<K, V>[] getEntries() {
        @SuppressWarnings("unchecked")
        Entry<K, V>[] result = (Entry<K, V>[]) new Entry[size.get()];
        int index = 0;
        for (int i = 0; i < entries.length(); i++) {
            Entry<K, V> entry = entries.get(i);
            if (entry != null) {
                result[index++] = entry;
            }
        }
        return result;
    }
}

/**
 * Represents an entry in the hash table.
 * Associates a key with a value.
 */
class Entry<K, V> {
    final K key;
    V value;

    Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

/**
 * Extendible Hash Table implementation.
 * Uses lock-free mechanisms and atomic operations.
 *
 * @implNote
 * Trade-offs and Considerations
 * Complexity: Lock-free implementations are complex and require careful handling of concurrent updates and memory visibility.
 * Performance: Lock-free designs can improve performance in high-concurrency scenarios but may not always be suitable for all workloads.
 * Consistency: Ensuring consistency and avoiding race conditions without locks requires rigorous testing and validation.
 */
public class LockFreeExtendibleHashTable<K, V> {
    private final int bucketSize;
    private AtomicReferenceArray<LockFreeBucket<K, V>> directory;
    private AtomicInteger globalDepth;

    /**
     * Constructs a LockFreeExtendibleHashTable with the specified bucket size.
     * @param bucketSize The maximum number of entries per bucket before it needs to be split.
     */
    @SuppressWarnings("unchecked")
    public LockFreeExtendibleHashTable(int bucketSize) {
        this.bucketSize = bucketSize;
        this.globalDepth = new AtomicInteger(1); // Initial global depth is 1.
        int initialSize = 1 << globalDepth.get(); // Directory size is 2^globalDepth.
        this.directory = new AtomicReferenceArray<>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            directory.set(i, new LockFreeBucket<>(bucketSize)); // Initialize all buckets.
        }
    }

    /**
     * Retrieves the value associated with the given key.
     * @param key The key to look up.
     * @return The value associated with the key.
     */
    public V get(K key) {
        int index = hash(key);
        return directory.get(index).get(key);
    }

    /**
     * Inserts a key-value pair into the hash table.
     * If the bucket is full, it will be split to accommodate more entries.
     * @param key The key to insert.
     * @param value The value to associate with the key.
     */
    public void put(K key, V value) {
        int index = hash(key);
        LockFreeBucket<K, V> bucket = directory.get(index);

        if (bucket.size() >= bucketSize) {
            splitBucket(index);
            index = hash(key); // Recompute bucket index after split.
            bucket = directory.get(index);
        }
        bucket.put(key, value);
    }

    /**
     * Removes the key-value pair associated with the given key.
     * @param key The key to remove.
     */
    public void remove(K key) {
        int index = hash(key);
        directory.get(index).remove(key);
    }

    /**
     * Splits a bucket when it becomes full.
     * Redistributes entries and updates the directory.
     * @param index The index of the bucket to split.
     */
    private void splitBucket(int index) {
        int localDepth = Integer.bitCount(directory.length() - 1); // Local depth of the bucket.

        // If the local depth equals the global depth, grow the directory.
        if (localDepth == globalDepth.get()) {
            growDirectory();
        }

        int mirrorIndex = index ^ (1 << localDepth); // Compute the mirror index.
        LockFreeBucket<K, V> newBucket = new LockFreeBucket<>(bucketSize);
        LockFreeBucket<K, V> oldBucket = directory.get(index);

        // Assign the new bucket to the mirror index.
        directory.set(mirrorIndex, newBucket);

        // Rehash entries from the old bucket to the new bucket.
        for (Entry<K, V> entry : oldBucket.getEntries()) {
            int newHash = hash(entry.key); // Compute the new index.
            directory.get(newHash).put(entry.key, entry.value); // Insert into the correct bucket.
        }
        directory.set(index, new LockFreeBucket<>(bucketSize)); // Create a new bucket at the old index.
    }

    /**
     * Grows the directory when necessary.
     * Doubles the directory size and redistributes the buckets.
     */
    private void growDirectory() {
        int oldSize = directory.length();
        int newSize = oldSize * 2;
        // AtomicReferenceArray: Used for buckets and the directory to allow concurrent updates without locking.
        AtomicReferenceArray<LockFreeBucket<K, V>> newDirectory = new AtomicReferenceArray<>(newSize);

        // Copy existing buckets and mirror them.
        for (int i = 0; i < oldSize; i++) {
            newDirectory.set(i, directory.get(i));
            newDirectory.set(i + oldSize, directory.get(i));
        }

        directory = newDirectory; // Update the directory.
        globalDepth.incrementAndGet(); // Increase global depth.
    }

    /**
     * Computes the index of the bucket in the directory for a given key.
     * @param key The key to hash.
     * @return The index of the bucket.
     */
    private int hash(K key) {
        return key.hashCode() & (directory.length() - 1); // Hash code with mask for bucket index.
    }

    /**
     * Gets the current size of the directory.
     * @return The size of the directory.
     */
    public int size() {
        return directory.length(); // Number of buckets in the directory.
    }
}

