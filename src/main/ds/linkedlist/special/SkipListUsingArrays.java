package main.ds.linkedlist.special;
import java.util.*;

/*
* A skip list is a probabilistic data structure that allows for efficient search, insertion and deletion of elements in a sorted list.
* It is a linked list with additional layers of linked lists that are probabilistically built on top of the original linked list.
* Each additional layer of links contains fewer elements, but no new elements.
*-----------------------------------------------------------------------------------------------------------------------
*
** Imagine you have a bunch of numbered cards. You could start by sorting them in ascending order, but this would take a long time.
**
** A skip list is like a ladder, with each rung representing a different layer of the list.
** The top rung is the sparsest, and the bottom rung is the densest.
** Each card has a forward pointer that points to the next card on the same rung that has a higher value.
** To put the cards in order, you would start at the top rung and follow the forward pointers
** until you reach a card with the value you are looking for.
** If you don't find the card you are looking for, you would move down to the next rung and repeat the process.
** For example, to find the card with the value 10, you would start at the top rung and follow the forward pointers
** until you reach the card with the value 15. Then, you would move down to the next rung and follow the forward pointers
** until you reach the card with the value 10. As you can see, the skip list allows you to quickly skip over many cards in the list,
** which makes it very efficient for search operations.
*
*-----------------------------------------------------------------------------------------------------------------------
* The ideology behind building a skip list in this way is to increase the chances of finding the element you are looking for quickly.
* The more layers of links there are, the more likely it is that you will find the element you are looking for in a single layer.
*
* The process of building a skip list is as follows:
* 1. Create a linked list.
* 2. For each element in the linked list, randomly decide whether or not to add it to the next layer of links.
* 3. Repeat step 2 until you have reached the desired number of layers.
*
* To search for an element in a skip list, you start at the top layer and
* follow the forward pointers until you reach a node that is smaller than the element you are looking for.
* Then, you move down to the next layer and repeat the process.
* This process continues until you reach a node that contains the element you are looking for,
* or you reach the bottom layer of the skip list.
* To insert an element into a skip list, you first create a new node and add it to the linked list.
* Then, you randomly decide whether or not to add the new node to each of the higher layers of the skip list.
* To delete an element from a skip list, you first find the node that contains the element you want to delete.
* Then, you remove the node from the linked list and from any of the higher layers of the skip list that it is in.
*
* Skip lists are a very efficient data structure for search, insertion, and deletion operations.
* They are typically faster than balanced trees, but not as fast as hash tables.
* However, skip lists are more versatile than hash tables, as they can be used to store elements in any order.
*/
public class SkipListUsingArrays<K extends Comparable<K>, V> {
    private static final double PROBABILITY = 0.5;
    private Node head;
    private int maxLevel;
    private Random random;

    private class Node {
        private K key;
        private V value;
        private List<Node> next;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            next = new ArrayList<>(level);
        }
    }

    public SkipListUsingArrays() {
        head = new Node(null, null, 1);
        maxLevel = 1;
        random = new Random();
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < PROBABILITY && level < maxLevel + 1) {
            level++;
        }
        return level;
    }

    public void put(K key, V value) {
        int level = randomLevel();
        if (level > maxLevel) {
            for (int i = maxLevel + 1; i <= level; i++) {
                head.next.add(null);
            }
            maxLevel = level;
        }

        Node newNode = new Node(key, value, level);
        Node current = head;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (current.next.get(i) != null && current.next.get(i).key.compareTo(key) < 0) {
                current = current.next.get(i);
            }
            if (i < level) {
                newNode.next.add(i, current.next.get(i));
                current.next.set(i, newNode);
            }
        }
    }

    public V get(K key) {
        Node current = head;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (current.next.get(i) != null && current.next.get(i).key.compareTo(key) < 0) {
                current = current.next.get(i);
            }
        }
        current = current.next.get(0);
        if (current != null && current.key.compareTo(key) == 0) {
            return current.value;
        }
        return null;
    }

    public void remove(K key) {
        Node current = head;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (current.next.get(i) != null && current.next.get(i).key.compareTo(key) < 0) {
                current = current.next.get(i);
            }
            if (current.next.get(i) != null && current.next.get(i).key.compareTo(key) == 0) {
                current.next.set(i, current.next.get(i).next.get(i));
            }
        }
    }
}

