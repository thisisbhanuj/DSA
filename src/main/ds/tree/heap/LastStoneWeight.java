package ds.tree.heap;

import java.util.ArrayList;

public class LastStoneWeight {
    private static ArrayList<Integer> heap;

    LastStoneWeight() {
        heap = new ArrayList<>();
    }

    private static void heapify(int index, int heapSize) {
        int largest = index;
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;

        if (leftChildIndex < heapSize && heap.get(leftChildIndex) > heap.get(largest)) {
            largest = leftChildIndex;
        }

        if (rightChildIndex < heapSize && heap.get(rightChildIndex) > heap.get(largest)) {
            largest = rightChildIndex;
        }

        if (largest != index) {
            swap(index, largest);
            heapify(largest, heapSize);
        }
    }

    private static void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private static void createMaxHeap(int[] stones) {
        for (int stone : stones) {
            heap.add(stone);
            int currentIndex = heap.size() - 1;
            int parentIndex = (currentIndex - 1) / 2;
            while (currentIndex > 0 && heap.get(currentIndex) > heap.get(parentIndex)) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
                parentIndex = (currentIndex - 1) / 2;
            }
        }
    }

    private static int smashStones() {
        while (heap.size() > 1) {
            int stone1 = heap.get(0);
            swap(0, heap.size() - 1);
            heap.remove(heap.size() - 1);
            heapify(0, heap.size());

            int stone2 = heap.get(0);
            int diff = stone1 - stone2;

            if (diff > 0) {
                heap.set(0, diff);
                heapify(0, heap.size());
            } else {
                heap.remove(0);
            }
        }

        if (heap.size() == 1) {
            return heap.get(0);
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        int[] stones = {5, 8, 2, 9, 6};
        LastStoneWeight lastStoneWeight = new LastStoneWeight();
        createMaxHeap(stones);
        int lastRemainingStone = smashStones();
        System.out.println("Weight of the last remaining stone: " + lastRemainingStone);
    }
}
