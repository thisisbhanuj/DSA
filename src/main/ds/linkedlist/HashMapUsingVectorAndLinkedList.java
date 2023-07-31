package ds.linkedlist;

import java.util.Vector;

public class HashMapUsingVectorAndLinkedList<K, V> {
    private Vector<LinkedListNode<K, V>> vector;

    private int capacity;

    public HashMapUsingVectorAndLinkedList(int size){
        this.capacity = size;
        this.vector = new Vector<>(size);
        for (int i = 0; i < size; i++) {
            vector.add(null);
        }
    }

    public int generateHashedIndex(K key){
        int hashedIndex = Math.abs(key.hashCode() % this.capacity);
        return hashedIndex;
    }

    public void put(K key, V value){
        int index = generateHashedIndex(key);
        LinkedListNode<K, V> head = vector.get(index);
        LinkedListNode<K, V> newNode = new LinkedListNode<>(key, value);
        if (head == null) {
            vector.set(index, newNode);
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
        LinkedListNode<K, V> head = vector.get(index);
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
        LinkedListNode<K, V> head = vector.get(index);
        if (head == null || size() == 0) {
            return false;
        } else {
            if (head.key.equals(key)) {
                vector.set(index, head.next);
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
            for (LinkedListNode<K, V> node : vector) {
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
        // the node variable is a local reference to the elements in the Vector,
        // and assigning null to the node variable will not update the actual elements in the Vector.
        /*for (LinkedListNode<K, V> node : vector) {
            node = null;
        }*/

        for (int i = 0; i < capacity; i++) {
            vector.set(i, null);
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
        HashMapUsingVectorAndLinkedList<String, Integer> hashSet = new HashMapUsingVectorAndLinkedList<>(10);

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
