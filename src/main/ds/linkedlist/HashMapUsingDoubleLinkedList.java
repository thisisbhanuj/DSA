package main.ds.linkedlist;

import java.util.Vector;

public class HashMapUsingDoubleLinkedList<K, V> {
    private Vector<DoublyLinkedListNode<K, V>> vector;

    private int capacity;

    public HashMapUsingDoubleLinkedList(int size) {
        this.capacity = size;
        this.vector = new Vector<>(size);
        for (int i = 0; i < size; i++) {
            vector.add(null);
        }
    }

    public int generateHashedIndex(K key) {
        int hashedIndex = Math.abs(key.hashCode() % this.capacity);
        return hashedIndex;
    }

    public void put(K key, V value) {
        int index = generateHashedIndex(key);
        DoublyLinkedListNode<K, V> head = vector.get(index);
        DoublyLinkedListNode<K, V> newNode = new DoublyLinkedListNode<>(key, value);
        if (head == null) {
            vector.set(index, newNode);
        } else {
            DoublyLinkedListNode<K, V> current = head;
            // Traverse all but the last node
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Key already exists, update the value
                    return;
                }
                current = current.next;
            }

            // Check the last node
            if (current.key.equals(key)) {
                current.value = value; // Key already exists, update the value
            } else {
                current.next = newNode; // Add the new node to the end of the linked list
                newNode.prev = current;
            }
        }
    }


    public V get(K key) {
        int index = generateHashedIndex(key);
        DoublyLinkedListNode<K, V> head = vector.get(index);
        if (head == null) {
            return null;
        } else {
            DoublyLinkedListNode<K, V> current = head;
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value;
                }
                current = current.next;
            }
        }
        return null;
    }

    public boolean remove(K key) {
        int index = generateHashedIndex(key);
        DoublyLinkedListNode<K, V> head = vector.get(index);
        if (head == null || size() == 0) {
            return false;
        } else {
            if (head.key.equals(key)) {
                vector.set(index, head.next);
                if (head.next != null) {
                    head.next.prev = null;
                }
                return true;
            }
            DoublyLinkedListNode<K, V> current = head;
            while (current != null) {
                if (current.key.equals(key)) {
                    if (current.prev != null) {
                        current.prev.next = current.next;
                    }
                    if (current.next != null) {
                        current.next.prev = current.prev;
                    }
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    public int size() {
        int size = 0;
        if (this.capacity > 0) {
            for (DoublyLinkedListNode<K, V> node : vector) {
                DoublyLinkedListNode<K, V> current = node;
                while (current != null) {
                    size++;
                    current = current.next;
                }
            }
        }
        return size;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            DoublyLinkedListNode<K, V> head = vector.get(i);
            while (head != null) {
                DoublyLinkedListNode<K, V> next = head.next;
                head.prev = null;
                head.next = null;
                head = next;
            }
            vector.set(i, null);
        }
    }

    private static class DoublyLinkedListNode<K, V> {
        K key;
        V value;
        DoublyLinkedListNode<K, V> prev;
        DoublyLinkedListNode<K, V> next;

        DoublyLinkedListNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        HashMapUsingVectorAndLinkedList<String, Integer> hashSet = new HashMapUsingVectorAndLinkedList<>(10);

        hashSet.put("Apple", 5);
        hashSet.put("Banana", 10);
        hashSet.put("Orange", 7);

        System.out.println("Size: " + hashSet.size());
        System.out.println("Value for 'Banana': " + hashSet.get("Banana"));

        boolean removed = hashSet.remove("Apple");
        System.out.println("Is removed? " + removed);
        System.out.println("Size: " + hashSet.size());

        hashSet.clear();
        System.out.println("Is empty after clear? " + hashSet.size());
    }
}
