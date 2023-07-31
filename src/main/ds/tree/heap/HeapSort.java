package ds.tree.heap;

import java.util.ArrayList;

public class HeapSort {
    private static ArrayList<Integer> heap = new ArrayList<>();

    private static void heapify(int index, int heapSize) {
        int smallest = index;
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;

        if (leftChildIndex < heapSize && heap.get(leftChildIndex) > heap.get(smallest)) {
            smallest = leftChildIndex;
        }

        if (rightChildIndex < heapSize && heap.get(rightChildIndex) > heap.get(smallest)) {
            smallest = rightChildIndex;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest, heapSize);
        }
    }

    private static void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private static ArrayList<Integer> extractAndHeapify() {
        int heapSize = heap.size();
        ArrayList<Integer> sortedArray = new ArrayList<>(heapSize);

        while (heapSize > 0) {
            sortedArray.add(0, heap.get(0));
            swap(0, heapSize - 1);
            heapSize--;
            heapify(0, heapSize);
        }

        return sortedArray;
    }

    private static void createMaxHeap(int[] unsortedArray) {
        heap.clear();
        for (int i = 0; i < unsortedArray.length; i++) {
            heap.add(unsortedArray[i]);
            int currentIndex = heap.size() - 1;
            int parentIndex = (currentIndex - 1) / 2;
            while (currentIndex > 0 && heap.get(currentIndex) > heap.get(parentIndex)) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex; // Moving forward
                parentIndex = (currentIndex - 1) / 2;
            }
        }
    }

    public static void main(String[] args) {
        int[] unsortedArray = {10, 5, 7, 0, 80, 3};
        createMaxHeap(unsortedArray);
        System.out.println("Max Heap: " + heap);
        ArrayList<Integer> sortedArray = extractAndHeapify();
        System.out.println("Sorted Array: " + sortedArray);
    }
}
