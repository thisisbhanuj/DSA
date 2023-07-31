package ds.leetcode.medium.top_k_frequent;

import java.util.*;

/*
    The time complexity of this approach is O(n + k)
    because it involves iterating through the input array to count frequencies and
    then iterating through the frequency buckets (k iterations) to extract the top-k frequent elements.

    The space complexity is O(n + k) as well,
    where "n" is the size of the input array (used for the frequency map), and
    "k" is the size of the frequency buckets used to store the elements with different frequencies.
*/
public class UsingCountingSort {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 2, 1, 1, 2, 1, 1, 2, 2, 3, 2, 5, 5, 5, 5, 5, 5, 5, 55, 5, 5, 5, 5, 5, 6, 3, 2};
        int k = 3; // Change k to the desired value for top-k frequent elements

        Map<Integer, Integer> map = new HashMap<>();
        for (int value : array) {
            map.put(value, map.getOrDefault(value, 0) + 1);
        }

        int maxFrequency = 0;
        for (int frequency : map.values()) {
            maxFrequency = Math.max(maxFrequency, frequency);
        }

        List<List<Integer>> frequencyBuckets = new ArrayList<>(maxFrequency + 1);
        for (int i = 0; i <= maxFrequency; i++) {
            frequencyBuckets.add(new ArrayList<>());
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int value = entry.getKey();
            int frequency = entry.getValue();
            frequencyBuckets.get(frequency).add(value);
        }

        List<Integer> topKFrequent = new ArrayList<>();
        for (int i = maxFrequency; i >= 0 && topKFrequent.size() < k; i--) {
            List<Integer> bucket = frequencyBuckets.get(i);
            topKFrequent.addAll(bucket);
        }

        System.out.println("Top-" + k + " frequent elements: " + topKFrequent);
    }
}
