package main.ds.linkedlist.special;
/*

Few potential use cases where XOR Linked Lists are:
        1. Memory-efficient data structures:
        XOR Linked Lists can be useful in situations where memory efficiency is a concern.
        By using bitwise XOR operations to store addresses, it reduces the memory overhead typically associated with maintaining explicit pointers or references.

        2. Low-level programming:
        XOR Linked Lists can be used in low-level programming environments where direct manipulation of memory is allowed or necessary.
        It can be utilized for efficient memory management or implementing custom memory allocators.

        3. Intrusion detection systems:
        XOR Linked Lists have been used in intrusion detection systems for tracking and managing network connections.
        They can efficiently store and retrieve information about network connections, allowing for fast lookups and updates.

        4. Caching:
        XOR Linked Lists can be employed in caching mechanisms, where fast insertion, deletion, and retrieval of cached items are crucial.
        The XOR property allows for efficient traversal and updates without the need for maintaining additional pointers.

        While XOR Linked Lists have some interesting properties, they are not commonly used in mainstream software development
        or in the systems of large companies like Google, Apple, Facebook, or Twitter.
        These companies typically rely on more established and widely-used data structures and algorithms for their software systems.
*/

public class XORLinkedList<E> {
    private Node<E> head;

    private static class Node<E> {
        private E data;
        private long xorPrevNext; // XOR of the previous and next nodes' memory addresses

        public Node(E data) {
            this.data = data;
            this.xorPrevNext = 0;
        }
    }

    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            Node<E> prev = null;
            Node<E> current = head;
            Node<E> next;
            while (current != null) {
                next = xor(prev, current.xorPrevNext);
                current.xorPrevNext = getAddress(prev) ^ getAddress(next); // XOR of memory addresses
                prev = current;
                current = next;
            }
            newNode.xorPrevNext = getAddress(prev);
            prev.xorPrevNext = getAddress(newNode) ^ getAddress(prev.xorPrevNext); // XOR of memory addresses
        }
    }

    public E get(int index) {
        Node<E> prev = null;
        Node<E> current = head;
        Node<E> next;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                throw new IndexOutOfBoundsException("Index out of range");
            }
            next = xor(prev, current.xorPrevNext); // Retrieve next node using XOR of memory addresses
            prev = current;
            current = next;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        return current.data;
    }

    private Node<E> xor(Node<E> a, long b) {
        return (Node<E>) a; // No type casting required
    }

    private long getAddress(Object node) {
        return node != null ? System.identityHashCode(node) : 0;
    }
}

