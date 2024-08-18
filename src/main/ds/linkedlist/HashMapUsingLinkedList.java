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

    public void put(K key, V value) {
        int index = generateHashedIndex(key); // Hash to find the bucket index
        LinkedListNode<K, V> head = store.get(index); // Get the head of the linked list at this index

        if (head == null) { // If the bucket is empty
            store.set(index, new LinkedListNode<>(key, value)); // Insert the new node
        } else {
            LinkedListNode<K, V> current = head;
            while (current != null) { // Traverse the list
                if (current.key.equals(key)) { // Compare the key
                    current.value = value; // Update the existing node
                    return;
                }
                if (current.next == null) { // If end of list is reached
                    current.next = new LinkedListNode<>(key, value); // Append new node
                    return;
                }
                current = current.next; // Move to next node
            }
        }
    }

    public V get(K key){
        int index = generateHashedIndex(key);
        LinkedListNode<K, V> current = store.get(index);
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(K key) {
        int index = generateHashedIndex(key);
        LinkedListNode<K, V> head = store.get(index);
        if (head == null) {
            return false;
        }
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
        return false;
    }

    public int size() {
        int size = 0;
        for (LinkedListNode<K, V> node : store) {
            LinkedListNode<K, V> current = node;
            while (current != null) {
                size++;
                current = current.next;
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
        HashMapUsingLinkedList<String, Integer> hashMap = new HashMapUsingLinkedList<>(10);

        hashMap.put("Apple", 5);
        hashMap.put("Banana", 10);
        hashMap.put("Orange", 7);
        hashMap.put("Orange", 8); // Adding duplicate key with updated value

        System.out.println("Size: " + hashMap.size());
        System.out.println("Value for 'Banana': " + hashMap.get("Banana"));

        boolean removed = hashMap.remove("Apple");
        System.out.println("Is removed? " + removed);
        System.out.println("Size: " + hashMap.size());

        hashMap.clear();
        System.out.println("Size after clear: " + hashMap.size());
    }
}
