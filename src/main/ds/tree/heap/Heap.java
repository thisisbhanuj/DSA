package ds.tree.heap;

import java.util.ArrayList;

public class Heap {
    private static ArrayList<Integer> heap;

    public Heap (int[] unSortedArray) {
        this.heap = new ArrayList<Integer>();
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
        return position > heap.size() / 2 && position <= heap.size();
    }

    public   static void swap (ArrayList<Integer> list, Integer current, Integer largest) {
        Integer temp = list.get(current);
        list.set(current, list.get(largest));
        list.set(largest, temp);
    }

    public static void insert(Integer value){
        heap.add(value);
        Integer current = heap.size() - 1;
        while ( value > heap.get(parentIndex(current)) && current >= 0){
            swap(heap, current, parentIndex(current));
            current = parentIndex(current);
        }
    }

    public static void maxHeapify(ArrayList<Integer> list, Integer current) {
        if (isLeaf(current)) return;

        int leftChildIndex = leftChildIndex(current);
        int rightChildIndex = rightChildIndex(current);
        Integer largest = current;

        if (leftChildIndex < list.size() && list.get(leftChildIndex) > list.get(current)) {
            largest = leftChildIndex;
        }
        if (rightChildIndex < list.size() && list.get(rightChildIndex) > list.get(largest)) {
            largest = rightChildIndex;
        }

        if (current != largest) {
            swap(list, current, largest);
            maxHeapify(list, largest);
        }

    }

    public static void minHeapify(ArrayList<Integer> list, Integer current) {
        if (isLeaf(current)) return;

        int leftChildIndex = leftChildIndex(current);
        int rightChildIndex = rightChildIndex(current);
        Integer smallest = current;

        if (leftChildIndex < list.size() && list.get(leftChildIndex) < list.get(current)) {
            smallest = leftChildIndex;
        }
        if (rightChildIndex < list.size() && list.get(rightChildIndex) < list.get(smallest)) {
            smallest = rightChildIndex;
        }

        if (smallest != current) {
            swap(list, current, smallest);
            minHeapify(list, smallest);
        }
    }

    public static Integer extractHighestPriority() {
        int highestPriority = heap.get(0);
        int endNodeIndex = heap.size() - 1;
        heap.set(0, heap.get(endNodeIndex));
        heap.remove(endNodeIndex);
        maxHeapify(heap, 0);
        return highestPriority;
    }

    public static void print() {
        for (int i = 0; i < heap.size() / 2; i++) {
            System.out.println();
            System.out.println( "Parent Index : " + heap.get(i) );

            if (leftChildIndex(i) < heap.size()) {
                System.out.println( "Left Index : " + leftChildIndex(i) );
            }
            if (rightChildIndex(i) < heap.size()) {
                System.out.println( "Right Index : " + rightChildIndex(i) );
            }
        }
        System.out.println();
    }

    public ArrayList<Integer> getMaxHeap(){
        return this.heap;
    }

    public static void main(String[] args) {
        heap = new ArrayList<>();
        insert(0);
        insert(1);
        insert(2);
        insert(3);
        insert(4);
        insert(5);
        insert(6);
        System.out.println("Initial : " + heap);

        print();

        maxHeapify(heap, 0);
        System.out.println("After Max Heap : " + heap);

        minHeapify(heap, 0);
        System.out.println("After Min Heap : " + heap);

        extractHighestPriority();
        print();
    }
}
