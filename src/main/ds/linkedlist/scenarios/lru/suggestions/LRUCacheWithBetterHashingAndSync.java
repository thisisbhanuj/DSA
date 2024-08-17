package main.ds.linkedlist.scenarios.lru.suggestions;

import ds.linkedlist.scenarios.HashedKey;

import java.util.HashMap;
import java.util.Map;

/*
To introduce thread safety features in LRU cache implementation,
we can use synchronization to ensure that multiple threads can safely access and modify the cache.
By using synchronization, you guarantee that each method is atomic and thread-safe.
However, keep in mind that synchronization can introduce performance overhead,
as only one thread can execute the synchronized block at a time.
If we have a high number of concurrent accesses to the cache,
we might want to explore alternative synchronization techniques or consider using concurrent data structures.
*/
public class LRUCacheWithBetterHashingAndSync {
    private static Map<HashedKey, DoubleLinkedList> cache; // Using HashedKey for better cache performance
    private static Integer cacheCapacity;
    private static DoubleLinkedList head;

    public LRUCacheWithBetterHashingAndSync(Integer capacity) {
        cacheCapacity = capacity;
        cache = new HashMap<>(capacity);

        DoubleLinkedList start = new DoubleLinkedList(new HashedKey(1), 1);
        DoubleLinkedList node1 = new DoubleLinkedList(new HashedKey(2), 2);
        DoubleLinkedList node2 = new DoubleLinkedList(new HashedKey(3), 3);
        DoubleLinkedList node3 = new DoubleLinkedList(new HashedKey(4), 4);
        start.next = node1;
        start.prev = null;
        node1.next = node2;
        node1.prev = start;
        node2.next = node3;
        node2.prev = node1;
        node3.next = null;
        node3.prev = node2;

        cache.put(start.key, start);
        cache.put(node1.key, start.next);
        cache.put(node2.key, node1.next);

        // Setting up the head
        head = start;
        // DoubleLinkedList.printList(head);
    }

    private static boolean isCacheFilled() {
        return cache.size() >= cacheCapacity;
    }

    private synchronized static boolean moveRecentlyUsed(HashedKey key, DoubleLinkedList node, boolean shouldUpdateCache) {
        DoubleLinkedList nodeToBeFound = node, prevNode = null;
        if (key != null) {
            if (nodeToBeFound != null) {
                prevNode = nodeToBeFound.prev;
                if (prevNode != null) {
                    prevNode.next = nodeToBeFound.next;
                    if (nodeToBeFound.next != null) {
                        nodeToBeFound.next.prev = prevNode;
                    }
                } else {
                    return true;
                }
                nodeToBeFound.next = head;
                head.prev = nodeToBeFound;
                nodeToBeFound.prev = null;
                head = nodeToBeFound;
            }

            if (shouldUpdateCache) cache.put(key, node);

            return true;
        }
        return false;
    }

    private synchronized static void removeLeastRecentlyUsed() {
        DoubleLinkedList nextNode = head;
        while (nextNode.next != null) {
            nextNode = nextNode.next;
        }
        Integer key = nextNode.key.getKey();
        nextNode.prev.next = null;
        nextNode.prev = null;
        if (key != null) {
            cache.remove(new HashedKey(key));
            System.out.println("Cache Key " + key + " evicted");
        }
    }

    private synchronized static boolean updateCache(HashedKey key, DoubleLinkedList node) {
        cache.put(key, node);
        return true;
    }

    private synchronized static DoubleLinkedList readFromStore(HashedKey key) {
        DoubleLinkedList nextNode = head;
        while (nextNode != null) {
            if (nextNode.key.equals(key)) {
                if (isCacheFilled()) {
                    removeLeastRecentlyUsed();
                    moveRecentlyUsed(key, nextNode, true);
                    DoubleLinkedList.printList(head);
                } else {
                    boolean isCacheUpdated = updateCache(key, nextNode);
                    if (isCacheUpdated) {
                        return nextNode;
                    }
                }
            }
            nextNode = nextNode.next;
        }
        return null;
    }

    private synchronized static DoubleLinkedList loadFromCache(HashedKey key) {
        if (cache.containsKey(key)) {
            System.out.println("Cache Hit for key: " + key);
            return cache.get(key);
        }
        System.out.println("Cache Miss for key: " + key);
        return null;
    }

    private synchronized static Integer find(Integer key) {
        HashedKey cacheKey = new HashedKey(key);
        DoubleLinkedList node = loadFromCache(cacheKey);
        if (node == null) {
            node = readFromStore(cacheKey);
            moveRecentlyUsed(cacheKey, node, true);
        } else {
            moveRecentlyUsed(cacheKey, node, false);
        }
        return node != null ? node.value : null;
    }

    // Note that the DoubleLinkedList class does not require synchronization
    // as it is only accessed and modified within the synchronized methods.
    private static class DoubleLinkedList {
        private HashedKey key;
        private Integer value;
        private DoubleLinkedList next;
        private DoubleLinkedList prev;

        DoubleLinkedList(HashedKey key, Integer value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        private static void printList(DoubleLinkedList head) {
            DoubleLinkedList current = head;
            while (current != null) {
                System.out.println(current.key + " - " + current.value);
                current = current.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Integer capacity = 3;
        new LRUCacheWithBetterHashingAndSync(capacity);
        find(2);
        DoubleLinkedList.printList(head);
        find(1);
        DoubleLinkedList.printList(head);
        find(3);
        DoubleLinkedList.printList(head);
        find(4);
        find(2);
        DoubleLinkedList.printList(head);
    }
}
