package main.ds.linkedlist.scenarios.lru;

import java.util.HashMap;
import java.util.Map;

/* Overall, the space complexity of the program is O(capacity)*/
public class LRUCacheUsingHashMapAndDoubleLinkedList {
    private static Map<Integer, Integer> cache;
    private static Integer cacheCapacity;
    private static DoubleLinkedList head;

    public LRUCacheUsingHashMapAndDoubleLinkedList(Integer capacity) {
        cacheCapacity = capacity;
        cache = new HashMap<>(capacity);

        DoubleLinkedList start = new DoubleLinkedList(1,1);
        DoubleLinkedList node1 = new DoubleLinkedList(2,2);
        DoubleLinkedList node2 = new DoubleLinkedList(3,3);
        DoubleLinkedList node3 = new DoubleLinkedList(4,4);
        start.next = node1; start.prev = null;
        node1.next = node2; node1.prev = start;
        node2.next = node3; node2.prev = node1;
        node3.next = null ; node3.prev = node2;
        // Setting up the head
        head = start;
    }

    /*
     * Time Complexity : O(n) as the operation needs to iterate through the doubly linked list
     *                    to find the node with the given key.
     */
    private static boolean moveRecentlyUsed(Integer key, Integer value, Boolean shouldUpdateCache) {
        DoubleLinkedList nodeToBeFound = head, prevNode = null;
        if (key != null) {
            // Find the node with the given key
            while (nodeToBeFound != null && nodeToBeFound.key != key) {
                prevNode = nodeToBeFound;
                nodeToBeFound = nodeToBeFound.next;
            }

            if (nodeToBeFound != null) {
                // Rearrange the links, if node is in between
                if (prevNode != null) {
                    prevNode.next = nodeToBeFound.next;
                    if (nodeToBeFound.next != null) {
                        nodeToBeFound.next.prev = prevNode;
                    }
                } else {
                    // The node is already at the front, no need to move
                    return true;
                }
                // Move it to the front
                nodeToBeFound.next = head;
                head.prev = nodeToBeFound;
                nodeToBeFound.prev = null;
                head = nodeToBeFound;
            }

            if (shouldUpdateCache) cache.put(key, value);

            return true;
        }
        return false;
    }

    /*
     * Time Complexity : This operation iterates over the DoubleLinkedList until it reaches the last node,
     *                   which takes O(capacity) time in the worst case.
     */
    private static void removeLeastRecentlyUsed(){
        DoubleLinkedList nextNode = head;
        while (nextNode.next != null) { // Stop at last node
            nextNode = nextNode.next;
        }
        // Found the last node
        Integer key = nextNode.key;
        nextNode.prev.next = null; // Resetting second-last to last node
        nextNode.prev = null;
        // Remove the cache entry as well
        if (key != null) cache.remove(key);
        System.out.println("Cache Key " + key + " evicted");
    }

    private static boolean isCacheFilled(){
        return cache.size() >= cacheCapacity;
    }
    /*
     * Time Complexity  : O(1) on average, as it involves adding or updating an entry in the HashMap.
     * Space Complexity : O(1)
     */
    private static boolean updateCache(Integer key, Integer value) {
        cache.put(key, value); // Either new or update
        return true;
    }

    /*
     * Time Complexity : O(n) on average.
     */
    private static Integer readFromStore(Integer key) {
        DoubleLinkedList nextNode = head;
        while (nextNode != null) {
            if (nextNode.key == key) {
                //System.out.println("Reading from the data store for key: " + key);
                if (isCacheFilled()) { // Checks is the cache is filled
                    removeLeastRecentlyUsed(); // Removes the least recently used node from the LL store
                    moveRecentlyUsed(key, nextNode.value, false); // Add the new key-value pair to the cache and the list
                    DoubleLinkedList.printList(head);
                } else {
                    boolean isCacheUpdated = updateCache(key, nextNode.value); // Update the cache
                    if (isCacheUpdated) {
                        //System.out.println("Cache updated for : " + key);
                        return nextNode.value;
                    }
                }
            }
            nextNode = nextNode.next;
        }
        return null;
    }

   /*
   * Time Complexity  : O(1) on average, as it involves a constant-time lookup in the HashMap
   * Space Complexity : O(capacity), as it stores a maximum of 'capacity' key-value pairs in the HashMap
   */
    private static Integer loadFromCache(Integer key) {
        if (cache.containsKey(key)) {
            System.out.println("Cache Hit for key: " + key);
            return cache.get(key);
        }
        System.out.println("Cache Miss for key: " + key);
        return null;
    }

    // Fetch value using key
    private static Integer find(Integer key) {
        Integer value = loadFromCache(key);
        if (value == null) { // Cache Miss
            value = readFromStore(key); // Look into the store
            // Move the recently accessed to the front of the Linked List store
            moveRecentlyUsed(key, value, true); // shouldUpdateCache set to true, as cache to be refreshed
        } else {
            moveRecentlyUsed(key, value, false); // shouldUpdateCache set to false, as cache already updated in readFromStore
        }
        return value;
    }

    private static class DoubleLinkedList {
        private Integer key;
        private Integer value;
        private DoubleLinkedList next;
        private DoubleLinkedList prev;

        DoubleLinkedList(Integer key, Integer value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        private static void printList(DoubleLinkedList head) {
            DoubleLinkedList current = head;
            while (current != null) {
                System.out.print(current.value + " ");
                current = current.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Setting Up initial Linked List data structure
        Integer capacity = 3;
        new LRUCacheUsingHashMapToStoreNodeRef(capacity);
        find(2);
        DoubleLinkedList.printList(head);
        find(1);
        DoubleLinkedList.printList(head);
        find(3);
        DoubleLinkedList.printList(head);
        // Cache will max out now
        find(4);
    }
}
