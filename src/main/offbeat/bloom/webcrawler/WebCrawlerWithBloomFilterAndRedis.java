package main.offbeat.bloom.webcrawler;

import redis.clients.jedis.Jedis;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.BitSet;
import java.util.function.Function;

public class WebCrawlerWithBloomFilterAndRedis {
    private final BitSet bitSet;
    private final int bitSetSize;
    private final int numberOfHashFunctions;
    private final Function<String, Integer>[] hashFunctions;
    private final Jedis redisClient;

    @SafeVarargs
    public WebCrawlerWithBloomFilterAndRedis(int bitSetSize, Jedis redisClient, Function<String, Integer>... hashFunctions) {
        this.bitSetSize = bitSetSize;
        this.hashFunctions = hashFunctions;
        this.numberOfHashFunctions = hashFunctions.length;
        this.bitSet = new BitSet(bitSetSize);
        this.redisClient = redisClient;
    }

    public void markUrlAsVisited(String url) {
        // Mark URL in Bloom filter
        for (Function<String, Integer> hashFunction : hashFunctions) {
            int hash = Math.abs(hashFunction.apply(url) % bitSetSize);
            bitSet.set(hash, true);
        }

        // Store URL in Redis for persistence
        redisClient.set(url, "visited");
    }

    public boolean hasUrlBeenVisited(String url) {
        // First, check in Bloom filter
        for (Function<String, Integer> hashFunction : hashFunctions) {
            int hash = Math.abs(hashFunction.apply(url) % bitSetSize);
            if (!bitSet.get(hash)) {
                return false; // Definitely not visited
            }
        }

        // If Bloom filter says "visited", double-check in Redis
        String value = redisClient.get(url);
        return value != null; // If the value exists in Redis, it has been visited
    }

    private static WebCrawlerWithBloomFilterAndRedis getWebCrawlerWithBloomFilterAndRedis(Jedis redisClient) {
        Function<String, Integer> hashFunc1 = String::hashCode;
        Function<String, Integer> hashFunc2 = s -> s.hashCode() + 31;

        // Create the Bloom Filter with Redis as a backup
        WebCrawlerWithBloomFilterAndRedis bloomFilterWithRedis = new WebCrawlerWithBloomFilterAndRedis(1000000, redisClient, hashFunc1, hashFunc2);

        // Mark some URLs as visited
        bloomFilterWithRedis.markUrlAsVisited("https://example.com");
        bloomFilterWithRedis.markUrlAsVisited("https://google.com");
        return bloomFilterWithRedis;
    }

    public static void main(String[] args) {
        // Load the .env file
        Dotenv dotenv = Dotenv.load();
        // Access environment variables
        String redisUrl = dotenv.get("UPSTASH_REDIS_REST_URL");
        String redisToken = dotenv.get("UPSTASH_REDIS_REST_TOKEN");
        String redisPort = dotenv.get("UPSTASH_REDIS_PORT");

        // Connect to Redis
        assert redisPort != null;
        Jedis redisClient = new Jedis(redisUrl, Integer.parseInt(redisPort), true);
        redisClient.auth(redisToken);

        // Hash functions for Bloom Filter
        WebCrawlerWithBloomFilterAndRedis bloomFilterWithRedis = getWebCrawlerWithBloomFilterAndRedis(redisClient);

        // Check if URLs have been visited
        System.out.println(bloomFilterWithRedis.hasUrlBeenVisited("https://example.com")); // true
        System.out.println(bloomFilterWithRedis.hasUrlBeenVisited("https://google.com"));  // true
        System.out.println(bloomFilterWithRedis.hasUrlBeenVisited("https://unknown.com")); // false (probably)
    }
}
