package main.offbeat.bloom.webcrawler;

import java.util.BitSet;
import java.util.function.Function;

public class WebCrawlerBloomFilter {
    private final BitSet bitSet;
    private final int bitSetSize;
    private final int numberOfHashFunctions;
    private final Function<String, Integer>[] hashFunctions;

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
