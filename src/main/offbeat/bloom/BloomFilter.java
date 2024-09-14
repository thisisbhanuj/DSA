package main.offbeat.bloom;

import java.util.BitSet;
import java.util.function.Function;

public class BloomFilter<T> {
    private final BitSet bitSet;
    private final int bitSetSize;
    private final Function<T, Integer>[] hashFunctions;

    @SafeVarargs
    public BloomFilter(int bitSetSize, Function<T, Integer>... hashFunctions) {
        this.bitSetSize = bitSetSize;
        this.hashFunctions = hashFunctions;
        this.bitSet = new BitSet(bitSetSize);
    }

    public void add(T item) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int hash = Math.abs(hashFunction.apply(item) % bitSetSize);
            bitSet.set(hash, true);
        }
    }

    public boolean mightContain(T item) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int hash = Math.abs(hashFunction.apply(item) % bitSetSize);
            if (!bitSet.get(hash)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Example with two hash functions
        Function<String, Integer> hashFunc1 = String::hashCode;
        Function<String, Integer> hashFunc2 = s -> s.hashCode() + 31;

        BloomFilter<String> bloomFilter = new BloomFilter<>(1000, hashFunc1, hashFunc2);

        bloomFilter.add("apple");
        bloomFilter.add("banana");

        System.out.println(bloomFilter.mightContain("apple")); // true
        System.out.println(bloomFilter.mightContain("banana")); // true
        System.out.println(bloomFilter.mightContain("cherry")); // false (probably)
    }
}
