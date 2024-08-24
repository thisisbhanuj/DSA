package main.ds.tree.heap;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class LastStoneWeight {
    public static ArrayList<Integer> heap = new ArrayList<>();

    static boolean isLeaf(int position) {
        return position >= heap.size() / 2 && position < heap.size();
    }

    static int leftStone(int position) {
        return 2 * position + 1;
    }

    static int rightStone(int position) {
        return 2 * position + 2;
    }

    static int parentStone(int position) {
        return (position - 1) / 2;
    }

    static void swap(int heaviest, int position) {
        int temp = heap.get(heaviest);
        heap.set(heaviest, heap.get(position));
        heap.set(position, temp);
    }

    static void heapify(ArrayList<Integer> stonesHeap, int stone) {
        if (isLeaf(stone)) return;

        int leftChildStone = leftStone(stone);
        int rightChildStone = rightStone(stone);

        int heaviest = stone;

        if (leftChildStone < heap.size() && heap.get(leftChildStone) > heap.get(heaviest)) {
            heaviest = leftChildStone;
        }

        if (rightChildStone < heap.size() && heap.get(rightChildStone) > heap.get(heaviest)) {
            heaviest = rightChildStone;
        }

        if (heaviest != stone) {
            swap(heaviest, stone);
            heapify(stonesHeap, heaviest);
        }
    }

    static void createStonesHeap(int[] sack) {
        for (int stone : sack) {
            heap.add(stone);
        }

        for (int stone = parentStone(heap.size() - 1); stone >= 0; stone--) {
            heapify(heap, stone);
        }
    }

    static void smash() {
        while (heap.size() > 1) {
            int largest = heap.get(0);
            swap(0, heap.size() - 1); // Move the largest to the end
            heap.remove(heap.size() - 1); // Remove the largest
            heapify(heap, 0); // Re-heapify

            int secondLargest = heap.get(0);
            swap(0, heap.size() - 1); // Move the second largest to the end
            heap.remove(heap.size() - 1); // Remove the second largest
            heapify(heap, 0); // Re-heapify

            if (largest != secondLargest) {
                heap.add(largest - secondLargest); // Add the difference back to the heap
                heapify(heap, heap.size() - 1); // // Bubble up the newly added stone or heapify
                System.out.println("Smashed Heap : " + heap);
            } else {
                System.out.println("Dusted");
            }
        }
    }

    static void display() {
        for (int index = 0; index < heap.size() / 2; index++) {
            System.out.println("Parent : " + heap.get(index));

            int leftChildIndex = leftStone(index);
            if (leftChildIndex < heap.size()) {
                System.out.println("Left : " + heap.get(leftChildIndex));
            }

            int rightChildIndex = rightStone(index);
            if (rightChildIndex < heap.size()) {
                System.out.println("Right : " + heap.get(rightChildIndex));
            }

            System.out.println();
        }
    }

    /**
     * Using a PriorityQueue (Min-Heap) in Java is a more efficient and
     * cleaner way to solve the Last Stone Weight problem.
     * Although a min-heap naturally returns the smallest element,
     * we can easily simulate a max-heap by storing the negative values of the stones.
     *
     * Time Complexity:
     * ***************
     * The time complexity of this solution is O(nlogn), where n is the number of stones.
     * This is because inserting and removing elements from a heap takes O(logn) time,
     * and this operation could be performed n−1 times in the worst case.
     *
     * Space Complexity:
     * ****************
     * The space complexity is O(n), where n is the number of stones,
     * since all stones are stored in the heap.
     */
    public static int lastStoneWeightUsingPQ(int[] stones) {
        // By using the comparator (a, b) -> b - a, we simulate a max-heap.
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

        // Add all stones to the max-heap
        for (int stone : stones) {
            maxHeap.add(stone);
        }

        // Smash stones until only one or none is left
        while (maxHeap.size() > 1) {
            int stone1 = maxHeap.poll(); // Retrieves and removes the head of the queue (which is the largest stone in our case).
            int stone2 = maxHeap.poll(); // Second heaviest stone

            if (stone1 != stone2) {
                maxHeap.add(stone1 - stone2); // Add the difference back to the heap
            }
        }

        // Return the last remaining stone or 0 if none are left
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }

    public static void main(String[] args) {
        int[] sack = {2, 7, 4, 5, 8, 1};
        createStonesHeap(sack);
        display();
        smash();
        System.out.println("Last remaining stone weight: " + (heap.isEmpty() ? 0 : heap.get(0)));

        int result = lastStoneWeightUsingPQ(sack);
        System.out.println("Last remaining stone weight via PQ: " + result);
    }
}
