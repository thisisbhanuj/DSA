package offbeat.dash.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a bucket in the extendible hash table.
 * Stores key-value pairs and handles basic operations like put, get, and remove.
 */
class Bucket<K, V> {
    private final List<Entry<K, V>> entries;

    public Bucket() {
        entries = new ArrayList<>();
    }

    /**
     * Retrieves the value associated with the given key.
     * @param key The key to look up.
     * @return The value associated with the key, or null if not found.
     */
    public synchronized V get(K key) {
        for (Entry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Inserts a key-value pair into the bucket.
     * If the key already exists, updates the existing value.
     * @param key The key to insert.
     * @param value The value to associate with the key.
     */
    public synchronized void put(K key, V value) {
        for (Entry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        entries.add(new Entry<>(key, value));
    }

    /**
     * Removes a key-value pair from the bucket.
     * @param key The key to remove.
     * @return true if the key was removed, false otherwise.
     */
    public synchronized boolean remove(K key) {
        return entries.removeIf(entry -> entry.key.equals(key));
    }

    /**
     * Retrieves all entries in the bucket.
     * @return A list of all entries in the bucket.
     */
    public synchronized List<Entry<K, V>> getEntries() {
        return new ArrayList<>(entries);
    }

    /**
     * Gets the number of entries in the bucket.
     * @return The size of the bucket.
     */
    public synchronized int size() {
        return entries.size();
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
 * Handles dynamic resizing and concurrent access.
 * A dynamic hashing scheme that grows the hash table by splitting buckets when they overflow.
 *
 * @param <K> The type of keys in the hash table.
 * @param <V> The type of values in the hash table.
 *
 * @see <a href="https://arxiv.org/pdf/2003.07302">...</a>
 */
public class ExtendibleHashTable<K, V> {
    private final int bucketSize;
    private int globalDepth;
    private Bucket<K, V>[] directory;
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Constructs an ExtendibleHashTable with the specified bucket size.
     * @param bucketSize The maximum number of entries per bucket before it needs to be split.
     */
    @SuppressWarnings("unchecked")
    public ExtendibleHashTable(int bucketSize) {
        this.bucketSize = bucketSize;
        this.globalDepth = 1; // Initial global depth is 1.
        this.directory = new Bucket[1 << globalDepth]; // Directory has 2^globalDepth buckets.
        for (int i = 0; i < directory.length; i++) {
            directory[i] = new Bucket<>(); // Initialize all buckets.
        }
    }

    /**
     * Retrieves the value associated with the given key.
     * @param key The key to look up.
     * @return The value associated with the key.
     */
    public V get(K key) {
        int hash = hash(key); // Compute bucket index.
        return directory[hash].get(key); // Retrieve value from the bucket.
    }

    /**
     * Inserts a key-value pair into the hash table.
     * If the bucket is full, it will be split to accommodate more entries.
     * @param key The key to insert.
     * @param value The value to associate with the key.
     */
    public void put(K key, V value) {
        lock.lock();
        try {
            int hash = hash(key); // Compute bucket index.
            Bucket<K, V> bucket = directory[hash];

            // If the bucket is full, split it.
            if (bucket.size() >= bucketSize) {
                splitBucket(hash);
                hash = hash(key); // Recompute bucket index after split.
            }
            directory[hash].put(key, value); // Insert key-value pair.
        } finally {
            lock.unlock(); // Release lock.
        }
    }

    /**
     * Removes the key-value pair associated with the given key.
     * @param key The key to remove.
     */
    public void remove(K key) {
        lock.lock();
        try {
            int hash = hash(key); // Compute bucket index.
            directory[hash].remove(key); // Remove entry from the bucket.
        } finally {
            lock.unlock(); // Release lock.
        }
    }

    /**
     * Splits a bucket when it becomes full.
     * Redistributes entries and updates the directory.
     * @param hash The index of the bucket to split.
     */
    private void splitBucket(int hash) {
        int localDepth = Integer.bitCount(directory.length - 1); // Local depth of the bucket.

        // If the local depth equals the global depth, grow the directory.
        if (localDepth == globalDepth) {
            growDirectory();
        }

        int mirrorIndex = hash ^ (1 << localDepth); // Compute the mirror index.

        Bucket<K, V> newBucket = new Bucket<>(); // Create a new bucket for the split.
        Bucket<K, V> oldBucket = directory[hash];

        directory[mirrorIndex] = newBucket; // Assign the new bucket to the mirror index.

        // Rehash entries from the old bucket to the new bucket.
        for (Entry<K, V> entry : oldBucket.getEntries()) {
            int newHash = hash(entry.key); // Compute the new index.
            directory[newHash].put(entry.key, entry.value); // Insert into the correct bucket.
        }

        directory[hash] = new Bucket<>(); // Create a new bucket at the old index.
        // Reinsert entries to maintain the split.
        for (Entry<K, V> entry : oldBucket.getEntries()) {
            int newHash = hash(entry.key); // Compute the new index.
            directory[newHash].put(entry.key, entry.value); // Insert into the correct bucket.
        }
    }

    /**
     * Grows the directory when necessary.
     * Doubles the directory size and redistributes the buckets.
     */
    private void growDirectory() {
        globalDepth++; // Increase global depth.
        int newSize = 1 << globalDepth; // New size of the directory.
        Bucket<K, V>[] newDirectory = new Bucket[newSize];

        // Copy existing buckets and mirror them.
        for (int i = 0; i < directory.length; i++) {
            newDirectory[i] = directory[i];
            newDirectory[i + (1 << (globalDepth - 1))] = directory[i];
        }

        directory = newDirectory; // Update the directory.
    }

    /**
     * Computes the index of the bucket in the directory for a given key.
     * @param key The key to hash.
     * @return The index of the bucket.
     */
    private int hash(K key) {
        return key.hashCode() & (directory.length - 1); // Hash code with mask for bucket index.
    }

    /**
     * Gets the current size of the directory.
     * @return The size of the directory.
     */
    public int size() {
        return directory.length; // Number of buckets in the directory.
    }
}
