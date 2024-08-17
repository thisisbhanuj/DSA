package main.ds.linkedlist.scenarios.lru.suggestions;

import java.util.LinkedHashMap;
import java.util.Map;

/*
Instead of a HashMap, we can utilize a LinkedHashMap, which maintains the insertion order of the elements.
It provides the necessary functionality for implementing an LRU cache without the need for a separate linked list.
It has an optional access-order mode that automatically moves accessed elements to the end of the iteration order.
*/
public class LRUCacheByLinkedHashMap {
    private Integer capacity;
    private Map<Integer, String> cache;

    LRUCacheByLinkedHashMap(Integer capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<Integer, String>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                // return super.removeEldestEntry(eldest);
                return size() > capacity;
            }
        };
    }

    public String getData(Integer key){
        return cache.getOrDefault(key, null);
    }

    public void put(Integer key, String value) {
        cache.put(key, value);
    }

    public Integer size() {
        return cache.size();
    }
    public static void main(String[] args) {
        LRUCacheByLinkedHashMap cache = new LRUCacheByLinkedHashMap(3);
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        cache.put(4, "Four"); // This will cause 1 to be evicted

        System.out.println("Size : " + cache.size()); // {4,3,2}

        System.out.println(cache.getData(2)); // Output: Two
        System.out.println(cache.getData(3)); // Output: Three
        System.out.println(cache.getData(4)); // Output: Three
        System.out.println(cache.getData(1)); // Output: Null
    }
}
