    package main.ds.leetcode.medium.top_k_frequent;

    import java.util.*;

    /*
    This approach has a time complexity of O(n log k),
    where "n" is the size of the input array and "k" is the desired number of top-k frequent elements.
    The time complexity arises from building the max heap (PriorityQueue)
    with a maximum size of "k" elements and extracting the top-k elements.
    */
    public class UsingMaxHeap {
        public static void main(String[] args) {
            int[] array = {1, 2, 3, 2, 1, 1, 2, 1, 1, 2, 2, 3, 2, 5, 5, 5, 5, 5, 5, 5, 55, 5, 5, 5, 5, 5, 6, 3, 2};
            int heapSize = 3; // Change k to the desired value for top-k frequent elements

            Map<Integer, Integer> map = new HashMap<>();
            for (int value : array) {
                map.put(value, map.getOrDefault(value, 0) + 1);
            }

            PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(heapSize, (a, b) -> b.getValue() - a.getValue());
            for(Map.Entry<Integer, Integer> entry : map.entrySet()){
                maxHeap.offer(entry);
            }

            // Extract the top-k frequent elements from the sorted list
            List<Integer> topKFrequent = new ArrayList<>();
            for (int i = 0; i < heapSize && i < maxHeap.size(); i++) {
                topKFrequent.add(maxHeap.poll().getKey());
            }

            System.out.println("Top-" + heapSize + " frequent elements: " + topKFrequent);
        }
    }
