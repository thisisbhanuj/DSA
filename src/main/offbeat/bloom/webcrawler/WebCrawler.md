To design a system that efficiently checks if a given URL has been visited before in a **web crawler**, we need to focus on **space efficiency** and **speed** due to the potentially large number of URLs being processed.

### Requirements:
1. **Efficient Lookup**: Ability to quickly check if a URL has been crawled.
2. **Low Memory Usage**: The system needs to handle millions/billions of URLs efficiently.
3. **Scalable**: The system must scale with the increasing number of URLs.
4. **False Positives are Acceptable**: We can tolerate occasional false positives (thinking a URL has been crawled when it hasn't), but **false negatives** (missing a URL that has been crawled) are unacceptable.

### Solution: Use a **Bloom Filter** to track visited URLs

A **Bloom Filter** is an ideal fit because it provides:
- **Constant time complexity** for insertion and lookup (`O(1)`).
- **Space efficiency** by using a bit array and multiple hash functions.

---

### System Design Using Bloom Filter

1. **Components**:
    - **Bloom Filter**: To track URLs that have already been visited.
    - **Hash Functions**: Multiple hash functions to reduce collisions and minimize false positives.
    - **Database/Storage** (optional): For persisting URLs or a more exact backup check for critical URLs where false positives cannot be tolerated.

2. **Architecture**:
    - **URL Fetcher**: Fetches URLs from a queue or URL list.
    - **Visited URL Checker**: Uses the Bloom filter to check if the URL has been visited.
    - **URL Downloader**: Downloads the content if the URL has not been visited.
    - **Bloom Filter Updater**: Updates the Bloom filter after visiting a new URL.
    - **URL Queue**: Manages the queue of URLs to visit (breadth-first or depth-first traversal).

---

### Detailed Workflow:
1. **URL Fetcher**:
    - Continuously pulls new URLs from the **URL queue** for crawling.
    - Before processing the URL, it checks if it’s already visited using the **Bloom filter**.

2. **Visited URL Checker**:
    - Checks the Bloom filter using multiple hash functions.
    - If the Bloom filter says "yes" (the URL is likely visited), the crawler skips this URL.
    - If the Bloom filter says "no" (the URL hasn’t been visited), it continues to download the content.

3. **URL Downloader**:
    - Downloads and processes the page's content.

4. **Bloom Filter Updater**:
    - Once the URL is visited, update the Bloom filter to mark it as "visited" using multiple hash functions to set the appropriate bits.

5. **Scalable Design Considerations**:
    - **Sharding**: If the Bloom filter grows too large, you can split it into multiple smaller filters (e.g., by domain or hash-based sharding).
    - **Distributed System**: For a distributed crawler, each worker could have its own Bloom filter, and filters could be synchronized periodically.

---

### Bloom Filter Parameters:
- **Bit Array Size (`m`)**: This determines how much memory the Bloom filter will use.
- **Number of Hash Functions (`k`)**: The more hash functions, the lower the false positive rate, but more computation will be required.
- **False Positive Rate (`p`)**: Acceptable rate of false positives.

You can calculate the optimal size of the Bloom filter and the number of hash functions using the formulas:
- `m = -(n * ln(p)) / (ln(2)^2)` where `n` is the number of URLs, `p` is the acceptable false positive rate.
- `k = (m/n) * ln(2)`

---

### Code Example:
```java
import java.util.BitSet;
import java.util.function.Function;

public class WebCrawlerBloomFilter {
    private BitSet bitSet;
    private int bitSetSize;
    private int numberOfHashFunctions;
    private Function<String, Integer>[] hashFunctions;

    @SafeVarargs
    public WebCrawlerBloomFilter(int bitSetSize, Function<String, Integer>... hashFunctions) {
        this.bitSetSize = bitSetSize;
        this.hashFunctions = hashFunctions;
        this.numberOfHashFunctions = hashFunctions.length;
        this.bitSet = new BitSet(bitSetSize);
    }

    public void markUrlAsVisited(String url) {
        for (Function<String, Integer> hashFunction : hashFunctions) {
            int hash = Math.abs(hashFunction.apply(url) % bitSetSize);
            bitSet.set(hash, true);
        }
    }

    public boolean hasUrlBeenVisited(String url) {
        for (Function<String, Integer> hashFunction : hashFunctions) {
            int hash = Math.abs(hashFunction.apply(url) % bitSetSize);
            if (!bitSet.get(hash)) {
                return false; // URL is not visited
            }
        }
        return true; // URL might have been visited (false positive possible)
    }

    public static void main(String[] args) {
        // Example with two hash functions
        Function<String, Integer> hashFunc1 = String::hashCode;
        Function<String, Integer> hashFunc2 = s -> s.hashCode() + 31;

        WebCrawlerBloomFilter bloomFilter = new WebCrawlerBloomFilter(1000000, hashFunc1, hashFunc2);

        // Mark some URLs as visited
        bloomFilter.markUrlAsVisited("https://example.com");
        bloomFilter.markUrlAsVisited("https://google.com");

        // Check if URLs have been visited
        System.out.println(bloomFilter.hasUrlBeenVisited("https://example.com")); // true
        System.out.println(bloomFilter.hasUrlBeenVisited("https://google.com"));  // true
        System.out.println(bloomFilter.hasUrlBeenVisited("https://unknown.com")); // false (probably)
    }
}
```

---

### Enhancements:
1. **Scaling with Shards**: If the URL space is too large, you could partition the URLs into shards (e.g., based on the domain or hash values) and maintain separate Bloom filters for each shard.

2. **Distributed Bloom Filter**: For a distributed system, you can replicate Bloom filters across nodes, or synchronize them periodically to keep track of the global state.

3. **Backed by a Persistent Storage**: For high-value URLs or to avoid false positives on critical pages, you can back the Bloom filter with a persistent **database** (like Redis or a NoSQL store) to double-check if a URL has been visited. If the Bloom filter returns "true", you can do a second lookup in a database to ensure accuracy.

---

This approach is space-efficient and can handle millions of URLs, though it trades **absolute accuracy** for **efficiency** due to the possibility of false positives.