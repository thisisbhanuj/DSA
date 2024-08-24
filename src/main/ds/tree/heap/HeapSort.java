package main.ds.tree.heap;

import java.util.ArrayList;

public class HeapSort {
    public static final ArrayList<Integer> heap = new ArrayList<>();

    static int parentIndex(int position) {
        return (position - 1) / 2;
    }

    static int getLeftChildIndex(Integer position) {
        return 2 * position + 1;
    }

    static int getRightChildIndex(Integer position) {
        return 2 * position + 2;
    }

    static boolean isLeaf(Integer position) {
        return position >= heap.size() / 2 && position < heap.size();
    }

    static void swap(ArrayList<Integer> heap, Integer position, Integer index) {
        int temp = heap.get(position);
        heap.set(position, heap.get(index));
        heap.set(index, temp);
    }

    static void heapify(ArrayList<Integer> heap, Integer position, Integer size) {
        if (isLeaf(position)) return;

        int leftChildIndex = getLeftChildIndex(position);
        int rightChildIndex = getRightChildIndex(position);

        int largestIndex = position;

        if (leftChildIndex < size && heap.get(leftChildIndex) > heap.get(position)) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < size && heap.get(rightChildIndex) > heap.get(largestIndex)) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != position) {
            swap(heap, position, largestIndex);
            heapify(heap, largestIndex, size);
        }
    }

    static void createMaxHeap(int[] data) {
        for (int val : data) {
            heap.add(val);
        }

        for (int index = parentIndex(heap.size() - 1); index >= 0; index--) {
            heapify(heap, index, heap.size());
        }
    }

    static void heapSortInPlace() {
        // Extract elements one by one
        for (int i = heap.size() - 1; i > 0; i--) {
            swap(heap, 0, i);  // Move current root to end
            heapify(heap, 0, i);  // Heapify the reduced heap
        }
    }

    static void printSortedHeap() {
        System.out.println("Sorted Heap:");
        for (int val : heap) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    static void display() {
        for (int index = 0; index < heap.size() / 2; index++) {
            System.out.println("Parent : " + heap.get(index));

            int leftChildIndex = getLeftChildIndex(index);
            if (leftChildIndex < heap.size()) {
                System.out.println("Left : " + heap.get(leftChildIndex));
            }

            int rightChildIndex = getRightChildIndex(index);
            if (rightChildIndex < heap.size()) {
                System.out.println("Right : " + heap.get(rightChildIndex));
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] data = {5, 6, 3, 1, 7, 8, 0, 4, 2, 11};

        createMaxHeap(data);
        display();
        heapSortInPlace();
        printSortedHeap();
    }
}
