package main.ds.linkedlist;

import java.util.ArrayList;

public class HashMapUsingLinkedList<K, V> {
    private final ArrayList<LinkedListNode<K, V>> store;

    private final int capacity;

    public HashMapUsingLinkedList(int size){
        this.capacity = size;
        this.store = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            store.add(null);
        }
    }

    public int generateHashedIndex(K key){
        return Math.abs(key.hashCode() % this.capacity);
    }

    public void put(K key, V value){
        int index = generateHashedIndex(key);
        LinkedListNode<K, V> head = store.get(index);
        LinkedListNode<K, V> newNode = new LinkedListNode<>(key, value);
        if (head == null) {
            store.set(index, newNode);
        } else {
            LinkedListNode<K, V> current = head;
            // Traverse all but the last node
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Key already exists, update the value
                    return;
                }
                current = current.next;
            }
            if ((current.key.equals(key))){
                current.value = value; // Key already exists, update the value
            } else {
                current.next = newNode; // Add the new node to the end of the linked list
            }
        }
    }

    public V get(K key){
        int index = generateHashedIndex(key);
        LinkedListNode<K, V> head = store.get(index);
        if (head == null) {
            return null;
        } else {
            LinkedListNode<K, V> current = head;
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
        LinkedListNode<K, V> head = store.get(index);
        if (head == null || size() == 0) {
            return false;
        } else {
            if (head.key.equals(key)) {
                store.set(index, head.next);
                return true;
            }
            LinkedListNode<K, V> prev = head;
            LinkedListNode<K, V> current = head.next;
            while (current != null) {
                if (current.key.equals(key)) {
                    prev.next = current.next;
                    return true;
                }
                prev = current;
                current = current.next;
            }
        }
        return false;
    }

    public int size() {
        int size = 0;
        if (this.capacity > 0) {
            for (LinkedListNode<K, V> node : store) {
                LinkedListNode<K, V> current = node;
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
            store.set(i, null);
        }
    }

    private static class LinkedListNode<K, V> {
        K key;
        V value;
        LinkedListNode<K, V> next;

        LinkedListNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public static void main(String[] args){
        HashMapUsingLinkedList<String, Integer> hashSet = new HashMapUsingLinkedList<>(10);

        hashSet.put("Apple", 5);
        hashSet.put("Banana", 10);
        hashSet.put("Orange", 7);
        hashSet.put("Orange", 7); // Adding duplicate key with updated value

        System.out.println("Size: " + hashSet.size());
        System.out.println("Value for 'Banana': " + hashSet.get("Banana"));

        boolean removed = hashSet.remove("Apple");
        System.out.println("Is removed? " + removed);
        System.out.println("Size: " + hashSet.size());

        hashSet.clear();
        System.out.println("Is empty after clear? " + hashSet.size());

    }
}
