package main.ds.tree.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Heap {
    public static ArrayList<Integer> heap;

    Heap(int[] unSortedArray) {
        heap = new ArrayList<>();
        for (int value : unSortedArray) {
            insert(value);
        }
    }

    public static Integer leftChildIndex(Integer position){
        return 2 * position + 1;
    }

    public static Integer rightChildIndex(Integer position){
        return 2 * position + 2;
    }

    public static Integer parentIndex(Integer position){
        return (position - 1)/2;
    }

    public static boolean isLeaf(Integer position) {
        return position >= heap.size() / 2 && position < heap.size();
    }

    public static void swap (ArrayList<Integer> list, Integer current, Integer largest) {
        Integer temp = list.get(current);
        list.set(current, list.get(largest));
        list.set(largest, temp);
    }

    public static void heapify(ArrayList<Integer> list, Integer current, Comparator<Integer> comparator) {
        if (isLeaf(current)) return;

        int leftChildIndex = leftChildIndex(current);
        int rightChildIndex = rightChildIndex(current);
        Integer target = current;

        if (leftChildIndex < list.size() && comparator.compare(list.get(leftChildIndex), list.get(target)) > 0) {
            target = leftChildIndex;
        }
        if (rightChildIndex < list.size() && comparator.compare(list.get(rightChildIndex), list.get(target)) > 0) {
            target = rightChildIndex;
        }

        if (!Objects.equals(target, current)) {
            swap(list, current, target);
            heapify(list, target, comparator);
        }
    }

    public static void maxHeapify(ArrayList<Integer> list, Integer current) {
        heapify(list, current, Comparator.naturalOrder());
    }

    public static void minHeapify(ArrayList<Integer> list, Integer current) {
        heapify(list, current, Comparator.reverseOrder());
    }

    public static Integer extractHighestPriority() {
        int highestPriority = heap.get(0);
        int endNodeIndex = heap.size() - 1;
        heap.set(0, heap.get(endNodeIndex));
        heap.remove(endNodeIndex);
        maxHeapify(heap, 0);
        return highestPriority;
    }

    /**
     * Method (Incremental Insertion):
     *
     * @apiNote When you use the insert method, you're adding elements to the heap one by one.
     * After adding an element, the insert method performs a "bubble-up" operation,
     * where the newly inserted element is compared with its parent and moved up the heap if necessary.
     * This ensures that the max heap property is maintained after each insertion.
     * But this can be inefficient if you're starting with an unsorted array because it rebuilds the heap from scratch for each new element.
     *
     * The time complexity of building a heap is O(n log n)
     *
     * Use insert when you're adding elements one by one, especially if you're receiving the data in a streaming manner or don't have all the elements upfront.
     */
    public static void insert(Integer value){
        heap.add(value);
        Integer current = heap.size() - 1;
        while ( value > heap.get(parentIndex(current)) && current >= 0){
            swap(heap, current, parentIndex(current));
            current = parentIndex(current);
        }
    }

    /**
     * This method is used when you have an entire array of elements and want to turn it into a heap in one go.
     * It works by calling maxHeapify starting from the last non-leaf node down to the root.
     * This ensures that the heap property is satisfied throughout the entire array.
     *
     * The time complexity of building a heap using buildMaxHeap is O(n)
     *
     * The reason buildMaxHeap is necessary after inserting elements from an unsorted array
     * is that it globally corrects the heap property in the most efficient way possible.
     *
     * Use buildMaxHeap when you already have a complete array of unsorted elements and want to efficiently transform it into a heap.
     */
    public static void buildMaxHeap() {
        for (int i = parentIndex(heap.size() - 1); i >= 0; i--) {
            maxHeapify(heap, i);
        }
    }

    public static void buildMinHeap() {
        for (int i = parentIndex(heap.size() - 1); i >= 0; i--) {
            minHeapify(heap, i);
        }
    }

    public static void display() {
        for (int i = 0; i < heap.size() / 2; i++) {
            System.out.println();
            System.out.println("Parent : " + heap.get(i));

            if (leftChildIndex(i) < heap.size()) {
                System.out.println("Left Child : " + heap.get(leftChildIndex(i)));
            }
            if (rightChildIndex(i) < heap.size()) {
                System.out.println("Right Child : " + heap.get(rightChildIndex(i)));
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        /**
         * Example Use Case:
         *
         * Streaming Data (Insert Method):
         * If you're receiving data in real-time and want to maintain a heap,
         * you would use the insert method to add each element to the heap as it arrives.
         *
         * Batch Processing (BuildMaxHeap Method):
         * If you have an entire dataset and want to process it as a heap,
         * you'd use the buildMaxHeap method to efficiently build the heap from the unsorted array.
         */
        int[] unSortedArray = {3, 5, 9, 6, 8, 20, 10, 12, 18, 9};

        // Create a Heap object with the unsorted array
        // Heap heapObject = new Heap(unSortedArray);

        // Create Max Heap
        heap = new ArrayList<>();
        for (int value : unSortedArray) {
            heap.add(value);
        }
        buildMaxHeap();
        display();
        System.out.println("**************************************");

        // Extract the highest priority (root of the heap)
        Integer highestPriority = extractHighestPriority();
        System.out.println("Highest Priority: " + highestPriority);
        // Print the heap after extracting the highest priority
        display();
        System.out.println("**************************************");

        // Create Min Heap
        heap = new ArrayList<>();
        for (int value : unSortedArray) {
            heap.add(value);
        }
        buildMinHeap();
        display();
        System.out.println("**************************************");
    }
}
