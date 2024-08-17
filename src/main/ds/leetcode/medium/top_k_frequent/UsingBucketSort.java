package main.ds.leetcode.medium.top_k_frequent;

import java.util.*;

/*
This approach allows you to save elements with the same frequency in the same bucket,
making it more space-efficient. The time complexity remains O(n + k),
where "n" is the size of the input array and "k" is the value of the top-k frequent elements we want to find.
The space complexity is also O(n + k) as we need to store elements in the buckets.
*/
public class UsingBucketSort {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 2, 1, 1, 2, 1, 1, 2, 2, 3, 2, 5, 5, 5, 5, 5, 5, 5, 55, 5, 5, 5, 5, 5, 6, 3, 2};
        int k = 3; // Change k to the desired value for top-k frequent elements

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int value : array) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        // Create a Vector of Lists to hold elements with the same frequency in the same bucket
        int maxFrequency = Collections.max(frequencyMap.values());
        Vector<List<Integer>> bucket = new Vector<>(maxFrequency + 1);
        for (int i = 0; i <= maxFrequency; i++) {
            bucket.add(new ArrayList<>());
        }

        // Add elements to the corresponding bucket based on their frequency
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int element = entry.getKey();
            int frequency = entry.getValue();
            bucket.get(frequency).add(element);
        }

        // Extract the top-k frequent elements from the buckets
        List<Integer> topKFrequent = new ArrayList<>();
        for (int i = maxFrequency; i >= 0 && topKFrequent.size() < k; i--) {
            List<Integer> elementsWithFrequency = bucket.get(i);
            if (!elementsWithFrequency.isEmpty()) {
                topKFrequent.addAll(elementsWithFrequency);
            }
        }

        System.out.println("Top-" + k + " frequent elements: " + topKFrequent);
    }
}
